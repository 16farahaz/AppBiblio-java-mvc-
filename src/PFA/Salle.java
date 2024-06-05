package PFA;

public class Salle {

int Numéro ;
String Nom , Etat , Type , Capacite ;


public Salle(int numéro, String nom, String etat, String type, String capacite) {
	this.Numéro = numéro;
	this.Nom = nom;
	this.Etat = etat;
    this.Type = type;
	this.Capacite = capacite;
}

public Salle(int num) {
	Numéro=num;
}

public int getNuméro() {
	return Numéro;
}
public void setNuméro(int numéro) {
	Numéro = numéro;
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
