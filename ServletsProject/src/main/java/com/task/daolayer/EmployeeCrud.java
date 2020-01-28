package main.java.com.task.daolayer;

import main.java.com.task.model.person.MedicalEmployee;

public interface EmployeeCrud extends DaoCrud<MedicalEmployee> {
    MedicalEmployee getById(int id);
    MedicalEmployee login(String login, String password);
}
