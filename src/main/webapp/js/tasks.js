/* ________________
  |                |
  | Task method(s) |
  |________________|
*/
// Sends the task creation request to the servlet
function createTask() {
  new Request("POST", "/tasks",
    JSON.stringify(getTaskFields()),
    getTasks
  );
}

function deleteTask(taskId) {
  new Request("DELETE", `/tasks/${taskId}`,
    null,
    getTasks
  );
}

// Gets the data from the task from
function getTaskFields() {
  let taskData = {};

  let nameE = GetElement("#create_task_name_field");
  taskData.name = nameE.value;

  let descriptionE = GetElement("#create_task_description_field");
  taskData.description = descriptionE.value;

  return taskData;
}

function clearTaskFields() {
  let nameE = GetElement("#create_task_name_field");
  nameE.value = "";

  let descriptionE = GetElement("#create_task_description_field");
  descriptionE.value = "";
}

function getTasks() {
  new Request("GET", "/tasks",
    null,
    loadTasks
  );
}

function loadTasks() {
  const tasksData = JSON.parse(this.responseText);
  let tasksE = GetElement("#tasks");
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
  visibilityOfPages("none");
  GetElement("#tasks_page").style.display = "block";

  GetElement("#current_schedule").style.display = "none";
  GetElement("#current_day").style.display = "none";

  getTasks();
}

function Task(id, name, description) {
  this.id = id;
  this.name = name;
  this.description = description;

  this.getElement = function () {
    let taskE = CreateElement("div");
    taskE.className = "task";
    taskE.setAttribute("id", this.id);

    let nameE = CreateElement("h2");
    nameE.textContent = this.name;
    taskE.appendChild(nameE);

    let descriptionE = CreateElement("p");
    descriptionE.textContent = this.description;
    taskE.appendChild(descriptionE);

    let editEl = CreateElement("i");
    editEl.className = "fa fa-cog";
    editEl.addEventListener("click", () => editTask(this.id));
    taskE.appendChild(editEl);

    let deleteE = CreateElement("i");
    deleteE.className = "fa fa-trash-alt";
    deleteE.addEventListener("click", () => deleteTask(this.id));
    taskE.appendChild(deleteE);

    return taskE;
  }
}

function editTask(taskId) {
  const tasksEl = GetElement("#tasks");
  const taskEl = tasksEl.querySelector("div[id='" + taskId + "']");
  const name = taskEl.querySelector("h2").innerHTML;
  const description = taskEl.querySelector("p").innerHTML;
  taskEl.textContent = "";

  const formEl = CreateElement("form");
  taskEl.appendChild(formEl);

  const inputNameEl = CreateElement("input");
  formEl.appendChild(inputNameEl);

  inputNameEl.setAttribute("type", "text");
  inputNameEl.setAttribute("placeholder", name);
  inputNameEl.setAttribute("size", "10");
  inputNameEl.setAttribute("id", "editName");

  const inputDescriptionEl = CreateElement("input");
  formEl.appendChild(inputDescriptionEl);

  inputDescriptionEl.setAttribute("type", "text");
  inputDescriptionEl.setAttribute("placeholder", description);
  inputDescriptionEl.setAttribute("size", "10");
  inputDescriptionEl.setAttribute("id", "editDescription");

  const editE = CreateElement("i");
  editE.className = "fa fa-check";
  formEl.appendChild(editE);

  editE.addEventListener("click", () => sendEditTask(taskId));

}

function sendEditTask(taskId) {
  const task = {};
  const editNameEl = GetElement("#editName");
  const editDescriptionEl = GetElement("#editDescription");
  task.name = editNameEl.value;
  task.description = editDescriptionEl.value;
  if (task.name === "" && task.description === "") {
    getTasks();
    return;
  }

  if (task.name === "") {
    task.name = editNameEl.placeholder;
  }

  if (task.description === "") {
    task.description = editDescriptionEl.placeholder;
  }

  new Request("PUT", `/tasks/${taskId}`,
    JSON.stringify(task),
    getTasks
  );
}




function init() {
  GetElement("#create_task_button").addEventListener("click", createTask);
}

document.addEventListener("DOMContentLoaded", init);