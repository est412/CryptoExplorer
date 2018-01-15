package ru.est412.cryptoexplorer;

import org.h2.tools.Server;

import java.sql.SQLException;

/**
 * Created by e.tukhvatullin on 11.01.2018.
 */
public class TestWithServer {

    public static void main(String[] args) throws SQLException {
        Server.createTcpServer("-tcpAllowOthers").start();
        App.main(new String[0]);
    }

}


