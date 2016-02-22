package ServerPackage;

import medicalRecords.Patient;

public class DeleteMedRecCommand implements Command
{
	UserCredentials uc;
	Patient pat;
	int runningNbr;

	public DeleteMedRecCommand(UserCredentials uc, Patient pat, int runningNbr)
	{
		this.uc = uc;
		this.pat = pat;
		this.runningNbr = runningNbr;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
