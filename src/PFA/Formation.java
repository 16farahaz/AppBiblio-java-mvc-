package PFA;

public class Formation {
	private int ID;
    private String Nom ; 
    private String Type ;
    private String Domaine ;
    private int Duree ;
    private String Contenue ; 
    private String Certification;
    private float Tarif ;
    private String Niveau;
    
	public Formation(int iD, String nom, String type, String domaine, int duree, String contenue,
			String certification, float tarif, String niveau) {
		ID = iD;
		Nom = nom;
		Type = type;
		Domaine = domaine;
		Duree = duree;
		Contenue = contenue;
		Certification = certification;
		Tarif = tarif;
		Niveau = niveau;
	}
	
	public Formation(int ID, float tarif) {
		this.ID=ID;
		this.Tarif=tarif;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNom() {
		return Nom;
	}

	public void setNom(String nom) {
		Nom = nom;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getDomaine() {
		return Domaine;
	}

	public void setDomaine(String domaine) {
		Domaine = domaine;
	}

	public int getDuree() {
		return Duree;
	}

	public void setDuree(int duree) {
		Duree = duree;
	}

	public String getContenue() {
		return Contenue;
	}

	public void setContenue(String contenue) {
		Contenue = contenue;
	}

	public String getCertification() {
		return Certification;
	}

	public void setCertification(String certification) {
		Certification = certification;
	}

	public float getTarif() {
		return Tarif;
	}

	public void setTarif(float tarif) {
		Tarif = tarif;
	}

	public String getNiveau() {
		return Niveau;
	}

	public void setNiveau(String niveau) {
		Niveau = niveau;
	}
    
    
}
