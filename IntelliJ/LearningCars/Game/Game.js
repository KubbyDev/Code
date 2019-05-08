const canvas = document.getElementById("myCanvas");
const ctx = canvas.getContext("2d");

class Game {

    cars = [];
    circuit = circuit1;

    update() {

        for(let car of this.cars)
            if(car.alive)
                car.update(this.circuit.lines);

    }

    draw() {

        ctx.clearRect(0,0, canvas.width, canvas.height);

        for(let car of this.cars)
            car.draw();

        this.circuit.draw();

    }

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