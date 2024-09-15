const menuBtn = document.getElementById("menu-btn");
const menu = document.querySelector("menu");

menuBtn.addEventListener("click", onMenuClick);

function onMenuClick() {
  console.log("clicked");

  menu.classList.toggle("d-none");
}
