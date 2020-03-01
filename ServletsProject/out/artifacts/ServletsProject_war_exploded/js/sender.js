function getEmployee(employee_id) {
    let xhr = prepareRequest(RESPONSE_TYPE, "POST", `/employees`);
    let obj = {
        id: employee_id
    };
    xhr.send(JSON.stringify(obj));
    xhr.onload = () => {
        if (xhr.status === 200) {
            viewAccount(xhr.response);
        }
    };
    sendRequest(xhr, viewAccount);
}

function getEmployees() {
    let xhr = prepareRequest(RESPONSE_TYPE, "POST", `/employees`);
    sendRequest(xhr, viewEmployees);
}

function getPatients() {
    let xhr = prepareRequest(RESPONSE_TYPE, "POST", `/patients`);
    sendRequest(xhr, createTable);
}

function getPatient(id, func) {
    let xhr = prepareRequest(RESPONSE_TYPE, "POST", `/patients`);

    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    let obj = {
        id: id
    };
    xhr.send(JSON.stringify(obj));
    xhr.onload = () => {
        if (xhr.status === 200) {
            if (xhr.response !== null)
                currentPatient = xhr.response;
                func(xhr.response);

        }
    };
}

function getUser(login, password) {
    let xhr = prepareRequest(RESPONSE_TYPE, "POST", `/auth`);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    let obj = {
        login: login,
        password: password
    };
    xhr.send(JSON.stringify(obj));
    xhr.onload = () => {
        if (xhr.status === 200) {
            if (xhr.response !== null) {
                if (xhr.response.position.toUpperCase() === "EMPLOYEE") {
                    loadContent("views/employee/employeeView.html");
                    employeesPosition = xhr.response.status.toUpperCase()
                } else if (xhr.response.position.toUpperCase() === "PATIENT") {
                    initPatient(xhr.response.id);
                    loadContent("views/patient/patientView.html");
                } else {
                    loadContent("LoginPage.html");
                }
            } else {
                loadContent("LoginPage.html");
            }
        }
    };
}

function initPatient(id) {
    let xhr = prepareRequest(RESPONSE_TYPE, "POST", `/patients`);

    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    let obj = {
        userId: id
    };
    xhr.send(JSON.stringify(obj));
    xhr.onload = () => {
        if (xhr.status === 200) {
            if (xhr.response !== null)
                currentPatient = xhr.response;
        }
    };
}

function discharge(id) {
    let xhr = prepareRequest(RESPONSE_TYPE, "POST", `/patients`);
    let obj = {
        id: id,
        status: 0
    };
    xhr.send(JSON.stringify(obj));
    xhr.onload = () => {
        if (xhr.status === 200) {
            getPatients();
        }
    };
}

function releaseEmployee(id) {
    let xhr = prepareRequest(RESPONSE_TYPE, "POST", `/employees`);
    let obj = {
        id: id,
        status: 0
    };
    xhr.send(JSON.stringify(obj));
    xhr.onload = () => {
        if (xhr.status === 200) {
            loadContent('views/employee/EmployeeList.html');
            getEmployees();
        }
    };
}

function treatPatient(id) {
    let request = `/patients?id=${id}`;

    therapies.forEach(therapy => {
        let elem = document.getElementById(therapy).options.selectedIndex;
        request += `&${therapy}=${elem + 1}`;
    });

    let xhr = prepareRequest(RESPONSE_TYPE, "POST", request);
    sendRequest(xhr, alert);
}

function getTherapy(therapyName) {
    let xhr = prepareRequest(RESPONSE_TYPE, "POST", `/therapies`);
    let obj = {
        therapy: therapyName
    };

    xhr.onload = () => {
        if (xhr.status === 200) {
            let index = -1;
            let select = document.getElementById(therapyName);
            xhr.response.forEach(option => {
                select.options[++index] = new Option(option.name, option.name);
            });
        }
    };
    xhr.send(JSON.stringify(obj));
}

function initPatientTherapyField(index, select) {
    console.log(patientInfo(index, currentPatient));
    select.value = patientInfo(index, currentPatient);
    if (employeesPosition.toString().toUpperCase() !== "DOCTOR") {
        if (select.id.toUpperCase() === "OPERATIONS" || select.id.toUpperCase() === "DIAGNOSIS") {
            select.disabled = true;
        }
    }
}


function loadContent(page) {
    const cont = document.getElementById('content');
    const http = createRequestObject();
    if (http) {
        http.open('GET', page + '?click=true');
        http.onload = () => {
            if (http.readyState === 4) {
                cont.innerHTML = http.responseText;
                let script = cont.getElementsByTagName('script');

                if (script[0] !== undefined) eval(script[0].innerText);

            }
        };
        http.send(null);
    } else {
        document.location = page;
    }
}

function prepareRequest(responseType, method, url) {
    let xhr = new XMLHttpRequest();
    xhr.responseType = responseType;
    xhr.open(method, url, true);

    return xhr;
}

function sendRequest(xhr, onloadFunc, parameter) {
    xhr.send();
    xhr.onload = () => {
        if (xhr.status === 200) {
            if (onloadFunc !== undefined) {
                if (parameter !== undefined) {
                    onloadFunc(parameter);
                } else {
                    onloadFunc(xhr.response);
                }
            }
        }
    };
}

