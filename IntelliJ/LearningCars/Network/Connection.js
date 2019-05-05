class Connection {
    
    weight = 0;
    target = null;
    
    constructor(target) {
        this.target = target;
    }
    
    simulate() {
        return this.weight * this.target.activation;
    }   
    
    copy() {
        
        let connection = new Connection();
        connection.target = this.target;
        connection.weight = this.weight;
        
        return connection;
    }
}