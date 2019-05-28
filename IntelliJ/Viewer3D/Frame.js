class Frame {

    static backgroundColor = "#2e2e2e";
    static objects = [];
    static lights = [new Light(new Vector(1.8,2,2), 20)];

    static setObjects(pObjects) {
        Frame.objects = pObjects;
    }

    static addObject(object) {
        Frame.objects.push(object)
    }

    static draw() {

        let rays = Camera.getRays(canvas.width, canvas.height);

        for(let x = 0; x < rays.length; x++)
            for (let y = 0; y < rays[x].length; y++) {
                ctx.fillStyle = calcPixel(rays[x][y]);
                ctx.fillRect(x, y, 1,1);
            }

        function calcPixel(dir) {

            //Tracage du ray
            let hit = new Ray(Camera.position, dir).trace();
            if(!hit.hasHit)
                return Frame.backgroundColor;

            //Calcul de la couleur du pixel en fonction de l'eclairage
            let brightness = 0.1;
            for(let light of Frame.lights) {

                let toLight = light.position
                    .subtract(hit.position)
                    .normalized();
                if(!new Ray(hit.position, toLight).hitsFace()) {
                    //On ajoute de la luminosite en fonction de la distance avec la lampe,
                    //de son intensite et de l'angle d'arrivee des rayons lumineux
                    brightness += light.intensity * Math.abs(Vector.dotProduct(toLight, hit.normal)) / Math.pow(Vector.sqrDistance(light.position, hit.position), 2);
                }
            }

            return adjustBrightness(hit.other.material.color, brightness);

            function adjustBrightness(color, multiplier) {

                let colorVec = new Vector(
                    parseInt(color.substr(1,2),16),
                    parseInt(color.substr(3,2),16),
                    parseInt(color.substr(5,2),16)
                );

                colorVec = colorVec.multiply(multiplier);

                return "#" + Math.round(Math.min(colorVec.x, 255)).toString(16).padStart(2, "00")
                    + Math.round(Math.min(colorVec.y, 255)).toString(16).padStart(2, "00")
                    + Math.round(Math.min(colorVec.z, 255)).toString(16).padStart(2, "00");
            }
        }
    }
}

