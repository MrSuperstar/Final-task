package main.java.com.task.daolayer.configure;

import java.sql.Connection;
import java.sql.SQLException;

public interface BaseConnectionPool {
    Connection retrieve() throws SQLException;
    void putBack(Connection connection);
}
