package main.java.com.task.daolayer;

import main.java.com.task.model.person.Patient;

public interface PatientCrud extends DaoCrud<Patient> {
    Patient getById(int id);
}
