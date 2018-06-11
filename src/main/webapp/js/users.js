function switchToUsersPage() {
  visibilityOfPages("none");
  GetElement("#user_selector_page").style.display = "block";

  new Request("GET", "/users",
    null,
    loadUsers
  );
}

function loadUsers() {
  const users = JSON.parse(this.responseText);
  const usersE = GetElement("#users");
  usersE.innerHTML = "";

  const tempE = CreateElement("option");
  tempE.value = "temp";
  tempE.textContent = "Select user...";
  usersE.appendChild(tempE);

  users.forEach(user => {
    const userE = CreateElement("option");
    userE.value = user.id;
    userE.textContent = user.name;

    usersE.appendChild(userE);
  });
}

function init() {
  GetElement("#user_selector").addEventListener("click", switchToUsersPage);
  GetElement("#users").addEventListener("change", event => {
    if (event.target.value != "temp") {
      switchToSchedulesPage(event.target.value);
    }
  });
}

document.addEventListener("DOMContentLoaded", init);