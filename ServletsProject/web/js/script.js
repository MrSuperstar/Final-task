let histAPI=!!(window.history && history.pushState);

function input_click() {
    let login = document.getElementById("uniq-login").value;
    let password = document.getElementById("uniq-password").value;

    getUser(login, password);
}

window.addEventListener('locationchange', function(){
    console.log('location changed!');
});

function urlHandler(bar) {
    switch (bar) {
        case "/login": loadContent('LoginPage.html'); break;
        case "/employees": loadContent('views/employee/EmployeeList.html');
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
    navbarPointCreate('Log in', 'navbar-login', '/login');
    navbarPointCreate('Employees list', 'navbar-employees', '/employees');

    document.body.addEventListener("click", function (e) {
        let element = e.target;

        if (element.tagName === 'A' && element.href) {
            e.preventDefault();
            history.pushState(null, "", element.pathname);
            urlHandler(element.pathname);
        }
    });
};