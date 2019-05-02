class Line {

    start = [];
    end = [];
    visible = false;
    color = "#ffffff";

    constructor() {
    }

    setStartEnd(start, end) {
        this.start = start;
        this.end = end;
        return this;
    }

    setVisible(visible) {
        this.visible = visible;
        return this;
    }

    setColor(color) {
        this.color = color;
        return this;
    }

    isColliding(lines) {

        for(let line of lines)
            if(this.collidesWith(line))
                return true;

        return false;
    }

    collidesWith(line) {

        return this.getCollisionPoint(line) !== null;
    }

    getCollisionPoint(other) {

        let point; //Le point d'intersection

        let slopeThis = (this.end[1] - this.start[1]) / (this.end[0] - this.start[0]);
        let slopeOther = (other.end[1] - other.start[1]) / (other.end[0] - other.start[0]);

        //Si les lignes sont paralleles
        if (slopeThis === slopeOther)
            return null;

        let interceptThis = this.start[1] - slopeThis * this.start[0];
        let interceptOther = other.start[1] - slopeOther * other.start[0];

        //Si une des lignes est verticale
        if (slopeThis === Infinity)
            point = [this.start[0], slopeOther * this.start[0] + interceptOther];
        else if (slopeOther === Infinity)
            point = [other.start[0], slopeThis * other.start[0] + interceptThis];
        else {

            //Cas normal (lignes non verticales et non paralleles)
            let x = (interceptOther - interceptThis) / (slopeThis - slopeOther);
            point = [x, slopeThis * x + interceptThis];
        }

        //Si le point est bien sur les deux segments
        if (point[0] > Math.min(this.start[0], this.end[0]) && point[0] < Math.max(this.start[0], this.end[0])
            && point[1] > Math.min(this.start[1], this.end[1]) && point[1] < Math.max(this.start[1], this.end[1]))
            return point;

        return null;
    }

    draw() {

        if(this.visible) {
            ctx.fillStyle = this.color;
            ctx.beginPath();
            ctx.moveTo(this.start[0], this.start[1]);
            ctx.lineTo(this.end[0], this.end[1]);
            ctx.stroke();
            ctx.closePath();
        }
    }
}