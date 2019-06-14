class Switch extends Gate {

    //Proprietes fonctionnelles ----------------------------------------------------------------------------------------

    isOn = true;

    update() {

        if(!this.isOn) {
            this.tempOutput = false;
            return;
        }

        super.update();
    }

    /***
     * Inverse l'etat de l'interrupteur
     */
    onClick() {
        this.isOn = !this.isOn;
    }

    //Proprietes graphiques --------------------------------------------------------------------------------------------

    drawBody() {

        super.drawBody();

        if(!this.isOn) {
            ctx.fillStyle = Interface.BACKGROUND_COLOR;
            ctx.fillRect(this.x - this.width/2 + this.width/5, this.y - this.height/2, this.width - 2*this.width/5, this.height);
        }
    }
}