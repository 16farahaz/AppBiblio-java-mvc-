package PFA;


import java.sql.Date;

import PFA.client; 

public class Revenue {
	
	String nom,source,description;
	float montant;
	Date date_de_recu;
	
	public Revenue(String nom, String source, String description, float montant, Date date_de_recu) {

		this.nom = nom;
		this.source = source;
		this.description = description;
		this.montant = montant;
		this.date_de_recu = date_de_recu;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getMontant() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	public Date getDate_de_recu() {
		return date_de_recu;
	}

	public void setDate_de_recu(Date date_de_recu) {
		this.date_de_recu = date_de_recu;
	}
		

		
}

