package ru.est412.cryptoexplorer.model;

import ru.est412.cryptoexplorer.db.DBh2;

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
public class Services {

    private Services() {
    }

    public static void load() throws SQLException {
        Providers.load();
        Engines.load();
        Algorithms.load();
        ServiceClasses.load();

        PreparedStatement ps = DBh2.getBufferedPreparedStatement(SERVICE_DELETE_ALL);
        ps.execute();
        ps = DBh2.getBufferedPreparedStatement(ALIAS_DELETE_ALL);
        ps.execute();
        ps.getConnection().commit();

        PreparedStatement psS = DBh2.getBufferedPreparedStatement(SERVICE_INSERT);
        PreparedStatement psA = DBh2.getBufferedPreparedStatement(ALIAS_INSERT);
        for (Provider provider : Security.getProviders()) {
            for (Provider.Service service : provider.getServices()) {
                psS.setLong(1, Providers.getId(provider.getName()));
                psS.setLong(2, Engines.getId(service.getType()));
                psS.setLong(3, ServiceClasses.getId(service.getClassName()));
                psS.executeUpdate();
                ResultSet rs = psS.getGeneratedKeys();
                rs.next();
                long serviceId = rs.getInt(1);
                psA.setLong(1, serviceId);
                psA.setLong(2, Algorithms.getId(service.getAlgorithm()));
                psA.setLong(3, -1);
                psA.executeUpdate();
                String aliasPrefix = new StringBuilder(Algorithms.ALIAS_PREFIX).append(service.getType()).append(".").toString();
                for (Map.Entry<Object, Object> entry : provider.entrySet()) {
                    String key = (String) entry.getKey();
                    if (key.startsWith(aliasPrefix)) {
                        // alias found
                        String alias = key.replace(aliasPrefix, "");
                        psA.setLong(1, serviceId);
                        psA.setLong(2, Algorithms.getId(alias));
                        psA.setLong(3, Algorithms.getId(service.getAlgorithm()));
                        psA.executeUpdate();
                    }
                }
            }
        }
        psA.getConnection().commit();
    }

}
