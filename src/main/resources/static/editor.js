/* Get URL-encoded parameters */
const params = new URLSearchParams(decodeURI(window.location.search));

/* URL for performing operations on this user */
const USER_URL = "api/users/" + params.get("id");

/* URL for performing operations on this user's photo */
const PHOTO_URL = "api/photos/" + params.get("id");

/* This user's ID */
const USER_ID = params.get("id");

/* Max photo upload size */
const MAX_UPLOAD_SIZE = 1024 * 1024;

/* Update fields with this user's data (including photo) */
function handle_display() {
    console.log(`Handling DISPLAY user ${USER_ID}`)
    fetch(USER_URL, {
        method: "GET",
        headers: {
            "Accept": "application/json",
        },
    }).then(async response => {
        if(!response.ok) { throw new Error(response.status); }
        const data = await response.json();
        console.log("[DEBUG] Response:");
        console.log(data);
        document.getElementById("USER_NAME").value = data.name;
        document.getElementById("USER_BALANCE").value = data.balance;
        document.getElementById("USER_PHOTO").src = PHOTO_URL;
    }).catch(error => {
        console.log(error);
        show_error("Couldn't fetch user. See console for error details.");
    });
}

/* Save changes to this user */
function handle_save() {
    console.log(`Saving changes to user "${USER_URL}"`);
    show_toast("Saving your changes...");
    let new_name = document.getElementById("USER_NAME").value;
    let new_balance = document.getElementById("USER_BALANCE").value;
    let new_user = {
        "id": `${USER_ID}`,
        "name": `${new_name}`,
        "balance": `${new_balance}`
    }
    fetch((USER_URL), {
        method: "PUT",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
        },
        body: JSON.stringify(new_user),
    }).then(async response => {
        if(!response.ok) { throw new Error(response.status); }
        window.location.href="index.html";
    }).catch(error => {
        console.log(error);
        if(error.message === "403") {
            show_error("Forbidden. (Can't edit outside of the labs)");
        } else if(error.message === "400") {
            show_error("Bad request. (Invalid name or balance)");
        } else {
            show_error("Couldn't save changes. See console for error details.");
        }
    });
}

/* Handle deleting this user */
function handle_delete() {
    console.log(`Handling deletion of user ${USER_ID}`);
    show_toast("Deleting user...");
    if(window.confirm("Are you sure you want to delete this user?")) {
        fetch((USER_URL), {
            method: "DELETE",
        }).then(async response => {
            if(!response.ok) { throw new Error(response.status); }
            window.location.href="index.html";
        }).catch(error => {
            console.log(error);
            if(error.message === "403") {
                show_error("Can't edit outside of the labs.");
            } else {
                show_error("Couldn't delete user. See console for error details.");
            }
        });
    } else {
        show_toast("Deletion cancelled.");
    }
}

/* Handle uploading a new photo */
function handle_upload_photo(input) {
    console.log("Handling upload of user photo...");
    if(input.files[0].size < MAX_UPLOAD_SIZE) {
        show_toast("Uploading photo...");
        const fr = new FileReader();
        fr.addEventListener("load", function(event) {
            fetch(PHOTO_URL, {
                method: "POST",
                headers: {
                    "Content-Type": "text/plain",
                },
                body: event.target.result,
            }).then(async response => {
                if(!response.ok) {
                    if(!response.ok) { throw new Error(response.status); }
                }
                show_toast("Photo uploaded.");
                document.getElementById("USER_PHOTO").src = event.target.result;
            }).catch(error => {
                console.log(error);
                if(error.message === "403") {
                    show_error("Can't edit outside of the labs.");
                } else {
                    show_error("Something went wrong uploading your photo.");
                }
            });
        });
        fr.readAsDataURL(input.files[0]);
    } else {
        show_error("Your photo is too large!");
    }
}

/* ===== On page load ===== */
handle_display()