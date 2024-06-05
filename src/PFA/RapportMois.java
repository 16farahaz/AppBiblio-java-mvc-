package PFA;

public class RapportMois {
	String DescrRevenue,DescrDepense;
	float revenue,depense,differene;
	
	public RapportMois(String DescrRevenue, String DescrDepense, float revenue, float depense) {
		this.DescrRevenue=DescrDepense;
		this.DescrRevenue=DescrRevenue;
		this.revenue=revenue;
		this.depense=depense;
		this.differene=revenue-depense;
	}

	public String getDescrRevenue() {
		return DescrRevenue;
	}

	public void setDescrRevenue(String descrRevenue) {
		DescrRevenue = descrRevenue;
	}

	public String getDescrDepense() {
		return DescrDepense;
	}

	public void setDescrDepense(String descrDepense) {
		DescrDepense = descrDepense;
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

	public float getDifferene() {
		return differene;
	}

	public void setDifferene(float differene) {
		this.differene = differene;
	}
	
	
}
