class Interface {

    static BACKGROUND_COLOR = "#353535";
    static BUTTON_SPACING = 5;
    static ZOOM_FACTOR = 1; //Plus ce nombre est grand, plus les portes seront grossies

    //0 = BUILD: cliquer une fois pour selectionner une porte, puis une autre fois pour la placer
    //Cliquer sur les portes a droite pour les ajouter, ou drag une porte dans la poubelle pour la supprimer

    //1 = CONNECTION: Cliquer sur une porte pour la selectionner, puis sur une autre porte pour la connecter

    //2 = INTERACTION: Cliquer sur un interrupteur pour changer son etat

    static mode = 0;

    static clear() {
        ctx.fillStyle = Interface.BACKGROUND_COLOR;
        ctx.fillRect(0,0,canvas.width, canvas.height);
    }

    /***
     * Dessine le menu contextuel du mode actuel
     */
    static draw() {
        Interface.getCurrentMode().updateContextualMenu();
    }

    static getButtonAtPosition(x, y) {

        for (let button of Interface.getCurrentMode().buttons)
            if (Math.abs(x - button.x) < button.width / 2 && Math.abs(y - button.y) < button.height / 2)
                return button;

        return null;
    }

    static init() {

        BuildMode.init();
        WiringMode.init();
        InteractionMode.init();
    }

    /***
     * Renvoie la classe correspondant au mode actuel (Build, Wiring ou Interaction)
     * @returns {*}
     */
    static getCurrentMode() {
        switch(Interface.mode) {
            case 0: return BuildMode;
            case 1: return WiringMode;
            case 2: return InteractionMode;
        }
    }

    /***
     * Zoome ou dezoome
     * @param factor
     */
    static zoom(factor) {

        Interface.ZOOM_FACTOR *= factor;
        CIRCLE_RADIUS *= factor;
        Connection.WIDTH *= factor;

        for(let gate of gates) {

            gate.width *= factor;
            gate.height *= factor;

            gate.x = (gate.x - mouseX)*factor + mouseX;
            gate.y = (gate.y - mouseY)*factor + mouseY;

            gate.fontSize *= factor;
        }
    }
}

