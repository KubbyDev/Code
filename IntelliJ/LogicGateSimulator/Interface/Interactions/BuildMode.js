class BuildMode {

    static selectedGate = null;
    static buttons; //Les boutons du menu contextuel (menu de droite)

    static onClick() {

        if(BuildMode.selectedGate !== null)
            BuildMode.selectedGate = null;
        else
            BuildMode.selectedGate = getGateAtPosition(mouseX, mouseY, gates);
    }

    static update() {

        if(BuildMode.selectedGate) {
            BuildMode.selectedGate.x = mouseX;
            BuildMode.selectedGate.y = mouseY;
        }
    }

    static onEnable() {

        BuildMode.selectedGate = null;
    }

    /***
     * Met a jour puis dessine le menu de droite
     */
    static updateContextualMenu() {

        //Bouton destroy
        BuildMode.buttons[0].x = canvas.width - 45;
        BuildMode.buttons[0].y = canvas.height - 30;
        BuildMode.buttons[0].width = 80;
        BuildMode.buttons[0].height = 50;
        BuildMode.buttons[0].draw();
    }

    static init() {

        BuildMode.buttons = [
            new Button()
                .setGraphicProperties("Destroy", "#ae110b")
                .setOnClick(() => {
                    gates = gates.remove(BuildMode.selectedGate);
                    Gate.removeAllConnectionsTo(BuildMode.selectedGate, gates);
                    BuildMode.selectedGate = null;
                }),
        ]
    }
}