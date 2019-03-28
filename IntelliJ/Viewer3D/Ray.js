
class Ray {

    constructor(start, dir) {

        this.start = start;
        this.direction = dir;
    }

    collideWith(object) {

        return new RaycastHit(false, Vector.zero, null);
    }

    trace() {








    }
}

class RaycastHit {

    constructor(hasHit, position, other) {
        this.hasHit = hasHit;
        this.position = position;
        this.other = other;
    }
}