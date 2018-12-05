
public class DecryptageCesar {

	public static void main(String[] args) {

		String s = "Url, Iraqerqv cebpunva, ra cnenyy�yr qr yn IWA qh 7/12, har pbas�erapr frpe�gr ra IN105 rfg betnavf�r � cnegve qr 20:00 rg cbhe har obaar cnegvr qr yn ahvg ! Ornhpbhc q'rager ibhf bag q�w� nffvfg� � prggr pbas�erapr yrf naa�rf cnff�rf, ryyr fren qr abhirnh qbaa�r cne Enzn, ha napvra �cvg�ra dhv ibhf cneyren ovra zvrhk qr yhv fv ibhf nyyrm y'�pbhgre ;) Vy ibhf cneyren qr cyrva qr crgvgrf pubfrf dh'vy n nccevfrf � rg fhe y'�pbyr qrchvf dh'vy l n �g�, nvafv dhr qrf pbafrvyf cbhe yr zbaqr qr y'ragercevfr. Ar znadhrm cnf q'nyyre cebsvgre qrf npgvivg�f cebcbf�rf cne IWA qr grzcf-ra-grzcf craqnag yn pbas, bh vairefrzrag : uggcf://jjj.snprobbx.pbz/riragf/292858717999516/ Dhrfgvbaf, erznedhrf, pubfrf cnf pynverf ? Irarm abzoerhk rg ibhf nherm ibf e�cbafrf !";
		
		String res = "";
		for(int i = 0; i < s.length(); i++)
			res += decrypt(s.charAt(i), 13);
		
		System.out.println(res);
	}
	
	public static char decrypt(char c, int key) {
		
		if(c >= 'a' && c <= 'z')
			return (char) (((int) c + key - 'a')%26 + 'a');
		
		if(c >= 'A' && c <= 'Z')
			return (char) (((int) c + key - 'A')%26 + 'A');
		
		return c;
	}

}
