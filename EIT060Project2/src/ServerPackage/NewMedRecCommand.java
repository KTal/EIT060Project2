package ServerPackage;

import medicalRecords.MedicalRecord;
import medicalRecords.Patient;

public class NewMedRecCommand implements Command
{
	UserCredentials uc;
	Patient pat;
	MedicalRecord mr;

	public NewMedRecCommand(UserCredentials uc, Patient pat, MedicalRecord mr)
	{
		this.uc = uc;
		this.pat = pat;
		this.mr = mr;
	}
		
	@Override
	public void execute() 
	{
		// TODO Auto-generated method stub
		
	}

}
