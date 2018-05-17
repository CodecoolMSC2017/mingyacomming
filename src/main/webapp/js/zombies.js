let canvas;
let context;
let fps;

let gameData = {
  isRunning: true,
  mouse: {},
  zombies: [],
  particles: [],
  stars: []
};
let image = new Image();
image.src = "images/zombie.gif_c200";

/*
  Zombie Class
*/
function Zombie() {
  /*dimension*/ this.w = 210, this.h = 210;
  /*position */ this.x = random(0, canvas.width - this.w), this.y = random(0, canvas.height - this.h);
  /*velocity */ this.xV = random(-5, 5), this.yV = random(-5, 5);
  /*rotation */ this.rotation = random(0, 360), this.rotV = 0.01;

  this.update = function () {
    this.physics();
    this.checkCollision();
    this.draw();
  }

  this.physics = function () {
    this.x += this.xV;
    this.y += this.yV;
    this.rotation += this.rotV;
  }

  this.checkCollision = function () {
    if (this.x + this.w >= canvas.width || this.x <= 0) {
      this.xV = -this.xV;
      this.rotV = -this.rotV;
    }
    if (this.y + this.h >= canvas.height || this.y <= 0) {
      this.yV = -this.yV;
      this.rotV = -this.rotV;
    }
  }

  this.checkHit = function (x, y) {
    if (x >= this.x && x <= this.x + this.w) {  // Check for horizontal collision
      if (y >= this.y && y <= this.y + this.h) {  // Check for vertical collision
        this.death();
        return true;
      }
    }
    return false;
  }

  this.death = function () {
    for (let i = 0; i < 20; i++) {
      gameData.particles.push(new Particle(this.x + this.w / 2, this.y + this.h / 2));
    }
    let i = gameData.zombies.indexOf(this);
    gameData.zombies.splice(i, 1);
  }

  this.draw = function () {
    
    let rotatedImage = rotateImage(image, this.rotation);
    context.drawImage(rotatedImage, this.x, this.y);
    //context.drawImage(image, this.x, this.y, this.w, this.h);
  }
}

/*
  Particle Class
*/
function Particle(x, y) {
  this.size = 5;
  this.x = x, this.y = y;
  this.xV = random(-10, 10), this.yV = random(-10, 10);
  this.life = 40;

  this.update = function () {
    if(this.life-- < 0) {
      this.destroy();
    }
    this.physics();
    this.draw();
  }

  this.physics = function () {
    this.x += this.xV;
    this.y += this.yV;
  }

  this.destroy = function () {
    let i = gameData.particles.indexOf(this);
    gameData.particles.splice(i, 1);
  }

  this.draw = function () {
    context.beginPath();
    context.arc(this.x, this.y, this.size, this.size, (Math.PI / 180) * 1000, false);
    context.closePath();
    context.fillStyle = "#f00";
    context.strokeStyle = "#f00";
    context.fill();
    context.stroke();
  }
}


// Core
function update() {
  requestAnimationFrame(update);
  
  startTime = new Date();

  context.fillStyle = "#111";
  context.fillRect(0, 0, canvas.width, canvas.height);
  gameData.stars.forEach(star => {
    context.beginPath();
    context.arc(star.x, star.y, 1, 1, (Math.PI / 180) * 100, false);
    context.closePath();
    context.fillStyle = "#fff";
    context.strokeStyle = "#fff";
    context.fill();
    context.stroke();
  });
  gameData.zombies.forEach(zombie => zombie.update());
  gameData.particles.forEach(particle => particle.update());

  endTime = new Date();
  let updateTime = endTime - startTime;

  context.fillStyle = "#fff";
  context.font = "30px Arial";

  fps.fps = Math.floor(1000 / updateTime);
  context.fillText(fps.smoothFps, 5, 50);

}

function FPS() {
  setInterval(() => {
    this.smoothFps = this.fps;
  }, 200);
}

function click(event) {
  gameData.mouse.x = event.layerX;
  gameData.mouse.y = event.layerY;
  for (let i = gameData.zombies.length - 1; i >= 0; i--) {
    if (gameData.zombies[i].checkHit(gameData.mouse.x, gameData.mouse.y)) {
      break;
    }
  }
}

function init() {
  canvas = document.getElementById("canvas");
  canvas.addEventListener("click", click);
  resizeCanvas();

  context = canvas.getContext("2d");

  // Dummy data
  for (let i = 0; i < 5; i++) {
    gameData.zombies.push(new Zombie());
  }

  // Background
  for (let i = 0; i < 100; i++) {
    gameData.stars.push({
      x: random(0, canvas.width),
      y: random(0, canvas.height)
    });
  }


  fps = new FPS();
  //setInterval(update, 33);
  update();
}

function resizeCanvas() {
  canvas.width = window.innerWidth;
  canvas.height = window.innerHeight - 50;
}

function random(min, max) {
  return (Math.random() * (max - min)) + min;
}

function rotateImage(image, rotation) {
  let tempCanvas = document.createElement('canvas');
  let tempContext = tempCanvas.getContext('2d');

  let size = Math.max(image.width, image.height) + 25;
  tempCanvas.width = size;
  tempCanvas.height = size;

  tempContext.translate(size / 2, size / 2);
  tempContext.rotate(rotation + Math.PI / 2);
  tempContext.drawImage(image, -(image.width / 2), -(image.height / 2));

  return tempCanvas;
}

document.addEventListener("DOMContentLoaded", init);
window.addEventListener("resize", resizeCanvas);