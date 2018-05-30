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

function getSchedules(userId) {
  if (userId == undefined) {
    userId = document.getElementById("user_data").getAttribute("userId");
  }

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
      scheduleData.name,
      scheduleData.public
    );
    schedulesE.appendChild(scheduleE.getElement());
  });

  clearScheduleFields();
}

function switchToSchedulesPage(userId) {
  visibilityOfPages("none");
  document.getElementById("schedules_page").style.display = "block";

  document.getElementById("current_schedule").style.display = "none";
  document.getElementById("current_day").style.display = "none";

  getSchedules(userId);
}

function Schedule(id, name, isPublic) {
  this.id = id;
  this.name = name;
  this.isPublic = isPublic;

  this.getElement = function () {
    let scheduleE = document.createElement("div");
    scheduleE.setAttribute("id", this.id);
    scheduleE.className = "schedule";

    let nameE = document.createElement("h2");
    nameE.textContent = this.name;
    scheduleE.appendChild(nameE);

    let publicEl = document.createElement("p");
    publicEl.innerHTML = this.isPublic;
    publicEl.style.display = "none";
    scheduleE.appendChild(publicEl);



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
    deleteE.className = "fa fa-trash-alt";
    deleteE.addEventListener("click", () => deleteSchedule(this.id));
    scheduleE.appendChild(deleteE);

    return scheduleE;
  }
}

function editSchedule(scheduleId) {
  const schedulesEl = document.getElementById("schedules");
  const scheduleEl = schedulesEl.querySelector("div[id='" + scheduleId + "']");
  const name = scheduleEl.querySelector("h2").innerHTML;
  const isPublic = scheduleEl.querySelector("p").innerHTML;
  scheduleEl.textContent = "";

  const formEl = document.createElement("form");
  scheduleEl.appendChild(formEl);

  const inputNameEl = document.createElement("input");
  formEl.appendChild(inputNameEl);

  inputNameEl.setAttribute("type", "text");
  inputNameEl.setAttribute("placeholder", name);
  inputNameEl.setAttribute("size", "10");
  inputNameEl.setAttribute("id", "editElement");

  const checkPublicEl = document.createElement("input");
  checkPublicEl.setAttribute("type", "checkbox");
  checkPublicEl.setAttribute("id", "checkpublic");
  if (isPublic === "true") {
    checkPublicEl.setAttribute("checked", "");
  }
  checkPublicEl.innerHTML = "public";

  formEl.appendChild(checkPublicEl);

  const editE = document.createElement("i");
  editE.className = "fa fa-check";
  formEl.appendChild(editE);

  editE.addEventListener("click", () => sendEditSchedule(scheduleId));

}

function sendEditSchedule(scheduleId) {
  const schedule = {};
  const editEl = document.getElementById("editElement");
  const ispublic = document.getElementById("checkpublic");
  if ((editEl.value === "") && (schedule.isPublic === ispublic.checked)) {
    getSchedules();
    return;
  }
  if (editEl.value === "") {
    schedule.name = editEl.placeholder;
  }
  else {
    schedule.name = editEl.value;
  }
  schedule.isPublic = ispublic.checked;

  new Request("PUT", `/schedules/${scheduleId}`,
    JSON.stringify(schedule),
    getSchedules
  );
}



function init() {
  document.getElementById("create_schedule_button").addEventListener("click", createSchedule);
}

document.addEventListener("DOMContentLoaded", init);