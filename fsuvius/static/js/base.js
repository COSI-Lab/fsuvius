function base_toggle_nav() {
    const nav = document.querySelector("body>nav");
    if(nav.classList.contains("expanded")) {
        nav.classList.remove("expanded");
    } else {
        nav.classList.add("expanded");
    }
}
