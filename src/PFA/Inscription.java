package PFA;

public class Inscription {
	client cl ;
	Session s ;
	String paiement;
	public Inscription(client cl, Session s, String paiement) {
		
		this.cl=cl;
		this.s=s;
		this.paiement = paiement;
	}
	
	public client getCl() {
		return cl;
	}

	public void setCl(client cl) {
		this.cl = cl;
	}

	public Session getS() {
		return s;
	}

	public void setS(Session s) {
		this.s = s;
	}

	public String getPaiement() {
		return paiement;
	}
	public void setPaiement(String paiement) {
		this.paiement = paiement;
	}
	
	
}