package ru.est412.cryptoexplorer;

import org.h2.tools.Server;
import ru.est412.cryptoexplorer.db.DBh2;
import ru.est412.cryptoexplorer.db.Filter;
import ru.est412.cryptoexplorer.model.Services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static ru.est412.cryptoexplorer.db.Requests.*;

/**
 * Created by e.tukhvatullin on 20.12.2017.
 */
public class TestUniversalSelect {

    public static void main(String[] args) throws Exception {
        Services.load();
        Server.createTcpServer("-tcpAllowOthers").start();
        //Filter.setAlgorithmId(1);
        Filter.setEngineId(-1);
        Filter.setServiceClassId(-1);
        Filter.setProviderId(1);
        String REQ = "select distinct alg.id, alg.name " +
                "from algorithm alg, alias ali, service s, engine e, provider p, service_class sc " +
                "where alg.id = ali.algorithm_id and ali.service_id = s.id and e.id = s.engine_id " +
                "and (e.id = ? or ? < 0) and p.id = s.provider_id and (p.id = ? or ? < 0) and sc.id = s.class_id " +
                "and (sc.id = ? or ? < 0) order by name";

        PreparedStatement ps = DBh2.prepareStatement(REQ);
        ps.setLong(1, Filter.getEngineId());
        ps.setLong(2, Filter.getEngineId());
        ps.setLong(3, Filter.getProviderId());
        ps.setLong(4, Filter.getProviderId());
        ps.setLong(5, Filter.getServiceClassId());
        ps.setLong(6, Filter.getServiceClassId());

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getLong(1) + " " + rs.getString(2));
        }

    }

}
