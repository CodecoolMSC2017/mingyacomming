const BASE_URL = "http://localhost:8080/mingyacomming";

// Login method(s)
function login() {
  const logData = getLogFields();
  let xhr = new XMLHttpRequest();

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


// Core
function init() {
  document.getElementById("log_button").addEventListener("click", login);
  document.getElementById("reg_button").addEventListener("click", register);
}

document.addEventListener("DOMContentLoaded", init);