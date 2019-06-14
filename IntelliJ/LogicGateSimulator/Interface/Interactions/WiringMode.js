class WiringMode {

    static selectedGate = null;
    static buttons; //Les boutons du menu contextuel (menu de droite)

    static onClick() {

        if(WiringMode.selectedGate !== null) {

            let selected = getGateAtPosition(mouseX, mouseY, gates);
            if(!selected)
                WiringMode.selectedGate = null;
            else
            {
                WiringMode.selectedGate.addInput(selected);
                WiringMode.selectedGate = null;
            }
        }
        else
            WiringMode.selectedGate = getGateAtPosition(mouseX, mouseY, gates);
    }

    static update() {

        if(WiringMode.selectedGate) {
            ctx.beginPath();
            ctx.strokeStyle = "#d3d3d3";
            ctx.lineWidth = Connection.WIDTH;
            ctx.moveTo(WiringMode.selectedGate.x, WiringMode.selectedGate.y);
            ctx.lineTo(mouseX, mouseY);
            ctx.stroke();
            ctx.closePath();
        }
    }

    static onEnable() {
    }

    /***
     * Met a jour puis dessine le menu de droite
     */
    static updateContextualMenu() {
    }

    static init() {
        WiringMode.buttons = [];
    }
}