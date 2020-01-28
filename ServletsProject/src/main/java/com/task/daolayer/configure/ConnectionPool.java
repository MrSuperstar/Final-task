package main.java.com.task.daolayer.configure;

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

    private static final int DEFAULT_POOL_SIZE = 10;
    private static final ConnectionPool CONNECTION_POOL = new ConnectionPool();
    private List<Connection> available = new ArrayList<>();
    ConfigurationManager manager = ConfigurationManager.getInstance();

    public static ConnectionPool getInstance() {
        return CONNECTION_POOL;
    }

    private ConnectionPool() {
        try {
            Class.forName(manager.getDataByKey("db.driver"));
        } catch (Exception e) {

        }

        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            available.add(getConnection());
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

        if (available.size() > 0) {
            newConn = available.get(0);
            available.remove(0);

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
            available.add(c);
        }
    }
}
