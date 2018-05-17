/* _______________
  |               |
  | Day method(s) |
  |_______________|
*/
// Sends the day creation request to the servlet
function createDay() {
  const id = document.getElementById("current_schedule").getAttribute("value");
  const dayData = getDayFields();

  const xhr = new XMLHttpRequest();
  xhr.addEventListener("load", getDays);
  xhr.open("POST", `${BASE_URL}/days?scheduleId=${id}`);
  xhr.send(JSON.stringify(dayData));
}

function deleteDay(dayId) {
  const xhr = new XMLHttpRequest();
  xhr.addEventListener("load", getDays);
  xhr.open("DELETE", `${BASE_URL}/days/${dayId}`);
  xhr.send();
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

    // Events
    nameE.addEventListener("click", () => {
      document.getElementById("current_day").setAttribute("value", this.id);
      switchToSlotsPage(this.id);
    });

    let deleteE = document.createElement("i");
    deleteE.className = "fa fa-remove";
    deleteE.addEventListener("click", () => deleteDay(this.id));
    dayE.appendChild(deleteE);

    return dayE;
  }
}



function init() {
  document.getElementById("create_day_button").addEventListener("click", createDay);
}

document.addEventListener("DOMContentLoaded", init);