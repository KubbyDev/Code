const messages = [];

const express = require('express');
const app = express();
app.listen(3000, () => console.log("listening"));
app.use(express.static('Client'));  //Le serveur envoie le fichier Client/index.html
app.use(express.json({}));

//Reception des messages
app.post('/messages', (request, response) => {

    messages.push(request.body);
    console.log("received message: " + request.body.message);
    response.json({
        errors: 'None'
    });

});

//Envoi des messages
app.get('/messages', (resquest, response) => {

    response.json(messages);

});