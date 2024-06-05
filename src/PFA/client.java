package PFA;

import java.sql.Date;

public class client {
	int CIN , NUM_TELEPHONE ; 
	 String NOM, PRENOM , ADRESS,EMAIL,NIVEAU, SEXE  ;
	 Date DATE_DE_NAISSANCE;
	public  client(int cIN,  String nOM, String pRENOM,String sexe ,String aDRESS, Date dATE_DE_NAISSANCE, 
			String eMAIL, String nIVEAU,  int nUM_TELEPHONE ) {
		
		CIN = cIN;
		
		NOM = nOM;
		PRENOM = pRENOM;
		SEXE = sexe ;
		ADRESS = aDRESS;
		DATE_DE_NAISSANCE = dATE_DE_NAISSANCE;
		
		EMAIL = eMAIL;
		NIVEAU=nIVEAU;
		
		NUM_TELEPHONE = nUM_TELEPHONE;
	}
	
	public client(int cin, String nom, String prenom) {
		this.CIN=cin;
		this.NOM=nom;
		this.PRENOM=prenom;
	}
	
	public int getCIN() {
		return CIN;
	}
	public void setCIN(int cIN) {
		CIN = cIN;
	}
	public int getNUM_TELEPHONE() {
		return NUM_TELEPHONE;
	}
	public void setNUM_TELEPHONE(int nUM_TELEPHONE) {
		NUM_TELEPHONE = nUM_TELEPHONE;
	}
	public String getNOM() {
		return NOM;
	}
	public void setNOM(String nOM) {
		NOM = nOM;
	}
	public String getPRENOM() {
		return PRENOM;
	}
	public void setPRENOM(String pRENOM) {
		PRENOM = pRENOM;
	}
	public Date getDATE_DE_NAISSANCE() {
		return DATE_DE_NAISSANCE;
	}
	public void setDATE_DE_NAISSANCE(Date dATE_DE_NAISSANCE) {
		DATE_DE_NAISSANCE = dATE_DE_NAISSANCE;
	}
	public String getADRESS() {
		return ADRESS;
	}
	public void setADRESS(String aDRESS) {
		ADRESS = aDRESS;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	public String getNIVEAU() {
		return NIVEAU;
	}
	public void setNIVEAU(String nIVEAU) {
		NIVEAU = nIVEAU;
	}
	
	public String getSEXE() {
		return SEXE;
	}
	public void setSEXE(String sEXE) {
		SEXE = sEXE;
	}
	}


