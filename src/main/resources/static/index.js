/* The URL for getting a list of users */
const USERS_URL = "api/users"

/* The URL prefix for performing operations on single users */
const USER_URL = "api/users/"

/* The URL prefix for performing operations on single photos */
const PHOTO_URL = "api/photos/"

/* Handle creating a user */
function handle_create() {
    let new_name = document.getElementById("CREATE_FIELD").value;
    if(new_name.length < 1) { return; }
    console.log("Handling CREATE new user...");
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
        window.alert("Failed to create user.");
    });
}

/* Handle updating a user */
function handle_update(id) {
    console.log(`Handling UPDATE for ID [${id}]`);
    let name_field = document.getElementById(`FIELD_NAME_${id}`);
    let balance_field = document.getElementById(`FIELD_BALANCE_${id}`);
    let new_name = name_field.value;
    let new_balance = balance_field.value;
    let new_user = {
        "id": `${id}`,
        "name": `${new_name}`,
        "balance": `${new_balance}`
    }
    console.log("[DEBUG] New user:");
    console.log(new_user);

    fetch((USER_URL + id), {
        method: "PUT",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
        },
        body: JSON.stringify(new_user),
    }).then(async response => {
        if(!response.ok) { throw new Error("PUT request failed!"); }
        const data = await response.json();
        console.log("[DEBUG] Response:")
        console.log(data);
        name_field.value = data.name;
        balance_field.value = data.balance;
    }).catch(error => {
        console.log(error);
        window.alert(`Failed to update user ${id}.`);
    });
}

/* Handle deleting a user */
function handle_remove(id) {
    console.log(`Handling REMOVE for ID [${id}]`);
    if(window.confirm("Are you sure you want to remove this user?")) {
        fetch((USER_URL + id), {
            method: "DELETE",
        }).then(async response => {
            if(!response.ok) { throw new Error("DELETE request failed!"); }
            display_list();
        }).catch(error => {
            console.log(error);
            window.alert(`Failed to delete user ${id}.`);
        });
    }
}

/* Handle +1 for a single user */
function handle_plus(id) {
    console.log(`Handling PLUS for ID [${id}]`);
    let balance_field = document.getElementById(`FIELD_BALANCE_${id}`);
    balance_field.value =  Number(balance_field.value) + 1.0;
    handle_update(id);
}

/* Handle -1 for a single user */
function handle_minus(id) {
    console.log(`Handling MINUS for ID [${id}]`);
    let balance_field = document.getElementById(`FIELD_BALANCE_${id}`);
    balance_field.value = Number(balance_field.value) - 1.0;
    handle_update(id);
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
        console.log(data);
        formatted_result = "";
        for(let i in data) {
            let user = data[i];
            console.log(user);
            var user_HTML = `
            <div class="entry_container" id="USER_${user.id}">
                <img class="user_photo" src="${PHOTO_URL}${user.id}">
                <p class="user_name" id="USER_NAME_${user.id}>${user.name}</p>
                <p class="user_balance" id="USER_BALANCE_${user.id}">${user.balance}</p>
                <button onclick="handle_plus('${user.id}')">+1</button>
                <button onclick="handle_minus('${user.id}')">-1</button>
                <button onclick="handle_edit('${user.id}')">Edit</button>
            </div>
            `
            formatted_result += user_HTML;
        }
        document.getElementById("USER_LIST").innerHTML = formatted_result;
    }).catch(error => {
        document.getElementById("USER_LIST").innerHTML = "<p>Sorry, something went wrong with displaying the list of users. Check the console for more info.</p>";
        console.log(error);
    });
}

/* ===== On page load ===== */
display_list();