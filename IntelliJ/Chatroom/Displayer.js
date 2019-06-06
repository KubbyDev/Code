class Displayer {

    static #x = 0;
    static #y = 0;
    static #width = 800;
    static #height = 400;
    static #initialised = false;
    static #messages = [];

    //Getters des proprietes definies au dessus
    static get topLeftX () {return Displayer.#x;}
    static get topLeftY () {return Displayer.#y;}
    static get Width () {return Displayer.#width;}
    static get Height () {return Displayer.#height;}
    static get Messages () {return Displayer.#messages;}

    /**
     * Cree un displayer vide et pret a recevoir des messages
     * @param x
     * @param y
     * @param width
     * @param height
     */
    static init(x, y, width, height) {

        //Si l'init s'est deja faite on annule
        if(Displayer.#initialised) {
            console.error("Init: The initialisation has already been done !");
            return;
        }

        //Si les parametres sont pas bons on annule
        if(!x || !y || !width || !height || width < 1 || height < 1) {
            console.error("Init: You must choose a position and a size for your Displayer !");
            return;
        }

        Displayer.#x = x;
        Displayer.#y = y;
        Displayer.#width = width;
        Displayer.#height = height;
        Displayer.#initialised = true;

        Displayer.update();
    }

    /**
     * Ajoute un message au displayer
     * @param message
     */
    static addMessage(message) {

        if( ! Displayer.#initialised) {
            console.error("addMessage: You must initialise the Displayer !");
            return;
        }

        if( ! (message instanceof Message)) {
            console.error("addMessage: You must pass in a Message object !");
            return;
        }

        Displayer.#messages.unshift(message);
    }

    /**
     * Met a jour l'affichage en fonction des messages presents a ce moment
     * Et de la position de la scrollbar
     */
    static update() {

    }
}