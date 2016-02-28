package ServerPackage;

import java.util.HashMap;

import auditing.Logger;
import medicalRecords.MedicalRecord;
import medicalRecords.Patient;

public class CommandFactory 
{
	public CommandFactory(){};

	public Command createCommand(String commandString, UserCredentials uc, 
			HashMap<String, Patient> patients, Logger log)
	{
		Command com = null;
		Boolean commandOK = false;
		String[] commandParts = commandString.split("\\#", -1);
		Patient pat;
		MedicalRecord mr;
		Boolean runningNbrOK = false;
		int runningNbr = -1;
		int commandLength = commandParts.length;

		if(commandLength >= 2)
		{
			if(patients.containsKey(commandParts[1]))
			{
				pat = patients.get(commandParts[1]);
				switch(commandParts[0])
				{
				case "N":
					if(commandParts.length == 6)
					{
						mr = checkMedicalRecordSyntax(commandParts);
						if(mr != null)
						{
							com = new NewMedRecCommand(uc, pat, mr, log);
							commandOK = true;
						}
					}
					break;

				case "R":				
					com = new ReadMedRecCommand(uc, pat, log);
					commandOK = true;
					break;

				case "D":					
					if(commandLength == 3)
					{
						try 
						{  
							runningNbr = Integer.parseInt(commandParts[2]);  
							runningNbrOK = true;  
						} 
						catch (NumberFormatException e) 
						{  					 
						}
						if(runningNbrOK)
						{
							mr = pat.findMedicalRecord(runningNbr, log);
							if(mr != null)
							{
								com = new DeleteMedRecCommand(uc, pat, runningNbr,
										log);
								commandOK = true;
							}
						}
					}
					break;

				case "U":					
					if(commandLength == 4 && commandParts[3].length() > 0)
					{
						try 
						{  
							runningNbr = Integer.parseInt(commandParts[2]);  
							runningNbrOK = true;  
						} 
						catch (NumberFormatException e) 
						{  					 
						}
						if(runningNbrOK)
						{
							mr = pat.findMedicalRecord(runningNbr, log);
							if(mr != null)
							{
								com = new UpdateMedRecCommand(uc, pat, mr,
										runningNbr, commandParts[3], log);
								commandOK = true;
							}
						}
					}

					break;

				default:

					break;
				}
			}
			if (!commandOK)
			{
				log.writeLog("Invalid syntax in command: " + commandString );
			}
		}
		return com;
	}

	private MedicalRecord checkMedicalRecordSyntax(String[] commandParts)
	{
		MedicalRecord mr= null;

		for(int i = 0; i < commandParts.length; i++)
		{
			if(commandParts[i].length() == 0)
			{
				return mr;
			}
		}
		mr = new MedicalRecord(commandParts[2], commandParts[3], commandParts[4],
				commandParts[5]);
		return mr;
	}
}
