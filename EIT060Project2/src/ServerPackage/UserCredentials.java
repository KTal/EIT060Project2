package ServerPackage;

public class UserCredentials 
{
	String userID;
	UserCategory uc;
	String name;
	String socialSecNo;
	String department;
	
	public UserCredentials(String userID, UserCategory uc, String name, 
			String socialSecNo, String department)
	{
		this.userID = userID;
		this.uc = uc;
		this.name = name;
		this.socialSecNo = socialSecNo;
		this.department = department;
	}
	
	public UserCategory getUc() {
		return uc;
	}

	public String getName() {
		return name;
	}

	public String getSocialSecNo() {
		return socialSecNo;
	}

	public String getDepartment() {
		return department;
	}

	public String getUserID() {
		return userID;
	}
}
