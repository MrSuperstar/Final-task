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
    let xhr = prepareRequest(RESPONSE_TYPE, "GET", `/patients`);
    sendRequest(xhr, createTable);
}

function getPatient(id) {
    let xhr = prepareRequest(RESPONSE_TYPE, "GET", `/patients?id=${id}`);
    sendRequest(xhr, showPatientInformation);
}

function getUser(login, password) {
    let radio = document.getElementsByName("user_selected");
    let status = "EMPLOYEE";
    /*
    radio.forEach(o => {
        if (o.checked) {
            status = o.value;
        }
    });*/
    let xhr = prepareRequest(RESPONSE_TYPE, "POST", `/auth`);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    let obj = {
        login: login,
        password: password,
        status: status
    };
    xhr.send(JSON.stringify(obj));
    xhr.onload = () => {
        if (xhr.status === 200) {
            if (xhr.response !== null) {
                if (status.toUpperCase() === "EMPLOYEE") {
                    loadContent("views/employee/employeeView.html");
                } else {
                    loadContent("views/patient/patientView.html");
                }
            } else {
                loadContent("LoginPage.html");
            }
        }
    };
}

function discharge(id) {
    let xhr = prepareRequest(RESPONSE_TYPE, "POST", `/patients?id=${id}&status=0`);
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
    xhr.send(JSON.stringify(obj));

    xhr.onload = () => {
        if (xhr.status === 200) {
            let index = -1;
            let select = document.getElementById(therapyName);
            xhr.response.forEach(option => {
                select.options[++index] = new Option(option.name, option.name);
            });

            if (therapyName === "operations") select.value = patientInfo(3, currentPatient);
            if (therapyName === "diagnosis") select.value = patientInfo(4, currentPatient);
            if (therapyName === "procedures") select.value = patientInfo(5, currentPatient);
            if (therapyName === "medicament") select.value = patientInfo(6, currentPatient);
        }
    };
}


function loadContent(page) {
    const cont = document.getElementById('content');
    const http = createRequestObject();
    if (http) {
        http.open('GET', page + '?click=true');
        http.onload = () => {
            if (http.readyState === 4) {
                cont.innerHTML = http.responseText;
                eval(cont.getElementsByTagName('script')[0].innerText);
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

