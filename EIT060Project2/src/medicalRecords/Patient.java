package medicalRecords;

import java.util.ArrayList;
import java.util.Iterator;

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
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SocialSecNo: " + socialSecNo + "\n");
		sb.append("Patient name: " + patientName + "\n");
		sb.append("Patient address: " + address + "\n");
		sb.append("Patient phone no: " + phoneNbr + "\n");
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
