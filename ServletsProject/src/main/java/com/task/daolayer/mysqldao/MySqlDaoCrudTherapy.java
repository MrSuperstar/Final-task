package main.java.com.task.daolayer.mysqldao;

import main.java.com.task.daolayer.TherapyCrud;
import main.java.com.task.daolayer.configure.BaseConnectionPool;
import main.java.com.task.daolayer.configure.ConfigurationManager;
import main.java.com.task.daolayer.configure.ConnectionPool;
import main.java.com.task.model.therapy.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlDaoCrudTherapy implements TherapyCrud {

    private static final Logger LOGGER = Logger.getLogger(MySqlDaoCrudTherapy.class);
    /* Block of constants */
    private static final String OPERATION_BY_ID = "sql.query.select.operationById";
    private static final String DIAGNOSE_BY_ID = "sql.query.select.diagnoseById";
    private static final String PROCEDURE_BY_ID = "sql.query.select.procedureById";
    private static final String MEDICAMENT_BY_ID = "sql.query.select.medicamentById";
    private static final String SELECT_THERAPY = "sql.query.select.therapy";

    ConfigurationManager manager = ConfigurationManager.getInstance();
    BaseConnectionPool connectionPool = ConnectionPool.getInstance();
    Statement statement = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    @Override
    public Collection<Therapy> select() {
        return null;
    }

    @Override
    public Therapy getById(int id, String table) {
        Therapy therapy = null;

        try {
            String query = manager.getDataByKey(getQueryByTableName(table));
            Connection connection = connectionPool.retrieve();

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int therapyId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                therapy = getTherapyByName(table);
                therapy.setId(therapyId);
                therapy.setName(name);
            }
            connectionPool.putBack(connection);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("The function 'getById' was completed successfully.");
        return therapy;
    }

    @Override
    public Collection<Therapy> select(String table) {
        Collection<Therapy> therapies = new ArrayList<>();

        try {
            Connection connection = connectionPool.retrieve();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format(manager.getDataByKey(SELECT_THERAPY), table));

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                Therapy therapy = getTherapyByName(table);
                therapy.setId(id);
                therapy.setName(name);

                therapies.add(therapy);
            }
            connectionPool.putBack(connection);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("The function 'select' was completed successfully.");
        return therapies;
    }


    @Override
    public boolean update(Therapy o) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean insert(Therapy o) {
        return false;
    }

    private static Therapy getTherapyByName(String name) {
        switch (name.toUpperCase()) {
            case "OPERATION": return new Operation();
            case "MEDICAMENT": return new Medicament();
            case "DIAGNOSE": return new Diagnose();
            case "PROCEDURE": return new Procedure();
        }
        return new Operation();
    }

    private static String getQueryByTableName(String name) {
        return getQuery(name);
    }

    private static String getQuery(String name) {
        switch (name.toUpperCase()) {
            case "OPERATION" : return MySqlDaoCrudTherapy.OPERATION_BY_ID;
            case "DIAGNOSE" : return MySqlDaoCrudTherapy.DIAGNOSE_BY_ID;
            case "MEDICAMENT" : return MySqlDaoCrudTherapy.MEDICAMENT_BY_ID;
            case "PROCEDURE" : return MySqlDaoCrudTherapy.PROCEDURE_BY_ID;
        }
        return null;
    }


}
