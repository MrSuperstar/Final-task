let employee_id = 0;

window.onload = function() {
    loadContent('LoginPage.html');
};

function input_click() {
    let idInput = document.getElementById("uniq-id");
    if (!idInput.validity.valid) {
        return;
    }
    employee_id = idInput.value;
    loadContent("views/employee/employeeView.html");
}

function navbarClick(bar_name) {
    switch (bar_name) {
        case "home":
            break;
        case "account":
            break;
        case "out":
            loadContent('LoginPage.html');
            break;
    }
}