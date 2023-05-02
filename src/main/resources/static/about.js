/* The URL to get bank balance from */
const BANK_BALANCE_URL = "api/bank_balance";

/* Get and display bank balance */
function display_bank_balance() {
    console.log("Handling DISPLAY bank balance...");
    fetch(BANK_BALANCE_URL, {
        method: "GET",
        headers: {
            "Accept": "application/json",
        },
    }).then(async response => {
        if(!response.ok) {
            throw new Error("GET request failed!");
        }
        const data = await response.text();
        console.log("[DEBUG] Processing user:");
        console.log(data);
        var bank_balance_HTML = `<h2>Bank balance: ${data} FSU</h2>`
        document.getElementById("BANK_BALANCE").innerHTML = bank_balance_HTML;
    }).catch(error => {
        document.getElementById("BANK_BALANCE").innerHTML = "<p>Sorry, something went wrong with displaying the bank balance. Check the console for more info.</p>";
        console.log(error);
    });
}

/* ===== On page load: ===== */
display_bank_balance();