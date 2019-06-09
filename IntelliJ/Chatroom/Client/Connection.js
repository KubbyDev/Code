class Connection {

    static async send(message) {

        let options = {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({message})           //TODO: Faire en sorte que ca marche
        };
        console.log(options);
        inputField.value = "";

        fetch('/messages', options).then(async (response) => console.log(await response.json()));
    }

    static async getMessages() {

        const rawResponse = await fetch('/messages');
        const elements = await rawResponse.json();

        console.log(elements);

        Displayer.messages = elements.reverse().map(element => new Message(element.message));
    }
}

//On recupere les nouveaux messages toutes les secondes
setInterval(Connection.getMessages, 1000);