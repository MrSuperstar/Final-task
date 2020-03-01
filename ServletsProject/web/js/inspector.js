function isTableExists(name) {
    let t;
    if ((t = document.getElementById(name)) !== null) {
        t.remove();
    }
}


function checkValid(obj) {
    if (obj !== undefined) return obj.name;
    return empty;
}

/**
 * Get user info by specific fields
 * @param field_number
 * @param patient
 * @returns {string|*}
 */
function patientInfo(field_number, patient) {
    switch (field_number) {
        case 0:
            return patient.id;
        case 1:
            return patient.name;
        case 2:
            return patient.gender;
        case 3:
            return checkValid(patient.operation);
        case 4:
            return checkValid(patient.diagnose);
        case 5:
            return checkValid(patient.procedure);
        case 6:
            return checkValid(patient.medicament);
        default:
            break;
    }
}