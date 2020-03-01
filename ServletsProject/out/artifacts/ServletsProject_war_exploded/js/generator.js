function addDischargeButtonsFromTable(tr, patient) {
    addButtonFromTable('Discharge', dischargePatient, patient.id, tr)
}

function addDetailsButtonsFromTable(tr, patient) {
    addButtonFromTable('Details', viewSpecificPatient, patient.id, tr)
}

function addRecordButtonsFromTable(tr, employee) {
    if ("DOCTOR" === employee.status.toUpperCase()) {
        addButtonFromTable('Details', viewDoctor, employee.id, tr)
    }
}

function addReleaseButtonFromTable(tr, employee) {
    addButtonFromTable('Release', releaseEmployee, employee.id, tr)
}

function addButtonFromTable(text, func, parameter, tr) {
    let td = document.createElement("td");
    let showInfoBtn = createBtn(text, func, parameter);

    td.appendChild(showInfoBtn);
    tr.appendChild(td);
}

function navbarPointCreate(name, id, href) {
    let li = document.createElement("li");
    li.id = id;
    let a = document.createElement("a");
    a.href = href;
    a.innerHTML = name;
    li.appendChild(a);
    document.getElementById("nav-bar-block").appendChild(li);
}

function createSelectObject(className, tr) {
    let td = document.createElement("td");
    let select = document.createElement("select");
    select.id = className;
    td.appendChild(select);
    tr.appendChild(td);
    getTherapy(className);

    switch (className.toString().toUpperCase()) {
        case "OPERATIONS": initPatientTherapyField(3, select); break;
        case "DIAGNOSIS": initPatientTherapyField(4, select); break;
        case "PROCEDURES": initPatientTherapyField(5, select); break;
        case "MEDICAMENT": initPatientTherapyField(6, select); break;
    }
}

function createTable(patients, func) {
    if (func === undefined) func = generateTableContent;
    let table = generateTableStructure("patient-table", titles, func, patients);
    document.getElementById("patients").appendChild(table);
}

function viewEmployees(employees) {
    let tit = ["id", "name", "gender", "status"];
    let table = generateTableStructure("employees-table", tit, generateEmployeesContent, employees);
    document.getElementById("employees-block").appendChild(table);
}

function viewEmployee(employee) {
    let tit = ["id", "name", "gender", "status"];
    let table = generateTableStructure("employees-table", tit, generateEmployeeContent, employee);
    document.getElementById("employees-block").appendChild(table);
}

function generateEmployeeContent(tbody, employee, needButton = true) {
    let tr = document.createElement("tr");
    for (let i = 0; i < 3; i++) {
        let td = document.createElement("td");
        td.innerHTML = patientInfo(i, employee);
        tr.appendChild(td);
    }
    let td = document.createElement("td");
    td.innerHTML = employee.status;
    tr.appendChild(td);
    if (needButton) {
        addReleaseButtonFromTable(tr, employee);
    }

    tbody.appendChild(tr);
    return tbody;
}

function generateEmployeesContent(tbody, employees, needButton = true) {
    employees.forEach(employee => {
        let tr = document.createElement("tr");
        for (let i = 0; i < 3; i++) {
            let td = document.createElement("td");
            td.innerHTML = patientInfo(i, employee);
            tr.appendChild(td);
        }
        let td = document.createElement("td");
        td.innerHTML = employee.status;
        tr.appendChild(td);
        if (needButton) {
            addRecordButtonsFromTable(tr, employee);
        }

        tbody.appendChild(tr);
    });
    return tbody;
}

function createHeader(tbody, titleCollection) {
    let header = document.createElement("tr");
    titleCollection.forEach(title => {
        let th = document.createElement("th");
        th.innerHTML = title;
        header.appendChild(th)
    });
    tbody.appendChild(header);
}

function createRequestObject() {
    try {
        return new XMLHttpRequest()
    } catch(e) {
        try {
            return new ActiveXObject('Msxml2.XMLHTTP')
        } catch(e) {
            try {
                return new ActiveXObject('Microsoft.XMLHTTP')
            } catch(e) {
                return null; }
        }
    }
}

function generateTableStructure(tableId, header, func, collection) {
    isTableExists(tableId);

    let table = document.createElement("table");
    table.id = tableId;
    table.className = "table_dark";
    let tbody = document.createElement("tbody");

    createHeader(tbody, header);

    if (func !== undefined) {
        table.appendChild(func(tbody, collection));
    }

    return table;
}

function createBtn(btnText, func, parameters) {
    let btn = document.createElement("button");
    btn.className = 'btn btn-dark';
    let textInBtn = document.createTextNode(btnText);
    btn.appendChild(textInBtn);

    if (func !== undefined) {
        btn.onclick = function () {
            func(parameters);
        };
    }
    return btn;
}

function generateTableContent(tbody, patients) {
    patients.forEach(patient => {
        let tr = document.createElement("tr");
        for (let i = 0; i < 7; i++) {
            let td = document.createElement("td");
            td.innerHTML = patientInfo(i, patient);
            tr.appendChild(td);
        }
        addDetailsButtonsFromTable(tr, patient);
        addDischargeButtonsFromTable(tr, patient);
        tbody.appendChild(tr);
    });
    return tbody;
}

function generateContent(tbody, patient) {
    let tr = document.createElement("tr");
    for (let i = 0; i < 3; i++) {
        let td = document.createElement("td");
        td.innerHTML = patientInfo(i, patient);
        tr.appendChild(td);
    }

    therapies.forEach(therapy => {
        createSelectObject(therapy, tr);
    });
    tbody.appendChild(tr);
    return tbody;
}

function viewTherapies() {
    therapies.forEach(therapy => {
        let element = document.getElementById("patient-" + therapy);
        switch (therapy) {
            case "operations": element.innerText = patientInfo(3, currentPatient); break;
            case "diagnosis": element.innerText = patientInfo(4, currentPatient); break;
            case "medicament": element.innerText = patientInfo(5, currentPatient); break;
            case "procedures": element.innerText = patientInfo(6, currentPatient); break;
        }
    });
}

function showPatientInformation(patient) {
    isTableExists("patient_treatment");
    currentPatient = patient;
    createTable(currentPatient, generateContent);
    let button = createBtn('Treat', treatPatient, patient.id);
    document.getElementById("patients-info").appendChild(button);
}