class Clock extends Gate {

    /*
        Cette porte n'a pas d'input
        Elle compte le nombre de frames et sa sortie change d'etat tout les <period> frames
     */

    //Proprietes fonctionnelles ----------------------------------------------------------------------------------------

    period = 50; //Le nombre de frames avant un changement d'etat
    current = 0; //Le nombre de frames avant le prochain changement d'etat

    update() {

        if(this.current < 0) {
            this.current = this.period;
            this.tempOutput = !this.output;
        }
        this.current--;
    }

    /***
     * Ouvre un popup pour choisir la periode
     */
    onClick() {

        Interface.openPopup();

        document.getElementById("clock_popup").style.display = 'block';
        Clock.openedPopup = this;
    }

    static openedPopup; //Cette variable enregistre la clock qui a requis l'ouverture d'un popup (celle qui doit donc etre modifiee)
    /***
     * Cette fonction est appellee par le bouton Done du popup
     */
    static choosePeriod() {

        let chosenValue = document.getElementById("clockInput").value;
        if(!isNaN(chosenValue))
            Clock.openedPopup.setPeriod(chosenValue);
        document.getElementById("clock_popup").style.display = 'none';

        Interface.closePopup();
    }

    /***
     * Change la periode de la clock (nombre de frames entre 2 changements d'etat)
     * @param period
     * @param current
     * @returns {Clock}
     */
    setPeriod(period, current) {

        this.period = period;

        if(current)
            this.current = current;

        return this;
    }

}