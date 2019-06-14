class InteractionMode {

    static buttons; //Les boutons du menu contextuel (menu de droite)

    static onClick() {

        let selectedSwitch = getGateAtPosition(mouseX, mouseY, gates);
        if(selectedSwitch && typeof selectedSwitch.onClick === 'function') //Si la fonction onClick existe
            selectedSwitch.onClick();
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