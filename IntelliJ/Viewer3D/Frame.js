
let fov = [70,40];
let cameraPosition = new Vector(0,0,0);
let cameraOrientation = [0,0];
let width = 720;
let height = 405;
let backgroundColor = "#878787";

function draw(objects) {

    for(let y = 0; y < height; y++) {
        for(let x = 0; x < width; x++) {

            calcPixel(x, y);

        }
    }
}

function calcPixel(x, y) {

    //Calcul du vecteur directeur du ray
    let dir = Vector.fromOrientation(
        (x/width-0.5)*2 *fov[0] + cameraOrientation[0],
        (y/height-0.5)*2 *fov[1] + cameraOrientation[1]
    );

    //Tracage du ray
    let hit = new Ray(cameraPosition, dir).trace();
    if(hit === null)
        return backgroundColor;

    //Calcul de la couleur du pixel en fonction de l'eclairage

}