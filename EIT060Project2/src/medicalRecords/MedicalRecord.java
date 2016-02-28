package medicalRecords;

import java.text.Format;
import java.text.SimpleDateFormat;

public class MedicalRecord 
{
	private int runningNbr;
	private String date;
	private String department;
	private String doctorName;
	private String nurseName;
	private String note;
	
	public MedicalRecord(String date, String department, String nurseName, String note) 
	{
		this.runningNbr = 0;
		this.date = date;
		this.department = department;
		this.doctorName = "";
		this.nurseName = nurseName;
		this.note = note;
	}

	public String getDate() 
	{
		return date;
	}

	public String getDepartment() 
	{
		return department;
	}

	public String getDoctorName() 
	{
		return doctorName;
	}

	public String getNurseName() 
	{
		return nurseName;
	}

	public String getNote() 
	{
		return note;
	}
	
	public void setNote(String note)
	{
		this.note = note;
	}
	
	public void setDoctorName(String doctorName)
	{
		this.doctorName = doctorName;
	}
	
	public int getRunningNbr()
	{
		return runningNbr;
	}
	
	public void setRunningNbr(int runningNbr)
	{
		this.runningNbr = runningNbr;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(" RunNo: " );
		sb.append(Integer.toString(runningNbr));	
		sb.append(" Date: " + date);		
		sb.append(" Department: " + department);
		sb.append(" Doctor: " + doctorName);
		sb.append(" Nurse: " + nurseName);
		sb.append(" Note: " + note);
		
		return sb.toString();
	}
}
