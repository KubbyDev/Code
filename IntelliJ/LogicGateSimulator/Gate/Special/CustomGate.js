class CustomGate extends Gate {

    //Proprietes fonctionnelles ----------------------------------------------------------------------------------------

    internGates = [];
    outputGates;
    //TODO: gerer les outputs multiples

    update() {

        for(let gate of this.internGates)
            gate.update();

        this.tempOutput = this.outputGate.tempOutput;
    }

    tickEnd() {

        for(let gate of this.internGates)
            gate.tickEnd();
        this.output = this.tempOutput;
    }

    /***
     * Modifie le circuit cache de cette custom gate
     */
    setInternGates(internGates) {
        this.internGates = internGates;
    }

    getGateForOutput(index) {
        return this.outputGates[index];
    }

    //Proprietes graphiques --------------------------------------------------------------------------------------------


}