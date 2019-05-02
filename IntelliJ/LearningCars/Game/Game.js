const canvas = document.getElementById("myCanvas");
const ctx = canvas.getContext("2d");

let cars = [];
let circuit = circuit1;

function draw() {

    ctx.clearRect(0,0, canvas.width, canvas.height);

    for(let car of cars)
        car.draw();

    circuit.draw();

}

function drawRect(points, color) {
    ctx.fillStyle = color;
    ctx.beginPath();
    ctx.moveTo(points[0][0], points[0][1]);
    ctx.lineTo(points[1][0], points[1][1]);
    ctx.lineTo(points[2][0], points[2][1]);
    ctx.lineTo(points[3][0], points[3][1]);
    ctx.closePath();
    ctx.fill();
}