package medicalRecords;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import auditing.Logger;

public class Patient
{
	String socialSecNo;
	String patientName;
	String address;
	String phoneNbr;
	ArrayList<String> treatingDoctors;
	int lastRunningNbr;
	ArrayList<MedicalRecord> mrList;
	public Patient(String socialSecNo, String patientName, String address, 
			String phoneNbr, ArrayList<String> treatingDoctors) {
		super();
		this.socialSecNo = socialSecNo;
		this.patientName = patientName;
		this.address = address;
		this.phoneNbr = phoneNbr;
		this.treatingDoctors = treatingDoctors;
		lastRunningNbr = 0;
	}
	
	public String getSocialSecNo()
	{
		return socialSecNo;
	}
	
	public Boolean isTreatingDoctor(String name)
	{
		for(ListIterator<String> i = treatingDoctors.listIterator(); i.hasNext(); ) 
		{
		   String doctor = i.next();
		    if( doctor.equals(name))
		    {
		    	return true;
		    }
		}
		return false;
	}
	
	public TransactionResult addMedicalRecord(MedicalRecord mr, Logger log)
	{
		lastRunningNbr++;
		mr.setRunningNbr(lastRunningNbr);
		mrList.add(mr);
		
		StringBuilder sb = new StringBuilder();
		sb.append("Added medical record for patient " + this.socialSecNo);
		sb.append(" " + this.patientName + "\n");
		sb.append(mr.toString());
		log.writeLog(sb.toString());
		return TransactionResult.CreateSucceeded;
	}
	
	public TransactionResult deleteMedicalRecord(int runningNbr, Logger log)
	{
		StringBuilder sb = new StringBuilder();
		
		for(ListIterator<MedicalRecord> i = mrList.listIterator(); i.hasNext(); ) 
		{
		    MedicalRecord mr = i.next();
		    if( mr.getRunningNbr() == runningNbr)
		    {
		    	i.remove();
		    	sb.append("Deleted medical record " + Integer.toString(runningNbr));
		    	sb.append(" for patient " + this.socialSecNo);
		    	log.writeLog(sb.toString());
		    	return TransactionResult.DeleteSucceeded;
		    }
		}
		sb.append("Failed to delete medical record " + Integer.toString(runningNbr));
    	sb.append(" for patient " + this.socialSecNo);
    	log.writeLog(sb.toString());
		return TransactionResult.DeleteFailed;
	}
	
	public TransactionResult appendToMedicalRecord(int runningNbr, 
			String appendNote, Logger log)
	{
		StringBuilder sb = new StringBuilder();
		
		for(ListIterator<MedicalRecord> i = mrList.listIterator(); i.hasNext(); ) 
		{
		    MedicalRecord mr = i.next();
		    if( mr.getRunningNbr() == runningNbr)
		    {
		    	String oldNote = mr.getNote().concat("\n");
		    	String newNote = oldNote.concat(appendNote);
		    	mr.setNote(newNote);
		    	i.set(mr);
		    	
		    	sb.append("Appended " + appendNote);
		    	sb.append(" to medical record " + Integer.toString(runningNbr));
		    	sb.append(" for patient " + this.socialSecNo);
		    	log.writeLog(sb.toString());
		    	
		    	return TransactionResult.AppendSucceeded;
		    }
		}
		sb.append("Failed to append to medical record " + Integer.toString(runningNbr));
    	sb.append(" for patient " + this.socialSecNo);
    	log.writeLog(sb.toString());
		return TransactionResult.AppendFailed;
	}
	
	public MedicalRecord findMedicalRecord(int runningNbr, Logger log)
	{
		StringBuilder sb = new StringBuilder();
		
		for(ListIterator<MedicalRecord> i = mrList.listIterator(); i.hasNext(); ) 
		{
		    MedicalRecord mr = i.next();
		    if( mr.getRunningNbr() == runningNbr)
		    {
		    	return mr;
		    }
		}
		sb.append("Could not find medical record " + Integer.toString(runningNbr));
		sb.append(" for patient " + this.socialSecNo);
		log.writeLog(sb.toString());
		return null;
	}
	
	public ArrayList<MedicalRecord> getMedicalRecords()
	{
		return mrList;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SocialSecNo: " + socialSecNo + "\n");
		sb.append("Patient name: " + patientName + "\n");
		sb.append("Patient address: " + address + "\n");
		sb.append("Patient phone no: " + phoneNbr + "\n");
		
		for(Iterator<String> i = treatingDoctors.iterator(); i.hasNext(); ) 
		{
		    String treatingDoctor = i.next();
		    sb.append("Treating doctor: " + treatingDoctor + "\n");
		}
		
		for(Iterator<MedicalRecord> i = mrList.iterator(); i.hasNext(); ) 
		{
		    MedicalRecord mr = i.next();
		    sb.append(mr.toString());
		}
		
		return sb.toString();
	}	
}
