package PFA;

public class Enseigne {
	int cin,numSession,salaire;
	String nom,prenom,diplome;
	public Enseigne(int cin,int numSession,int salaire,String nom,String prenom){
		this.cin=cin;
		this.numSession=numSession;
		this.salaire=salaire;
		this.nom=nom;
		this.prenom=prenom;
	}
	
	public Enseigne(int cin,int numSession,String diplome,String nom,String prenom){
		this.cin=cin;
		this.numSession=numSession;
		this.diplome=diplome;
		this.nom=nom;
		this.prenom=prenom;
	}


	public String getDiplome() {
		return diplome;
	}

	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getCin() {
		return cin;
	}

	public void setCin(int cin) {
		this.cin = cin;
	}

	public int getNumSession() {
		return numSession;
	}

	public void setNumSession(int numSession) {
		this.numSession = numSession;
	}

	public int getSalaire() {
		return salaire;
	}

	public void setSalaire(int salaire) {
		this.salaire = salaire;
	}
	
}
