package ServerPackage;

import auditing.Logger;
import medicalRecords.MedicalRecord;
import medicalRecords.Patient;

public class CredentialChecker 
{
	private UserCredentials uc;
	private Patient pat;
	private MedicalRecord mr;
	private Logger log;
	
	public CredentialChecker(UserCredentials uc, Patient pat,
			MedicalRecord mr, Logger log)
	{
		this.uc = uc;
		this.pat = pat;
		this.mr = mr;
		this.log = log;
	}
	
	public Boolean checkCredentials(TransactionType tt)
	{
		return checkCredentials(tt, false);
	}
	
	public Boolean checkCredentials(TransactionType tt, Boolean logOff)
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
					if(uc.getSocialSecNo().equals(pat.getSocialSecNo()))
					{
						accessGranted = true;
					}
				}			
				else if(uc.getUc() == UserCategory.Nurse)
				{
					if(uc.getDepartment().equals(mr.getDepartment()))
					{
						accessGranted = true;
					}
				}
				else if(uc.getUc() == UserCategory.Doctor)
				{
					if(uc.getDepartment().equals(mr.getDepartment()))
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
					if(uc.getName().equals(mr.getNurseName()))
					{
						accessGranted = true;
					}
				}
				else if(uc.getUc() == UserCategory.Doctor)
				{
					if(uc.getName().equals(mr.getDoctorName()))
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
		
		if(!logOff)
		{
			createMessage(accessGranted, tt);
		}
		return accessGranted;
	}
	
	public void createMessage(Boolean accessGranted, TransactionType tt)
	{
		StringBuilder sb = new StringBuilder();
		
		if(accessGranted)
		{
			sb.append("Access granted. ");
		}
		else
		{
			sb.append("Access denied. ");
		}
		sb.append("Transaction type: " + tt.toString() + " for patient " + pat.getSocialSecNo());
		sb.append(" requested by ");
		if(uc.getUc() == UserCategory.Patient)
		{
			sb.append(uc.getSocialSecNo());
		}
		else
		{
			sb.append(uc.getName() + " ");
		}

		if(uc.getUc()== UserCategory.Doctor || uc.getUc()== UserCategory.Nurse)
		{
			sb.append("in department " + uc.getDepartment());
		}
		
		log.writeLog(sb.toString());
	}
	
}
