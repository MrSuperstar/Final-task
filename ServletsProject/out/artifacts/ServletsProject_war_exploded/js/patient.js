let currentPatient = null;

function showPatientInformation(patient) {
    isTableExists("patient_treatment");
    currentPatient = patient;
    createTable(currentPatient, generateContent);
    let button = createBtn('Treat', treatPatient, patient.id);
    document.getElementById("patients-info").appendChild(button);
}





