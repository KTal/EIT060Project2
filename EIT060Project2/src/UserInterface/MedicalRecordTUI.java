package UserInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicalRecordTUI 
{
	BufferedReader in;

	public MedicalRecordTUI()
	{
		in = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Welcome to the medical record system for lost souls.");
		System.out.println("");
		System.out.println("What would you like to do?");
	}

	public String inputCommand()
	{
		StringBuilder commandString = new StringBuilder();
		String com;

		try 
		{
			do
			{
				System.out.println("\nN - Add record\nR - Read record\nU - Update record\nD - Delete record\nQ - Quit");
				System.out.print(">> ");
				com = in.readLine().trim().toUpperCase();
			}
			while(com.length() != 1 || !com.matches("NRUDQ"));

			System.out.println("");
			switch(com)
			{
			case "N":
				commandString.append(getAddCommand());			
				break;

			case "R":				
				commandString.append(getReadCommand());
				break;

			case "U":				
				commandString.append(getUpdateCommand());
				break;

			case "D":			
				commandString.append(getDeleteCommand());
				break;

			case "Q":
				break;

			default:
				break;
			}
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return commandString.toString();
	}

	private String getAddCommand()
	{
		StringBuilder commandString = new StringBuilder();
		commandString.append("N#");
		
		Date dt = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		commandString.append((ft.format(dt)));
		commandString.append("#");
		
		System.out.print("Department: ");
		try 
		{
			String department  = in.readLine();
			commandString.append(department);
			commandString.append("#");
			System.out.println();
			
			System.out.print("Treating doctor: ");
			String doctorName  = in.readLine();
			commandString.append(doctorName);
			commandString.append("#");
			System.out.println();
			
			System.out.print("Treating nurse: ");
			String nurseName  = in.readLine();
			commandString.append(nurseName);
			commandString.append("#");
			System.out.println();
			
			System.out.print("Note: ");
			String note  = in.readLine();
			commandString.append(note);
			System.out.println();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return commandString.toString();
	}

	private String getReadCommand()
	{
		StringBuilder commandString = new StringBuilder();
		commandString.append("R#");
		commandString.append(getSocialSecNo());
		return commandString.toString();
	}

	private String getUpdateCommand()
	{
		StringBuilder commandString = new StringBuilder();
		String runningNbr = getRunningNbr();
		
		if(runningNbr.length() > 0)
		{
			commandString.append("U#");
			commandString.append(getSocialSecNo());
			commandString.append("#");
			commandString.append(runningNbr);
			
			System.out.print("Note: ");
			try 
			{
				String note  = in.readLine();
				commandString.append("#");
				commandString.append(note);
				System.out.println("");
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return commandString.toString();
	}

	private String getDeleteCommand()
	{
		StringBuilder commandString = new StringBuilder();
		String runningNbr = getRunningNbr();
		
		if(runningNbr.length() > 0)
		{
			commandString.append("D#");
			commandString.append(getSocialSecNo());
			commandString.append("#");
			commandString.append(runningNbr);
		}
		return commandString.toString();
	}
	
	private String getSocialSecNo()
	{
		String socialSecNo = "";
		System.out.print("SocialSecurityNo: ");
		try 
		{
			socialSecNo  = in.readLine();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("");
		return socialSecNo;
	}
	
	private String getRunningNbr()
	{
		String runningNbr = "";
		System.out.print("RunningNbr: ");
		try 
		{
			runningNbr  = in.readLine();
			try 
			{ 
		        int runNo = Integer.parseInt(runningNbr); 
		    } 
			catch(Exception e) 
			{ 
		    } 
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("");
		return runningNbr;
	}

	public void displayResults(String output)
	{
		String[] resultParts = output.split("\\#", -1);
		
		if(resultParts.length > 1)
		{
			switch(resultParts[0])
			{
				case "N":
				case "U":
				case "D":
					System.out.println(resultParts[1]);			
				break;
				
				case "R":
					displayReadResults(resultParts);
					break;
					
				default:
					break;
			}
		}		
	}
	
	private void displayReadResults(String[] resultParts)
	{
		int resultLength = resultParts.length;
		
		if(resultLength == 2)
		{
			System.out.println(resultParts[1]);
		}
		else
		{
			int numberOfRecords = resultLength / 8;
			int j;
			
			for(int i = 0; i < numberOfRecords; i++)
			{
				j = i * 8 + 2;
				
				System.out.print("#" + resultParts[j]);
				System.out.print(" " + resultParts[j+1]);
				System.out.print(" Department: " + resultParts[j+2]);
				System.out.print(" Treating doctor: " + resultParts[j+3]);
				System.out.print(" Treating nurse: " + resultParts[j+4]);
				System.out.print(" Note: " + resultParts[j+5]);
			}
			System.out.println();
		}
	}
}
