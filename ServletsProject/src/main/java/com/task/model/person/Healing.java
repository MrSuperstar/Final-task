package main.java.com.task.model.person;

import main.java.com.task.model.therapy.Diagnose;
import main.java.com.task.model.therapy.Operation;

public interface Healing extends MedicalEmployee {
    void appointOperation(Patient patient, Operation operation);
    void appointDiagnose(Patient patient, Diagnose diagnosis);
}
