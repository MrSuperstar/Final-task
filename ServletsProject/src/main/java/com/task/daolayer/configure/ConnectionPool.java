package main.java.com.task.daolayer.configure;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Connection pool implementation.
 * By default, twenty connections are created.
 */
public class ConnectionPool implements BaseConnectionPool{

    private static final Logger logger = Logger.getLogger(ConnectionPool.class);
    private static final int DEFAULT_POOL_SIZE = 10;
    private static final ConnectionPool connectionPool = new ConnectionPool();
    private List<Connection> availableConns = new ArrayList<>();
    ConfigurationManager manager = ConfigurationManager.getInstance();

    public static ConnectionPool getInstance() {
        return connectionPool;
    }

    private ConnectionPool() {
        try {
            Class.forName(manager.getDataByKey("db.driver"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            availableConns.add(getConnection());
        }
    }

    /**
     * Method to create a new connection.
     * @return Connection.
     */
    private Connection getConnection() {
        Connection conn = null;

        try {
            String url = manager.getDataByKey("db.url");
            String user = manager.getDataByKey("db.userName");
            String password = manager.getDataByKey("db.password");
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return conn;
    }

    /**
     * Method to get connection from pool.
     * @return
     * @throws SQLException
     */
    @Override
    public synchronized Connection retrieve() throws SQLException {
        Connection newConn = null;

        if (availableConns.size() > 0) {
            newConn = availableConns.get(0);
            availableConns.remove(0);

        } else {
            newConn = getConnection();
        }
        return newConn;
    }

    /**
     * The method to return the connection to the pool.
     * @param c
     * @throws NullPointerException
     */
    @Override
    public synchronized void putBack (Connection c) throws NullPointerException {
        if (c != null) {
            availableConns.add(c);
        }
    }
}
