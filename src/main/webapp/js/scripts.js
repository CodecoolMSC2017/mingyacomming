const BASE_URL = "http://localhost:8080/mingyacomming";

function Request(type, url, data, after) {
  const xhr = new XMLHttpRequest();
  xhr.addEventListener("load", after);
  xhr.open(type, `${BASE_URL}${url}`);
  xhr.send(data);
}

function GetElement(selector) {
  return document.querySelector(selector);
}

function CreateElement(elementName) {
  return document.createElement(elementName);
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
    username: GetElement("#log_username_field").value,
    password: GetElement("#log_password_field").value
  };
}

function onGoogleSignIn(googleUser) {
    const id_token = googleUser.getAuthResponse().id_token;
    //console.log(id_token);
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'glogin');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.addEventListener('load', changeUserTab);
    xhr.send('idtoken=' + id_token);
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
    username: GetElement("#reg_username_field").value,
    password: GetElement("#reg_password_field").value,
    email: GetElement("#reg_email_field").value
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

   const auth2 = gapi.auth2.getAuthInstance();
   auth2.signOut();
   //auth2.disconnect();
}

// UserTab
function changeUserTab() {
  const message = JSON.parse(this.responseText);

  if (this.status != 200) {
    addMessage(this.status, message.messege);
    return;
  }

  getUserData();
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

  //visibilityOfCreateForms(userData.role == "admin" ? "none" : "block");
  GetElement("#user_selector").style.display = userData.role == "admin" ? "inline-block" : "none";
  hideUserData();

  const userDataE = GetElement("#user_data");
  userDataE.setAttribute("userId", userData.id);

  let roleE = CreateElement("label");
  roleE.className = "role";
  roleE.style.display = "block";
  roleE.textContent = userData.role;
  userDataE.appendChild(roleE);

  let usernameE = CreateElement("label");
  usernameE.className = "username";
  usernameE.style.display = "block";
  usernameE.textContent = userData.name;
  userDataE.appendChild(usernameE);

  let coinsE = CreateElement("i");
  coinsE.className = "fas fa-coins";
  coinsE.style.color = "#ffd800"
  coinsE.style.display = "block";
  coinsE.textContent = " 0";
  userDataE.appendChild(coinsE);
}

function hideUserData() {
  GetElement("#buttons").style.display = "none";
  GetElement("#login_form").style.display = "none";
  GetElement("#user_data").style.display = "block";
  GetElement("#user_profile_buttons").style.display = "block";
  GetElement("#tabs").style.display = "block";
}

function deleteUserData() {
  GetElement("#buttons").style.display = "block";
  GetElement("#login_form").style.display = "block";
  GetElement("#user_data").style.display = "none";
  GetElement("#user_data").innerHTML = "";
  GetElement("#user_profile_buttons").style.display = "none";
  GetElement("#tabs").style.display = "none";

  visibilityOfPages("none");
}

function getUserData() {
  new Request("GET", "/user",
    null,
    loadUserData
  );
}

function visibilityOfPages(visibility) {
  GetElement("#user_selector_page").style.display = visibility;
  GetElement("#schedule_searcher_page").style.display = visibility;
  GetElement("#tasks_page").style.display = visibility;
  GetElement("#schedules_page").style.display = visibility;
  GetElement("#days_page").style.display = visibility;
  GetElement("#slots_page").style.display = visibility;
}

function visibilityOfCreateForms(visibility) {
  GetElement("#task_form").style.display = visibility;
  GetElement("#schedule_form").style.display = visibility;
  GetElement("#day_form").style.display = visibility;
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
  let messageE = CreateElement("div");

  if (status >= 200 && status < 300) {
    messageE.className = "message";
  } else if (status >= 500) {
    messageE.className = "message warning";
  } else {
    messageE.className = "message error";
  }

  messageE.textContent = `${status} : ${content}`;

  let messagesE = GetElement("#messages");
  messagesE.appendChild(messageE);

  setTimeout(messageE.remove(), 5000);
}



/* _________
  |         |
  | C O R E |
  |_________|
*/
function init() {
  getUserData();

  // Setup event listeners
  GetElement("#log_button").addEventListener("click", login);
  GetElement("#reg_button").addEventListener("click", register);
  GetElement("#logout_button").addEventListener("click", logout);

  GetElement("#user_selector").addEventListener("click", () => { });
  GetElement("#schedule_searcher").addEventListener("click", switchToSearcher);;
  GetElement("#my_tasks").addEventListener("click", switchToTasksPage);
  GetElement("#my_schedules").addEventListener("click", switchToSchedulesPage);
  GetElement("#current_schedule").addEventListener("click", () => {
    switchToDaysPage(GetElement("#current_schedule").getAttribute("value"));
  });
  GetElement("#current_day").addEventListener("click", () => {
    switchToSlotsPage(GetElement("#current_day").getAttribute("value"));
  });

  GetElement("#log_tab").addEventListener("click", () => {
    GetElement("#login_form").style.display = "block";
    GetElement("#register_form").style.display = "none";
  });
  GetElement("#reg_tab").addEventListener("click", () => {
    GetElement("#register_form").style.display = "block";
    GetElement("#login_form").style.display = "none";
  });

  GetElement("#game_button").addEventListener("click", () => {
    let content = GetElement("#content");
    if (content.style.display == "none") {
      content.style.display = "block";
    } else {
      content.style.display = "none";
    }
  });
}

document.addEventListener("DOMContentLoaded", init);