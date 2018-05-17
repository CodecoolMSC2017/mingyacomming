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
  document.getElementById("user_data").innerHTML = "";
}

function getUserData(callback) {
  let xhr = new XMLHttpRequest();
  xhr.addEventListener("load", callback);
  xhr.open("GET", `${BASE_URL}/user`);
  xhr.send();
}



/* ________________
  |                |
  | Task method(s) |
  |________________|
*/
// Sends the task creation request to the servlet
function createTask() {
  const taskData = getTaskFields();

  let xhr = new XMLHttpRequest();
  xhr.addEventListener("load", getTasks);
  xhr.open("POST", `${BASE_URL}/tasks`);
  xhr.send(JSON.stringify(taskData));
}

// Gets the data from the task from
function getTaskFields() {
  let taskData = {};

  let nameE = document.getElementById("create_task_name_field");
  taskData.name = nameE.value;

  let descriptionE = document.getElementById("create_task_description_field");
  taskData.description = descriptionE.value;

  return taskData;
}

function clearTaskFields() {
  let nameE = document.getElementById("create_task_name_field");
  nameE.value = "";

  let descriptionE = document.getElementById("create_task_description_field");
  descriptionE.value = "";
}

function getTasks() {
  const xhr = new XMLHttpRequest();
  xhr.addEventListener("load", loadTasks);
  xhr.open("GET", `${BASE_URL}/tasks`);
  xhr.send();
}

function loadTasks() {
  const tasksData = JSON.parse(this.responseText);

  let tasksE = document.getElementById("tasks");
  tasksE.innerHTML = "";

  tasksData.forEach(taskData => {
    let taskE = new Task(
      taskData.id,
      taskData.name, taskData.description
    );
    tasksE.appendChild(taskE.getElement());
  });

  clearTaskFields();
}

function switchToTasksPage() {
  document.getElementById("tasks_page").style.display = "block";
  document.getElementById("schedules_page").style.display = "none";
  document.getElementById("days_page").style.display = "none";

  document.getElementById("current_schedule").style.display = "none";
  document.getElementById("current_day").style.display = "none";

  getTasks(loadTasks);
}

function Task(id, name, description) {
  this.id = id;
  this.name = name;
  this.description = description;

  this.getElement = function () {
    let taskE = document.createElement("div");
    taskE.className = "task";
    taskE.setAttribute("id", this.id);

    let nameE = document.createElement("h2");
    nameE.textContent = this.name;
    taskE.appendChild(nameE);

    let descriptionE = document.createElement("p");
    descriptionE.textContent = this.description;
    taskE.appendChild(descriptionE);

    return taskE;
  }
}




/* ____________________
  |                    |
  | Schedule method(s) |
  |____________________|
*/
// Sends the schedule creation request to the servlet
function createSchedule() {
  const scheduleData = getScheduleFields();

  let xhr = new XMLHttpRequest();
  xhr.addEventListener("load", getSchedules);
  xhr.open("POST", `${BASE_URL}/schedules`);
  xhr.send(JSON.stringify(scheduleData));
}

// Gets the data from the schedule form
function getScheduleFields() {
  let scheduleData = {};

  let nameE = document.getElementById("create_schedule_name_field");
  scheduleData.name = nameE.value;

  return scheduleData;
}

function clearScheduleFields() {
  let nameE = document.getElementById("create_schedule_name_field");
  nameE.value = "";
}

function getSchedules() {
  const xhr = new XMLHttpRequest();
  xhr.addEventListener("load", loadSchedules);
  xhr.open("GET", `${BASE_URL}/schedules`);
  xhr.send();
}

function loadSchedules() {
  const schedulesData = JSON.parse(this.responseText);

  let schedulesE = document.getElementById("schedules");
  schedulesE.innerHTML = "";

  schedulesData.forEach(scheduleData => {
    let scheduleE = new Schedule(
      scheduleData.id,
      scheduleData.name
    );
    schedulesE.appendChild(scheduleE.getElement());
  });

  clearScheduleFields();
}

function switchToSchedulesPage() {
  document.getElementById("tasks_page").style.display = "none";
  document.getElementById("schedules_page").style.display = "block";
  document.getElementById("days_page").style.display = "none";

  document.getElementById("current_schedule").style.display = "none";
  document.getElementById("current_day").style.display = "none";

  getSchedules(loadSchedules);
}

function Schedule(id, name) {
  this.id = id;
  this.name = name;

  this.getElement = function () {
    let scheduleE = document.createElement("div");
    scheduleE.setAttribute("id", this.id);
    scheduleE.className = "schedule";

    let nameE = document.createElement("h2");
    nameE.textContent = this.name;
    scheduleE.appendChild(nameE);

    // Events
    scheduleE.addEventListener("click", () => {
      document.getElementById("current_schedule").setAttribute("value", this.id);
      switchToDaysPage(this.id);
    });

    return scheduleE;
  }
}



/* _______________
  |               |
  | Day method(s) |
  |_______________|
*/
// Sends the day creation request to the servlet
function createDay() {
  const id = document.getElementById("current_schedule").getAttribute("value");
  const dayData = getDayFields();

  let xhr = new XMLHttpRequest();
  xhr.addEventListener("load", getDays);
  xhr.open("POST", `${BASE_URL}/days?scheduleId=${id}`);
  xhr.send(JSON.stringify(dayData));
}

// Gets the data from the day form
function getDayFields() {
  let dayData = {};

  let nameE = document.getElementById("create_day_name_field");
  dayData.name = nameE.value;

  return dayData;
}

function clearDayFields() {
  let nameE = document.getElementById("create_day_name_field");
  nameE.value = "";
}

function getDays() {
  const id = document.getElementById("current_schedule").getAttribute("value");

  const xhr = new XMLHttpRequest();
  xhr.addEventListener("load", loadDays);
  xhr.open("GET", `${BASE_URL}/days?scheduleId=${id}`);
  xhr.send();
}

function loadDays() {
  const daysData = JSON.parse(this.responseText);
  console.log(daysData);

  let daysE = document.getElementById("days");
  daysE.innerHTML = "";

  daysData.forEach(dayData => {
    let dayE = new Day(
      dayData.id,
      dayData.name
    );

    daysE.appendChild(dayE.getElement());
  });

  clearDayFields();
}

function switchToDaysPage(scheduleId) {
  document.getElementById("tasks_page").style.display = "none";
  document.getElementById("schedules_page").style.display = "none";
  document.getElementById("days_page").style.display = "block";

  console.log("hello");
  document.getElementById("current_schedule").style.display = "inline";

  getDays(scheduleId);
}

function Day(id, name) {
  this.id = id;
  this.name = name;

  this.getElement = function () {
    let dayE = document.createElement("div");
    dayE.setAttribute("id", this.id);
    dayE.className = "day";

    let nameE = document.createElement("h2");
    nameE.textContent = this.name;
    dayE.appendChild(nameE);

    return dayE;
  }
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

  document.getElementById("create_task_button").addEventListener("click", createTask);
  document.getElementById("create_schedule_button").addEventListener("click", createSchedule);
  document.getElementById("create_day_button").addEventListener("click", createDay);

  document.getElementById("my_tasks").addEventListener("click", switchToTasksPage);
  document.getElementById("my_schedules").addEventListener("click", switchToSchedulesPage);
  document.getElementById("current_schedule").addEventListener("click", () => {
    switchToDaysPage(this.getAttribute("value"));
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