class NetworkSave {

    static parse(rawSave) {

        let network = new Network();

        //Contient une seule ligne: layer cible, id cible, weight separes par des espaces
        function parseConnection(rawData) {
            let data = rawData.split(' ').map(str => parseFloat(str));
            let connection = new Connection(network.getNeuron(data[0], data[1]));
            connection.weight = data[2];
            return connection;
        }

        //Premiere ligne: biais
        //Lignes suivantes: connections
        function parseNeuron(rawData, layer, id) {
            let data = rawData.split('\n');
            let neuron = new Neuron(layer, id);
            neuron.biais = parseFloat(data[0]);
            for(let line of data.splice(1))
                neuron.connections.push(parseConnection(line));
            return neuron;
        }

        //Blocs: separes par 2 sauts de ligne
        //Paragraphes: separes par 1 saut de ligne
        //Premier bloc: nombre d'inputs
        //Blocs suivants: layers
        //Chaque bloc est compose d'un paragraphe par neurone

        let blocks = rawSave.split("\n\n\n");

        //Premiere couche (couche virtuelle des inputs)
        let inputNumber = parseInt(blocks[0]);
        network.inputs = new Array(inputNumber);
        for(let i = 0; i < inputNumber; i++)
            network.inputs[i] = new Input(i);

        //Couches suivantes
        network.layers = [];
        let layerId = 1;
        for(let block of blocks.splice(1)) {

            let paragraphs = block.split("\n\n");
            let layer = [];

            let neuronId = 0;
            for(let paragraph of paragraphs) {
                layer.push(parseNeuron(paragraph, layerId, neuronId));
                neuronId++;
            }

            network.layers.push(layer);
            layerId++;
        }

        return network;
    }

    static serialize(network) {

        //Contient une seule ligne: layer cible, id cible, weight separes par des espaces
        function serializeConnection(connection) {
            return connection.target.layer + " " + connection.target.id + " " + connection.weight;
        }

        //Premiere ligne: biais
        //Lignes suivantes: connections
        function serializeNeuron(neuron) {
            let ret = neuron.biais;
            for(let connection of neuron.connections)
                ret += "\n" + serializeConnection(connection);
            return ret;
        }

        //Blocs: separes par 2 sauts de ligne
        //Paragraphes: separes par 1 saut de ligne
        //Premier bloc: nombre d'inputs
        //Blocs suivants: layers
        //Chaque bloc est compose d'un paragraphe par neurone

        let ret = network.inputs.length;

        for(let layer of network.layers) {

            ret += "\n";
            for(let neuron of layer)
                ret += "\n\n" + serializeNeuron(neuron);
        }

        return ret;
    }
}