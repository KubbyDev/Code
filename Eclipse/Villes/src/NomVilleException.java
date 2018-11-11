class NomVilleException extends Exception {
	public NomVilleException(String pVille) {
		System.out.println("Le nom de la ville contient moins de 3 caracteres ! (" + pVille + ")");
	}
}
