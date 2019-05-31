class Frame {

    static backgroundColor = Color.fromRGB(46, 46, 46);
    static objects = [];
    static lights = [];

    static setObjects(pObjects) {
        Frame.objects = pObjects;
    }

    static addObject(object) {
        Frame.objects.push(object)
    }

    static setLights(pLights) {
        Frame.lights = pLights;
    }

    static addLight(light) {
        Frame.lights.push(light);
    }

    static draw() {

        let rays = Camera.getRays(canvas.width, canvas.height);

        for(let x = 0; x < rays.length; x++)
            for (let y = 0; y < rays[x].length; y++) {
                ctx.fillStyle = calcPixel(rays[x][y], 2).getString();
                ctx.fillRect(x, y, 1,1);
            }

        function calcPixel(ray, maxBounces) {

            let finalColor = Frame.backgroundColor;

            //Tracage du ray
            let hit = ray.trace();
            if(!hit.hasHit)
                return Frame.backgroundColor;
            else
                finalColor = hit.face.material.color;

            //Calcul de la couleur du pixel en fonction de l'eclairage
            let brightness = 0.1;
            for(let light of Frame.lights) {

                let toLight = light.position
                    .subtract(hit.position)
                    .normalize();
                let lightHit = new Ray(hit.position.add(hit.normal.multiply(0.00001)), toLight).trace();
                if(! (lightHit.hasHit && Vector.sqrDistance(hit.position, lightHit.position) < Vector.sqrDistance(hit.position, light.position))) {
                    //On ajoute de la luminosite en fonction de la distance avec la lampe,
                    //de son intensite et de l'angle d'arrivee des rayons lumineux
                    brightness += light.intensity * Math.abs(Vector.dotProduct(toLight, hit.normal)) / Vector.sqrDistance(light.position, hit.position);
                }
            }
            finalColor = finalColor.adjustBrightness(brightness);

            //Calcul de la transparence
            if(hit.face.material.opacity < 1)
                finalColor = finalColor.mix(
                    hit.face.material.opacity,
                    calcPixel(
                        new Ray(hit.position.add(ray.direction.multiply(0.00001)),ray.direction),
                        maxBounces
                    ),
                    1 - hit.face.material.opacity
                );

            if(hit.face.material.reflexivity > 0 && maxBounces > 0) {
                let reflected = hit.normal.multiply(-2 * Vector.dotProduct(hit.normal, ray.direction)).add(ray.direction);
                finalColor = finalColor.mix(
                    1 - hit.face.material.reflexivity,
                    calcPixel(
                        new Ray(hit.position.add(reflected.multiply(0.00001)), reflected),
                        maxBounces-1
                    ),
                    hit.face.material.reflexivity
                )
            }

            return finalColor;
        }
    }
}

