
class Mesh {

    constructor(faces, points) {

        this.position = Vector.zero;
        this.faces = faces;
        this.points = points;
    }

    scale(value) {

        for(let p of this.points) {
            p.position.subtract(this.position).multiply(value).add(this.position);
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

    /**
     * Lis un .obj (Ne prend en compte que la position des points et leurs liaisons)
     * @param file: Le contenu du fichier en format obj
     */
    static parse(file) {

        let points = [];
        let faces = [];

        for(let line of file.split('\n')) {

            let words = line.split(' ');

            //Points
            if(words[0] === 'v')
                points.push(new Point(new Vector(parseFloat(words[1]), parseFloat(words[2]), parseFloat(words[3]))));

            //Faces
            if(words[0] === 'f')
                faces.push(new Face([
                    points[parseInt(words[1].split('/')[0]) -1],
                    points[parseInt(words[2].split('/')[0]) -1],
                    points[parseInt(words[3].split('/')[0]) -1]
                ]));
        }

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
        this.material = new Material(Color.fromRGB(0,248,255));
    }
}