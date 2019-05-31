class Color {

    string = null;
    vector = null;

    constructor(string, vector) {
        if(string && vector) {
            this.string = string;
            this.vector = vector;
        }
    }

    getString() {

        if(this.string == null)
            this.string = this.calcString(this.vector);

        return this.string;
    }

    getVector() {

        if(this.vector == null)
            this.vector = this.calcVector(this.string);

        return this.vector;
    }

    calcString(vec) {

        this.string = "#"
            + Math.round(Math.min(vec.x, 255)).toString(16).padStart(2, "00")
            + Math.round(Math.min(vec.y, 255)).toString(16).padStart(2, "00")
            + Math.round(Math.min(vec.z, 255)).toString(16).padStart(2, "00");
        return this.string;
    }

    calcVector(str) {

        this.vector = new Vector(
            parseInt(str.substr(1,2),16),
            parseInt(str.substr(3,2),16),
            parseInt(str.substr(5,2),16)
        );
        return this.vector;
    }

    static fromString(string) {
        let color = new Color();
        color.string = string;
        return color;
    }

    static fromRGB(r,g,b) {
        return Color.fromVector(new Vector(r,g,b))
    }

    static fromVector(vector) {
        let color = new Color();
        color.vector = vector;
        return color;
    }

    adjustBrightness(multiplier) {
        return Color.fromVector(this.getVector().multiply(multiplier));
    }

    /***
     * Mixe cette couleur avec une autre (moyenne coefficientee)
     * @param selfImportance
     * @param other
     * @param otherImportance
     * @returns {Color}
     */
    mix(selfImportance, other, otherImportance) {
        return Color.fromVector(
            this.getVector().multiply(selfImportance).add(other.getVector().multiply(otherImportance)).divide(selfImportance+otherImportance)
        );
    }

    static mix(color1, importance1, color2, importance2) {
        return color1.mix(importance1, color2, importance2);
    }
 }