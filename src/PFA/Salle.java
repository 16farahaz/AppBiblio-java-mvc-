package PFA;

public class Salle {

int Num�ro ;
String Nom , Etat , Type , Capacite ;


public Salle(int num�ro, String nom, String etat, String type, String capacite) {
	this.Num�ro = num�ro;
	this.Nom = nom;
	this.Etat = etat;
    this.Type = type;
	this.Capacite = capacite;
}

public Salle(int num) {
	Num�ro=num;
}

public int getNum�ro() {
	return Num�ro;
}
public void setNum�ro(int num�ro) {
	Num�ro = num�ro;
}
public String getNom() {
	return Nom;
}
public void setNom(String nom) {
	Nom = nom;
}
public String getEtat() {
	return Etat;
}
public void setEtat(String etat) {
	Etat = etat;
}
public String getType() {
	return Type;
}
public void setType(String type) {
	Type = type;
}
public String getCapacite() {
	return Capacite;
}
public void setCapacite(String capacite) {
	Capacite = capacite;
}

}
