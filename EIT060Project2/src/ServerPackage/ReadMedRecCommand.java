package ServerPackage;

import java.util.ArrayList;

import auditing.Logger;
import medicalRecords.MedicalRecord;
import medicalRecords.Patient;

public class ReadMedRecCommand implements Command
{
	private UserCredentials uc;
	private Patient pat;
	private Logger log;

	public ReadMedRecCommand(UserCredentials uc, Patient pat, Logger log)
	{
		this.uc = uc;
		this.pat = pat;
		this.log = log;
	}

	@Override
	public String execute() 
	{
		ArrayList<MedicalRecord> mrList = pat.getMedicalRecords();
		StringBuilder sb = new StringBuilder();
		int medicalRecordsNbr = 0;
		MedicalRecord mr;
		CredentialChecker cc = null;
		
		sb.append("R#");
		for(int i = 0; i < mrList.size(); i++)
		{
			mr = (MedicalRecord)mrList.get(i);
			cc = new CredentialChecker(uc, pat, mr, log);

			if(cc.checkCredentials(TransactionType.Read, true))
			{
				if(medicalRecordsNbr > 0)
				{
					sb.append("#R#");
				}

				sb.append("Access granted.#");

				medicalRecordsNbr++;
				
				sb.append(mr.getRunningNbr());
				sb.append("#");
				sb.append(mr.getDate());
				sb.append("#");
				sb.append(mr.getDepartment());
				sb.append("#");
				sb.append(mr.getDoctorName());
				sb.append("#");
				sb.append(mr.getNurseName());
				sb.append("#");
				sb.append(mr.getNote());
			}
		}
		if(medicalRecordsNbr == 0)
		{
			sb.append("Access denied.");
			if(cc != null)
			{
				cc.createMessage(false, TransactionType.Read);
			}	
		}
		else
		{
			cc.createMessage(true, TransactionType.Read);
		}
		return sb.toString();
	}
}