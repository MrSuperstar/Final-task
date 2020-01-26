package main.java.com.task.daolayer;

public interface BaseFactory {
    EmployeeCrud getEmployeeDao();
    PatientCrud getPatientDao();
    TherapyCrud getTherapyDao();
}
