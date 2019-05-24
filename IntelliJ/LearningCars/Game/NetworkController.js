function clamp(val) {
    return Math.max(0, Math.min(val, 1))
}

class NetworkController {

    static MAX_RAY_DISTANCE = 200;

    network;
    parentCar;

    constructor(parentCar) {
        this.parentCar = parentCar;
        this.network = Network.random(5, 2);
    }

    getInputs(circuit) {
        let answer = this.network.simulate(this.getDistances(circuit).map(x => x/NetworkController.MAX_RAY_DISTANCE));
        return [
            (clamp(answer[0])-0.5)*2,
            (clamp(answer[1])-0.5)*2
        ];
    }

    getDistances(circuit) {
        let test = [
          this.getDistance(Vector.fromOrientation(this.parentCar.rotation).multiply(NetworkController.MAX_RAY_DISTANCE).toLine(this.parentCar.position), circuit),
          this.getDistance(Vector.fromOrientation(this.parentCar.rotation+45).multiply(NetworkController.MAX_RAY_DISTANCE).toLine(this.parentCar.position), circuit),
          this.getDistance(Vector.fromOrientation(this.parentCar.rotation-45).multiply(NetworkController.MAX_RAY_DISTANCE).toLine(this.parentCar.position), circuit),
          this.getDistance(Vector.fromOrientation(this.parentCar.rotation-90).multiply(NetworkController.MAX_RAY_DISTANCE).toLine(this.parentCar.position), circuit),
          this.getDistance(Vector.fromOrientation(this.parentCar.rotation+90).multiply(NetworkController.MAX_RAY_DISTANCE).toLine(this.parentCar.position), circuit),
        ];

        /*
        ctx.fillStyle = "#ff0000";
        let a = Vector.fromOrientation(this.parentCar.rotation).multiply(test[0]).add(this.parentCar.position);
        ctx.fillRect(a.x-1, a.y-1, 3, 3);
        a = Vector.fromOrientation(this.parentCar.rotation+45).multiply(test[1]).add(this.parentCar.position);
        ctx.fillRect(a.x-1, a.y-1, 3, 3);
        a = Vector.fromOrientation(this.parentCar.rotation-45).multiply(test[2]).add(this.parentCar.position);
        ctx.fillRect(a.x-1, a.y-1, 3, 3);
        a = Vector.fromOrientation(this.parentCar.rotation-90).multiply(test[3]).add(this.parentCar.position);
        ctx.fillRect(a.x-1, a.y-1, 3, 3);
        a = Vector.fromOrientation(this.parentCar.rotation+90).multiply(test[4]).add(this.parentCar.position);
        ctx.fillRect(a.x-1, a.y-1, 3, 3);
         */

        return test;
    }

    //Calcule la distance de la premiere collision avec le circuit le long de line
    getDistance(line, circuit) {

        let minDistance = NetworkController.MAX_RAY_DISTANCE;
        for(let circuitLine of circuit) {

            let collisionPoint = line.getCollisionPoint(circuitLine);
            if(collisionPoint != null)
                minDistance = Math.min(minDistance, Vector.distance(collisionPoint, this.parentCar.position));
        }

        return minDistance;
    }
}