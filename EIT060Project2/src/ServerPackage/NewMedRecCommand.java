package ServerPackage;

import auditing.Logger;
import medicalRecords.MedicalRecord;
import medicalRecords.Patient;
import medicalRecords.TransactionResult;

public class NewMedRecCommand implements Command
{
	private UserCredentials uc;
	private Patient pat;
	private MedicalRecord mr;
	private Logger log;

	public NewMedRecCommand(UserCredentials uc, Patient pat, MedicalRecord mr, 
			Logger log)
	{
		this.uc = uc;
		this.pat = pat;
		this.mr = mr;
		this.log = log;
	}
		
	@Override
	public String execute() 
	{
		CredentialChecker cc = new CredentialChecker(uc, pat, mr, log);

		if(cc.checkCredentials(TransactionType.New))
		{
			TransactionResult tr = pat.addMedicalRecord(mr, log);
			return "N#" + tr.toString() + "#";			
		}
		else
		{
			return "N#Access denied.#";
		}	
	}

}
