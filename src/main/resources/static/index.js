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
        show_error("missingusername");
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
        show_toast("createduser");
    }).catch(error => {
        console.log(error);
        if(error.message === "403") {
            show_error("forbidden");
        } else if(error.message === "400") {
            show_error("badrequest");
        } else {
            show_error("unknownusererror");
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
    show_toast("savechanges");
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
            "balance": `${new_balance}`,
            "lastActive": Date.now()
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
            document.getElementById(`USER_BALANCE_${id}`).innerHTML = `${data.balance}`;
            show_toast("changessaved");
        }).catch(error => {
            console.log(error);
            if(error.message === "403") {
                show_error("forbidden");
            } else {
                show_error("unknownusererror");
            }
        });
    }).catch(error => {
        console.log(error);
        show_error("noparameters");
    });
}

/* Display list of all users */
function display_list(refresh=false) {
    console.log("Handling DISPLAY users...");
    if(refresh) { show_toast("refresh"); }
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
        let data = await response.json();
        data.sort((a, b) => b.lastActive - a.lastActive);
        //console.log("[DEBUG] Response:");
        //console.log(data);
        user_list_html = "";
        for(let i in data) {
            let user = data[i];
            user_list_html += getUserHTML(user);
        }
        document.getElementById("USER_LIST").innerHTML = user_list_html;
        if(refresh) { show_toast("refreshdone"); }
    }).catch(error => {
        console.log(error);
        show_error("listuserserror");
    });
}

/* Gets the HTML for a single user */
function getUserHTML(user) {
    const now = Date.now();
    const lastActive = user.lastActive;

    // user becomes invisible after 30 days
    const visible = (now - lastActive) < 2.592e+9

    var userHTML = document.getElementById("defaultUser").cloneNode(true);
    if (!visible) userHTML.setAttribute("class", "userpreview_container user_inactive");

    userHTML.setAttribute("id", `USER_${user.id}`);

    userHTML.querySelector('#userpreview_photo').setAttribute("src", `${PHOTO_URL}${user.id}`);

    var nameElement = userHTML.querySelector('#user_name');
    nameElement.setAttribute("id", `USER_NAME_${user.id}`);
    nameElement.innerHTML = `${user.name}`;

    var balanceElement = userHTML.querySelector('#user_balance');
    balanceElement.setAttribute("id", `USER_BALANCE_${user.id}`);
    balanceElement.innerHTML = `${user.balance}`;

    var buttonPlus1 = userHTML.querySelector('#buttonPlus1');
    buttonPlus1.setAttribute("onclick",`handle_plus('${user.id}')`)

    var buttonMinus1 = userHTML.querySelector('#buttonMinus1');
    buttonMinus1.setAttribute("onclick",`handle_minus('${user.id}')`)

    var buttonDollar = userHTML.querySelector('#buttonDollar');
    buttonDollar.setAttribute("onclick",`easyDeposit('${user.id}')`)

    var buttonEdit = userHTML.querySelector('#buttonEdit');
    buttonEdit.setAttribute("href",`editor.html?id=${user.id}`)

    return userHTML.outerHTML;
}

function toggleInactiveUsers() {
    const currentDisplay = getComputedStyle(document.documentElement).getPropertyValue('--display-inactive') == "grid";
    document.documentElement.style.setProperty('--display-inactive', currentDisplay ? "none" : "grid" );
    var hiddenButton = document.getElementById("SHOW_HIDDEN");
    hiddenButton.textContent= currentDisplay ? hiddenButton.dataset["show"] : hiddenButton.dataset["hide"];
    display_list();
}

function easyDeposit(id) {
    const promptText = document.getElementById("prompt-lang").dataset["dollaramount"];
    try {
        const depositDollars = prompt(promptText);
        if (depositDollars == null || depositDollars == "") {
            show_toast("Balance not changed.");
            return;
        }
        const depositFsu = parseFloat(depositDollars) / 0.75; // 1 FSU = 0.75 USD
        if (depositFsu == NaN) {
            show_toast("Balance not changed.");
            return;
        }
        handle_balance_change(id,depositFsu);
    }
    catch {
        show_error("Something went wrong. Please try again.")
    }
}

function loadNews() {
    const content = localStorage.getItem("news");
    document.getElementById("NEWS").value = content;
}

function storeNews() {
    const content = document.getElementById("NEWS").value;
    localStorage.setItem("news", content);
}

/* ===== On page load ===== */
display_list();
loadNews();