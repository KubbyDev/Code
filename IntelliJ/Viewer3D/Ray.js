
class Ray {

    constructor(start, dir) {

        this.start = start;
        this.direction = dir;
    }

    collideWith(face) {

        //Soient S le point de depart du ray et u son vecteur directeur
        //Soient P le plan dans lequel est inscrit la face (le triangle constitue des points que l'on nommera A, B et C)
        //Soit n le vecteur normal de P, et M le point d'intersection entre le ray et P

        //Vecteurs AB, AC et BC
        let ab = Vector.subtract(face.points[1].position, face.points[0].position);
        let ac = Vector.subtract(face.points[2].position, face.points[0].position);
        let bc = Vector.subtract(face.points[2].position, face.points[1].position);

        //Vecteur normal de P
        let n = Vector.crossProduct(ab, ac);

        //Si le ray est parallele au plan
        let d_ndir = Vector.dotProduct(n, this.direction);
        if(d_ndir === 0)
            return new RaycastHit(false, Vector.zero, null, Vector.zero);

        //Soit t le reel verifiant M = u*t + S
        //t = (-n.S + s)/(n.u), s etant le 4e terme definissant le plan
        //Pour tout point M, M appartient a P <=> n.M = -s
        let t = -(Vector.dotProduct(n, this.start) - Vector.dotProduct(n, face.points[0].position))/d_ndir;

        //Si t est negatif la face est derriere la camera, donc on la touche pas
        if(t < 0.01)
            return new RaycastHit(false, Vector.zero, null, Vector.zero);

        //M est le point d'intersection du ray et de P
        let m = Vector.add(Vector.multiply(this.direction, t), this.start);

        //On verifie si M est dans le triangle
        let am = Vector.subtract(m, face.points[0].position);
        let bm = Vector.subtract(m, face.points[1].position);
        if (Vector.dotProduct(n, Vector.crossProduct(ab, am)) < 0
         || Vector.dotProduct(n, Vector.crossProduct(am, ac)) < 0
         || Vector.dotProduct(n, Vector.crossProduct(bc, bm)) < 0)
            return new RaycastHit(false, Vector.zero, null, Vector.zero);

        //Si tous ces tests sont passes c'est bon on a un hit
        return new RaycastHit(true, m, null, n.normalize());
    }

    trace() {

        let hits = [];

        //On calcule le hit pour chaque face de chaque objet

        for(let object of objects) {
            for (let face of object.faces) {
                let hit = this.collideWith(face);
                if (hit.hasHit) {
                    hit.other = object;
                    hits.push(hit);
                }
            }
        }

        //On recupere le hit le plus proche et on le renvoie

        let finalHit = new RaycastHit(false, Vector.zero, null);
        let closest = Infinity;

        for(let hit of hits) {
            let sqrDistance = Vector.sqrDistance(hit.position, cameraPosition);
            if (sqrDistance < closest) {
                closest = sqrDistance;
                finalHit = hit;
            }
        }

        return finalHit;
    }
}

class RaycastHit {

    constructor(hasHit, position, other, normal) {
        this.hasHit = hasHit;
        this.position = position;
        this.other = other;
        this.normal = normal;
    }
}