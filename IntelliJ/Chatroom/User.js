class User {

    nickname = "Anonymous";
    #_picture; //TODO: Anonymous picture

    constructor(nickname, picture) {

        if(!nickname)
            return;

        this.nickname = nickname;

        if(!picture)
            return;

        this.#_picture = picture;
    }

    /**
     * Returns a User with "Anonymous" as nickname and the default picture
     * @returns {User}
     */
    static get default() {
        return new User();
    }

    get picture() {
        //TODO: Get picture (with resize)
    }
}