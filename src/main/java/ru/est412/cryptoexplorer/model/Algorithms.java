package ru.est412.cryptoexplorer.model;

import ru.est412.cryptoexplorer.db.DBh2;
import ru.est412.cryptoexplorer.db.Filter;

import java.security.Provider;
import java.security.Security;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static ru.est412.cryptoexplorer.db.Requests.*;

/**
 * Created by e.tukhvatullin on 26.11.2017.
 */
public class Algorithms {

    public static final String ALIAS_PREFIX = "Alg.Alias.";
    public static final String ALIAS_PREFIX_REGEX = "Alg\\.Alias\\.[^.]*\\.";

    private static SortedMap<String, Long> nameToId = new TreeMap<>();
    private static SortedMap<Long, String> idToName = new TreeMap<>();

    private Algorithms() {
    }

    public static void load() throws SQLException {
        nameToId.clear();
        idToName.clear();

        PreparedStatement ps = DBh2.getBufferedPreparedStatement(ALGORITHM_DELETE_ALL);
        ps.execute();
        ps.getConnection().commit();

        // Уникальность и сортировка
        for (Provider provider : Security.getProviders()) {
            for (Provider.Service service : provider.getServices()) {
                nameToId.put(service.getAlgorithm(), 0L);
             }
            // Алиасы
            for (Map.Entry<Object, Object> entry : provider.entrySet()) {
                String key = (String) entry.getKey();
                if (key.startsWith(ALIAS_PREFIX)) {
                    nameToId.put(key.replaceFirst(ALIAS_PREFIX_REGEX, ""), 0L);
                }
            }
        }

        ps = DBh2.getBufferedPreparedStatement(ALGORITHM_INSERT);
        for (String algorithm : nameToId.keySet()) {
            ps.setString(1, algorithm);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            nameToId.put(algorithm, rs.getLong(1));
            idToName.put(rs.getLong(1), algorithm);
        }
        ps.getConnection().commit();
    }

    // TODO убрать дублирование
    public static List<Entity> getEntities() throws SQLException {
        List<Entity> algorithmEntities = new ArrayList<>();
        PreparedStatement ps = prepareStatement();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            algorithmEntities.add(new Entity(rs));
        }
        //rs.close();
        //ps.close();
        return algorithmEntities;
    }

    public static PreparedStatement prepareStatement() throws SQLException {
        PreparedStatement ps;
        if (Filter.isEmpty()) {
            ps = DBh2.getBufferedPreparedStatement(ALGORITHM_SELECT_ALL);
        } else if (Filter.getAlgorithmId() >= 0) {
            ps = DBh2.getBufferedPreparedStatement(ALGORITHM_SELECT_ONE);
            ps.setLong(1, Filter.getAlgorithmId());
        } else {
            ps = DBh2.getBufferedPreparedStatement(ALGORITHM_SELECT);
            ps.setLong(1, Filter.getEngineId());
            ps.setLong(2, Filter.getEngineId());
            ps.setLong(3, Filter.getProviderId());
            ps.setLong(4, Filter.getProviderId());
            ps.setLong(5, Filter.getServiceClassId());
            ps.setLong(6, Filter.getServiceClassId());
        }
        return ps;
    }

    public static String getName(long id) {
        return idToName.get(id);
    }

    public static long getId(String name) {
        return nameToId.get(name);
    }


    // TODO оптимизировать везде классы Entity
    public static class Entity {

        private long id;
        private String name;

        public Entity() {
        }

        public Entity(long id, String name, double version, String className, String info) {
            this.id = id;
            this.name = name;
        }

        public Entity(ResultSet rs) throws SQLException {
            this.id = rs.getInt(1);
            this.name = rs.getString(2);
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }

    }

}
