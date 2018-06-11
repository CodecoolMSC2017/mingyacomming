/* _______________
  |               |
  | Day method(s) |
  |_______________|
*/
// Sends the day creation request to the servlet
function createDay() {
  const id = GetElement("#current_schedule").getAttribute("value");
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

  let nameE = GetElement("#create_day_name_field");
  dayData.name = nameE.value;

  return dayData;
}

function clearDayFields() {
  let nameE = GetElement("#create_day_name_field");
  nameE.value = "";
}

function getDays() {
  const id = GetElement("#current_schedule").getAttribute("value");

  new Request("GET", `/days?scheduleId=${id}`,
    null,
    loadDays
  );
}

function loadDays() {
  const daysData = JSON.parse(this.responseText);

  let daysE = GetElement("#days");
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
  const daysEl = GetElement("#days");
  const dayEl = daysEl.querySelector("div[id='" + dayId + "']");
  const name = dayEl.querySelector("h2").innerHTML;
  dayEl.textContent = "";

  const formEl = CreateElement("form");
  dayEl.appendChild(formEl);

  const inputNameEl = CreateElement("input");
  formEl.appendChild(inputNameEl);

  inputNameEl.setAttribute("type", "text");
  inputNameEl.setAttribute("placeholder", name);
  inputNameEl.setAttribute("size", "10");
  inputNameEl.setAttribute("id", "editName");

  const editE = CreateElement("i");
  editE.className = "fa fa-check";
  formEl.appendChild(editE);

  editE.addEventListener("click", () => sendEditDay(dayId));

}

function sendEditDay(dayId) {
  const day = {};
  const editNameEl = GetElement("#editName");
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
  visibilityOfPages("none");
  GetElement("#days_page").style.display = "block";

  GetElement("#current_schedule").style.display = "inline-block";
  GetElement("#current_day").style.display = "none";

  getDays(scheduleId);
}

function Day(id, name) {
  this.id = id;
  this.name = name;

  this.getElement = function () {
    let dayE = CreateElement("div");
    dayE.setAttribute("id", this.id);
    dayE.className = "day";

    let nameE = CreateElement("h2");
    nameE.textContent = this.name;
    dayE.appendChild(nameE);

    // Events
    nameE.addEventListener("click", () => {
      let currentDayE = GetElement("#current_day");
      currentDayE.setAttribute("value", this.id);
      currentDayE.textContent = "Day: " + this.name;
      switchToSlotsPage(this.id);
    });

    let editE = CreateElement("i");
    editE.className = "fa fa-cog";
    editE.addEventListener("click", () => editDay(this.id));
    dayE.appendChild(editE);

    let deleteE = CreateElement("i");
    deleteE.className = "fa fa-trash-alt";
    deleteE.addEventListener("click", () => deleteDay(this.id));
    dayE.appendChild(deleteE);

    return dayE;
  }
}



function init() {
  GetElement("#create_day_button").addEventListener("click", createDay);
}

document.addEventListener("DOMContentLoaded", init);