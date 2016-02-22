package ServerPackage;

import medicalRecords.Patient;

public class UpdateMedRecCommand implements Command 
{

	UserCredentials uc;
	Patient pat;
	int runningNbr;
	String note;

	public UpdateMedRecCommand(UserCredentials uc, Patient pat, int runningNbr, 
			String note)
	{
		this.uc = uc;
		this.pat = pat;
		this.runningNbr = runningNbr;
		this.note = note;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
