package main.java.com.task.model.hospital;

import main.java.com.task.daolayer.BaseFactory;
import main.java.com.task.model.person.Healing;
import main.java.com.task.model.person.MedicalEmployee;
import main.java.com.task.model.person.Patient;
import main.java.com.task.model.therapy.Diagnose;
import main.java.com.task.model.therapy.Medicament;
import main.java.com.task.model.therapy.Operation;
import main.java.com.task.model.therapy.Procedure;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Base class for hospital.
 */
public abstract class Hospital {
    private String hospitalName;
    private BaseFactory factory;

    public Hospital(BaseFactory factory) {
        this.factory = factory;
    }

    public final boolean appointOperation(@NotNull MedicalEmployee employee, @NotNull Patient patient, @NotNull Operation operation) {
        if (employee != null && patient != null && operation != null) {
            /* If employee is doctor he can appoint operation thanks to the implementation of the interface */
            if (employee.getStatus().equals(MedicalEmployee.Status.Doctor)) {
                ((Healing) employee).appointOperation(patient, operation);
                getFactory().getPatientDao().update(patient); // update patient in database

                return true;
            }
        }

        return false;
    }

    public final boolean appointDiagnose(@NotNull MedicalEmployee employee, @NotNull Patient patient, @NotNull Diagnose diagnose) {
        if (employee != null && patient != null && diagnose != null) {
            /* If employee is doctor he can appoint operation thanks to the implementation of the interface */
            if (employee.getStatus().equals(MedicalEmployee.Status.Doctor)) {
                ((Healing) employee).appointDiagnose(patient, diagnose);
                getFactory().getPatientDao().update(patient);

                return true;
            }
        }

        return false;
    }

    public final boolean appointProcedure(@NotNull MedicalEmployee employee, @NotNull Patient patient, @NotNull Procedure procedure) {
        if (employee != null && patient != null && procedure != null) {
            employee.appointProcedure(patient, procedure);
            getFactory().getPatientDao().update(patient);

            return true;
        }

        return false;
    }

    public final boolean appointMedicament(@NotNull MedicalEmployee employee, @NotNull Patient patient, @NotNull Medicament medicament) {
        if (employee != null && patient != null && medicament != null) {
            employee.appointMedicament(patient, medicament);
            getFactory().getPatientDao().update(patient);

            return true;
        }

        return false;
    }

    protected BaseFactory getFactory() {
        return factory;
    }

    /**
     * Getting an employee by number.
     * @param id - A number of employee.
     * @return Found employee or null.
     */
    @Nullable
    protected MedicalEmployee getSpecificEmployee(int id) {
        MedicalEmployee employee = null;

        if (id >= 1) {
            employee = factory.getEmployeeDao().getById(id);
        }

        return employee;
    }

    /**
     * Obtaining all employees of a certain specialty: doctor or nurse.
     * @param status Specialty of employees.
     * @return List of accepteble employees.
     */
    @Nullable
    protected Collection<MedicalEmployee> getEmployeesByStatus(MedicalEmployee.Status status) {
        Collection<MedicalEmployee> employees = new ArrayList<>();

        for (MedicalEmployee employee : getEmployees()) {
            if (employee.getStatus().equals(status)) {
                employees.add(employee);
            }
        }

        return employees;
    }

    /**
     * Getting a list of all employees working (not dismissed) in the hospital.
     * @return
     */
    @Nullable
    protected Collection<MedicalEmployee> getEmployees() {
        return factory.getEmployeeDao().select();
    }

    public String getHospitalName() {
        return hospitalName;
    }

    protected void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hospital hospital = (Hospital) o;
        return Objects.equals(hospitalName, hospital.hospitalName) &&
                Objects.equals(factory, hospital.factory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hospitalName, factory);
    }

    @Override
    public String toString() {
        return String.format("Hospital's name: %s\n", getHospitalName());
    }
}
