class Camera {

    static fov = [96,54];
    static position = new Vector(10,-0.5,0);
    static orientation = [-180,0];

    static getRays(width, height) {

        //Vecteurs avant, droite et haut de la camera
        let forward = Vector.fromOrientation(Camera.orientation[0], Camera.orientation[1]).normalize();
        let right = new Vector(-forward.z, 0, forward.x).normalize();
        let up = Vector.crossProduct(right, forward).normalize();

        //Taille du cadre de la camera
        let sizeX = Math.abs(Math.tan(Camera.fov[0]*Math.PI/360))*2;
        let sizeY = Math.abs(Math.tan(Camera.fov[1]*Math.PI/360))*2;

        let screenOrigin = forward
            .subtract(right.multiply(sizeX/2))
            .add(up.multiply(sizeY/2))
            .add(Camera.position);

        let rays = new Array(width);
        for(let x = 0; x < width; x++) {
            rays[x] = new Array(height);
            for(let y = 0; y < height; y++)
                rays[x][y] = new Ray(
                    Camera.position
                    ,
                    screenOrigin
                    .add(right.multiply(sizeX*x/width))
                    .subtract(up.multiply(sizeY*y/height))
                    .subtract(Camera.position)
                    .normalize()
                );
        }

        return rays;
    }
}