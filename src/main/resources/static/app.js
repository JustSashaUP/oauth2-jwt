const output = document.getElementById("output");

window.login = function () {
    window.location.href =
        window.location.origin + "/oauth2/authorization/google";
};

function renderProfile(profile) {
    const profilePicture = document.createElement("img");
    const name = document.createElement("div");
    const email = document.createElement("div");

    profilePicture.src = profile.picture;
    profilePicture.alt = profile.name ? `${profile.name} picture` : "Profile profilePicture";
    profilePicture.width = 96;
    profilePicture.height = 96;

    name.textContent = `${profile.name ?? ""}`;
    email.textContent = `${profile.email ?? ""}`;

    output.replaceChildren(profilePicture, name, email);
}

window.loadProfile = async function () {
    const resp = await fetch(window.location.origin + "/api/profile", {
        credentials: "include"
    });

    if (resp.status === 401 || resp.status === 403) {
        output.textContent = "Unauthorized: no token or invalid!"
    }

    const data = await resp.json();
    renderProfile(data);
};

window.logout = function () {
    output.textContent = "Logout!"
};