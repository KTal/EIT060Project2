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
		
		String commandLetters = "NRUDQ";
		int index = -1;

		try 
		{
			do
			{
				System.out.println("\nN - Add record\nR - Read record\nU - Update record\nD - Delete record\nQ - Quit");
				System.out.print(">> ");
				com = in.readLine().trim().toUpperCase();
				index = commandLetters.indexOf(com);
			}
			while(com.length() != 1 || index == -1);

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

		try 
		{
			commandString.append(getSocialSecNo());
			commandString.append("#");
			
			Date dt = new Date();
			SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
			commandString.append((ft.format(dt)));
			commandString.append("#");
			
			System.out.print("Department: ");
			String department  = in.readLine().trim();
			commandString.append(department);
			commandString.append("#");
			
//			System.out.print("Treating doctor: ");
//			String doctorName  = in.readLine().trim();
//			commandString.append(doctorName);
//			commandString.append("#");
			
			System.out.print("Treating nurse: ");
			String nurseName  = in.readLine().trim();
			commandString.append(nurseName);
			commandString.append("#");
			
			System.out.print("Note: ");
			String note  = in.readLine().trim();
			commandString.append(note);
			
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
		
		commandString.append("U#");
		commandString.append(getSocialSecNo());
		commandString.append("#");
		
		String runningNbr = getRunningNbr();
		
		if(runningNbr.length() > 0)
		{						
			commandString.append(runningNbr);
			
			System.out.print("Note: ");
			try 
			{
				String note  = in.readLine().trim();
				commandString.append("#");
				commandString.append(note);
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			commandString.setLength(0);
		}
		return commandString.toString();
	}

	private String getDeleteCommand()
	{
		StringBuilder commandString = new StringBuilder();
		
		commandString.append("D#");
		commandString.append(getSocialSecNo());
		commandString.append("#");
		
		String runningNbr = getRunningNbr();
		
		if(runningNbr.length() > 0)
		{
			
			commandString.append(runningNbr);
		}
		else
		{
			commandString.setLength(0);
		}
		return commandString.toString();
	}
	
	private String getSocialSecNo()
	{
		String socialSecNo = "";
		System.out.print("SocialSecurityNo: ");
		try 
		{
			socialSecNo  = in.readLine().trim();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return socialSecNo;
	}
	
	private String getRunningNbr()
	{
		String runningNbr = "";
		System.out.print("RunningNbr: ");
		try 
		{
			runningNbr  = in.readLine().trim();
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
		else
		{
			System.out.println(resultParts[0]);
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
				System.out.println(" " + resultParts[j+1]);
				System.out.println("Department: " + resultParts[j+2]);
				System.out.println("Treating doctor: " + resultParts[j+3]);
				System.out.println("Treating nurse: " + resultParts[j+4]);
				System.out.println("Note: " + resultParts[j+5]);
			}
			System.out.println();
		}
	}
}
