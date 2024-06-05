package PFA;

import java.sql.Date;

public class Stat {
	Formation form;
	String niveauCli;
	int ageCli;
	Date dateDeb;
	Date dateFin;
	
	public Stat(Formation form, String niveauCli, int ageCli, Date dateDeb, Date dateFin) {
		this.form=form;
		this.niveauCli=niveauCli;
		this.ageCli=ageCli;
		this.dateDeb=dateDeb;
		this.dateFin=dateFin;
	}
	

	public Formation getForm() {
		return form;
	}

	public void setForm(Formation form) {
		this.form = form;
	}

	public String getNiveauCli() {
		return niveauCli;
	}

	public void setNiveauCli(String niveauCli) {
		this.niveauCli = niveauCli;
	}

	public int getAgeCli() {
		return ageCli;
	}

	public void setAgeCli(int ageCli) {
		this.ageCli = ageCli;
	}

	public Date getDateDeb() {
		return dateDeb;
	}

	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	
	
}
