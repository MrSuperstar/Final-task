package main.java.com.task.model.person;

import main.java.com.task.model.therapy.Medicament;
import main.java.com.task.model.therapy.Procedure;

public interface MedicalEmployee {
    enum Status { Nurse, Doctor }
    Status getStatus();
    void appointProcedure(Patient patient, Procedure procedure);
    void appointMedicament(Patient patient, Medicament medicament);
    Human getHuman();
}
