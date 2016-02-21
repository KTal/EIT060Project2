package ServerPackage;

import java.util.HashMap;

import auditing.Logger;
import medicalRecords.Patient;

public class CommandFactory 
{
	public static Command createCommand(String args, UserCredentials uc, 
			HashMap<String, Patient> patients, Logger log)
	{
		Command com = null;
		return com;
	}
}
