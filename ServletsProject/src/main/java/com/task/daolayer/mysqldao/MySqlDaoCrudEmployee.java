package main.java.com.task.daolayer.mysqldao;

import main.java.com.task.daolayer.EmployeeCrud;
import main.java.com.task.daolayer.configure.BaseConnectionPool;
import main.java.com.task.daolayer.configure.ConfigurationManager;
import main.java.com.task.daolayer.configure.ConnectionPool;
import main.java.com.task.model.person.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Implement CRUD operations for an employee entity.
 */
public class MySqlDaoCrudEmployee implements EmployeeCrud {

    /* A block of constants */
    private static final Logger LOGGER = Logger.getLogger(MySqlDaoCrudEmployee.class);
    private static final String EMPLOYEE_BY_ID = "sql.query.select.employeeById";
    private static final String SELECT_ALL_EMPLOYEES = "sql.query.select.employees";
    private static final String UPDATE_EMPLOYEE = "sql.query.update.employee";
    private static final String DELETE_EMPLOYEE = "sql.query.resignation.employee";
    private static final String INSERT_EMPLOYEE = "sql.query.insert.employee";
    private static final String LOGIN_EMPLOYEE = "sql.query.select.employeeByUserId";
    private static final String DEFAULT_POSITION = "DOCTOR";

    ConfigurationManager manager = ConfigurationManager.getInstance();
    BaseConnectionPool connectionPool = ConnectionPool.getInstance();
    Statement statement = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    @Override
    public MedicalEmployee getById(int id) {
        return getMedicalEmployee(id, EMPLOYEE_BY_ID);
    }

    private MedicalEmployee getByUserId(int userId) {
        return getMedicalEmployee(userId, LOGIN_EMPLOYEE);
    }

    @Override
    public MedicalEmployee login(String login, String password) {
        MySqlDaoCrudUser mySqlDaoCrudUser = new MySqlDaoCrudUser();
        User user = mySqlDaoCrudUser.authentication(login, password);

        MedicalEmployee employee = getByUserId(user.getId());

        LOGGER.info("The function 'login' was completed successfully.");
        return employee;
    }

    private MedicalEmployee getMedicalEmployee(int id, String loginEmployee) {
        MedicalEmployee employee = null;

        try {
            Connection connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(manager.getDataByKey(loginEmployee));
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString(2);
                String gender = resultSet.getString(3);
                String position = resultSet.getString(4);

                employee = DEFAULT_POSITION.equals(position.toUpperCase())
                        ? new Doctor(name, Gender.valueOf(gender.toUpperCase()))
                        : new Nurse(name, Gender.valueOf(gender.toUpperCase()));

                employee.getHuman().setId(id);
            }
            connectionPool.putBack(connection);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.info("The function 'getById' was completed successfully.");
        return employee;
    }

    @Override
    public Collection<MedicalEmployee> select() {
        Collection<MedicalEmployee> employees = new ArrayList<>();

        try {
            Connection connection = connectionPool.retrieve();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(manager.getDataByKey(SELECT_ALL_EMPLOYEES));

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String gender = resultSet.getString(3);
                String position = resultSet.getString(4);

                if (DEFAULT_POSITION.equals(position.toUpperCase())) {
                    Doctor doctor = new Doctor(name, Gender.valueOf(gender.toUpperCase()));
                    doctor.setId(id);
                    employees.add(doctor);
                } else {
                    Nurse nurse = new Nurse(name, Gender.valueOf(gender.toUpperCase()));
                    nurse.setId(id);
                    employees.add(nurse);
                }
            }

            connectionPool.putBack(connection);
        } catch(SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("The function 'select' was completed successfully.");
        return employees;
    }

    @Override
    public boolean update(MedicalEmployee o) {
        int result = 0;

        try {
            Connection connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(manager.getDataByKey(UPDATE_EMPLOYEE));
            preparedStatement.setString(1, o.getStatus().toString());

            result = preparedStatement.executeUpdate();
            connectionPool.putBack(connection);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("The function 'update' was completed successfully.");
        return result == 1;
    }

    @Override
    public boolean delete(int id) {
        int result = 0;

        try {
            Connection connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(manager.getDataByKey(DELETE_EMPLOYEE));
            preparedStatement.setInt(1, id);

            result = preparedStatement.executeUpdate();
            connectionPool.putBack(connection);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("The function 'delete' was completed successfully.");
        return result == 1;
    }

    @Override
    public boolean insert(MedicalEmployee o) {
        int result = 0;

        try {
            Connection connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(manager.getDataByKey(INSERT_EMPLOYEE));
            preparedStatement.setString(1, o.getHuman().getName());
            preparedStatement.setInt(2, o.getHuman().getGender().getCode());
            preparedStatement.setString(3, o.getStatus().toString());

            result = preparedStatement.executeUpdate();
            connectionPool.putBack(connection);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("The function 'insert' was completed successfully.");
        return result == 1;
    }
}
