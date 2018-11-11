public class Hat {
    
    public String tag;
    public String name;
    public String lore;
    public int data;
    public String item;
    
    public Hat(String name, String lore, int data, String item) {
        this.tag = name.toLowerCase().replace(" ", "").replace("hat", "");
        this.name = name;
        this.lore = lore;
        this.data = data;
        this.item = item;
        Hats.hat.add(this);
    }

}