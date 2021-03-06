package main.java.com.task.daolayer.mysqldao;

import main.java.com.task.daolayer.PatientCrud;
import main.java.com.task.daolayer.configure.BaseConnectionPool;
import main.java.com.task.daolayer.configure.ConfigurationManager;
import main.java.com.task.daolayer.configure.ConnectionPool;
import main.java.com.task.model.person.Gender;
import main.java.com.task.model.person.Patient;
import main.java.com.task.model.person.User;
import main.java.com.task.model.therapy.*;
import com.sun.istack.internal.Nullable;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Implement CRUD operations for an patient entity.
 */
public class MySqlDaoCrudPatient implements PatientCrud {

    private static final Logger LOGGER = Logger.getLogger(MySqlDaoCrudPatient.class);
    static MySqlDaoCrudTherapy mySqlDaoCrudTherapy = new MySqlDaoCrudTherapy();
    /* Block of constants */
    private static final String PATIENT_BY_ID = "sql.query.select.patientById";
    private static final String SELECT_ALL_PATIENTS = "sql.query.select.patients";
    private static final String UPDATE_PATIENT = "sql.query.update.patient";
    private static final String DELETE_PATIENT = "sql.query.release.patient";
    private static final String INSERT_PATIENT = "sql.query.insert.patient";
    private static final String LOGIN_PATIENT = "sql.query.select.patientByUserId";
    private static int result = 0;

    ConfigurationManager manager = ConfigurationManager.getInstance();
    BaseConnectionPool connectionPool = ConnectionPool.getInstance();
    Statement statement = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    @Nullable
    @Override
    public Patient getById(int id) {
        return getPatient(id, PATIENT_BY_ID);
    }

    @Override
    public Patient getPatientByUserId(int id) {
        return getPatient(id, LOGIN_PATIENT);
    }

    private Patient getPatient(int id, String loginPatient) {
        Patient patient = null;

        try {
            Connection connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(manager.getDataByKey(loginPatient));
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int patientId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String gender = resultSet.getString(3);
                patient = new Patient(name, Gender.valueOf(gender.toUpperCase()));
                patient.setDiagnose((Diagnose) getTherapyByTableName(resultSet.getInt(4), "Diagnose"));
                patient.setMedicament((Medicament) getTherapyByTableName(resultSet.getInt(5), "Medicament"));
                patient.setOperation((Operation) getTherapyByTableName(resultSet.getInt(6), "Operation"));
                patient.setProcedure((Procedure) getTherapyByTableName(resultSet.getInt(7), "Procedure"));

                patient.setId(patientId);
            }
            connectionPool.putBack(connection);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("The function 'getById' was completed successfully.");
        return patient;
    }

    @Override
    public Patient login(String login, String password) {
        MySqlDaoCrudUser mySqlDaoCrudUser = new MySqlDaoCrudUser();
        User user = mySqlDaoCrudUser.authentication(login, password);

        Patient patient = getPatientByUserId(user.getId());
        LOGGER.info("The function 'login' was completed successfully.");

        return patient;
    }

    @Override
    public Collection<Patient> select() {
        Collection<Patient> patients = new ArrayList<>();

        try {
            Connection connection = connectionPool.retrieve();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(manager.getDataByKey(SELECT_ALL_PATIENTS));

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String gender = resultSet.getString(3);
                Patient patient = new Patient(name, Gender.valueOf(gender.toUpperCase()));
                patient.setId(id);
                patient.setDiagnose((Diagnose) getTherapyByTableName(resultSet.getInt(4), "Diagnose"));
                patient.setMedicament((Medicament) getTherapyByTableName(resultSet.getInt(5), "Medicament"));
                patient.setOperation((Operation) getTherapyByTableName(resultSet.getInt(6), "Operation"));
                patient.setProcedure((Procedure) getTherapyByTableName(resultSet.getInt(7), "Procedure"));
                patients.add(patient);
            }
            connectionPool.putBack(connection);
        } catch(SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("The function 'select' was completed successfully.");
        return patients;
    }

    @Override
    public boolean update(Patient o) {
        try {
            Connection connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(manager.getDataByKey(UPDATE_PATIENT));
            preparedStatement.setInt(1, o.getId());
            preparedStatement.setInt(2, o.getOperation().getId());
            preparedStatement.setInt(3, o.getDiagnose().getId());
            preparedStatement.setInt(4, o.getMedicament().getId());
            preparedStatement.setInt(5, o.getProcedure().getId());

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
        try {
            Connection connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(manager.getDataByKey(DELETE_PATIENT));
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
    public boolean insert(Patient o) {
        try {
            Connection connection = connectionPool.retrieve();
            preparedStatement = connection.prepareStatement(manager.getDataByKey(INSERT_PATIENT));
            preparedStatement.setString(1, o.getName());
            preparedStatement.setInt(2, o.getGender().getCode());

            result = preparedStatement.executeUpdate();
            connectionPool.putBack(connection);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("The function 'insert' was completed successfully.");
        return result == 1;
    }

    private static Therapy getTherapyByTableName(int id, String name) {
        return mySqlDaoCrudTherapy.getById(id, name);
    }
}
