
class Ray {

    constructor(start, dir) {

        this.start = start;
        this.direction = dir;
    }

    collideWith(face) {








        return new RaycastHit(false, Vector.zero, null);
    }

    trace() {

        let hits = [];

        //On calcule le hit pour chaque face de chaque objet

        for(let object of objects) {
            for (let face of object.faces) {
                let hit = this.collideWith(face);
                if (hit.hasHit)
                    hits.push(hit);
            }
        }

        //On recupere le hit le plus proche et on le renvoie

        let finalHit = new RaycastHit(false, Vector.zero, null);
        let closest = Infinity;

        for(let hit of hits) {
            let sqrDistance = Vector.sqrDistance(hit.position, cameraPosition) < closest;
            if (sqrDistance < closest) {
                closest = sqrDistance;
                finalHit = hit;
            }
        }

        return finalHit;
    }
}

class RaycastHit {

    constructor(hasHit, position, other) {
        this.hasHit = hasHit;
        this.position = position;
        this.other = other;
    }
}