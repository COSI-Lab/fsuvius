var toast_timeout;

/* Show a regular grey toast */
function show_toast(message) {
    clearTimeout(toast_timeout);
    var td = document.getElementById("TOAST_MESSAGE");
    td.innerHTML = message;
    td.className = "show";
    toast_timeout = setTimeout(hide_toast, 3000);
}

/* Show a red toast for errors */
function show_error(message) {
    clearTimeout(toast_timeout);
    var td = document.getElementById("TOAST_MESSAGE");
    td.innerHTML = message;
    td.className = "show error";
    toast_timeout = setTimeout(hide_toast, 3000);
}

/* Used by show_toast, 
    can also be invoked manually to hide the current toast */
function hide_toast() {
    var td = document.getElementById("TOAST_MESSAGE");
    td.className = td.className.replace("show", "hide");
}