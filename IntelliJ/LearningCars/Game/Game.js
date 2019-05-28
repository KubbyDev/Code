class Game {

    cars = [];
    circuit = Circuit.SIMPLE;

    initCars() {

        this.cars.forEach(car => car.setPosition(this.circuit.startPos.x, this.circuit.startPos.y));
        this.cars.forEach(car => car.setRotation(this.circuit.startRot));
    }

    update() {

        for(let car of this.cars)
            if(car.alive)
                car.update(this.circuit);

    }

    draw() {

        ctx.clearRect(0,0, canvas.width, canvas.height);

        for(let car of this.cars)
            car.draw();

        this.circuit.draw();
    }

    drawNetwork(network) {
        NetworkDrawer.draw(network, this.circuit.networkPosition[1].x, this.circuit.networkPosition[1].y, this.circuit.networkPosition[0]);
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