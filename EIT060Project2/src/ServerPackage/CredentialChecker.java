package ServerPackage;

import auditing.Logger;
import medicalRecords.MedicalRecord;
import medicalRecords.Patient;

public class CredentialChecker 
{
	TransactionType tt;
	UserCredentials uc;
	Patient pat;
	MedicalRecord mr;
	Logger log;
	
	public CredentialChecker(TransactionType tt, UserCredentials uc, Patient pat,
			MedicalRecord mr, Logger log)
	{
		this.tt = tt;
		this.uc = uc;
		this.pat = pat;
		this.mr = mr;
		this.log = log;
	}
	
	public Boolean checkCredentials()
	{
		Boolean accessGranted = false;
		
		switch(tt)
		{
			case New:
				
				if(uc.getUc() == UserCategory.Doctor)
				{
					if(pat.isTreatingDoctor(uc.getName()))
					{
						accessGranted = true;
					}
				}
				
				break;
			case Read:
				
				if(uc.getUc() == UserCategory.Patient)
				{
					if(uc.getSocialSecNo() == pat.getSocialSecNo())
					{
						accessGranted = true;
					}
				}			
				else if(uc.getUc() == UserCategory.Nurse)
				{
					if(uc.getDepartment() == mr.getDepartment())
					{
						accessGranted = true;
					}
				}
				else if(uc.getUc() == UserCategory.Doctor)
				{
					if(uc.getDepartment() == mr.getDepartment())
					{
						accessGranted = true;
					}
				}
				else if(uc.getUc() == UserCategory.GovernmentAgency)
				{
					accessGranted = true;
				}
				break;
				
			case Update:
				
				if(uc.getUc() == UserCategory.Nurse)
				{
					if(uc.getName() == mr.getNurseName())
					{
						accessGranted = true;
					}
				}
				else if(uc.getUc() == UserCategory.Doctor)
				{
					if(uc.getName() == mr.getDoctorName())
					{
						accessGranted = true;
					}
				}
				
				break;
				
			case Delete:
				
				if(uc.getUc() == UserCategory.GovernmentAgency)
				{
					accessGranted = true;
				}
				
				break;
				
			default:
				
				break;
		}
		
		createMessage(accessGranted);
		return accessGranted;
	}
	
	private void createMessage(Boolean accessGranted)
	{
		StringBuilder sb = new StringBuilder();
		
		if(accessGranted)
		{
			sb.append("Access granted: ");
		}
		else
		{
			sb.append("Access denied: ");
		}
		sb.append(tt.toString() + " for patient " + pat.getSocialSecNo());
		sb.append(" requested by " + uc.getUserID() + " ");
		sb.append(uc.getUc().toString() + " ");
		if(uc.getUc()== UserCategory.Doctor || uc.getUc()== UserCategory.Nurse)
		{
			sb.append(uc.getName() + " in department " + uc.getDepartment());
		}
		else if(uc.getUc()== UserCategory.Patient)
		{
			sb.append(uc.getSocialSecNo());
		}
		
		log.writeLog(sb.toString());
	}
	
}
