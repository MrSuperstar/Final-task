let histAPI=!!(window.history && history.pushState);

function input_click() {
    let login = document.getElementById("uniq-login").value;
    let password = document.getElementById("uniq-password").value;

    getUser(login, password);
}


function navbarClick(bar_name) {
    switch (bar_name) {
        case "home":
            break;
        case "Log in": loadContent('LoginPage.html');
            break;
        case "Employees list": loadContent('views/employee/EmployeeList.html');
            getEmployees();
            break;
        case "out":
            loadContent('LoginPage.html');
            break;
    }
}

function viewDoctor(id) {
    getEmployee(id);
}

window.onload = function(){
    navbarPointCreate('Log in', 'navbar-login');
    navbarPointCreate('Employees list', 'navbar-employees');
    /*
    if (histAPI) {
        window.setTimeout(function() {
            window.addEventListener("popstate",
                function() {
                    alert(location.pathname);
                    popstate(location.pathname);
                },
                false);
        }, 1);
    }*/
};

function popstate(url) {
    loadContent(url);
}