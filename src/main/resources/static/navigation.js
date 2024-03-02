document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("welcomeLink").addEventListener("click", function(event) {
        event.preventDefault();

        fetch("/bugtracker/default-authority")
            .then(response => response.text())
            .then(authority => {
                if (authority.trim() === "ADMIN") {
                    window.location.href = "/admin-panel.html";
                } else if (authority.trim() === "USER") {
                    window.location.href = "/user-panel.html";
                } else {
                    console.error("Unknown authority:", authority);
                }
            })
            .catch(error => {
                console.error("Error fetching authorities:", error);
            });
    });
});

function redirectToWelcomePage() {
    var welcomeLink = document.getElementById("welcomeLink");
    if (welcomeLink) {
        welcomeLink.click();
    }
}
