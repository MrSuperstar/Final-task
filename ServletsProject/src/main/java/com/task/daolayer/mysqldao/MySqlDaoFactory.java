package main.java.com.task.daolayer.mysqldao;

import main.java.com.task.daolayer.BaseFactory;
import main.java.com.task.daolayer.EmployeeCrud;
import main.java.com.task.daolayer.PatientCrud;
import main.java.com.task.daolayer.TherapyCrud;

public class MySqlDaoFactory implements BaseFactory {

    public EmployeeCrud employeeCrud = null;
    public PatientCrud patientCrud = null;
    public TherapyCrud therapyCrud = null;

    @Override
    public EmployeeCrud getEmployeeDao() {
        if (employeeCrud == null) employeeCrud = new MySqlDaoCrudEmployee();
        return employeeCrud;
    }

    @Override
    public PatientCrud getPatientDao() {
        if (patientCrud == null) patientCrud = new MySqlDaoCrudPatient();
        return patientCrud;
    }

    @Override
    public TherapyCrud getTherapyDao() {
        if (therapyCrud == null) therapyCrud = new MySqlDaoCrudTherapy();
        return therapyCrud;
    }
}
