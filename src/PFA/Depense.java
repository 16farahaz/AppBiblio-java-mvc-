package PFA;

import java.sql.Date;

public class Depense {
	String Nom, Type,Description;
	float montant;
	Date datedep;
	public Depense(String nom, String type, String description, float montant, Date datedep) {
		this.Nom = nom;
		this.Type = type;
		this.Description = description;
		this.montant = montant;
		this.datedep = datedep;
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
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public float getMontant() {
		return montant;
	}
	public void setMontant(float montant) {
		this.montant = montant;
	}
	public Date getDatedep() {
		return datedep;
	}
	public void setDatedep(Date datedep) {
		this.datedep = datedep;
	}
	
	

}