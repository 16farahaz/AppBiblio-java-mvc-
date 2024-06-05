package PFA;

public class Users {
	String Role,Name,MDP;
	int ID;
	
	
		public Users(int ID ,String Role, String Name, String MDP) {
			this.Role = Role;
			this.Name = Name;
			this.MDP = MDP;
			this.ID=ID ; 
		}
		
		public Users(String Role, String Name, String MDP) {
			this.Role = Role;
			this.Name = Name;
			this.MDP = MDP; 
		}


		public int getID() {
			return ID;
		}

		public void setID(int iD) {
			ID = iD;
		}

		public String getRole() {
			return Role;
		}

		public void setRole(String role) {
			Role = role;
		}

		public String getName() {
			return Name;
		}

		public void setName(String name) {
			Name = name;
		}

		public String getMDP() {
			return MDP;
		}

		public void setMDP(String mDP) {
			MDP = mDP;
		}


		
	
}
