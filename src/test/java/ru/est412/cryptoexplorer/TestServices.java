package ru.est412.cryptoexplorer;

import ru.est412.cryptoexplorer.db.DBh2;
import ru.est412.cryptoexplorer.model.Engines;
import ru.est412.cryptoexplorer.model.Services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by e.tukhvatullin on 20.12.2017.
 */
public class TestServices {

    public static void main(String[] args) throws Exception {
        Services.load();

        PreparedStatement ps = DBh2.getPreparedStatement("select * from service_class order by id");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getLong(1) + " " + rs.getString(2));
        }

        ps = DBh2.getPreparedStatement("select * from engine order by id");
        rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getLong(1) + " " + rs.getString(2));
        }

        ps = DBh2.getPreparedStatement("select * from provider order by id");
        rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getLong(1) + " " + rs.getString(2));
        }


        ps = DBh2.getPreparedStatement("select * from service order by provider_id");
        rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println("PR = "+rs.getLong(2) + " EN = " + rs.getString(3));
        }
    }

}
