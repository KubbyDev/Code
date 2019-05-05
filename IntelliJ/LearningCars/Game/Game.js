const canvas = document.getElementById("myCanvas");
const ctx = canvas.getContext("2d");

let cars = [];
let circuit = circuit1;

function update() {

    for(let car of cars)
        if(car.alive)
            car.update(circuit.lines);

}

function draw() {

    ctx.clearRect(0,0, canvas.width, canvas.height);

    for(let car of cars)
        car.draw();

    circuit.draw();

}

function drawRect(points, color) {
    ctx.fillStyle = color;
    ctx.beginPath();
    ctx.moveTo(points[0].x, points[0].y);
    ctx.lineTo(points[1].x, points[1].y);
    ctx.lineTo(points[2].x, points[2].y);
    ctx.lineTo(points[3].x, points[3].y);
    ctx.closePath();
    ctx.fill();
}