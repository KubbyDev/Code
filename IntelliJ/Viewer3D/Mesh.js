
class Mesh {

    constructor(faces, points) {

        this.position = Vector.zero;
        this.faces = faces;
        this.points = points;
        this.color = "#00f8ff";
    }

    scale(value) {

        for(let p of this.points) {
            p.position.subtract(this.position);
            p.position.multiply(value);
            p.position.add(this.position);
        }
    }

    static get cube() {

        let points = [
            new Point(new Vector(-0.5,-0.5,-0.5)),
            new Point(new Vector(-0.5,-0.5,0.5)),
            new Point(new Vector(-0.5,0.5,0.5)),
            new Point(new Vector(-0.5,0.5,-0.5)),
            new Point(new Vector(0.5,0.5,-0.5)),
            new Point(new Vector(0.5,0.5,0.5)),
            new Point(new Vector(0.5,-0.5,0.5)),
            new Point(new Vector(0.5,-0.5,-0.5))
        ];

        let faces = [
            new Face([points[0], points[1], points[2]]),
            new Face([points[0], points[3], points[2]]),
            new Face([points[0], points[7], points[4]]),
            new Face([points[0], points[3], points[4]]),
            new Face([points[5], points[6], points[7]]),
            new Face([points[5], points[4], points[7]]),
            new Face([points[1], points[2], points[5]]),
            new Face([points[1], points[6], points[5]]),
            new Face([points[2], points[5], points[4]]),
            new Face([points[2], points[3], points[4]]),
            new Face([points[0], points[1], points[6]]),
            new Face([points[0], points[7], points[6]])
        ];

        return new Mesh(faces, points);
    }
}

class Point {

    constructor(position) {
        this.position = position;
    }
}

class Face {

    constructor(points) {
        this.points = points;
    }
}