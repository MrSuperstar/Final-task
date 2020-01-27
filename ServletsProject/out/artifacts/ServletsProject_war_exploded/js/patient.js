let pacient = null;

function viewAccount(pacient) {
    alert(pacient.name);
}

function record() {
    doctorAppointment();
}

function loadFields() {
    let content = document.getElementById("patient-content");

    let includedDiv = document.createElement("div");
    includedDiv.id = "include_div";

    content.appendChild(includedDiv);
}



