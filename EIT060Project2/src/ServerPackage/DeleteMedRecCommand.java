package ServerPackage;

import auditing.Logger;
import medicalRecords.Patient;
import medicalRecords.TransactionResult;

public class DeleteMedRecCommand implements Command
{
	private UserCredentials uc;
	private Patient pat;
	private int runningNbr;
	private Logger log;

	public DeleteMedRecCommand(UserCredentials uc, Patient pat, int runningNbr,
			Logger log)
	{
		this.uc = uc;
		this.pat = pat;
		this.runningNbr = runningNbr;
		this.log = log;
	}

	@Override
	public String execute() 
	{
		CredentialChecker cc = new CredentialChecker(uc, pat, null, log);
		
		if(cc.checkCredentials(TransactionType.Delete))
		{
			TransactionResult tr = pat.deleteMedicalRecord(runningNbr, log);
			return "D#" + tr.toString();			
		}
		else
		{
			return "D#Access denied.";
		}
	}
}
