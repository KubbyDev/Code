class Car {

    controller = new HumanController();
    position = [0,0];
    rotation = 0;
    width = 5;
    length = 12;
    color = "#FF0000";
    speed = 1;
    turnRate = 1;
    alive = true;

    corners = [[0,0],[0,0],[0,0],[0,0]];
    hitbox = [new Line(), new Line(), new Line(), new Line()];
    areCornersCorrect = false;

    //Recupere les ordres venant du controller et les applique
    update() {

    }

    constructor() {
    }

    setColor(color) {
        this.color = color;
        return this;
    }

    setPosition(position) {
        this.position = position;
        return this;
    }

    setPosition(x, y) {
        this.position = [x, y];
        return this;
    }

    setRotation(r) {
        this.rotation = r;
        return this;
    }

    getCorners() {
        if(!this.areCornersCorrect)
            this.calcCorners();

        return this.corners;
    }

    calcCorners() {
        let degR = this.rotation*Math.PI/180;

        this.corners[0] = [this.position[0] + this.length*Math.cos(degR) - this.width*Math.sin(degR),
            this.position[1] + this.length*Math.sin(degR) + this.width*Math.cos(degR)];

        this.corners[1] = [this.position[0] + this.length*Math.cos(degR) + this.width*Math.sin(degR),
            this.position[1] + this.length*Math.sin(degR) - this.width*Math.cos(degR)];

        this.corners[2] = [this.position[0] - this.length*Math.cos(degR) + this.width*Math.sin(degR),
            this.position[1] - this.length*Math.sin(degR) - this.width*Math.cos(degR)];

        this.corners[3] = [this.position[0] - this.length*Math.cos(degR) - this.width*Math.sin(degR),
            this.position[1] - this.length*Math.sin(degR) + this.width*Math.cos(degR)];

        this.areCornersCorrect = true;
    }

    moveForward(enginePower) {
        this.position[0] += Math.cos(this.rotation)*enginePower*this.speed;
        this.position[1] += Math.sin(this.rotation)*enginePower*this.speed;

        this.areCornersCorrect = false;
    }

    turn(input) {
        this.rotation += this.turnRate * input;

        this.areCornersCorrect = false;
    }

    isColliding(circuit) {

        this.getCorners();
        this.hitbox[0].setStartEnd(this.corners[0], this.corners[1]);
        this.hitbox[1].setStartEnd(this.corners[1], this.corners[2]);
        this.hitbox[2].setStartEnd(this.corners[2], this.corners[3]);
        this.hitbox[3].setStartEnd(this.corners[3], this.corners[0]);

        for(let line of this.hitbox)
            if(line.isColliding(circuit))
                return true;

        return false;
    }
}