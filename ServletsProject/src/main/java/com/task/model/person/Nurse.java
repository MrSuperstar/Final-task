package main.java.com.task.model.person;

import main.java.com.task.model.therapy.Medicament;
import main.java.com.task.model.therapy.Procedure;

import java.util.Objects;

public class Nurse extends Human implements MedicalEmployee {

    private Status status;

    public Nurse(String name, Gender gender) {
        super(name, gender);
        status = Status.Nurse;
    }

    @Override
    public Status getStatus() {
        return status;
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
    public String toString() {
        return String.format("%s\nstatus: %s\n", super.toString(), getStatus());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Nurse nurse = (Nurse) o;
        return status == nurse.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status);
    }
}
