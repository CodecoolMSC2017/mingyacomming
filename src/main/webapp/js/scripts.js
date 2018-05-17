const BASE_URL = "http://localhost:8080/mingyacomming";



/* _________________
  |                 |
  | Login method(s) |
  |_________________|
*/
function login() {
  const logData = getLogFields();

  let xhr = new XMLHttpRequest();
  xhr.addEventListener("load", changeUserTab);
  xhr.open("POST", `${BASE_URL}/login`);
  xhr.send(JSON.stringify(logData));
}

function getLogFields() {
  let logData = {};

  let usernameE = document.getElementById("log_username_field");
  logData.username = usernameE.value;

  let passwordE = document.getElementById("log_password_field");
  logData.password = passwordE.value;

  return logData;
}



/* ____________________
  |                    |
  | Register method(s) |
  |____________________|
*/
function register() {
  const regData = getRegFields();

  let xhr = new XMLHttpRequest();
  xhr.addEventListener("load", checkResp);
  xhr.open("POST", `${BASE_URL}/register`);
  xhr.send(JSON.stringify(regData));
}

function getRegFields() {
  let regData = {};

  let usernameE = document.getElementById("reg_username_field");
  regData.username = usernameE.value;

  let passwordE = document.getElementById("reg_password_field");
  regData.password = passwordE.value;

  return regData;
}



/* __________________
  |                  |
  | Logout method(s) |
  |__________________|
*/
function logout() {
  let xhr = new XMLHttpRequest();
  xhr.addEventListener("load", deleteUserData);
  xhr.open("GET", `${BASE_URL}/logout`);
  xhr.send();
}

// UserTab
function changeUserTab() {
  const message = JSON.parse(this.responseText);

  if (this.status != 200) {
    addMessage(this.status, message.messege);
    return;
  }

  getUserData(loadUserData);
  switchToTasksPage();
}



/* ___________________
  |                   |
  | Profile method(s) |
  |___________________|
*/
function loadUserData() {

  hideUserData();

  const userData = JSON.parse(this.responseText);

  let usernameE = document.createElement("label");
  usernameE.textContent = userData.name;

  document.getElementById("user_data").appendChild(usernameE);
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
  document.getElementById("user_profile_buttons").style.display = "none";
  document.getElementById("tabs").style.display = "none";

  document.getElementById("tasks_page").style.display = "none";
  document.getElementById("schedules_page").style.display = "none";
  document.getElementById("days_page").style.display = "none";

  document.getElementById("user_data").innerHTML = "";
}

function getUserData(callback) {
  let xhr = new XMLHttpRequest();
  xhr.addEventListener("load", callback);
  xhr.open("GET", `${BASE_URL}/user`);
  xhr.send();
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



/* _________
  |         |
  | C O R E |
  |_________|
*/
function init() {
  // Setup event listeners
  document.getElementById("log_button").addEventListener("click", login);
  document.getElementById("reg_button").addEventListener("click", register);
  document.getElementById("logout_button").addEventListener("click", logout);

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