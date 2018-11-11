import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Hats {

	public static ArrayList<Hat> hat = new ArrayList<Hat>();
	public static String path = "C:\\Users\\Deve\\Desktop\\HatsActions\\";
	
	public static void main(String[] args) {
		
		
		fillArray();
		
		/*
		for(int i = 0; i < hat.size(); i++) {
			
			File f = new File(path + hat.get(i).tag + ".mcfunction");
			try {
				f.createNewFile();
			} catch (IOException e) { e.printStackTrace(); }
			
			PrintWriter writer;
			try {
				writer = new PrintWriter(f, "UTF-8");
				writer.println(commandSign(hat.get(i).tag, hat.get(i).name));
				writer.close();
			} catch (FileNotFoundException e) {e.printStackTrace();} catch (UnsupportedEncodingException e) {e.printStackTrace();}
			
		}
		*/
		
		/*
		signActionFile("unicorn", -1, "Unicorn");
		signActionFile("leaves", -2, "Leaves");
		signActionFile("jukebox", -7, "Jukebox");
		signActionFile("trump", -10, "Trump");
		signActionFile("lunalovegood", -11, "Luna Lovegood");
		signActionFile("bunnygirl", -12, "Bunny Girl");
		signActionFile("peruvianhat", -22, "Peruvian");
		signActionFile("jacksparrowhat", -23, "Jack Sparrow");
		signActionFile("stormtrooperhelmet", -27, "Star Wars");
		signActionFile("astronaut", -31, "Astronaut");
		signActionFile("commandblockcrazy", -36, "Command Block Crazy");
		
		getHatSign("Unicorn", -1);
		getHatSign("Leaves", -2);
		getHatSign("Jukebox", -7);
		getHatSign("Trump", -10);
		getHatSign("Luna Lovegood", -11);
		getHatSign("Bunny Girl", -12);
		getHatSign("Peruvian", -22);
		getHatSign("Jack Sparrow", -23);
		getHatSign("Star Wars", -27);
		getHatSign("Astronaut", -31);
		getHatSign("Command Crazy", -36);
		*/
		
	}	
	
	public static void getHatSign(String signLine2, int value) {

		System.out.print("give @p minecraft:sign 1 0 {display:{Name:\"" + signLine2 + "\"},BlockEntityTag:{");
		System.out.print("Text1:\"{\\\"clickEvent\\\":{\\\"action\\\":\\\"run_command\\\",\\\"value\\\":\\\"/trigger SelectedHat set " + value + "\\\"},\\\"text\\\":\\\"---------------\\\"}\",");
		System.out.print("Text2:\"{\\\"text\\\":\\\"Hat:\\\",\\\"color\\\":\\\"white\\\",\\\"bold\\\":\\\"true\\\"}\",");
		System.out.print("Text3:\"{\\\"text\\\":\\\"" + signLine2 + "\\\",\\\"color\\\":\\\"green\\\",\\\"bold\\\":\\\"true\\\"}\",");
		System.out.print("Text4:\"{\\\"text\\\":\\\"---------------\\\"}\"");
		System.out.println("}}");
		
	}
	
	public static String commandSign(String tagName, String displayName) {

		String s = "";
		
        s += "scoreboard players tag @s add " + tagName;
        s += "\n";
        s += "playsound minecraft:block.note.pling record @p ~ ~ ~ 2 1 1";
        s += "\n";
        s += "tellraw @p [\"\",{\"text\":\"[HAT] \",\"color\":\\\"green\\\",\\\"bold\\\":true},{\"text\":\"You found the " + displayName + " hat!\",\"color\":\\\"aqua\\\",\\\"bold\\\":false}]";

        return s;
    }
	
	public static void generateSign(String signLine1, String signLine2, String functionName) {

		System.out.print("/give @p minecraft:sign 1 0 {BlockEntityTag:{");
		System.out.print("Text1:\"{\\\"clickEvent\\\":{\\\"action\\\":\\\"run_command\\\",\\\"value\\\":\\\"execute @s ~ ~ ~ function gunivers-lib:Dev/map/hierarchy/easteregg/hat/GetHat/" + functionName + "\\\"},\\\"text\\\":\\\"---------------\\\"}\",");
		System.out.print("Text2:\"{\\\"text\\\":\\\"" + signLine1 + "\\\"}\",");
		System.out.print("Text3:\"{\\\"text\\\":\\\"" + signLine2 + "\\\"}\",");
		System.out.print("Text4:\"{\\\"text\\\":\\\"---------------\\\"}\"");
		System.out.print("}}");
		
	}
	
	public static void fillArray() {
		
		new Hat("Unicorn", "UNICORN POWER!!!", 0, "end_rod");
		new Hat("Astronaut", "Houston, we have a problem!", 0, "glass");
		new Hat("Command block crazy", "One day in the head of LeiRoF", 0, "command_block");
		new Hat("Santa Claus Hat", "Jingle bell, jingle bell", 49, "diamond_shovel");
		new Hat("Pikachu", "Who is that Pokémon?", 39, "diamond_shovel");
		new Hat("Trump", "I will build a great wall!", 30, "diamond_shovel");
		new Hat("Luna Lovegood", "", 24, "diamond_shovel"); //Desc TODO
		new Hat("Bunny Girl", "I'm a Bunny girl, in a Bunny world ", 27, "diamond_shovel");
		new Hat("Pink Ribbon", "I'M FABULOUUUUUUS!!!", 48, "diamond_shovel");
		new Hat("Lion", "Hakuna Matata", 23, "diamond_shovel");
		new Hat("Wolf", "John Snow approved", 26, "diamond_shovel");
		new Hat("Sorting Hat", "Gryffindor!", 15, "diamond_shovel");
		new Hat("Devil Horn", "Deal with the Devil!", 8, "diamond_shovel");
		new Hat("Golden Hood", "Rush to Gold", 4, "diamond_shovel");
		new Hat("Pumpkin Hat", "This is Halloween, this is Halloween\nPumpkins scream in the dead of night", 2, "diamond_shovel");
		new Hat("SM Hat", "It feels so good!", 55, "diamond_shovel");
		new Hat("Hagrid beard", "You are a wizard Harry", 100, "diamond_shovel");
		new Hat("Big Eyes", "I CAN SEE YOU!", 107, "diamond_shovel");
		new Hat("Deer", "", 6, "diamond_shovel"); //Desc TODO
		new Hat("Reindeer", "", 54, "diamond_shovel"); //Desc TODO
		new Hat("Peruvian Hat", "", 43, "diamond_shovel"); //Desc TODO
		new Hat("Jack Sparrow Hat", "You're not making any sense at all", 46, "diamond_shovel");
		new Hat("Fez", "Fez is cool.", 47, "diamond_shovel");
		new Hat("Death", "Also called \"my ex-wife\"", 144, "diamond_shovel");
		new Hat("Crown", "King's dead! Glory to the King!", 112, "diamond_shovel");
		new Hat("Link Hat", "HEY! LISTEN!", 99, "diamond_shovel");
		new Hat("Storm Trooper Helmet", "IT'S A TRAP!", 87, "diamond_shovel");
		new Hat("Slime Hat", "Asilis & theogiraudet approved", 3, "diamond_shovel");
		new Hat("Witch Hat", "Are u a wizard?", 116, "diamond_shovel");
		
	}
	
	public static void signActionFile(String tagName, int value, String hatName) {
		System.out.println("# " + hatName);
		error(tagName, value);
		tellraw(tagName, value, hatName);
		sounds(tagName, value);
		advancement(tagName, value);
		giveTag(tagName, value);
		System.out.print("\n");
	}
	
	public static void error(String tagName, int value) {
		System.out.print("execute @a[score_SelectedHat_min=" + value + ",score_SelectedHat=" + value + ",tag=" + tagName + "] ~ ~ ~ ");
		System.out.println("tellraw @s {\"text\":\"You already have this hat !\",\"color\":\"red\"}");
	}
	
	public static void advancement(String tagName, int value) {
		System.out.println("execute @a[score_SelectedHat_min=" + value + ",score_SelectedHat=" + value + "] ~ ~ ~ " + "advancement grant @s only hat:" + tagName);
	}
	
	public static void sounds(String tagName, int value) {
		System.out.println("execute @a[score_SelectedHat_min=" + value + ",score_SelectedHat=" + value + ",tag=" + tagName + "] ~ ~ ~ playsound block.note.snare ambient @s");
		System.out.println("execute @a[score_SelectedHat_min=" + value + ",score_SelectedHat=" + value + ",tag=!" + tagName + "] ~ ~ ~ playsound entity.experience_orb.pickup ambient @s");
	}
	
	public static void tellraw(String tagName, int value, String hatName) {
		System.out.println("execute @a[score_SelectedHat_min=" + value + ",score_SelectedHat=" + value + ",tag=!" + tagName + "] ~ ~ ~ tellraw @s {\"text\":\">> New Hat: \",\"color\":\"white\",\"bold\":\"true\",\"extra\":[{\"text\":\"You found the\",\"color\":\"dark_green\",\"bold\":\"false\"},{\"text\":\" " + hatName + " \",\"color\":\"green\"},{\"text\":\"hat !\",\"color\":\"dark_green\",\"bold\":\"false\"}]}");
	}
	
	public static void giveTag(String tagName, int value) {
		System.out.println("scoreboard players tag @a[score_SelectedHat_min=" + value + ",score_SelectedHat=" + value + "] add " + tagName);
	}
	
}
