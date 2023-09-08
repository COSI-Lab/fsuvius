/* URL for getting a list of users */
const USERS_URL = "api/users"

/* URL prefix for performing operations on single users */
const USER_URL = "api/users/"

/* URL prefix for performing operations on single photos */
const PHOTO_URL = "api/photos/"

/* Toast messages */
var toast_timeout;

function show_toast(message) {
    clearTimeout(toast_timeout);
    var td = document.getElementById("TOAST_MESSAGE");
    td.innerHTML = message;
    td.className = "show";
    toast_timeout = setTimeout(hide_toast, 3000);
}

function hide_toast() {
    var td = document.getElementById("TOAST_MESSAGE");
    td.className = td.className.replace("show", "hide");
}

/* Handle creating a user */
function handle_create() {
    let new_name = document.getElementById("CREATE_FIELD").value;
    if(new_name.length < 1) {
        show_toast("Please type a username to create.");
        return; 
    }
    console.log("Handling creation of new user...");
    fetch(USERS_URL, {
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
        },
        body: new_name,
    }).then(async response => {
        if(!response.ok) { throw new Error("POST request failed!"); }
        document.getElementById("CREATE_FIELD").value = "";
        display_list();
    }).catch(error => {
        console.log(error);
        show_toast("Failed to create user.");
    });
}

/* Handle +1 for a single user */
function handle_plus(id) {
    handle_balance_change(id, +1.0);
}

/* Handle -1 for a single user */
function handle_minus(id) {
    handle_balance_change(id, -1.0);
}

/* Change a user's balance */
function handle_balance_change(id, offset) {
    console.log(`Handling balance update for user ${id}`);
    show_toast("Saving your changes...");

    /* Get current user parameters */
    fetch(USER_URL + id, {
        method: "GET",
        headers: {
            "Accept": "application/json",
        },
    }).then(async response => {
        if(!response.ok) { throw new Error("GET request failed!"); }
        const data = await response.json();
        //console.log("[DEBUG] Response:");
        //console.log(data);
        var new_balance = data.balance + offset;
        let new_user = {
            "id": `${id}`,
            "name": `${data.name}`,
            "balance": `${new_balance}`
        }

        /* Put user with new parameters */
        fetch(USER_URL + id, {
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify(new_user),
        }).then(async response => {
            if(!response.ok) { throw new Error("PUT request failed!"); }
            const data = await response.json();
            //console.log("[DEBUG] Response:");
            //console.log(data);
            document.getElementById(`USER_BALANCE_${id}`).innerHTML = `${data.balance} FSU`;
            show_toast("Changes saved.");
        }).catch(error => {
            console.log(error);
            show_toast("Couldn't save changes. See console for error details.");
        });

    }).catch(error => {
        console.log(error);
        show_toast("Couldn't save changes. See console for error details.");
    });
}

/* Display list of all users */
function display_list() {
    console.log("Handling DISPLAY users...");
    fetch(USERS_URL, {
        method: "GET",
        headers: {
            "Accept": "application/json",
        },
    }).then(async response => {
        if(!response.ok) { throw new Error("GET request failed!"); }
        const data = await response.json();
        //console.log("[DEBUG] Response:");
        //console.log(data);
        formatted_result = "";
        for(let i in data) {
            let user = data[i];
            console.log(user);
            var user_HTML = `
            <div class="userpreview_container" id="USER_${user.id}">
                <img class="userpreview_photo" loading="lazy" src="${PHOTO_URL}${user.id}">
                <div class="userpreview_content">
                    <h2 class="user_name" id="USER_NAME_${user.id}">${user.name}</h2>
                    <h3 class="user_balance" id="USER_BALANCE_${user.id}">${user.balance} FSU</h3>
                    <button onclick="handle_plus('${user.id}')">+1</button>
                    <button onclick="handle_minus('${user.id}')">-1</button>
                    <a href="editor.html?id=${user.id}"><button>Edit</button></a>
                </div>
            </div>
            `
            formatted_result += user_HTML;
        }
        document.getElementById("USER_LIST").innerHTML = formatted_result;
    }).catch(error => {
        console.log(error);
        show_toast("Couldn't display list of users. See console for error details.");
    });
}

/* ===== On page load ===== */
display_list();