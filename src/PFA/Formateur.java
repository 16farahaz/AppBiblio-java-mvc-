package PFA;

import java.sql.Date;
import java.time.LocalDate;

public class Formateur {
 int CIN , NUM_TELEPHONE ; 
 String NOM, PRENOM , ADRESS,EMAIL,DIPLÔME,  EXPERTISE, SEXE  ;
 Date DATE_DE_NAISSANCE; 
public Formateur(int cIN,  String nOM, String pRENOM,String sexe ,String aDRESS, Date dATE_DE_NAISSANCE, 
		String eMAIL, String dIPLÔME, String eXPERTISE,  int nUM_TELEPHONE ) {
	
	CIN = cIN;
	
	NOM = nOM;
	PRENOM = pRENOM;
	SEXE = sexe ;
	ADRESS = aDRESS;
	DATE_DE_NAISSANCE = dATE_DE_NAISSANCE;
	
	EMAIL = eMAIL;
	DIPLÔME = dIPLÔME;
	EXPERTISE = eXPERTISE;
	
	NUM_TELEPHONE = nUM_TELEPHONE;
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
public String getDIPLÔME() {
	return DIPLÔME;
}
public void setDIPLÔME(String dIPLÔME) {
	DIPLÔME = dIPLÔME;
}
public String getEXPERTISE() {
	return EXPERTISE;
}
public void setEXPERTISE(String eXPERTISE) {
	EXPERTISE = eXPERTISE;
}
public String getSEXE() {
	return SEXE;
}
public void setSEXE(String sEXE) {
	SEXE = sEXE;
}
}