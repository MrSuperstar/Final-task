package main.java.com.task.model.person;

import main.java.com.task.model.therapy.Diagnose;
import main.java.com.task.model.therapy.Medicament;
import main.java.com.task.model.therapy.Operation;
import main.java.com.task.model.therapy.Procedure;

import java.util.Objects;

public class Doctor extends User implements Healing {
    private Status status;

    public Doctor(String name, Gender gender) {
        super(name, gender);
        status = Status.Doctor;
        setPosition(Position.EMPLOYEE);
    }

    @Override
    public void appointOperation(Patient patient, Operation operation) {
        patient.setOperation(operation);
    }

    @Override
    public void appointDiagnose(Patient patient, Diagnose diagnosis) {
        patient.setDiagnose(diagnosis);
    }

    @Override
    public void appointProcedure(Patient patient, Procedure procedure) {
        patient.setProcedure(procedure);
    }

    @Override
    public void appointMedicament(Patient patient, Medicament medicament) {
        patient.setMedicament(medicament);
    }

    @Override
    public Human getHuman() {
        return this;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("%s\nstatus: %s\n", super.toString(), getStatus());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        return status == doctor.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status);
    }
}
