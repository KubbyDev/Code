package puissancequatre;

import neuralnetwork.Network;

public class AIv2 extends MinimaxAI {

	public Network plateRater;
	public Network playDecider;
	public int maxTime;
	
	public AIv2(int timeMS, Network pPR, Network pPD) {
		
		super(8);
		
		plateRater = pPR;
		playDecider = pPD;
		maxTime = timeMS;
		
	}
	
	@Override
	protected int onHasToStop(int isOpponent, int stopChecker, int[] plateau, int player) {
		
		//Renvoie une note du plateau rencontre. Plus ce plateau a de chances (selon le plateRater) de mener a la victoire, plus la note est elevee
		return Network.getAnswer(plateRater.simulate(AI.readPlateau(plateau, player)));
	}
	
	@Override
	protected int onNothingFound(int isOpponent, int stopChecker, int[] plateau, int player) {
		
		return select(getRates(plateau, player * -1, isOpponent * -1, stopChecker-1), isOpponent * -1);
	}
	
	@Override
	protected int onVictory(int isOpponent, int stopChecker, int[] plateau, int player) {
		
		return stopChecker * -isOpponent * 1000;
	}
	
	@Override
	protected boolean hasToStop(int stopChecker) {
		
		return stopChecker == 1;
	}
	
}
