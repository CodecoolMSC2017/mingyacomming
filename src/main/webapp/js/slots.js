function createSlot(slot_time) {
  let slotData = {
    time: slot_time,
    dayId: document.getElementById("current_day").getAttribute("value"),
    taskId: DragTask.currentlyDraggedTask.id,
  };

  new Request("POST", `/slots?dayId=${slotData.dayId}`,
    JSON.stringify(slotData),
    getSlots
  );
}

function deleteSlot(slotId) {
  new Request("DELETE", `/slots/${slotId}`,
    null,
    getSlots
  );
}

function changeSlot(time, slotId) {
  let changeData = {
    time: time,
    taskId: DragTask.currentlyDraggedTask.id,
    dayId: document.querySelector("#current_day").getAttribute("value"),
    isChecked: false
  };

  new Request("PUT", `/slots/${slotId}`,
    JSON.stringify(changeData),
    getSlots
  );
}

function getSlots() {
  const dayId = document.getElementById("current_day").getAttribute("value");

  new Request("GET", `/slots?dayId=${dayId}`,
    null,
    loadSlots
  );
}

function checkSlot(slot) {
  const newData = {
    time: slot.time,
    taskId: slot.task.id,
    dayId: document.getElementById("current_day").getAttribute("value"),
    isChecked: !slot.is_checked
  };

  new Request("PUT", `/slots/${slot.id}`,
    JSON.stringify(newData),
    getSlots
  );
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
        slotData.slot.checked,
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
  new Request("GET", `/tasks`,
    null,
    loadAvailableTasks
  );
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
  document.getElementById("schedule_searcher_page").style.display = "none";
  document.getElementById("tasks_page").style.display = "none";
  document.getElementById("schedules_page").style.display = "none";
  document.getElementById("days_page").style.display = "none";
  document.getElementById("slots_page").style.display = "block";

  document.getElementById("current_day").style.display = "inline-block";

  getSlots();
}

// Classes
function Slot(id, time, is_checked, task_id, task_name, task_description) {
  this.id = id;
  this.time = time;
  this.is_checked = is_checked;
  this.task = new Task(task_id, task_name, task_description);

  this.getElement = function () {
    let slotE = document.createElement("div");
    slotE.className = "slot";
    slotE.setAttribute("time", this.time);
    slotE.setAttribute("data", ` - ${this.task.name} - ${this.task.description}`);

    let deleteE = document.createElement("i");
    deleteE.className = "fa fa-trash-alt";
    deleteE.style.marginLeft = "5px";
    deleteE.addEventListener("click", () => deleteSlot(this.id));
    slotE.appendChild(deleteE);

    let checkE = document.createElement("i");
    checkE.className = this.is_checked ? "far fa-check-circle" : "far fa-circle";
    checkE.style.marginLeft = "5px";
    checkE.addEventListener("click", () => checkSlot(this));
    slotE.appendChild(checkE);

    // Events
    slotE.addEventListener("dragover", event => {
      event.preventDefault();
    });

    slotE.addEventListener("dragenter", event => {
      if (event.target.className == "slot") {
        slotE.style.backgroundColor = "#f96d00";
        slotE.style.transform = "perspective(1000px) translateZ(100px)";
      }
    });

    slotE.addEventListener("dragleave", event => {
      if (event.target.className == "slot") {
        slotE.style.backgroundColor = null;
        slotE.style.transform = null;
      }
    });

    slotE.draggable = true;

    slotE.addEventListener("dragstart", event => {
      DragTask.currentlyDraggedTask = this.task;
    });

    slotE.addEventListener("drop", event => {
      changeSlot(this.time, this.id);
    });

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
        event.target.style.backgroundColor = null;
        event.target.style.transform = null;
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
    taskE.setAttribute("data", `${this.name} - ${this.description}`);

    // Events
    taskE.draggable = true;

    taskE.addEventListener("dragstart", event => {
      DragTask.currentlyDraggedTask = this;
    });

    return taskE;
  }
}
