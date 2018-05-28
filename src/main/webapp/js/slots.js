function createSlot(slot_time) {
  let slotData = {
    time: slot_time,
    dayId: document.getElementById("current_day").getAttribute("value"),
    taskId: DragTask.currentlyDraggedTask.id,
  };

  console.log(slotData);

  const xhr = new XMLHttpRequest();
  xhr.addEventListener("load", getSlots);
  xhr.open("POST", `${BASE_URL}/slots?dayId=${slotData.dayId}`);
  xhr.send(JSON.stringify(slotData));
}

function deleteSlot(slotId) {
  const xhr = new XMLHttpRequest();
  xhr.addEventListener("load", getSlots);
  xhr.open("DELETE", `${BASE_URL}/slots/${slotId}`);
  xhr.send();
}

function getSlots() {
  const dayId = document.getElementById("current_day").getAttribute("value");

  const xhr = new XMLHttpRequest();
  xhr.addEventListener("load", loadSlots);
  xhr.open("GET", `${BASE_URL}/slots?dayId=${dayId}`);
  xhr.send();
}

function loadSlots() {
  const slotTasksData = JSON.parse(this.responseText);

  let slotsE = document.getElementById("slots");
  slotsE.innerHTML = "";

  let headerE = document.createElement("h1");
  headerE.textContent = "Tasks";
  slotsE.appendChild(headerE);

  for (let i = 0; i < 24; i++) {
    let slotData = slotTasksData.find(slotTask => slotTask.slot.time === i);
    if (slotData == undefined) {
      let placeHolder = new PlaceHolder(i);

      slotsE.appendChild(placeHolder.getElement());
    } else {
      let slot = new Slot(
        slotData.slot.id,
        slotData.slot.time,
        slotData.task.id,
        slotData.task.name,
        slotData.task.description
      );

      slotsE.appendChild(slot.getElement());
    }
  }

  getAvailableTasks();
}

// Available slots
function getAvailableTasks() {
  const xhr = new XMLHttpRequest();
  xhr.addEventListener("load", loadAvailableTasks);
  xhr.open("GET", `${BASE_URL}/tasks`);
  xhr.send();
}

function loadAvailableTasks() {
  const tasksData = JSON.parse(this.responseText);

  let tasksE = document.getElementById("available_tasks");
  tasksE.innerHTML = "";
  let headerE = document.createElement("h1");
  headerE.textContent = "Available Tasks";
  tasksE.appendChild(headerE);

  tasksData.forEach(taskData => {
    let taskE = new DragTask(
      taskData.id,
      taskData.name, taskData.description
    );
    tasksE.appendChild(taskE.getElement());
  });
}

function switchToSlotsPage(dayId) {
  document.getElementById("tasks_page").style.display = "none";
  document.getElementById("schedules_page").style.display = "none";
  document.getElementById("days_page").style.display = "none";
  document.getElementById("slots_page").style.display = "block";

  document.getElementById("current_day").style.display = "inline-block";

  getSlots();
}

// Classes
function Slot(id, time, task_id, task_name, task_description) {
  this.id = id;
  this.time = time;
  this.task = new Task(task_id, task_name, task_description);

  this.getElement = function () {
    let slotE = document.createElement("div");
    slotE.className = "slot";
    slotE.setAttribute("time", this.time);

    let dataE = document.createElement("p");
    dataE.style.display = "inline";
    dataE.textContent = `${this.time} - ${this.task.name} - ${this.task.description} `;
    slotE.appendChild(dataE);

    let deleteE = document.createElement("i");
    deleteE.className = "fa fa-remove";
    deleteE.style.marginLeft = "5px";
    deleteE.addEventListener("click", () => deleteSlot(this.id));
    slotE.appendChild(deleteE);

    return slotE;
  }
}

function PlaceHolder(time) {
  this.time = time;

  this.getElement = function () {
    let spaceHolderE = document.createElement("div");
    spaceHolderE.className = "slot";
    spaceHolderE.setAttribute("time", this.time);

    // Events
    spaceHolderE.addEventListener("dragover", event => {
      event.preventDefault();
    });

    spaceHolderE.addEventListener("dragenter", event => {
      if (event.target.className == "slot") {
        event.target.style.backgroundColor = "#5f5";
        event.target.style.transform = "perspective(1000px) translateZ(100px)";
      }
    });

    spaceHolderE.addEventListener("dragleave", event => {
      if (event.target.className == "slot") {
        event.target.style.backgroundColor = "#777";
        event.target.style.transform = "perspective(1000px) translateZ(0px)";
      }
    });

    spaceHolderE.addEventListener("drop", event => {
      createSlot(time);
    });

    return spaceHolderE;
  }
}

function DragTask(id, name, description) {
  let currentlyDraggedTask;
  this.id = id;
  this.name = name;
  this.description = description;

  this.getElement = function () {
    let taskE = document.createElement("div");
    taskE.className = "slot";
    taskE.setAttribute("id", this.id);

    let dataE = document.createElement("p");
    dataE.textContent = `${this.name} : ${this.description}`;
    taskE.appendChild(dataE);

    // Events
    taskE.draggable = true;

    taskE.addEventListener("dragstart", event => {
      DragTask.currentlyDraggedTask = this;
    });

    return taskE;
  }
}
