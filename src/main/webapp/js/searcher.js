/* ___________________
  |                   |
  | Schedule searcher |
  |___________________|
*/
var currentSchedule;

function switchToSearcher() {
  document.getElementById("schedule_searcher_page").style.display = "block";
  document.getElementById("tasks_page").style.display = "none";
  document.getElementById("schedules_page").style.display = "none";
  document.getElementById("days_page").style.display = "none";
  document.getElementById("slots_page").style.display = "none";

  document.getElementById("current_schedule").style.display = "none";
  document.getElementById("current_day").style.display = "none";
}

function getPublicSchedule() {
  const scheduleId = document.getElementById("search_schedule_id_field").value;
  new Request("GET", `/guest/${scheduleId}`,
    null,
    loadAllData);
}

function loadAllData() {
  currentSchedule = JSON.parse(this.responseText);
  loadInspectDays();
  console.log(currentSchedule);
}

function loadInspectDays() {
  let searchContent = document.getElementById("search_days");
  searchContent.innerHTML = "";

  currentSchedule.days.forEach(day => {
    searchContent.appendChild(new InspectDay(day.id, day.name).getElement());
  });
}

function loadInspectSlots(name, dayId) {
  let slotTasksData = currentSchedule.slotTasks.filter(slotTask => slotTask.slot.day_id == dayId);
  
  let slotsE = document.getElementById("search_slots");
  slotsE.innerHTML = "";

  let headerE = document.createElement("h1");
  headerE.textContent = name;
  slotsE.appendChild(headerE);

  for (let i = 0; i < 24; i++) {
    let slotData = slotTasksData.find(slotTask => slotTask.slot.time === i);
    if (slotData == undefined) {
      let placeHolder = new PlaceHolder(i);

      slotsE.appendChild(placeHolder.getElement());
    } else {
      let slot = new InspectSlot(
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
}

function InspectDay(id, name) {
  this.id = id;
  this.name = name;

  this.getElement = function () {
    let dayE = document.createElement("div");
    dayE.setAttribute("id", this.id);
    dayE.className = "day";

    let nameE = document.createElement("h2");
    nameE.textContent = this.name;
    dayE.appendChild(nameE);

    // Events
    nameE.addEventListener("click", () => {
      loadInspectSlots(this.name, this.id);
    });

    return dayE;
  }
}

function InspectSlot(id, time, is_checked, task_id, task_name, task_description) {
  this.id = id;
  this.time = time;
  this.is_checked = is_checked;
  this.task = new Task(task_id, task_name, task_description);

  this.getElement = function () {
    let slotE = document.createElement("div");
    slotE.className = "slot";
    slotE.setAttribute("time", this.time);
    slotE.setAttribute("data", ` - ${this.task.name} - ${this.task.description}`);

    let checkE = document.createElement("i");
    checkE.className = this.is_checked ? "far fa-check-circle" : "far fa-circle";
    checkE.style.marginLeft = "5px";
    slotE.appendChild(checkE);

    return slotE;
  }
}

function init() {
  document.getElementById("search_schedule_button").addEventListener("click", getPublicSchedule);
}

document.addEventListener("DOMContentLoaded", init);