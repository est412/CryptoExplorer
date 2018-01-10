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

        PreparedStatement ps = DBh2.getPreparedStatement(ALGORITHM_DELETE_ALL);
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

        ps = DBh2.getPreparedStatement(ALGORITHM_INSERT);
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
    // TODO закрывать rs и ps??? проверить на утечку памяти
    public static List<Entity> getEntities() throws SQLException {
        List<Entity> algorithmEntities = new ArrayList<>();
        PreparedStatement ps = DBh2.getPreparedStatement(buldRequest());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            algorithmEntities.add(new Entity(rs));
        }
        return algorithmEntities;
    }

    public static String getName(long id) {
        return idToName.get(id);
    }

    public static long getId(String name) {
        return nameToId.get(name);
    }

    public static String buldRequest() {
        StringBuilder sb = new StringBuilder(ALGORITHM_SELECT_ALL);
        if (Filter.getAlgorithmId() >= 0) {
            sb.append(" where ").append(WHERE_ALGORITHM_EQ).append(Filter.getAlgorithmId());
        } else if (!Filter.isEmpty()) {
            sb.append(", ").append(FROM_SERVICE);
            if (Filter.getEngineId() >= 0) sb.append(", ").append(FROM_ENGINE);
            if (Filter.getProviderId() >= 0) sb.append(", ").append(FROM_PROVIDER);
            if (Filter.getServiceClassId() >= 0) sb.append(", ").append(FROM_SERVICE_CLASS);

            sb.append(" where ");
            sb.append(WHERE_ALGORITHM_REL);
            if (Filter.getEngineId() >= 0) sb.append(" and ").append(WHERE_ENGINE_REL).append(" and ").append(WHERE_ENGINE_EQ).append(Filter.getEngineId());
            if (Filter.getProviderId() >= 0) sb.append(" and ").append(WHERE_PROVIDER_REL).append(" and ").append(WHERE_PROVIDER_EQ).append(Filter.getProviderId());
            if (Filter.getServiceClassId() >= 0) sb.append(" and ").append(WHERE_SERVICE_CLASS_REL).append(" and ").append(WHERE_SERVICE_CLASS_EQ).append(Filter.getServiceClassId());
        }
        sb.append(" ").append(ORDER_BY_DEFAULT);
        return sb.toString();
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
