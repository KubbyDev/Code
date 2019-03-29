
let fov = [70,40];
let cameraPosition = new Vector(5,0,0);
let cameraOrientation = [-180,-10];
let backgroundColor = "#0b0b0b";
let objects = [];
let lights = [new Light(new Vector(2,2,2), 100)];

function setObjects(pObjects) {
    objects = pObjects;
}

function addObject(object) {
    objects.push(object)
}

function drawFrame() {

    let angle = 5;

    cameraOrientation[0] = (cameraOrientation[0]+180 + angle)%360-180;
    cameraPosition.rotate(-angle);

    for(let y = 0; y < canvas.height; y++) {
        for (let x = 0; x < canvas.width; x++) {
            ctx.fillStyle = calcPixel(x, y);
            ctx.fillRect(x, y, 1,1)
        }
    }
}

function calcPixel(x, y) {

    //Calcul du vecteur directeur du ray
    let dir = Vector.fromOrientation(
        ((x/canvas.width)-0.5)*2 *fov[0] + cameraOrientation[0],
        -((y/canvas.height)-0.5)*2 *fov[1] + cameraOrientation[1]
    );

    //Tracage du ray
    let hit = new Ray(cameraPosition, dir).trace();
    if(!hit.hasHit)
        return backgroundColor;

    //Calcul de la couleur du pixel en fonction de l'eclairage
    let brightness = 1;
    /*
    let brightness = 0.1;
    for(let light of lights) {
        let lightHit = new Ray(hit.position, Vector.subtract(light.position, hit.position)).trace();
        if(!lightHit.hasHit || true)
            brightness += Math.max(0, light.intensity - Vector.sqrDistance(lightHit.position, hit.position))/10;
    }
    */

    return adjustBrightness(hit.other.color, brightness);
}

function adjustBrightness(color, multiplier) {

    let colorVec = new Vector(
        parseInt(color.substr(1,2),16),
        parseInt(color.substr(3,2),16),
        parseInt(color.substr(5,2),16)
    );

    colorVec.multiply(multiplier);

    return "#" + Math.round(Math.min(colorVec.x, 255)).toString(16).padStart(2, "00")
               + Math.round(Math.min(colorVec.y, 255)).toString(16).padStart(2, "00")
               + Math.round(Math.min(colorVec.z, 255)).toString(16).padStart(2, "00");
}