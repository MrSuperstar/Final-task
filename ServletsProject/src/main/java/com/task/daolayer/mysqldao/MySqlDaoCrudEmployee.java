package main.java.com.task.daolayer.mysqldao;

import main.java.com.task.daolayer.EmployeeCrud;
import main.java.com.task.daolayer.WriteInfo;
import main.java.com.task.daolayer.configure.BaseConnectionPool;
import main.java.com.task.daolayer.configure.ConfigurationManager;
import main.java.com.task.daolayer.configure.ConnectionPool;
import main.java.com.task.model.person.*;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Implement CRUD operations for an employee entity.
 */
public class MySqlDaoCrudEmployee implements EmployeeCrud {

    /* A block of constants */
    private static final String EMPLOYEE_BY_ID = "sql.query.select.employeeById";
    private static final String SELECT_ALL_EMPLOYEES = "sql.query.select.employees";
    private static final String UPDATE_EMPLOYEE = "sql.query.update.employee";
    private static final String DELETE_EMPLOYEE = "sql.query.resignation.employee";
    private static final String INSERT_EMPLOYEE = "sql.query.insert.employee";
    private static final String DEFAULT_POSITION = "DOCTOR";


    ConfigurationManager manager = ConfigurationManager.getInstance();
    BaseConnectionPool connectionPool = ConnectionPool.getInstance();
    Statement statement = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    @Override
    public MedicalEmployee getById(int id) {
        MedicalEmployee employee = null;

        try {
            Connection connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(manager.getDataByKey(EMPLOYEE_BY_ID));
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
                connectionPool.putBack(connection);
            }
        } catch (SQLException | IOException e) {
            WriteInfo.writeErrorMessage(e.getMessage());
        }
        if (employee != null) {
            WriteInfo.writeInfoMessage("Information about the employee( : " + employee.toString() + ") successfully received");
        }
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
        } catch (IOException | SQLException e) {
            WriteInfo.writeErrorMessage(e.getMessage());
        }

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
        } catch (SQLException | IOException e) {
            WriteInfo.writeErrorMessage(e.getMessage());
        }

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
        } catch (SQLException | IOException e) {
            WriteInfo.writeErrorMessage(e.getMessage());
        }

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
        } catch (SQLException | IOException e) {
            WriteInfo.writeErrorMessage(e.getMessage());
        }

        return result == 1;
    }
}