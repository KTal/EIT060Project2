package medicalRecords;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicalRecord 
{
	int runningNbr;
	String date;
	String department;
	String doctorName;
	String nurseName;
	String note;
	
	public MedicalRecord(String date, String department, 
			String doctorName, String nurseName, String note) 
	{
		this.runningNbr = 0;
		this.date = date;
		this.department = department;
		this.doctorName = doctorName;
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
		sb.append("RunNo: " );
		sb.append(Integer.toString(runningNbr));
		sb.append("\n");
		
		sb.append("Date: " );
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		sb.append(dateString);
		sb.append("\n");
		
		sb.append("Department: " + department + "\n");
		sb.append("Doctor: " + doctorName + "\n");
		sb.append("Nurse: " + nurseName + "\n");
		sb.append("Note: " + note  + "\n");
		
		return sb.toString();
	}
}
