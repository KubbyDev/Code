
class Light {

    position;
    intensity;
    color = Color.fromRGB(255,0,0);

    constructor(position, intensity) {
        this.position = position;
        this.intensity = intensity;
    }
}