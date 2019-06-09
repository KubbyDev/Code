class User {

    nickname;
    picture;  //Une ImageHTMLElement

    constructor(nickname, picture) {

        //Valeurs de base
        this.nickname = "Anonymous";
        this.picture = new Image();
        this.picture.src = "Anonymous.png";

        //Pseudo
        if(!nickname)
            return;
        this.nickname = nickname;

        //Image de profil
        if(!picture)
            return;
        this.picture = picture;
    }

    /**
     * Returns a User with "Anonymous" as nickname and the default picture
     * @returns {User}
     */
    static get default() {
        return new User();
    }
}