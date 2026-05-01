const output = document.getElementById("output");

window.login = function () {
    window.location.href =
        window.location.origin + "/oauth2/authorization/google";
};

window.loadProfile = async function () {
    const resp = await fetch(window.location.origin + "/api/profile", {
        credentials: "include"
    });

    if (resp.status === 401 || resp.status === 403) {
        output.textContent = "Unauthorized: no token or invalid!"
    }

    const data = await resp.text();
    output.textContent = data;
};

window.logout = function () {
    output.textContent = "Logout!"
};