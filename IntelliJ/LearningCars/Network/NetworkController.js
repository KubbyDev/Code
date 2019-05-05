function clamp(val) {
    return Math.min(1, Math.max(val, -1))
}

class NetworkController {

    network;
    parentCar;

    constructor(parentCar) {
        this.parentCar = parentCar;
    }

    getInputs() {
        let answer = this.network.simulate(this.getDistances());
        return [clamp(answer[0]),clamp(answer[1])];
    }

    getDistances() {
        return [
          this.getDistance(Vector.fromOrientation(this.parentCar.rotation)),
          this.getDistance(Vector.fromOrientation(this.parentCar.rotation+45)),
          this.getDistance(Vector.fromOrientation(this.parentCar.rotation-45)),
          this.getDistance(Vector.fromOrientation(this.parentCar.rotation-90)),
          this.getDistance(Vector.fromOrientation(this.parentCar.rotation+90)),
        ];
    }

    //Calcule la distance de la premiere collision avec le circuit le long de line
    getDistance(line) {

        let minDistance = 1000000;
        for(let circuitLine of circuit) {

            let collisionPoint = line.getCollisionPoint(circuitLine);
            if(collisionPoint != null)
                minDistance = Math.min(minDistance, Vector.distance(collisionPoint, this.parentCar.position));
        }

        return minDistance;
    }
}