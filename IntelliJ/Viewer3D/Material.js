class Material {

    color;
    opacity;
    reflexivity;

    constructor(color, opacity, reflexivity) {

        if(!color) color = "#FFFFFF";
        if(!opacity) opacity = 1;
        if(!reflexivity) reflexivity = 0;

        this.color = color;
        this.opacity = opacity;
        this.reflexivity = reflexivity;
    }
}