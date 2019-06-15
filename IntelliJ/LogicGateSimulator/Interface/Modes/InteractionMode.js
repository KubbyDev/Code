class InteractionMode {

    static buttons; //Les boutons du menu contextuel (menu de droite)

    static onClick() {

        let selectedGate = getGateAtPosition(mouseX, mouseY);
        if(selectedGate && typeof selectedGate.onClick === 'function') //Si la fonction onClick existe
            selectedGate.onClick();
    }

    static update() {
    }

    static onEnable() {
    }

    /***
     * Met a jour puis dessine le menu de droite
     */
    static updateContextualMenu() {
    }

    static init() {
        InteractionMode.buttons = [];
    }
}