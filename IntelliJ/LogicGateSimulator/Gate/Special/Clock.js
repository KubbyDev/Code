class Clock extends Gate {

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