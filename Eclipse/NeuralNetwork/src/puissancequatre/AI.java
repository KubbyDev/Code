package puissancequatre;

import neuralnetwork.BiologicalNetwork;
import neuralnetwork.DeepNetwork;
import neuralnetwork.Network;
import neuralnetwork.SimpleNetwork;

public class AI extends Player {

	public Network reseau;
	
	public AI(SimpleNetwork sn) {
		
		reseau = sn;
		
	}
	
	public AI(DeepNetwork dn) {
		
		reseau = dn;
		
	}
	
	public AI(BiologicalNetwork bn) {
		
		reseau = bn;
		
	}
	
	@Override
	public int play(int[] plateau, int player) {
		
		//System.out.println("Play");
		
		float[] inputs = readPlateau(plateau, player);		
		float[] outputs = reseau.simulate(inputs);
		
		//Training.disp(inputs);
		//Training.disp(outputs);
		
		float maxValue = 1.0f;
		float newMaxValue = 0.0f;
		
		int play = -1;
		
		do {
			
			//On prend tous les output qui ont le score maximum et on determine le meilleur score sans eux
			for(int i = 0; i < outputs.length; i++) {
				
				//Si le resultat trouve est un max, et qu'on peut y jouer, on garde
				if(outputs[i] == maxValue && Game.canPlayAt(i, plateau))
					play = i;
				
				//Sinon, on verifie que ce n'est pas un output deja teste, et on change la newMaxValue si necessaire
				else if(outputs[i] < maxValue)
					newMaxValue = Math.max(outputs[i], newMaxValue);
				
			}
			
			maxValue = newMaxValue;
			newMaxValue = 0;
			
		} while(play == -1);
				
		//System.out.println("Play: " + play);
		
		return play;
	}
	
	public static float[] readPlateau(int[] plateau, int player) {
		
		float[] f = new float[85];
	
		for(int i = 0; i < 42; i++)
			f[i] = (plateau[i] == player) ? 1.0f : 0.0f;

		for(int i = 42; i < 84; i++)
			f[i] = (plateau[i-42] == -player) ? 1.0f : 0.0f;
		
		//Cet input permet de "mettre du courant" dans le reseau meme si aucun input n'est allume
		f[84] = 1.0f;
		
		return f;
	}
	
}
