/* URL for getting a list of users */
const USERS_URL = "api/users"

/* URL prefix for performing operations on single users */
const USER_URL = "api/users/"

/* URL prefix for performing operations on single photos */
const PHOTO_URL = "api/photos/"

/* Handle creating a user */
function handle_create() {
    let new_name = document.getElementById("CREATE_FIELD").value;
    if(new_name.length < 1) {
        show_error("Please type a username to create.");
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
        if(!response.ok) { throw new Error(response.status); }
        document.getElementById("CREATE_FIELD").value = "";
        display_list();
        show_toast("Created user.");
    }).catch(error => {
        console.log(error);
        if(error.message === "403") {
            show_error("Forbidden. (Can't edit outside of the labs)");
        } else if(error.message === "400") {
            show_error("Bad request. (Invalid name or balance)");
        } else {
            show_error("Failed to create user. See console for error details.");
        }
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
        if(!response.ok) { throw new Error(response.status); }
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
            console.log(response.status);
            if(!response.ok) { throw new Error(response.status); }
            const data = await response.json();
            //console.log("[DEBUG] Response:");
            //console.log(data);
            document.getElementById(`USER_BALANCE_${id}`).innerHTML = `${data.balance} FSU`;
            show_toast("Changes saved.");
        }).catch(error => {
            console.log(error);
            if(error.message === "403") {
                show_error("Can't edit outside of the labs.");
            } else {
                show_error("Couldn't save changes. See console for error details.");
            }
        });
    }).catch(error => {
        console.log(error);
        show_error("Couldn't get user parameters. See console for error details.");
    });
}

/* Display list of all users */
function display_list(refresh=false) {
    console.log("Handling DISPLAY users...");
    if(refresh) { show_toast("Refreshing list of users..."); }
    fetch(USERS_URL, {
        method: "GET",
        headers: {
            "Accept": "application/json",
        },
    }).then(async response => {
        if(!response.ok) {
            console.log(`Request for "${USERS_URL}"` + 
                        ` failed with status ${response.status}`);
            throw new Error(response.status);
        }
        const data = await response.json();
        //console.log("[DEBUG] Response:");
        //console.log(data);
        user_list_html = "";
        for(let i in data) {
            let user = data[i];
            user_list_html += getUserHTML(user);
        }
        document.getElementById("USER_LIST").innerHTML = user_list_html;
        if(refresh) { show_toast("Refreshed list of users."); }
    }).catch(error => {
        console.log(error);
        show_error("Couldn't display list of users. See console for error details.");
    });
}

/* Gets the HTML for a single user */
function getUserHTML(user) {
    return `
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
}

/* ===== On page load ===== */
display_list();