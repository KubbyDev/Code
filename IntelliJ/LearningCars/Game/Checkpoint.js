class Checkpoint {

    line;
    center;
    rotation;
    halfLenght = 30;

    static color = "#00ff47";

    constructor(center, rotation, length) {
        this.halfLenght = length/2;
        this.rotation = rotation;
        this.center = center;
        this.line = new Line().setVisible(true).setColor(Checkpoint.color);
        this.line.start = center.add(Vector.fromOrientation(rotation).multiply(this.halfLenght));
        this.line.end = center.add(Vector.fromOrientation(rotation+180).multiply(this.halfLenght));
    }

    rotate(angle) {
        this.rotation += angle;
        this.line.start = this.center.add(Vector.fromOrientation(this.rotation).multiply(this.halfLenght));
        this.line.end = this.center.add(Vector.fromOrientation(this.rotation+180).multiply(this.halfLenght));
    }

    stretch(add) {
        this.halfLenght += add;
        this.line.start = this.center.add(Vector.fromOrientation(this.rotation).multiply(this.halfLenght));
        this.line.end = this.center.add(Vector.fromOrientation(this.rotation+180).multiply(this.halfLenght));
    }

    draw() {
        this.line.draw();
    }
}