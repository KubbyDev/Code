import java.util.ArrayList;

public class Level {

	public ArrayList<Button> buttons = new ArrayList<Button>();
	public int nbreButtons;
	public Vector spawn;
	public int index;
	public String name;
	public ArrayList<Vector> bound1 = new ArrayList<Vector>();
	public ArrayList<Vector> bound2 = new ArrayList<Vector>();
	
	public Level(Vector pSpawn, int pNbreButtons, String pName, int pIndex) {
		
		spawn = pSpawn;
		index = pIndex;
		name = pName;
		nbreButtons = pNbreButtons;
		bound1.add(new Vector(spawn.x - 70, 0, spawn.z - 70));
		bound2.add(new Vector(spawn.x + 70, 0, spawn.z + 70)); 
		Main.levels.add(this);
	}
	
	public Level(Vector pSpawn, int pNbreButtons, String pName, int pIndex, Vector pBoundCenter) {
		
		spawn = pSpawn;
		index = pIndex;
		name = pName;
		nbreButtons = pNbreButtons;
		bound1.add(new Vector(pBoundCenter.x - 70, 0, pBoundCenter.z - 70));
		bound2.add(new Vector(pBoundCenter.x + 70, 0, pBoundCenter.z + 70));
		
		Main.levels.add(this);
	}
	
	public Level(Vector pSpawn, int pNbreButtons, String pName, int pIndex, ArrayList<Vector> pBound1, ArrayList<Vector> pBound2) {
		
		spawn = pSpawn;
		index = pIndex;
		name = pName;
		nbreButtons = pNbreButtons;
		
		if(pBound1.size() != pBound2.size())
			System.out.print("\n\nERREUR: Mauvaise definition des bounds du level " + index + "\n\n");
		
		for(int i = 0; i < pBound1.size(); i++) {
			bound1.add(pBound1.get(i));
			bound2.add(pBound2.get(i));
		}
		
		Main.levels.add(this);
	}
	
	public void addButton(Button button) {
		
		buttons.add(button);
		
		if(!isInBounds(button))
			System.out.print("\n\n\nBUTTON NOT IN BOUNDS\nLevel " + index + " Button " + buttons.size() + "\n\n\n");
		
	}
	
	public boolean isInBounds(Button b) {
		
		for(int i = 0; i < bound1.size(); i++)
			if(b.pos.x > bound1.get(i).x && b.pos.x < bound2.get(i).x && b.pos.z > bound1.get(i).z && b.pos.z < bound2.get(i).z)
				return true;
		
		return false;
	}
	
	public void generateStart() {
	
		if(Main.dispComm) System.out.println("# Level " + index);
		System.out.println("setblock ~ ~ ~-1 quartz_block");
		System.out.println("/title @a subtitle §eYou have to find " + nbreButtons + " buttons!");
		System.out.println("/title @a title §a§lLevel " + index);
		System.out.println("JUMP");
		//System.out.println("/title @a times 5 30 8");
		//System.out.println("execute @a ~ ~ ~ playsound random.levelup @a ~ ~ ~ 100000000 1.5");
		System.out.println("/tp " + Main.buttAS + " " + Main.tpButtAS(nbreButtons));
		System.out.println("/tp @a " + spawn);
		//ATTENTION
		System.out.println("JUMP");
		//System.out.println("fill ~-36 100 951 ~-36 100 1032 redstone_block 0 replace quartz_block"); 		//ATTENTION
		generateTAcomm((index == 35) ? 1 : index+1, true);
		generateTAcomm((index == 1) ? 35 : index-1, false);
		System.out.println("JUMP");
		//System.out.println("kill @e[type=xp_orb]");
		//System.out.println("kill @e[type=item]");
		
		for(Button b : buttons) 
			if(!b.isGolden)
				System.out.println("setblock " + b.pos + " stone_button " + b.dam);
		
		if(index % 5 != 0)
			System.out.println("say §r§l§2Level " + index + "§r: §a" + name + "§r. §6You have to find " + nbreButtons + " buttons");
		else
			System.out.println("say §r§l§4Level " + index + " (BOSS)§r: §c" + name + "§r. §6You have to find " + nbreButtons + " buttons");
		
	}
	
	public void generateUnstuck() {
		
		//ATTENTION
		System.out.println("execute @e[type=snowball] ~ ~ ~ /execute @e[x=" + (index-1 + Main.posLvlAS1) + ",y=99,z=950,dx=0,dy=5,dz=2,type=armor_stand] ~ ~ ~ tp @a " + spawn + "\n");   		//ATTENTION
		
	}
	
	public void generateHelp() {
		
		for(Button b : buttons)
			if(!b.isGolden)
				//ATTENTION
				System.out.println("execute @e[type=xp_bottle] ~ ~ ~ execute @e[x=" + (index-1 + Main.posLvlAS1) + ",y=99,z=950,dx=0,dy=5,dz=2,type=armor_stand] ~ ~ ~ summon xp_orb " + b.pos + "\n");       //ATTENTION
		
	}
	
	public void generateTAcomm(int index, boolean add) {
		
		if(add) {
			
			for(int i = 0; i < Main.levels.get(index-1).bound1.size(); i++) {
				
				System.out.print("/tickingarea add " + Main.levels.get(index-1).bound1.get(i) + " " + Main.levels.get(index-1).bound2.get(i) + " lvl" + index);
			
				if(Main.levels.get(index-1).bound1.size() > 1)
					System.out.print("p" + i);
				
				System.out.println();
			}

		}
		else { 
			
			for(int i = 0; i < Main.levels.get(index-1).bound1.size(); i++) {
				
				System.out.print("tickingarea remove lvl" + index);
				
				if(Main.levels.get(index-1).bound1.size() > 1)
					System.out.print("p" + i);
				
				System.out.println();
				
			}

		}
		
	}
	
	public void generateClickDetect() {
		
		if(Main.dispComm) System.out.println("# Level " + index);
		
		int i = 0;
		for(Button b : buttons) {
			
			i++;
			System.out.print("\n");
			if(Main.dispComm) System.out.println("# Button " + i);
			System.out.println("testforblock " + b.pos + ((b.isGolden) ? " wooden_button " : " stone_button ") + (b.dam + 8));
			//System.out.println("/setblock ~ ~ ~-2 quartz_block");
			System.out.println("/setblock " + b.pos + " air 0 destroy");
			if(b.isGolden) {
				//System.out.println("tp " + Main.goldAS + " ~1 ~2 ~");
				System.out.println("tp @a " + spawn);
			}
			else {
				//System.out.println("/execute " + Main.buttAS + " ~-1 ~ ~ detect ~ ~ ~ quartz_block 0 tp @e[type=armor_stand,r=2] ~ ~ ~");
				//System.out.println("execute @a ~ ~ ~ /playsound random.pop @p ~ ~ ~ 100000 0.5");
			}		
		
		}
		
	}
	
}
