/* ____________________
  |                    |
  | Schedule method(s) |
  |____________________|
*/
// Sends the schedule creation request to the servlet
function createSchedule() {
  const scheduleData = getScheduleFields();

  const xhr = new XMLHttpRequest();
  xhr.addEventListener("load", getSchedules);
  xhr.open("POST", `${BASE_URL}/schedules`);
  xhr.send(JSON.stringify(scheduleData));
}

function deleteSchedule(scheduleId) {
  const xhr = new XMLHttpRequest();
  xhr.addEventListener("load", getSchedules);
  xhr.open("DELETE", `${BASE_URL}/schedules/${scheduleId}`);
  xhr.send();
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
    nameE.addEventListener("click", () => {
      document.getElementById("current_schedule").setAttribute("value", this.id);
      switchToDaysPage(this.id);
    });

    let editE= document.createElement("i");
    editE.textContent = "edit";

    editE.addEventListener("click", ()=> editSchedule(this.id));
    scheduleE.appendChild(editE);

    let deleteE = document.createElement("i");
    deleteE.className = "fa fa-remove";
    deleteE.addEventListener("click", () => deleteSchedule(this.id));
    scheduleE.appendChild(deleteE);

    return scheduleE;
  }
}

function editSchedule(scheduleId) {
  const scheduleEl = document.querySelector("div[id='" + scheduleId + "']");
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
  editE.innerHTML = "edit";
  formEl.appendChild(editE);

  editE.addEventListener("click", ()=> sendEditSchedule(scheduleId));

}

function sendEditSchedule(scheduleId) {
  const schedule = {};
  schedule.name = document.getElementById("editElement").value;
  const xhr = new XMLHttpRequest();
  xhr.addEventListener("load", getSchedules);
  xhr.open("PUT", `${BASE_URL}/schedules/${scheduleId}`);
  xhr.send(JSON.stringify(schedule));
}



function init() {
  document.getElementById("create_schedule_button").addEventListener("click", createSchedule);
}

document.addEventListener("DOMContentLoaded", init);