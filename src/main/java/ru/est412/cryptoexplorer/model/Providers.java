package ru.est412.cryptoexplorer.model;

import ru.est412.cryptoexplorer.db.DBh2;
import ru.est412.cryptoexplorer.db.Filter;
import ru.est412.cryptoexplorer.db.Requests;

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
public class Providers {

    private static SortedMap<String, Long> nameToId = new TreeMap<>();
    private static SortedMap<Long, String> idToName = new TreeMap<>();

    private Providers() {
    }

    public static void load() throws SQLException {

        nameToId.clear();
        idToName.clear();

        PreparedStatement ps = DBh2.getBufferedPreparedStatement(PROVIDER_DELETE_ALL);
        ps.execute();
        ps.getConnection().commit();


        // Уникальность и сортировка
        for (Provider provider : Security.getProviders()) {
            nameToId.put(provider.getName(), 0L);
        }

        ps = DBh2.getBufferedPreparedStatement(Requests.PROVIDER_INSERT);
        for (String provider : nameToId.keySet()) {
            ps.setString(1, provider);
            ps.setDouble(2, Security.getProvider(provider).getVersion());
            ps.setString(3, Security.getProvider(provider).getClass().getName());
            ps.setString(4, Security.getProvider(provider).getInfo());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            nameToId.put(provider, rs.getLong(1));
            idToName.put(rs.getLong(1), provider);
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
            ps = DBh2.getBufferedPreparedStatement(PROVIDER_SELECT_ALL);
        } else if (Filter.getProviderId() >= 0) {
            ps = DBh2.getBufferedPreparedStatement(PROVIDER_SELECT_ONE);
            ps.setLong(1, Filter.getProviderId());
        } else {
            ps = DBh2.getBufferedPreparedStatement(PROVIDER_SELECT);
            ps.setLong(1, Filter.getAlgorithmId());
            ps.setLong(2, Filter.getAlgorithmId());
            ps.setLong(3, Filter.getEngineId());
            ps.setLong(4, Filter.getEngineId());
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

        private long id;
        private String name;
        private double version;
        private String className;
        private String info;

        public Entity() {
        }

        public Entity(long id, String name, double version, String className, String info) {
            this.id = id;
            this.name = name;
            this.version = version;
            this.className = className;
            this.info = info;
        }

        public Entity(ResultSet rs) throws SQLException {
            this.id = rs.getInt(1);
            this.name = rs.getString(2);
            this.version = rs.getDouble(3);
            this.className = rs.getString(4);
            this.info = rs.getString(5);
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

        public double getVersion() {
            return version;
        }

        public void setVersion(double version) {
            this.version = version;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String toString() {
            return name + " " + version;
        }

    }
}
