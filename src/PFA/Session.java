package PFA;

import java.sql.Date;

public class Session {
	int numSession,nbrEnseigne;
	Formation form;
	String nomSession,heurDeb,heurFin,jour;
	Salle salle;
	Date dateDebut,dateFin;
	
	public Session(int numSession, String nomSession, Formation form, Date dateDeb, Date dateFin,String heurDeb,String heurFin,String jour, Salle salle) {
		this.numSession=numSession;
		this.nomSession=nomSession;
		this.form=form;
		this.dateDebut=dateDeb;
		this.dateFin=dateFin;
		this.heurDeb=heurDeb;
		this.heurFin=heurFin;
		this.jour=jour;
		this.salle=salle;
		nbrEnseigne=0;
	}

	public Session(int numSession, Formation form) {
		this.numSession=numSession;
		this.form=form;
	}
	
	public Session(int numSession) {
		this.numSession=numSession;
	}
	
	public String getHeurFin() {
		return heurFin;
	}

	public void setHeurFin(String heurFin) {
		this.heurFin = heurFin;
	}
	
	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public int getNumSession() {
		return numSession;
	}

	public void setNumSession(int numSession) {
		this.numSession = numSession;
	}

	public Formation getForm() {
		return form;
	}

	public void setForm(Formation form) {
		this.form = form;
	}

	public String getNomSession() {
		return nomSession;
	}

	public void setNomSession(String nomSession) {
		this.nomSession = nomSession;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public int getNbrEnseigne() {
		return nbrEnseigne;
	}

	public void setNbrEnseigne(int nbrEnseigne) {
		this.nbrEnseigne = nbrEnseigne;
	}

	public String getHeurDeb() {
		return heurDeb;
	}

	public void setHeurDeb(String heurDeb) {
		this.heurDeb = heurDeb;
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String jour) {
		this.jour = jour;
	}
	
}
