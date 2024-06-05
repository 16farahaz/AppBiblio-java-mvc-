package PFA;

public class RapportAnne {
	String mois;
	float revenue,depense,difference;
	
	public RapportAnne(String mois, float revenue, float depense) {
		this.mois=mois;
		this.revenue=revenue;
		this.depense=depense;
		this.difference=revenue-depense;
	}

	public String getMois() {
		return mois;
	}

	public void setMois(String mois) {
		this.mois = mois;
	}

	public float getRevenue() {
		return revenue;
	}

	public void setRevenue(float revenue) {
		this.revenue = revenue;
	}

	public float getDepense() {
		return depense;
	}

	public void setDepense(float depense) {
		this.depense = depense;
	}

	public float getDifference() {
		return difference;
	}

	public void setDifference(float difference) {
		this.difference = difference;
	}
	
	
}
