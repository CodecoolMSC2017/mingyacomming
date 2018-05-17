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

function deleteTask(taskId) {
  let xhr = new XMLHttpRequest();
  xhr.addEventListener("load", getTasks);
  xhr.open("DELETE", `${BASE_URL}/tasks/${taskId}`);
  xhr.send();
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

    let deleteE = document.createElement("i");
    deleteE.className = "fa fa-remove";
    deleteE.addEventListener("click", () => deleteTask(this.id));
    taskE.appendChild(deleteE);

    return taskE;
  }
}



function init() {
  document.getElementById("create_task_button").addEventListener("click", createTask);
}

document.addEventListener("DOMContentLoaded", init);