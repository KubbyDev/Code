package reseau;

public class TestResult {

	public int testsNumber = 0;
	public int errorsNumber = 0;
	public float errorRate = 0.0f;
	
	public TestResult(int pTestsNumber, int pErrorsNumber) {
		
		testsNumber = pTestsNumber;
		errorsNumber = pErrorsNumber;
		
		errorRate = ((float) errorsNumber / (float) testsNumber) * 100;
		
	}
	
	public void display() {
		
		errorRate = ((float) errorsNumber / (float) testsNumber) * 100;
		
		System.out.println("\nNumber of test examples: " + testsNumber
				+ " - Errors: " + errorsNumber
				+ " - Error Rate: " + errorRate
				+ " %");
		
	}
	
}
