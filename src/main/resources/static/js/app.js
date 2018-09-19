$(function() {
    $("#auto-hide-notification").fadeIn();
    setTimeout(function () {
        $("#auto-hide-notification").fadeOut();
    }, 1000);
});

function copyLink(url) {
    var dummy = document.createElement("input");
    document.body.appendChild(dummy);
    dummy.setAttribute("id", "dummy_id");
    document.getElementById("dummy_id").value=url;
    dummy.select();
    document.execCommand("copy");
    document.body.removeChild(dummy);

    $("#custom-alert-msg").text("Copied to clipboard!");
    $("#custom-alert").fadeIn();
    setTimeout(function () {
        $("#custom-alert").fadeOut();
    }, 1000);
}

function playOnNewTab(url) {
    window.open(url, "_blank");
}