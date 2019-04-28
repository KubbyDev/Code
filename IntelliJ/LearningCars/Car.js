class Car {

    position = [0,0];
    rotation = 0;
    width = 5;
    length = 12;
    color = "#FF0000";

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
        let corners = [[0,0],[0,0],[0,0],[0,0]];
        let degR = this.rotation*Math.PI/180;

        corners[0] = [this.position[0] + this.length*Math.cos(degR) - this.width*Math.sin(degR),
            this.position[1] + this.length*Math.sin(degR) + this.width*Math.cos(degR)];

        corners[1] = [this.position[0] + this.length*Math.cos(degR) + this.width*Math.sin(degR),
            this.position[1] + this.length*Math.sin(degR) - this.width*Math.cos(degR)];

        corners[2] = [this.position[0] - this.length*Math.cos(degR) + this.width*Math.sin(degR),
            this.position[1] - this.length*Math.sin(degR) - this.width*Math.cos(degR)];

        corners[3] = [this.position[0] - this.length*Math.cos(degR) - this.width*Math.sin(degR),
            this.position[1] - this.length*Math.sin(degR) + this.width*Math.cos(degR)];

        return corners;
    }
}