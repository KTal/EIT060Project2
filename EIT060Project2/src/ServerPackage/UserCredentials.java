package ServerPackage;

public class UserCredentials 
{
	private UserCategory uc;
	private String name;
	private String socialSecNo;
	private String department;
	
	public UserCredentials(UserCategory uc, String name, 
			String socialSecNo, String department)
	{
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
}
