/* _______________
  |               |
  | Day method(s) |
  |_______________|
*/
// Sends the day creation request to the servlet
function createDay() {
  const id = document.getElementById("current_schedule").getAttribute("value");
  const dayData = getDayFields();

  new Request("POST", `/days?scheduleId=${id}`,
    JSON.stringify(dayData),
    getDays
  );
}

function deleteDay(dayId) {
  new Request("DELETE", `/days/${dayId}`,
    null,
    getDays
  );
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

  new Request("GET", `/days?scheduleId=${id}`,
    null,
    loadDays
  );
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

function editDay(dayId) {
  const daysEl = document.getElementById("days");
  const dayEl = daysEl.querySelector("div[id='" + dayId + "']");
  const name = dayEl.querySelector("h2").innerHTML;
  dayEl.textContent = "";

  const formEl = document.createElement("form");
  dayEl.appendChild(formEl);

  const inputNameEl = document.createElement("input");
  formEl.appendChild(inputNameEl);

  inputNameEl.setAttribute("type", "text");
  inputNameEl.setAttribute("placeholder", name);
  inputNameEl.setAttribute("size", "10");
  inputNameEl.setAttribute("id", "editName");

  const editE = document.createElement("i");
  editE.className = "fa fa-check";
  formEl.appendChild(editE);

  editE.addEventListener("click", () => sendEditDay(dayId));

}

function sendEditDay(dayId) {
  const day = {};
  const editNameEl = document.getElementById("editName");
  day.name = editNameEl.value;

  if (day.name === "") {
    getDays();
    return;
  }

  if (day.name === "") {
    day.name = editNameEl.placeholder;
  }

  new Request("PUT", `/day/${dayId}`,
    JSON.stringify(day),
    getDays
  );
}

function switchToDaysPage(scheduleId) {
  document.getElementById("tasks_page").style.display = "none";
  document.getElementById("schedules_page").style.display = "none";
  document.getElementById("days_page").style.display = "block";
  document.getElementById("slots_page").style.display = "none";

  document.getElementById("current_schedule").style.display = "inline-block";
  document.getElementById("current_day").style.display = "none";

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
      let currentDayE = document.getElementById("current_day");
      currentDayE.setAttribute("value", this.id);
      currentDayE.textContent = "Day: " + this.name;
      switchToSlotsPage(this.id);
    });

    let editE = document.createElement("i");
    editE.className = "fa fa-cog";
    editE.addEventListener("click", () => editDay(this.id));
    dayE.appendChild(editE);

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