package ru.est412.cryptoexplorer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static ru.est412.cryptoexplorer.db.Requests.*;

/**
 * Created by e.tukhvatullin on 26.11.2017.
 */
public class DBh2 {

    private static final String URL = "jdbc:h2:mem:ce";
    private static final String SCRIPT = ";INIT=runscript from 'classpath:db/tables.sql'";
    private static final boolean isAutoCommit = false;

    // TODO переделать концепцию ps
    private static Map<String, PreparedStatement> preparedStatements = new HashMap<>();
    private static Connection conn;

    private DBh2() {
    }

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL+SCRIPT);
        }
        conn.setAutoCommit(isAutoCommit);
        return conn;
    }

    public static PreparedStatement getPreparedStatement(String query) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = preparedStatements.get(query);
        if (ps == null || ps.isClosed()) {
            ps = conn.prepareStatement(query);
            preparedStatements.put(query, ps);
        }
        return ps;
    }

}
