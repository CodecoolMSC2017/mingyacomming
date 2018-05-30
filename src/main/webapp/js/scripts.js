const BASE_URL = "http://localhost:8080/mingyacomming";

function Request(type, url, data, after) {
  const xhr = new XMLHttpRequest();
  xhr.addEventListener("load", after);
  xhr.open(type, `${BASE_URL}${url}`);
  xhr.send(data);
}



/* _________________
  |                 |
  | Login method(s) |
  |_________________|
*/
function login() {
  new Request("POST", "/login",
    JSON.stringify(getLogFields()),
    changeUserTab
  );
}

function getLogFields() {
  return {
    username: document.getElementById("log_username_field").value,
    password: document.getElementById("log_password_field").value
  };
}



/* ____________________
  |                    |
  | Register method(s) |
  |____________________|
*/
function register() {
  new Request("POST", "/register",
    JSON.stringify(getRegFields()),
    null
  );
}

function getRegFields() {
  return {
    username: document.getElementById("reg_username_field").value,
    password: document.getElementById("reg_password_field").value
  };
}



/* __________________
  |                  |
  | Logout method(s) |
  |__________________|
*/
function logout() {
  new Request("GET", "/logout",
    null,
    deleteUserData
  );
}

// UserTab
function changeUserTab() {
  const message = JSON.parse(this.responseText);

  if (this.status != 200) {
    addMessage(this.status, message.messege);
    return;
  }

  getUserData();
  switchToTasksPage();
}



/* ___________________
  |                   |
  | Profile method(s) |
  |___________________|
*/
function loadUserData() {
  const userData = JSON.parse(this.responseText);

  if (userData == null) {
    return;
  }

  visibilityOfCreateForms(userData.role == "admin" ? "none" : "block");
  hideUserData();
  switchToSchedulesPage();

  const userDataE = document.getElementById("user_data");

  let roleE = document.createElement("label");
  roleE.className = "role";
  roleE.style.display = "block";
  roleE.textContent = userData.role;
  userDataE.appendChild(roleE);

  let usernameE = document.createElement("label");
  usernameE.className = "username";
  usernameE.style.display = "block";
  usernameE.textContent = userData.name;
  userDataE.appendChild(usernameE);

  let coinsE = document.createElement("i");
  coinsE.className = "fas fa-coins";
  coinsE.style.color = "#ffd800"
  coinsE.style.display = "block";
  coinsE.textContent = " 0";
  userDataE.appendChild(coinsE);
}

function hideUserData() {
  document.getElementById("buttons").style.display = "none";
  document.getElementById("login_form").style.display = "none";
  document.getElementById("user_data").style.display = "block";
  document.getElementById("user_profile_buttons").style.display = "block";
  document.getElementById("tabs").style.display = "block";
}

function deleteUserData() {
  document.getElementById("buttons").style.display = "block";
  document.getElementById("login_form").style.display = "block";
  document.getElementById("user_data").style.display = "none";
  document.getElementById("user_data").innerHTML = "";
  document.getElementById("user_profile_buttons").style.display = "none";
  document.getElementById("tabs").style.display = "none";

  visibilityOfPages("none");
}

function getUserData() {
  new Request("GET", "/user",
    null,
    loadUserData
  );
}

function visibilityOfPages(visibility) {
  document.getElementById("tasks_page").style.display = visibility;
  document.getElementById("schedules_page").style.display = visibility;
  document.getElementById("days_page").style.display = visibility;
  document.getElementById("slots_page").style.display = visibility;
}

function visibilityOfCreateForms(visibility) {
  document.getElementById("task_form").style.display = visibility;
  document.getElementById("schedule_form").style.display = visibility;
  document.getElementById("day_form").style.display = visibility;
}



/* ___________________
  |                   |
  | Message method(s) |
  |___________________|
*/
function checkResp() {
  const message = JSON.parse(this.responseText);
  addMessage(message.messege, this.status);
}

function addMessage(status, content) {
  let messageE = document.createElement("div");

  if (status >= 200 && status < 300) {
    messageE.className = "message";
  } else if (status >= 500) {
    messageE.className = "message warning";
  } else {
    messageE.className = "message error";
  }

  messageE.textContent = `${status} : ${content}`;

  let messagesE = document.getElementById("messages");
  messagesE.appendChild(messageE);

  setTimeout(messageE.remove(), 5000);
}


data = {
  time: 0,
  taskId: 0,
  dayId: 0
}


/* _________
  |         |
  | C O R E |
  |_________|
*/
function init() {
  getUserData();

  // Setup event listeners
  document.getElementById("log_button").addEventListener("click", login);
  document.getElementById("reg_button").addEventListener("click", register);
  document.getElementById("logout_button").addEventListener("click", logout);

  document.getElementById("schedule_searcher").addEventListener("click", switchToSearcher);;
  document.getElementById("my_tasks").addEventListener("click", switchToTasksPage);
  document.getElementById("my_schedules").addEventListener("click", switchToSchedulesPage);
  document.getElementById("current_schedule").addEventListener("click", () => {
    switchToDaysPage(document.getElementById("current_schedule").getAttribute("value"));
  });
  document.getElementById("current_day").addEventListener("click", () => {
    switchToSlotsPage(document.getElementById("current_day").getAttribute("value"));
  });

  document.getElementById("log_tab").addEventListener("click", () => {
    document.getElementById("login_form").style.display = "block";
    document.getElementById("register_form").style.display = "none";
  });
  document.getElementById("reg_tab").addEventListener("click", () => {
    document.getElementById("register_form").style.display = "block";
    document.getElementById("login_form").style.display = "none";
  });

  document.getElementById("game_button").addEventListener("click", () => {
    let content = document.getElementById("content");
    if (content.style.display == "none") {
      content.style.display = "block";
    } else {
      content.style.display = "none";
    }
  });
}

document.addEventListener("DOMContentLoaded", init);