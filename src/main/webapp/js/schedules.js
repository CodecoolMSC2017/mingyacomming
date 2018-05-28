/* ____________________
  |                    |
  | Schedule method(s) |
  |____________________|
*/
// Sends the schedule creation request to the servlet
function createSchedule() {
  new Request("POST", "/schedules",
    JSON.stringify(getScheduleFields()),
    getSchedules
  );
}

function deleteSchedule(scheduleId) {
  new Request("DELETE", `/schedules/${scheduleId}`,
    null,
    getSchedules
  );
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
  new Request("GET", "/schedules",
    null,
    loadSchedules
  );
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
  document.getElementById("slots_page").style.display = "none";

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
    nameE.addEventListener("click", () => {
      let currentScheduleE = document.getElementById("current_schedule");
      currentScheduleE.setAttribute("value", this.id);
      currentScheduleE.textContent = "Schedule: " + this.name;
      switchToDaysPage(this.id);
    });

    let editE = document.createElement("i");
    editE.className = "fa fa-cog";

    editE.addEventListener("click", () => editSchedule(this.id));
    scheduleE.appendChild(editE);

    let deleteE = document.createElement("i");
    deleteE.className = "fa fa-remove";
    deleteE.addEventListener("click", () => deleteSchedule(this.id));
    scheduleE.appendChild(deleteE);

    return scheduleE;
  }
}

function editSchedule(scheduleId) {
  const schedulesEl = document.getElementById("schedules");
  const scheduleEl = schedulesEl.querySelector("div[id='" + scheduleId + "']");
  const name = scheduleEl.querySelector("h2").innerHTML;
  scheduleEl.textContent = "";

  const formEl = document.createElement("form");
  scheduleEl.appendChild(formEl);

  const inputNameEl = document.createElement("input");
  formEl.appendChild(inputNameEl);

  inputNameEl.setAttribute("type", "text");
  inputNameEl.setAttribute("placeholder", name);
  inputNameEl.setAttribute("size", "10");
  inputNameEl.setAttribute("id", "editElement");

  const editE = document.createElement("i");
  editE.className = "fa fa-check";
  formEl.appendChild(editE);

  editE.addEventListener("click", () => sendEditSchedule(scheduleId));

}

function sendEditSchedule(scheduleId) {
  const schedule = {};
  const editEl = document.getElementById("editElement");
  schedule.name = editEl.value;

  if (schedule.name === "") {
    getSchedules();
    return;
  }

  new Request("PUT", `schedules/${scheduleId}`,
    JSON.stringify(schedule),
    getSchedules
  );
}



function init() {
  document.getElementById("create_schedule_button").addEventListener("click", createSchedule);
}

document.addEventListener("DOMContentLoaded", init);