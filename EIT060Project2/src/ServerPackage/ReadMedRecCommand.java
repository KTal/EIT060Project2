package ServerPackage;

import medicalRecords.MedicalRecord;
import medicalRecords.Patient;

public class ReadMedRecCommand implements Command
{
	UserCredentials uc;
	Patient pat;

	public ReadMedRecCommand(UserCredentials uc, Patient pat)
	{
		this.uc = uc;
		this.pat = pat;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
