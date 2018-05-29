function getGuestData() {
    const dayId = document.getElementById("current_day").getAttribute("value");
  
    new Request("GET", `/guest/${scheduleId}`,
      null,
      loadSlots
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
          slotData.task.id,
          slotData.task.name,
          slotData.task.description
        );
  
        slotsE.appendChild(slot.getElement());
      }
    }
    getAvailableTasks();
  }

function Slot(id, time, task_id, task_name, task_description) {
    this.id = id;
    this.time = time;
    this.task = new Task(task_id, task_name, task_description);
  
    this.getElement = function () {
      let slotE = document.createElement("div");
      slotE.className = "slot";
      slotE.setAttribute("time", this.time);
      slotE.setAttribute("data", ` - ${this.task.name} - ${this.task.description}`);
  
      let deleteE = document.createElement("i");
      deleteE.className = "fa fa-remove";
      deleteE.style.marginLeft = "5px";
      deleteE.addEventListener("click", () => deleteSlot(this.id));
      slotE.appendChild(deleteE);
  
      let checkE = document.createElement("i");
      checkE.className = "fa fa-circle-o";
      checkE.style.marginLeft = "5px";
      checkE.addEventListener("click", () => console.log("Checked"));
      slotE.appendChild(checkE);
  
      return slotE;
    }
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
    
    return dayE;
    }
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

      return scheduleE;
    }
}
