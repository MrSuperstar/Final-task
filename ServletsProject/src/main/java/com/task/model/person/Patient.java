package main.java.com.task.model.person;

import main.java.com.task.model.therapy.Diagnose;
import main.java.com.task.model.therapy.Medicament;
import main.java.com.task.model.therapy.Operation;
import main.java.com.task.model.therapy.Procedure;

import java.util.Objects;

public class Patient extends User {

    private Diagnose diagnose;
    private Procedure procedure;
    private Medicament medicament;
    private Operation operation;

    public Patient(String name, Gender gender) {
        super(name, gender);
        setPosition(User.Position.PATIENT);
    }

    @Override
    public String toString() {
        return String.format("%s\noperation: %s\tdiagnose: %s\tprocedure: %s\tmedicament: %s\n",
                super.toString(), getOperation(), getDiagnose(), getProcedure(), getMedicament());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(diagnose, patient.diagnose) &&
                Objects.equals(procedure, patient.procedure) &&
                Objects.equals(medicament, patient.medicament) &&
                Objects.equals(operation, patient.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), diagnose, procedure, medicament, operation);
    }

    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnosis) {
        this.diagnose = diagnosis;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
