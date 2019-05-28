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
        return new Vector(this.x*mult, this.y*mult, this.z*mult);
    }

    divide(div) {
        return new Vector(this.x/div, this.y/div, this.z/div);
    }

    add(other) {
        return new Vector(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    subtract(other) {
        return new Vector(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    static add(a, b) {
        return new Vector(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    static subtract(a, b) {
        return new Vector(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    static multiply(u, k) {
        return new Vector(u.x * k, u.y * k, u.z * k)
    }

    static divide(u, k) {
        return new Vector(u.x / k, u.y / k, u.z / k)
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

    /** The forward vector of (0,0) is (1,0,0)
     * @param h horizontal orientation
     * @param v vertical orientation
     * @returns The forward vector of the given orientation
     */
    static fromOrientation(h, v) {

        //Passage en radian
        h *= Math.PI/180;
        v *= Math.PI/180;

        //Calcul des sin et des cos
        let cos_v = Math.cos(v);
        let cos_h = Math.cos(h);
        let sin_v = Math.sin(v);
        let sin_h = Math.sin(h);

        //Calcul du vecteur
        return new Vector(cos_h*cos_v, sin_v, cos_v*sin_h);
    }

    static crossProduct(a, b) {
        return new Vector(
            a.y*b.z-a.z*b.y,
            a.z*b.x-a.x*b.z,
            a.x*b.y-a.y*b.x);
    }

    static dotProduct(a, b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    /**
     * @returns The same vector with length 1
     */
    normalized() {
        return this.divide(this.length());
    }

    scaleX(newX) {
        return this.multiply(newX/this.x);
    }

    scaleX(newY) {
        return this.multiply(newY/this.y);
    }

    scaleX(newZ) {
        return this.multiply(newZ/this.z);
    }

    rotate(h) {
        let cos = Math.cos(h *Math.PI/180);
        let sin = Math.sin(h *Math.PI/180);

        return new Vector(this.x*cos + this.z*sin, this.y, this.z*cos - this.x*sin);
    }

    //PAS SUR QUE CA MARCHE
    toOrientation() {
        let v = new Vector(this.x, this.y, this.z).normalized();
        return [Math.atan2(v.z, v.x)*180/Math.PI, Math.asin(v.y)*180/Math.PI];
    }

    toString() {
        return "x: " + this.x + ", y: " + this.y + ", z: " + this.z;
    }

    disp() {
        console.log(this.toString());
    }
}