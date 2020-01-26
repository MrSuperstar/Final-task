function addDischargeButtonsFromTable(tr, patient) {
    addButtonFromTable('Discharge', dischargePatient, patient.id, tr)
}

function addDetailsButtonsFromTable(tr, patient) {
    addButtonFromTable('Details', viewSpecificPatient, patient.id, tr)
}

function addButtonFromTable(text, func, parameter, tr) {
    let td = document.createElement("td");
    let showInfoBtn = createBtn(text, func, parameter);

    td.appendChild(showInfoBtn);
    tr.appendChild(td);
}


function createSelectObject(className, tr) {
    let td = document.createElement("td");
    let select = document.createElement("select");
    select.id = className;
    td.appendChild(select);
    tr.appendChild(td);
    getTherapy(className);
}


function createTable(patients, func) {
    if (func === undefined) func = generateTableContent;
    let table = generateTableStructure("patient-table", titles, func, patients);
    document.getElementById("patients").appendChild(table);
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
    let tbody = document.createElement("tbody");

    createHeader(tbody, header);

    if (func !== undefined) {
        table.appendChild(func(tbody, collection));
    }

    return table;
}

function createBtn(btnText, func, parameters) {
    let btn = document.createElement("button");
    btn.className = 'btn';
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