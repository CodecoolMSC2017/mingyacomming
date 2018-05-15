const BASE_URL = "http://localhost:8080/mingyacomming";

// Login method(s)
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


// Register method(s)
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


// UserTab
function changeUserTab() {
  const message = JSON.parse(this.responseText);
  createMessage(message);

  if (message.response != 200) {
    return;
  }
  console.log("logged in");
  document.getElementById("login_form").style.display = "none";
}

function getUserData() {
  let xhr = new XMLHttpRequest();
  xhr.addEventListener("load", );
  xhr.open("POST", `${BASE_URL}/register`);
  xhr.send(JSON.stringify(regData));
}

// Create task method(s)
function createTask() {
  const taskData = getCreateTaskFields();

  let xhr = new XMLHttpRequest();
  xhr.addEventListener("load", checkResp);
  xhr.open("POST", `${BASE_URL}/tasks`);
  xhr.send(JSON.stringify(taskData));
}

function getCreateTaskFields() {
  let taskData = {};

  let nameE = document.getElementById("create_task_name_field");
  taskData.name = nameE.value;

  let descriptionE = document.getElementById("create_task_description_field");
  taskData.description = descriptionE.value;

  return taskData;
}


// Message method(s)
function checkResp() {
  const message = JSON.parse(this.responseText);
  console.log(message);
}

function createMessage(message) {
  console.log(message);
}

function addMessage(content) {
  
}

// Core
function init() {
  document.getElementById("log_button").addEventListener("click", login);
  document.getElementById("reg_button").addEventListener("click", register);
  document.getElementById("create_task_button").addEventListener("click", createTask);

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