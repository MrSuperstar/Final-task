function input_click() {
    let login = document.getElementById("uniq-login").value;
    let password = document.getElementById("uniq-password").value;

    getUser(login, password);
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
            loadContent(element.pathname);
        }
    });
};


function recreateState() {
    loadContent(window.location.href);
}