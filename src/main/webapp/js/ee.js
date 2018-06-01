function SpaceShip() {
  this.x = -250;
  this.y = -250;

  this.element = document.querySelector("#spaceship");
  document.body.appendChild(this.element);

  setInterval(() => {
    this.x += 0.6;
    this.y += 0.4;

    this.element.style.left = `${this.x}px`;
    this.element.style.top = `${this.y}px`;
  }, 30);
}

function starWars() {
  const audio = new Audio("audios/starwars.mp3");
  audio.play();
  new SpaceShip();
}

function init() {
  document.querySelector("#ee").addEventListener("click", starWars);
}

document.addEventListener("DOMContentLoaded", init);