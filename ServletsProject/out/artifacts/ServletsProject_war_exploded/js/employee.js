function viewPatientsList() {
    loadContent("views/employee/Patients.html");
    getPatients();
}

function viewSpecificPatient(id) {
    loadContent("views/employee/Patients.html");
    getPatient(id);
}

function dischargePatient(id) {
    loadContent("views/employee/Patients.html");
    discharge(id);
}

function viewAccount(employee) {
    alert(employee.name);
}