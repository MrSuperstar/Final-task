let histAPI=!!(window.history && history.pushState);

function input_click() {
    let login = document.getElementById("uniq-login").value;
    let password = document.getElementById("uniq-password").value;

    getUser(login, password, "patient");
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

window.onload = function(){
    loadContent('LoginPage.html');
    if (histAPI) {
        window.setTimeout(function() {
            window.addEventListener("popstate",
                function() {
                    alert(location.pathname);
                    popstate(location.pathname);
                },
                false);
        }, 1);
    }
};

function popstate(url) {
    loadContent(url);
}