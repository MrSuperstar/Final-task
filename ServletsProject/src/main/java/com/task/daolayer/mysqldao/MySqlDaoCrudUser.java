package main.java.com.task.daolayer.mysqldao;

import main.java.com.task.daolayer.UserCrud;
import main.java.com.task.daolayer.configure.BaseConnectionPool;
import main.java.com.task.daolayer.configure.ConfigurationManager;
import main.java.com.task.daolayer.configure.ConnectionPool;
import main.java.com.task.model.person.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Collection;

public class MySqlDaoCrudUser implements UserCrud {

    /* A block of constants */
    private static final Logger LOGGER = Logger.getLogger(MySqlDaoCrudUser.class);
    private static final String USER_BY_ID = "sql.query.select.getUserById";
    private static final String AUTHENTICATION_USER = "sql.query.select.authentication";
    private static final String REGISTRATION_USER = "sql.query.update.employee";

    ConfigurationManager manager = ConfigurationManager.getInstance();
    BaseConnectionPool connectionPool = ConnectionPool.getInstance();
    Statement statement = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    @Override
    public User getUserById(int id) {
        User user = null;

        try {
            Connection connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(manager.getDataByKey(USER_BY_ID));
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            user = readUserData(resultSet);
            connectionPool.putBack(connection);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.info("The function 'getById' was completed successfully.");
        return user;
    }

    @Override
    public User authentication(String login, String password) {
        User user = null;

        try {
            Connection connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(manager.getDataByKey(AUTHENTICATION_USER));
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            user = readUserData(resultSet);
            connectionPool.putBack(connection);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.info("The function 'getById' was completed successfully.");
        return user;
    }

    private User readUserData(ResultSet set) {
        User user = null;
        try {
            while (set.next()) {
                int id = resultSet.getInt(1);
                String login = resultSet.getString(2);
                String name = resultSet.getString(4);
                Gender gender = Gender.valueOf(resultSet.getString(5).toUpperCase());
                User.Position position = User.Position.valueOf(resultSet.getString(6).toUpperCase());

                user = new User(name, gender);
                user.setLogin(login);
                user.setPosition(position);
                user.setId(id);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return user;
    }

    @Override
    public void registration(User user) {

    }

    @Override
    public Collection<User> select() {
        return null;
    }

    @Override
    public boolean update(User o) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean insert(User o) {
        return false;
    }
}
