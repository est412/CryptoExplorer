package ru.est412.cryptoexplorer;

import ru.est412.cryptoexplorer.db.DBh2;

import java.sql.*;

/**
 * Created by e.tukhvatullin on 25.11.2017.
 */
public class TestH2 {

    public static void Test1() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:ce;INIT=runscript from 'classpath:tables.sql'");
        conn.setAutoCommit(true);
        System.out.println(conn.getSchema());
        PreparedStatement ps = conn.prepareStatement("insert into engine set name = ?");
        ps.setString(1, "qqq");
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        System.out.println("Your latest emp no is: "+rs.getInt(1));
        conn.close();
    }

    public static void Test2() throws SQLException {
        Connection conn = DBh2.getConnection();
        conn.setAutoCommit(true);
        System.out.println(conn.getSchema());
        PreparedStatement ps = conn.prepareStatement("insert into engine set name = ?");
        ps.setString(1, "qqq");
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        System.out.println("Your latest emp no is: "+rs.getInt(1));
        //System.out.println("Your latest emp no is: "+rs.getString(2));
        conn.close();
    }

    public static void main(String[] args) throws SQLException {
        Test2();
    }

}
