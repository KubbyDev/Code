import java.util.ArrayList;

public class Main {

	public static ArrayList<Level> levels = new ArrayList<Level>();
	public static ArrayList<Button> buttons = new ArrayList<Button>();
	public static String lvlAS = "@e[x=986,y=99,z=950,dx=36,dy=4,dz=2,type=armor_stand]"; 
	public static String buttAS = "@e[x=989,y=99,z=1048,dx=61,dy=4,dz=2,type=armor_stand]";   
	public static String goldAS = "@e[x=949,y=99,z=1048,dx=38,dy=4,dz=2,type=armor_stand]"; 
	public static int posLvlAS1 = 987;                                   
	public static boolean dispComm = false;
	
	public static void main(String[] args) {

		createLevels();
		createButtons();
		
		generateUnstuck();
		
	}
	
	//---------------------------------------------------------------	
	//  Generateurs 
	
	public static void generateStart() {
		
		for(Level l : levels) {
			l.generateStart();
			System.out.print("\n\n");
		}
			
	}
	
	public static void generateUnstuck() {

		for(Level l : levels)
			l.generateUnstuck();
		
		System.out.print("setblock 1043 105 951 quartz_block"); //A MODIFIER
		
	}
	
	public static void generateHelp() {

		for(Level l : levels)
			l.generateHelp();
		
		System.out.print("setblock 1041 105 951 quartz_block"); //A MODIFIER
		
	}
	
	public static void generateClickDetect() {
		
		for(Level l : levels) {
			l.generateClickDetect();
			System.out.print("\n\n\n");
		}
	
	}
	
	public static void generateButtonsTitle() {
		
		for(int i = 2; i <= 10; i++) {
			
			if(i != 9) {
				for(int y = 0; y < i; y++)
					System.out.println("/title @a actionbar §6§lButtons§7[§c"+ y + "§7/§6" + i + "§7]");
			
				System.out.println("/title @a actionbar §6§lButtons§7[§6"+ i + "§7/§6" + i + "§7]\n");
			}
			
		}
		
		
	}
	
	//---------------------------------------------------------------
	//  Definition des objects	
	
	public static ArrayList<Vector> specialBounds(int level, boolean bound1) {
		
		switch(level) {
		
		case 30: {
			ArrayList<Vector> al = new ArrayList<Vector>();
			
			if(bound1) {
				al.add(new Vector(-2272, 0, -96));
			}
			else {
				al.add(new Vector(-2129, 0, 63));
			}
			
			return al;
		}
		
		case 33: {
			ArrayList<Vector> al = new ArrayList<Vector>();
			
			if(bound1) {
				al.add(new Vector(-2672, 0, -64));
				al.add(new Vector(-2736, 0, -64));
			}
			else {
				al.add(new Vector(-2593, 0, 63));
				al.add(new Vector(-2657, 0, 63));
			}
			
			return al;
		}
		
		case 35: {
			ArrayList<Vector> al = new ArrayList<Vector>();
			
			if(bound1) {
				al.add(new Vector(-3600, 0, -64));
				al.add(new Vector(-3600, 0, 48));
				al.add(new Vector(-3728, 0, -64));
				al.add(new Vector(-3728, 0, 48));	
			}
			else {
				al.add(new Vector(-3489, 0, 63));
				al.add(new Vector(-3489, 0, 175));
				al.add(new Vector(-3585, 0, 63));
				al.add(new Vector(-3585, 0, 175));
			}
			
			return al;
		}
		
		default: {
			System.out.println("\n\nMAUVAIS ID DE LEVEL (specialBounds n'est pas definie pour le level: " + level + ")\n\n");
			return null;
		}
		}
		
	}
	
	public static void createLevels() {

		
		new Level(new Vector(-58, 30, -45), 2, "Color Resistors", 1);
		new Level(new Vector(-118, 13, -19), 2, "Fiery Dome", 2);
		new Level(new Vector(-152, 15, -46), 3, "Subnautical Anchors", 3);
		new Level(new Vector(-204, 10, -44), 2, "Acorn Pantry", 4);
		new Level(new Vector(-272, 41, -28), 4, "Sunburnt Barn", 5);
		new Level(new Vector(-324, 25, -38), 2, "Neon Reactor", 6);
		new Level(new Vector(-377, 30, -36), 3, "Pixie Puffland", 7);
		new Level(new Vector(-441, 13, -32), 2, "Button Toll", 8);
		new Level(new Vector(-488, 46, -31), 3, "Bit Omen of Buttons", 9);
		new Level(new Vector(-545, 57, -32), 5, "Button Unknown Playgrounds", 10);
		new Level(new Vector(-593, 49, -40), 2, "Buttons & Buffoons", 11);
		new Level(new Vector(-664, 72, -42), 3, "Bottombarrel Alehouse", 12);
		new Level(new Vector(-719, 72, -39), 3, "McButton's Goldenpot", 13);
		new Level(new Vector(-822, 82, -36), 3, "Delight Concentration", 14);
		new Level(new Vector(-886, 40, -19), 6, "Button Adagio in B minor", 15);
		new Level(new Vector(-956, 40, -20), 3, "Button Drawers", 16);
		new Level(new Vector(-1045, 72, -16), 3, "Dental Troubleshooting", 17);
		new Level(new Vector(-1148, 75, -15), 4, "Zoom Inn", 18);
		new Level(new Vector(-1290, 53, -14), 4, "Nucular Button Forum", 19, new Vector(-1260, 53, -14));
		new Level(new Vector(-1371, 57, -12), 7, "Buttressed Warehouse", 20, new Vector(-1371, 57, -30));
		new Level(new Vector(-1481, 52, -45), 2, "Buttonquail's Eggernesst", 21);
		new Level(new Vector(-1507, 85, -44), 4, "Purple Motif", 22);
		new Level(new Vector(-1638, 87, -48), 3, "Whirlpurpure Bayou", 23);
		new Level(new Vector(-1749, 99, -25), 3, "Buttolon III", 24, new Vector(-1775, 99, -25));
		new Level(new Vector(-1713, 13, -33), 7, "Chessboard Checkmate", 25, new Vector(-1760, 13, -33));
		new Level(new Vector(-1046, 48, -5794), 4, "Switched Rapids", 26);
		new Level(new Vector(-1877, 124, -6), 3, "Wicker Still-Life", 27);
		new Level(new Vector(-1971, 92, -37), 3, "Glowmoth Cocoon", 28, new Vector(-1971, 92, -10));
		new Level(new Vector(-2042, 84, -14), 5, "Crystal Grotto", 29, new Vector(-2075, 84, -14));
		new Level(new Vector(-2198, 108, 31), 8, "The Observants", 30, specialBounds(30, true), specialBounds(30, false));
		new Level(new Vector(-2330, 97, 16), 5, "Power Core", 31, new Vector(-2330, 97, -16));
		new Level(new Vector(-2462, 145, -11), 4, "Serpentine Stairwell", 32, new Vector(-2486, 145, -11));
		new Level(new Vector(-2615, 141, -2), 7, "Buttoncell Accumulator", 33, specialBounds(33, true), specialBounds(33, false));
		new Level(new Vector(-2886, 176, -4), 6, "Savannah Safari", 34, new Vector(-2845, 176, -4));
		new Level(new Vector(-3546, 58, 53), 10, "Gates to the Cosmohedron", 35, specialBounds(35, true), specialBounds(35, false));
		
	}

	//A FAIRE
	public static void createButtons() {

		//Level 1
		levels.get(0).addButton(new Button(new Vector(-46, 34, -47), 1));
		levels.get(0).addButton(new Button(new Vector(-58, 31, -29), 0));
		levels.get(0).addButton(new Button(new Vector(-81, 39, -33), 5, true));
		
		//Level 2
		levels.get(1).addButton(new Button(new Vector(-114, 13, -45), 5));
		levels.get(1).addButton(new Button(new Vector(-94, 13, -23), 1));
		levels.get(1).addButton(new Button(new Vector(-92, 9, -30), 4, true));

		//Level 3
		levels.get(2).addButton(new Button(new Vector(-172, 18, -22), 1));
		levels.get(2).addButton(new Button(new Vector(-140, 16, -40), 5));
		levels.get(2).addButton(new Button(new Vector(-153, 30, -12), 1));
		levels.get(2).addButton(new Button(new Vector(-128, 37, -30), 4, true));

		//Level 4
		levels.get(3).addButton(new Button(new Vector(-207, 11, -47), 3));
		levels.get(3).addButton(new Button(new Vector(-214, 13, -25), 4));
		levels.get(3).addButton(new Button(new Vector(-185, 33, -33), 4, true));

		//Level 5
		levels.get(4).addButton(new Button(new Vector(-243, 43, -47), 3));
		levels.get(4).addButton(new Button(new Vector(-281, 43, -39), 1));
		levels.get(4).addButton(new Button(new Vector(-261, 42, -26), 0));
		levels.get(4).addButton(new Button(new Vector(-268, 43, -17), 4));
		levels.get(4).addButton(new Button(new Vector(-255, 49, -44), 3, true));

		//Level 6
		levels.get(5).addButton(new Button(new Vector(-325, 29, -43), 1));
		levels.get(5).addButton(new Button(new Vector(-314, 24, -15), 0));
		levels.get(5).addButton(new Button(new Vector(-343, 22, -45), 3, true));

		//Level 7
		levels.get(6).addButton(new Button(new Vector(-402, 31, -45), 2));
		levels.get(6).addButton(new Button(new Vector(-384, 29, -43), 5));
		levels.get(6).addButton(new Button(new Vector(-396, 33, -33), 1));
		levels.get(6).addButton(new Button(new Vector(-416, 33, -36), 5, true));

		//Level 8
		levels.get(7).addButton(new Button(new Vector(-436, 22, -40), 4));
		levels.get(7).addButton(new Button(new Vector(-434, 19, -32), 0));
		levels.get(7).addButton(new Button(new Vector(-430, 33, -27), 4, true));

		//Level 9
		levels.get(8).addButton(new Button(new Vector(-480, 47, -33), 4));
		levels.get(8).addButton(new Button(new Vector(-474, 44, -25), 0));
		levels.get(8).addButton(new Button(new Vector(-468, 48, -39), 1));
		levels.get(8).addButton(new Button(new Vector(-465, 53, -26), 5, true));

		//Level 10
		levels.get(9).addButton(new Button(new Vector(-525, 58, -40), 5));
		levels.get(9).addButton(new Button(new Vector(-557, 60, -46), 3));
		levels.get(9).addButton(new Button(new Vector(-533, 62, -40), 1));
		levels.get(9).addButton(new Button(new Vector(-551, 57, -23), 1));
		levels.get(9).addButton(new Button(new Vector(-546, 58, -20), 5));
		levels.get(9).addButton(new Button(new Vector(-511, 59, -46), 4, true));

		//Level 11
		levels.get(10).addButton(new Button(new Vector(-581, 50, -16), 3));
		levels.get(10).addButton(new Button(new Vector(-578, 50, -36), 5));
		levels.get(10).addButton(new Button(new Vector(-613, 50, -30), 5, true));

		//Level 12
		levels.get(11).addButton(new Button(new Vector(-666, 70, -14), 1));
		levels.get(11).addButton(new Button(new Vector(-681, 71, -31), 3));
		levels.get(11).addButton(new Button(new Vector(-673, 70, -25), 1));
		levels.get(11).addButton(new Button(new Vector(-655, 71, -26), 2, true));

		//Level 13
		levels.get(12).addButton(new Button(new Vector(-725, 69, -23), 1));
		levels.get(12).addButton(new Button(new Vector(-736, 72, -34), 4));
		levels.get(12).addButton(new Button(new Vector(-751, 76, -31), 2));
		levels.get(12).addButton(new Button(new Vector(-736, 72, -30), 5, true));

		//Level 14
		levels.get(13).addButton(new Button(new Vector(-818, 85, -14), 0));
		levels.get(13).addButton(new Button(new Vector(-801, 81, -14), 4));
		levels.get(13).addButton(new Button(new Vector(-843, 90, -21), 0));
		levels.get(13).addButton(new Button(new Vector(-841, 85, 4), 2, true));

		//Level 15
		levels.get(14).addButton(new Button(new Vector(-925, 39, -24), 1));
		levels.get(14).addButton(new Button(new Vector(-921, 40, 0), 0));
		levels.get(14).addButton(new Button(new Vector(-887, 48, 2), 5));
		levels.get(14).addButton(new Button(new Vector(-922, 37, -14), 1));
		levels.get(14).addButton(new Button(new Vector(-906, 39, 4), 3));
		levels.get(14).addButton(new Button(new Vector(-896, 38, -27), 0));
		levels.get(14).addButton(new Button(new Vector(-905, 50, 9), 2, true));

		//Level 16
		levels.get(15).addButton(new Button(new Vector(-954, 41, 2), 2));
		levels.get(15).addButton(new Button(new Vector(-954, 47, -33), 5));
		levels.get(15).addButton(new Button(new Vector(-979, 43, -7), 0));
		levels.get(15).addButton(new Button(new Vector(-981, 49, -29), 4, true));

		//Level 17
		levels.get(16).addButton(new Button(new Vector(-1081, 74, -20), 0));
		levels.get(16).addButton(new Button(new Vector(-1044, 73, -6), 3));
		levels.get(16).addButton(new Button(new Vector(-1067, 73, -2), 4));
		levels.get(16).addButton(new Button(new Vector(-1066, 71, -39), 3, true));

		//Level 18
		levels.get(17).addButton(new Button(new Vector(-1156, 75, -38), 1));
		levels.get(17).addButton(new Button(new Vector(-1185, 75, -23), 2));
		levels.get(17).addButton(new Button(new Vector(-1188, 77, -5), 3));
		levels.get(17).addButton(new Button(new Vector(-1147, 75, 0), 1));
		levels.get(17).addButton(new Button(new Vector(-1177, 80, -45), 3, true));

		//Level 19
		levels.get(18).addButton(new Button(new Vector(-1259, 44, -31), 3));
		levels.get(18).addButton(new Button(new Vector(-1235, 47, 21), 4));
		levels.get(18).addButton(new Button(new Vector(-1291, 43, -9), 3));
		levels.get(18).addButton(new Button(new Vector(-1262, 55, -12), 4));
		levels.get(18).addButton(new Button(new Vector(-1262, 61, -24), 2, true));

		//Level 20
		levels.get(19).addButton(new Button(new Vector(-1368, 59, -50), 3));
		levels.get(19).addButton(new Button(new Vector(-1369, 57, -30), 0));
		levels.get(19).addButton(new Button(new Vector(-1363, 54, -23), 2));
		levels.get(19).addButton(new Button(new Vector(-1379, 65, -18), 1));
		levels.get(19).addButton(new Button(new Vector(-1368, 53, -33), 1));
		levels.get(19).addButton(new Button(new Vector(-1353, 58, -19), 3));
		levels.get(19).addButton(new Button(new Vector(-1384, 56, -18), 1));
		levels.get(19).addButton(new Button(new Vector(-1388, 54, -29), 5, true));

		//Level 21
		levels.get(20).addButton(new Button(new Vector(-1457, 64, -42), 5));
		levels.get(20).addButton(new Button(new Vector(-1463, 52, -54), 1));
		levels.get(20).addButton(new Button(new Vector(-1441, 71, -45), 4, true));

		//Level 22
		levels.get(21).addButton(new Button(new Vector(-1530, 93, -57), 4));
		levels.get(21).addButton(new Button(new Vector(-1544, 87, -39), 4));
		levels.get(21).addButton(new Button(new Vector(-1530, 89, -26), 1));
		levels.get(21).addButton(new Button(new Vector(-1518, 87, -34), 3));
		levels.get(21).addButton(new Button(new Vector(-1507, 93, -16), 4, true));

		//Level 23
		levels.get(22).addButton(new Button(new Vector(-1627, 97, -19), 1));
		levels.get(22).addButton(new Button(new Vector(-1638, 83, -10), 0));
		levels.get(22).addButton(new Button(new Vector(-1626, 103, -44), 1));
		levels.get(22).addButton(new Button(new Vector(-1655, 94, -34), 5, true));

		//Level 24
		levels.get(23).addButton(new Button(new Vector(-1785, 99, -23), 3));
		levels.get(23).addButton(new Button(new Vector(-1796, 123, 0), 2));
		levels.get(23).addButton(new Button(new Vector(-1738, 107, -33), 4));
		levels.get(23).addButton(new Button(new Vector(-1776, 101, -43), 2, true));

		//Level 25
		levels.get(24).addButton(new Button(new Vector(-1775, 13, -60), 2));
		levels.get(24).addButton(new Button(new Vector(-1735, 13, -85), 3));
		levels.get(24).addButton(new Button(new Vector(-1714, 12, -47), 4));
		levels.get(24).addButton(new Button(new Vector(-1764, 13, 8), 4));
		levels.get(24).addButton(new Button(new Vector(-1711, 12, -18), 1));
		levels.get(24).addButton(new Button(new Vector(-1804, 12, -75), 4));
		levels.get(24).addButton(new Button(new Vector(-1706, 13, -29), 4));
		levels.get(24).addButton(new Button(new Vector(-1774, 10, 3), 3, true));

		//Level 26
		levels.get(25).addButton(new Button(new Vector(-1049, 49, -5788), 2));
		levels.get(25).addButton(new Button(new Vector(-1046, 51, -5771), 0));
		levels.get(25).addButton(new Button(new Vector(-1034, 51, -5771), 1));
		levels.get(25).addButton(new Button(new Vector(-1044, 44, -5789), 0));
		levels.get(25).addButton(new Button(new Vector(-1045, 53, -5766), 2, true));

		//Level 27
		levels.get(26).addButton(new Button(new Vector(-1858, 122, -20), 5));
		levels.get(26).addButton(new Button(new Vector(-1873, 124, -26), 0));
		levels.get(26).addButton(new Button(new Vector(-1879, 126, 0), 1));
		levels.get(26).addButton(new Button(new Vector(-1897, 126, -8), 2, true));

		//Level 28
		levels.get(27).addButton(new Button(new Vector(-1959, 93, -6), 0));
		levels.get(27).addButton(new Button(new Vector(-1964, 104, -7), 2));
		levels.get(27).addButton(new Button(new Vector(-1984, 93, -11), 3));
		levels.get(27).addButton(new Button(new Vector(-2003, 107, -8), 5, true));

		//Level 29
		levels.get(28).addButton(new Button(new Vector(-2080, 80, -27), 4));
		levels.get(28).addButton(new Button(new Vector(-2076, 77, -5), 3));
		levels.get(28).addButton(new Button(new Vector(-2101, 83, -20), 5));
		levels.get(28).addButton(new Button(new Vector(-2048, 80, -22), 5));
		levels.get(28).addButton(new Button(new Vector(-2048, 79, 2), 0));
		levels.get(28).addButton(new Button(new Vector(-2111, 98, -9), 5, true));

		//Level 30
		levels.get(29).addButton(new Button(new Vector(-2138, 110, 39), 2));
		levels.get(29).addButton(new Button(new Vector(-2229, 113, -51), 5));
		levels.get(29).addButton(new Button(new Vector(-2166, 110, -59), 4));
		levels.get(29).addButton(new Button(new Vector(-2227, 108, -81), 1));
		levels.get(29).addButton(new Button(new Vector(-2230, 112, -17), 5));
		levels.get(29).addButton(new Button(new Vector(-2230, 115, -65), 5));
		levels.get(29).addButton(new Button(new Vector(-2191, 111, 39), 0));
		levels.get(29).addButton(new Button(new Vector(-2246, 109, -43), 4));
		levels.get(29).addButton(new Button(new Vector(-2237, 140, -21), 5, true));

		//Level 31
		levels.get(30).addButton(new Button(new Vector(-2306, 81, -17), 4));
		levels.get(30).addButton(new Button(new Vector(-2354, 112, 14), 1));
		levels.get(30).addButton(new Button(new Vector(-2310, 94, 5), 4));
		levels.get(30).addButton(new Button(new Vector(-2317, 96, -7), 2));
		levels.get(30).addButton(new Button(new Vector(-2321, 99, -42), 5));
		levels.get(30).addButton(new Button(new Vector(-2335, 68, -34), 3, true));

		//Level 32
		levels.get(31).addButton(new Button(new Vector(-2500, 126, 5), 3));
		levels.get(31).addButton(new Button(new Vector(-2457, 147, -17), 0));
		levels.get(31).addButton(new Button(new Vector(-2516, 131, -25), 2));
		levels.get(31).addButton(new Button(new Vector(-2492, 143, -1), 2));
		levels.get(31).addButton(new Button(new Vector(-2500, 138, -43), 3, true));
		
		//Level 33
		levels.get(32).addButton(new Button(new Vector(-2688, 141, 4), 4));
		levels.get(32).addButton(new Button(new Vector(-2669, 164, 0), 5));
		levels.get(32).addButton(new Button(new Vector(-2614, 155, -4), 4));
		levels.get(32).addButton(new Button(new Vector(-2618, 149, -34), 5));
		levels.get(32).addButton(new Button(new Vector(-2642, 142, -35), 2));
		levels.get(32).addButton(new Button(new Vector(-2688, 138, 25), 3));
		levels.get(32).addButton(new Button(new Vector(-2629, 144, 51), 1));
		levels.get(32).addButton(new Button(new Vector(-2659, 160, -16), 2, true));

		//Level 34
		levels.get(33).addButton(new Button(new Vector(-2811, 181, 31), 5));
		levels.get(33).addButton(new Button(new Vector(-2854, 180, 38), 3));
		levels.get(33).addButton(new Button(new Vector(-2877, 174, -8), 0));
		levels.get(33).addButton(new Button(new Vector(-2870, 176, -38), 5));
		levels.get(33).addButton(new Button(new Vector(-2859, 178, 3), 1));
		levels.get(33).addButton(new Button(new Vector(-2856, 186, -36), 3));
		levels.get(33).addButton(new Button(new Vector(-2808, 186, 2), 5, true));

		//Level 35
		levels.get(34).addButton(new Button(new Vector(-3628, 55, 44), 5));
		levels.get(34).addButton(new Button(new Vector(-3585, 57, 82), 2));
		levels.get(34).addButton(new Button(new Vector(-3536, 54, 111), 5));
		levels.get(34).addButton(new Button(new Vector(-3559, 55, 72), 2));
		levels.get(34).addButton(new Button(new Vector(-3641, 55, 105), 2));
		levels.get(34).addButton(new Button(new Vector(-3692, 61, 41), 5));
		levels.get(34).addButton(new Button(new Vector(-3699, 72, -11), 5));
		levels.get(34).addButton(new Button(new Vector(-3630, 56, -19), 2));
		levels.get(34).addButton(new Button(new Vector(-3574, 72, -35), 3));
		levels.get(34).addButton(new Button(new Vector(-3559, 67, -18), 1));
		levels.get(34).addButton(new Button(new Vector(-3630, 67, -17), 3, true));
		
	}
	
	//---------------------------------------------------------------
	//  Autres
	
	public static String tpButtAS(int nbreButtons) {
		
		switch (nbreButtons) {
		
		case 2: return "1049 101 1049";
		case 3: return "1045 101 1049";
		case 4: return "1040 101 1049";
		case 5: return "1034 101 1049";
		case 6: return "1027 101 1049";
		case 7: return "1019 101 1049";
		case 8: return "1010 101 1049";
		case 10: return "1000 101 1049";
		
		default: return "----------------- ERROR ---------------------------- TPBUTTAS " + nbreButtons;
		}
		
	}

}