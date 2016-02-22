package ServerPackage;

import auditing.Logger;
import medicalRecords.MedicalRecord;
import medicalRecords.Patient;
import medicalRecords.TransactionResult;

public class UpdateMedRecCommand implements Command 
{

	UserCredentials uc;
	Patient pat;
	MedicalRecord mr;
	int runningNbr;
	String note;
	Logger log;

	public UpdateMedRecCommand(UserCredentials uc, Patient pat, MedicalRecord mr,
			int runningNbr, String note, Logger log)
	{
		this.uc = uc;
		this.pat = pat;
		this.mr = mr;
		this.runningNbr = runningNbr;
		this.note = note;
		this.log = log;
	}

	@Override
	public String execute() 
	{
		CredentialChecker cc = new CredentialChecker(uc, pat, mr, log);

		if(cc.checkCredentials(TransactionType.Update))
		{
			TransactionResult tr = pat.appendToMedicalRecord(runningNbr, note, log);
			return "U#" + tr.toString() + "#";			
		}
		else
		{
			return "U#Access denied.#";
		}
	}
}