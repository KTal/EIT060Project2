package ServerPackage;

import medicalRecords.MedicalRecord;
import medicalRecords.Patient;

public class CredentialChecker 
{
	TransactionType tt;
	UserCredentials uc;
	Patient pat;
	MedicalRecord mr;
	
	public CredentialChecker(TransactionType tt, UserCredentials uc, Patient pat,
			MedicalRecord mr)
	{
		this.tt = tt;
		this.uc = uc;
		this.pat = pat;
		this.mr = mr;
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
		
		// ToDo
		// Log result of checkCredentials
		
		return accessGranted;
	}
}
