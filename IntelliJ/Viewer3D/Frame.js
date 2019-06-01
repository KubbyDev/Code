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
                ctx.fillStyle = calcPixel(rays[x][y]).getString();
                ctx.fillRect(x, y, 1,1);
            }

        function calcPixel(ray, maxBounces) {

            if(!maxBounces)
                maxBounces = 2;

            //Tracage du ray
            let hit = ray.trace();
            if(!hit.hasHit)
                return Frame.backgroundColor;

            let finalColor = hit.face.material.color;

            //Calcul de la couleur du pixel en fonction de l'eclairage
            let brightness = 0.1;
            for(let light of Frame.lights) {

                let toLight = light.position
                    .subtract(hit.position)
                    .normalize();
                let lightSqrDistance = Vector.sqrDistance(hit.position, light.position);

                let lightHit = new Ray(hit.position.add(hit.normal.multiply(0.00001)), toLight).trace();
                let exposure = 1;

                //Tant qu'on touche une face mais qu'elle n'est pas opaque et qu'on ne depasse pas la lumiere
                while(lightHit.hasHit &&
                    Vector.sqrDistance(hit.position, lightHit.position) < lightSqrDistance &&
                    lightHit.face.material.opacity < 1) {

                    //On modifie l'exposition en fonction de l'opactite de la face
                    exposure *= 1 - Math.pow(lightHit.face.material.opacity, 2);

                    //On relance un rayon
                    lightHit = new Ray(lightHit.position.add(toLight.multiply(0.00001)), toLight).trace();
                }

                //A moins qu'on ait touche une face opaque avant avoir depasse la lumiere
                if(! (lightHit.hasHit && lightHit.face.material.opacity === 1 && Vector.sqrDistance(hit.position, lightHit.position) < lightSqrDistance))
                    brightness += light.intensity
                        * Math.abs(Vector.dotProduct(toLight, hit.normal))  //Inclinaison de la face
                        / Vector.distance(light.position, hit.position)     //Distance de la lumiere
                        * exposure;                                         //Exposition (depend de l'opacite des faces traversees)
            }

            finalColor = finalColor.adjustBrightness(Math.min(1,brightness*hit.face.material.opacity));

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

