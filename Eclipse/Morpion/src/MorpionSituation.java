import java.io.Serializable;

public class MorpionSituation implements Serializable {
	
	private static final long serialVersionUID = -5072937003434264093L;
	
	private int[] situation = new int[9];
	private int[] resolution;
	
	public MorpionSituation() {
	}
	
	public MorpionSituation(int[] plat) {
		this.situation = Morpion.createCopy(plat);
		this.calcResolution();
	}
	
	public void calcResolution() {
		
		int[] possiblePlays = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		int nbrePossiblePlays = 0;
		for(int i = 0; i < this.situation.length; i++)
		{
			if(this.situation[i] == 0)
			{
				possiblePlays[nbrePossiblePlays] = i;
				nbrePossiblePlays++;
			}		
		}
		this.resolution = new int[nbrePossiblePlays * 2];
		
		int i = 0;
		while(possiblePlays[i] != -1)
		{
			this.resolution[i] = possiblePlays[i];
			i++;
		}
		
		while(i < this.resolution.length)
		{
			this.resolution[i] = 1;
			i++;
		}
	}

	public void dispResolution() {
		for(int i : this.resolution)
		{
			System.out.print(i + " ");
		}
	}

	public int[] getResolution() {
		return this.resolution;
	}
	
	public int[] getSituation() {
		return this.situation;
	}
	
	public void setSituation(int[] newSituation) {
		Morpion.copyTo(newSituation, this.situation);
	}
	
	public int getRes(int i) {
		return this.resolution[i];
	}
	
	public int getSit(int i) {
		return this.situation[i];
	}

	public void setResolution(int[] newResolution) {
		Morpion.copyTo(newResolution, this.resolution);
	}
	
}
