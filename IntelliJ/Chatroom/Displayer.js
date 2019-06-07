class Displayer {

    static MESSAGE_SPACING = 5;
    static TEXT_LEFT_OFFSET = 10;

    static #x; //Coin haut gauche position x
    static #y; //Coin haut gauche position y
    static #w; //Largeur
    static #h; //Hauteur
    static #initialised = false;
    static #messages = [];

    //Getters des proprietes definies au dessus
    static get topLeftX () {return Displayer.#x;}
    static get topLeftY () {return Displayer.#y;}
    static get width () {return Displayer.#w;}
    static get height () {return Displayer.#h;}
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
        Displayer.#w = width;
        Displayer.#h = height;
        Displayer.#initialised = true;
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
     * @param ctx: canvas 2D context
     */
    static update(ctx) {  //TODO: Et de la position de la scrollbar.

        ctx.fillStyle = "#e0e0e0";
        ctx.fillRect(this.#x, this.#y, this.#w, this.#h);

        let writeHeight = Message.FONT_SIZE + Displayer.MESSAGE_SPACING + Displayer.#y;
        for(let i = 0; i < this.#messages.length; i++) {

            //Affiche le message
            this.#messages[i].draw(ctx, Displayer.TEXT_LEFT_OFFSET + Displayer.#x, writeHeight, Displayer.#w - Displayer.TEXT_LEFT_OFFSET);

            writeHeight += Displayer.MESSAGE_SPACING + this.#messages[i].getHeight(Displayer.#w - Displayer.TEXT_LEFT_OFFSET);
        }
    }
}