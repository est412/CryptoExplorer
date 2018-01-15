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
public class Engines {

    private static SortedMap<String, Long> nameToId = new TreeMap<>();
    private static SortedMap<Long, String> idToName = new TreeMap<>();

    private Engines() {
    }

    public static void load() throws SQLException {
        nameToId.clear();
        idToName.clear();

        PreparedStatement ps = DBh2.getBufferedPreparedStatement(ENGINE_DELETE_ALL);
        ps.execute();
        ps.getConnection().commit();

        // Уникальность и сортировка
        for (Provider provider : Security.getProviders()) {
            for (Provider.Service service : provider.getServices()) {
                nameToId.put(service.getType(), 0L);
            }
        }

        ps = DBh2.getBufferedPreparedStatement(ENGINE_INSERT);
        for (String engine : nameToId.keySet()) {
            ps.setString(1, engine);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            nameToId.put(engine, rs.getLong(1));
            idToName.put(rs.getLong(1), engine);
        }
        ps.getConnection().commit();
    }

    public static List<Entity> getEntities() throws SQLException {
        List<Entity> entities = new ArrayList<>();
        PreparedStatement ps = prepareStatement();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            entities.add(new Entity(rs));
        }
        //rs.close();
        //ps.close();
        return entities;
    }

    public static PreparedStatement prepareStatement() throws SQLException {
        PreparedStatement ps;
        if (Filter.isEmpty()) {
            ps = DBh2.getBufferedPreparedStatement(ENGINE_SELECT_ALL);
        } else if (Filter.getEngineId() >= 0) {
            ps = DBh2.getBufferedPreparedStatement(ENGINE_SELECT_ONE);
            ps.setLong(1, Filter.getEngineId());
        } else {
            ps = DBh2.getBufferedPreparedStatement(ENGINE_SELECT);
            ps.setLong(1, Filter.getAlgorithmId());
            ps.setLong(2, Filter.getAlgorithmId());
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

    /**
     * Created by e.tukhvatullin on 26.11.2017.
     */
    public static class Entity {

        private static final int ID_COLUMN = 1;

        private long id;
        private String name;

        public Entity() {
        }

        public Entity(ResultSet rs) throws SQLException {
            this.id = rs.getInt(1);
            this.name = rs.getString(2);
        }

        public Entity(ResultSet rs, String name) throws SQLException {
            rs.next();
            rs.getLong(ID_COLUMN);
            this.name = name;
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
