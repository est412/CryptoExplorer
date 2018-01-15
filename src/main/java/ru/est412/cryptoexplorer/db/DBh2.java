package ru.est412.cryptoexplorer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by e.tukhvatullin on 26.11.2017.
 */
public class DBh2 {

    private static final String URL = "jdbc:h2:mem:ce";
    private static final String OPTS = ";DB_CLOSE_DELAY=-1";
    private static final String SCRIPT = ";INIT=runscript from 'classpath:db/tables.sql'";
    private static final String CONN_STRING = URL + OPTS + SCRIPT;
    private static final boolean IS_AUTO_COMMIT = false;

    private static Map<String, PreparedStatement> preparedStatements = new HashMap<>();
    private static Connection conn;

    private DBh2() throws SQLException {
    }

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(CONN_STRING);
        }
        conn.setAutoCommit(IS_AUTO_COMMIT);
        return conn;
    }

    public static PreparedStatement prepareStatement(String query) throws SQLException {
        Connection conn = getConnection();
        return conn.prepareStatement(query);
    }

    public static PreparedStatement getBufferedPreparedStatement(String query) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = preparedStatements.get(query);
        if (ps == null || ps.isClosed()) {
            ps = conn.prepareStatement(query);
            preparedStatements.put(query, ps);
        }
        return ps;
    }

}
