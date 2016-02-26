package ServerPackage;

import auditing.Logger;

public class CredentialFactory 
{
	private Logger log;
	
	public CredentialFactory(Logger log)
	{
		this.log = log;
	}
	
	public UserCredentials createCredentials(String credentialsString)
	{
		UserCredentials uc = null;
		String[] credentialsParts = credentialsString.split("\\#", -1);
		
		int credentialsLength = credentialsParts.length;
		
		if(credentialsLength == 2)
		{
			if(credentialsParts[0].equals(UserCategory.Patient.toString()))
			{
				uc = new UserCredentials(UserCategory.Patient, "", credentialsParts[1], "");
			}
			else if(credentialsParts[0].equals(UserCategory.GovermentAgency.toString()))
			{
				uc = new UserCredentials(UserCategory.GovermentAgency, credentialsParts[1], "", "");
			}
		}
		else if(credentialsLength == 3)
		{
			if(credentialsParts[0].equals(UserCategory.Doctor.toString()))
			{
				uc = new UserCredentials(UserCategory.Doctor, credentialsParts[1], "", 
						credentialsParts[2]);
			}
			else if(credentialsParts[0].equals(UserCategory.Nurse.toString()))
			{
				uc = new UserCredentials(UserCategory.Nurse, credentialsParts[1], "", 
						credentialsParts[2]);
			}
		}
		
		if(uc == null)
		{
			log.writeLog("Invalid user credentials.");
		}
		
		return uc;
	}
}
