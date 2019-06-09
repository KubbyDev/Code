function test() {
    console.log("gne");
}

'use strict';
class Message {

    static LINE_SPACING = 0;
    static FONT_SIZE = 15;
    static TEXT_LEFT_OFFSET = 60;
    static IMAGE_SIDE_LENGTH = 50;

    #_text = "";
    #_sender = User.default;

    get text() {return this.#_text;}
    get sender() {return this.#_sender;}

    constructor(text, sender) {

        if(!text)
            return;

        this.#_text = text;

        if(!sender)
            return;

        this.#_sender = sender;
    }

    /**
     * Coupe le message en revenant a la ligne si besoin (w est le nombre de pixels de large)
     * @param w
     */
    cutInLines(w) {

        let lines = [];
        let currentLine = "";
        let words = this.#_text.split(' ');

        let charsNumber = w/(0.55*Message.FONT_SIZE)-1; //Nombre de caracteres affichables sur une ligne
        let totalWidth = 0;
        let wordIndex = 0;

        //Pour chaque mot
        while(wordIndex < words.length) {

            //Si on a pas depasse la longueur max
            if(totalWidth + words[wordIndex].length <= charsNumber) {

                //On ajoute le mot et on passe au suivant
                currentLine += words[wordIndex] + " ";
                totalWidth += words[wordIndex].length +1;
                wordIndex++;
            }
            //Si on a depasse la longueur max
            else {

                //Si on a un mot trop long pour la ligne
                if(totalWidth === 0) {

                    //On le casse
                    let longWord = words[wordIndex];
                    words[wordIndex] = longWord.substr(0, charsNumber);
                    words.splice(wordIndex+1, 0, longWord.substr(charsNumber));
                }
                else {

                    //On passe a la ligne suivante
                    lines.push(currentLine);
                    currentLine = "";
                    totalWidth = 0;
                }
            }
        }

        lines.push(currentLine);
        return lines;
    }

    /**
     * Renvoie la place en pixels que prendra le message en hauteur
     * @param lines
     */
    getHeight(lines) {
        return Math.max(
            Message.IMAGE_SIDE_LENGTH,
            lines.length * (Message.LINE_SPACING + Message.FONT_SIZE) + (Message.FONT_SIZE*1.1) + Message.LINE_SPACING
        );
    }

    /**
     * Dessine le message (x, y = coin haut gauche)
     * @param lines
     * @param ctx
     * @param x
     * @param y
     */
    draw(lines, ctx, x, y) {

        //Image

        ctx.drawImage(
            this.#_sender.picture,
            (Message.TEXT_LEFT_OFFSET - Message.IMAGE_SIDE_LENGTH)/2 + x,
            y - (Message.FONT_SIZE*1.1),
            Message.IMAGE_SIDE_LENGTH,
            Message.IMAGE_SIDE_LENGTH
        );

        //Pseudo

        ctx.fillStyle = "#ff5100";
        ctx.font = (Message.FONT_SIZE*1.1) + "px monospace";

        ctx.fillText(this.#_sender.nickname, x + Message.TEXT_LEFT_OFFSET, y);
        y += (Message.FONT_SIZE*1.1) + Message.LINE_SPACING;

        //Messages

        ctx.fillStyle = "#000000";
        ctx.font = Message.FONT_SIZE + "px monospace";

        for(let i = 0; i < lines.length; i++) {
            ctx.fillText(
                lines[i],
                x + Message.TEXT_LEFT_OFFSET,
                i * (Message.LINE_SPACING + Message.FONT_SIZE) + y
            );
        }
    }
}