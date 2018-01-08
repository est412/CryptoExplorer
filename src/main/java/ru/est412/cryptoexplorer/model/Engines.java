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

        PreparedStatement ps = DBh2.getPreparedStatement(ENGINE_DELETE_ALL);
        ps.execute();
        ps.getConnection().commit();

        // Уникальность и сортировка
        for (Provider provider : Security.getProviders()) {
            for (Provider.Service service : provider.getServices()) {
                nameToId.put(service.getType(), 0L);
            }
        }

        ps = DBh2.getPreparedStatement(ENGINE_INSERT);
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
        PreparedStatement ps = DBh2.getPreparedStatement(buldRequest());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            entities.add(new Entity(rs));
        }
        return entities;
    }

    public static String getName(long id) {
        return idToName.get(id);
    }

    public static long getId(String name) {
        return nameToId.get(name);
    }

    public static String buldRequest() {
        StringBuilder sb = new StringBuilder(ENGINE_SELECT_ALL);
        if (!Filter.isEmpty()) {
            sb.append(", ").append(FROM_SERVICE);
            if (Filter.getAlgorithmId() >= 0) sb.append(", ").append(FROM_ALGORITHM);
            if (Filter.getProviderId() >= 0) sb.append(", ").append(FROM_PROVIDER);
            if (Filter.getServiceClassId() >= 0) sb.append(", ").append(FROM_SERVICE_CLASS);

            sb.append(" where ");
            sb.append(WHERE_ENGINE_REL);
            if (Filter.getAlgorithmId() >= 0) sb.append(" and ").append(WHERE_ALGORITHM_REL).append(" and ").append(WHERE_ALGORITHM_EQ).append(Filter.getAlgorithmId());
            if (Filter.getProviderId() >= 0) sb.append(" and ").append(WHERE_PROVIDER_REL).append(" and ").append(WHERE_PROVIDER_EQ).append(Filter.getProviderId());
            if (Filter.getServiceClassId() >= 0) sb.append(" and ").append(WHERE_SERVICE_CLASS_REL).append(" and ").append(WHERE_SERVICE_CLASS_EQ).append(Filter.getServiceClassId());
        }
        sb.append(" ").append(ORDER_BY_DEFAULT);
        return sb.toString();
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
