
let fov = [70,40];
let cameraPosition = new Vector(0,0,0);
let cameraOrientation = [0,0];
let width = 720;
let height = 405;
let backgroundColor = "#878787";
let objects = [];
let lights = [new Light(new Vector(2,2,2), 5)];

function setObjects(pObjects) {
    objects = pObjects;
}

function addObject(object) {
    objects.add(object)
}

function drawFrame() {

    const canvas = document.getElementById("myCanvas");
    const ctx = canvas.getContext("2d");

    ctx.beginPath();
    ctx.fill();

    for(let y = 0; y < height; y++) {
        for(let x = 0; x < width; x++) {
            ctx.rect(x, y, 1, 1);
            ctx.fillStyle = calcPixel(x, y);
        }
    }

    ctx.closePath();
}

function calcPixel(x, y) {

    //Calcul du vecteur directeur du ray
    let dir = Vector.fromOrientation(
        (x/width-0.5)*2 *fov[0] + cameraOrientation[0],
        (y/height-0.5)*2 *fov[1] + cameraOrientation[1]
    );

    //Tracage du ray
    let hit = new Ray(cameraPosition, dir).trace();
    if(!hit.hasHit)
        return backgroundColor;

    //Calcul de la couleur du pixel en fonction de l'eclairage
    let brightness = 0.1;
    for(let light of lights) {
        let lightHit = new Ray(hit.position, light.position.subtract(hit.position)).trace();
        if(!lightHit.hasHit)
            brightness += Math.max(0, light.intensity - Vector.sqrDistance(lightHit.position, hit.position));
    }

    return adjustBrightness(hit.other.color, brightness);
}

function adjustBrightness(color, multiplier) {

    let colorVec = new Vector(
        parseInt(color.substr(1,2),16),
        parseInt(color.substr(3,2),16),
        parseInt(color.substr(5,2),16)
    );

    colorVec.multiply(multiplier);

    return "#" + Math.round(Math.min(colorVec.x, 255)).toString(16)
               + Math.round(Math.min(colorVec.y, 255)).toString(16)
               + Math.round(Math.min(colorVec.z, 255)).toString(16);
}