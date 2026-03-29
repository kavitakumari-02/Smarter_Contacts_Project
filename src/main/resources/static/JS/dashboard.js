function toggleSidebar(){

let sidebar = document.getElementById("sidebar");
let content = document.getElementById("content");

sidebar.classList.toggle("active");
content.classList.toggle("shift");

}