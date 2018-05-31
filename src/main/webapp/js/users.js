function switchToUsersPage() {
  visibilityOfPages("none");
  document.getElementById("user_selector_page").style.display = "block";

  new Request("GET", "/users",
    null,
    loadUsers
  );
}

function loadUsers() {
  const users = JSON.parse(this.responseText);
  const usersE = document.getElementById("users");
  usersE.innerHTML = "";

  const tempE = document.createElement("option");
  tempE.value = "temp";
  tempE.textContent = "Select user...";
  usersE.appendChild(tempE);

  users.forEach(user => {
    const userE = document.createElement("option");
    userE.value = user.id;
    userE.textContent = user.name;

    usersE.appendChild(userE);
  });
}

function init() {
  document.getElementById("user_selector").addEventListener("click", switchToUsersPage);
  document.getElementById("users").addEventListener("change", event => {
    if (event.target.value != "temp") {
      switchToSchedulesPage(event.target.value);
    }
  });
}

document.addEventListener("DOMContentLoaded", init);