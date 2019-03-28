class Vector {

    constructor(x, y, z) {

        this.x = x;
        this.y = y;
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

    sqrLength() {

        const sqr = x => x*x;
        return sqr(this.x) + sqr(this.y) + sqr(this.z);
    }

    length() {
        return Math.sqrt(this.sqrLength());
    }

    static length(v) {
        return v.length();
    }

    static sqrLength(v) {
        return v.sqrLength();
    }

    static sqrDistance(a, b) {
        return new Vector(b.x - a.x, b.y - a.y, b.z - a.z).sqrLength();
    }

    static distance(a, b) {
        return Math.sqrt(Vector.sqrDistance(a, b));
    }

    /**
     * @param horizontal orientation
     * @param vertical orientation
     * @returns The forward vector of the given orientation
     */
    static fromOrientation(h, v) {

        //Passage en radian
        h = h*Math.PI/180;
        v = v*Math.PI/180;

        //Calcul des sin et des cos
        let cos_v = Math.cos(v);
        let cos_h = Math.cos(h);
        let sin_v = Math.sin(v);
        let sin_h = Math.sin(h);

        //Calcul du vecteur
        return new Vector(cos_h*sin_v, cos_v, sin_h*sin_v);
    }

    /**
     * @returns The same vector with length 1
     */
    normalize() {
        return this.divide(this.length());
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