class Vector {

    constructor(x, y, z) {
        this.x = x,
            this.y = y,
            this.z = z;
    }

    static get zero() {
        return new Vector(0,0,0);
    }

    multiply(mult) {
        this.x *= mult;
        this.y *= mult;
        this.z *= mult;
    }

    divide(div) {
        this.x /= div;
        this.y /= div;
        this.z /= div;
    }

    add(other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
    }

    subtract(other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
    }

    dot(other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    static sqrDistance(a, b) {

        const sqr = x => x*x;
        return sqr(b.x - a.x) + sqr(b.y - a.y) + sqr(b.z - a.z);
    }

    static distance(a, b) {
        return Math.sqrt(this.sqrDistance(a, b));
    }

    rotate(h, v) {

    }

    toString() {
        return "x: " + this.x + ", y: " + this.y + ", z: " + this.z;
    }

    disp() {
        console.log(this.toString());
    }
}