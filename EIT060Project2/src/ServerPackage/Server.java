package ServerPackage;
import java.io.*;
import java.net.*;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.net.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;

import auditing.Logger;
import medicalRecords.Patient;

import java.math.BigInteger;

public class Server implements Runnable {
	private ServerSocket serverSocket = null;
	private static int numConnectedClients = 0;
	private HashMap<String, Patient> patients;

	public Server()
	{
		test();
	}

	public Server(ServerSocket ss) throws IOException 
	{
		serverSocket = ss;
		createSomePatients();
		newListener();
	}

	private void createSomePatients()
	{
		patients = new HashMap<String, Patient>();
		Patient pat;
		ArrayList<String> doctors;
		String socialSecNo;

		socialSecNo = "6409021999";
		doctors = new ArrayList<String>(Arrays.asList("Dr. Morpheus", "Dr. Trinity"));			
		pat = new Patient(socialSecNo, "Thomas Anderson", "3 matrix Av.", "199920032003",
				doctors);
		if(!patients.containsKey(socialSecNo))
		{
			patients.put(socialSecNo, pat);
		}

		socialSecNo = "1239021599";
		doctors = new ArrayList<String>(Arrays.asList("Dr. Smith"));			
		pat = new Patient(socialSecNo, "James Howlett", "10 Downing Street.", "656223",
				doctors);
		if(!patients.containsKey(socialSecNo))
		{
			patients.put(socialSecNo, pat);
		}

		socialSecNo = "3404521959";
		doctors = new ArrayList<String>(Arrays.asList("Dr. Trinity"));			
		pat = new Patient(socialSecNo, "Donald Trump", "1 America Av.", "1234920042003",
				doctors);
		if(!patients.containsKey(socialSecNo))
		{
			patients.put(socialSecNo, pat);
		}
	}

	public void run() {
		try {
			SSLSocket socket=(SSLSocket)serverSocket.accept();
			newListener();
			SSLSession session = socket.getSession();
			X509Certificate cert = (X509Certificate)session.getPeerCertificateChain()[0];
			String subject = cert.getSubjectDN().getName();
			String issuer = cert.getIssuerDN().getName();  //THIS HAS BEEN ADDED!!!!
			BigInteger serialnumber = cert.getSerialNumber(); //THIS HAS BEEN ADDED
			numConnectedClients++;

			System.out.println("client connected");
			System.out.println("client name (cert subject DN field): " + subject);
			System.out.println("Issuer name (cert issuer DN field): " + issuer); //ALSO ADDED!!!!!!!!!
			System.out.println("Certificate serial number: " + serialnumber); //THIS IS ALSO ADDED!!!
			System.out.println(numConnectedClients + " concurrent connection(s)\n");

			PrintWriter out = null;
			BufferedReader in = null;
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			Logger log = new Logger();
			CredentialFactory credFac = new CredentialFactory(log);
			UserCredentials uc = credFac.createCredentials(subject);

			if(uc != null)
			{
				CommandFactory comFac = new CommandFactory();
				Command com;

				String clientMsg = null;
				while ((clientMsg = in.readLine()) != null) 
				{
					com = comFac.createCommand(clientMsg, uc, patients, log);

					if(com != null)
					{
						out.println(com.execute());
					}
					else
					{
						out.println("Invalid request: " + clientMsg);
					}
				}
			}
			else
			{
				out.println("Access denied.");
			}
			in.close();
			out.close();
			socket.close();
			numConnectedClients--;
			System.out.println("client disconnected");
			System.out.println(numConnectedClients + " concurrent connection(s)\n");
		} catch (IOException e) {
			System.out.println("Client died: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}

	private void newListener() { (new Thread(this)).start(); } // calls run()

	public static void main(String args[]) 
	{
		// When testing.
		//new Server();

		System.out.println("\nServer Started\n");
		int port = -1;
		if (args.length >= 1) 
		{
			port = Integer.parseInt(args[0]);
		}
		String type = "TLS";
		try 
		{
			ServerSocketFactory ssf = getServerSocketFactory(type);
			ServerSocket ss = ssf.createServerSocket(port);
			((SSLServerSocket)ss).setNeedClientAuth(true); // enables client authentication
			new Server(ss);
		} 
		catch (IOException e) 
		{
			System.out.println("Unable to start Server: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void test()
	{
		createSomePatients();

		ArrayList<String> clientRequests = new ArrayList<String>();
		clientRequests.add("N#6409021999#2016-02-24#Oncology#Dr. Morpheus#Joker#Bad breath.");
		clientRequests.add("N#6409021999#2016-02-24#Oncology#Dr. Morpheus#Joker#Broken brain.");
		clientRequests.add("R#6409021999");
		clientRequests.add("N#6409021999#2016-02-24#Oncology#Dr. Morpheus#Joker#Bad stomach.");
		clientRequests.add("N#6409021999#2016-02-24#Oncology#Dr. Morpheus#Joker#Thinks he's a cat.");
		clientRequests.add("R#6409021999");
		clientRequests.add("R#6409021999");
		clientRequests.add("R#6409021999");
		clientRequests.add("R#6409021999");
		clientRequests.add("R#6409021999");
		clientRequests.add("U#6409021999#1#Headache");
		clientRequests.add("U#6409021999#a#Headache");
		clientRequests.add("U#6409021999#12#Headache");
		clientRequests.add("U#6409021999#2#Pinky toe cancer");
		clientRequests.add("U#6409021999#2#Curly hair disease");
		clientRequests.add("U#6409021999#2#Motor mouth");
		clientRequests.add("D#6409021999#2");
		clientRequests.add("D#6409021999#2");
		clientRequests.add("D#6409021999#2");
		clientRequests.add("D#6409021999#2");
		ArrayList<String> credSubject = new ArrayList<String>();
		credSubject.add("Nurse#Joker#Oncology");
		credSubject.add("Doctor#Dr. Morpheus#Oncology");
		credSubject.add("Patient#3404521959");
		credSubject.add("Doctor#Dr. Smith#Psychiatry");
		credSubject.add("Doctor#Dr. Morpheus#Oncology");
		credSubject.add("Doctor#Dr. Morpheus#Oncology");
		credSubject.add("Doctor#Dr. Smith#Psychiatry");
		credSubject.add("Patient#6409021999");
		credSubject.add("Patient#3404521959");
		credSubject.add("Nurse#Joker#Oncology");
		credSubject.add("Doctor#Dr. Morpheus#Oncology");
		credSubject.add("Doctor#Dr. Morpheus#Oncology");
		credSubject.add("Doctor#Dr. Morpheus#Oncology");
		credSubject.add("Doctor#Dr. Smith#Psychiatry");
		credSubject.add("Nurse#Oracle#Oncology");
		credSubject.add("Nurse#Joker#Oncology");
		credSubject.add("GovernmentAgency#The Architect");
		credSubject.add("Doctor#Dr. Morpheus#Oncology");
		credSubject.add("Nurse#Joker#Oncology");
		credSubject.add("Patient#6409021999");


		Logger log = new Logger();
		CredentialFactory credFac = new CredentialFactory(log);
		UserCredentials uc;
		CommandFactory comFac = new CommandFactory();
		Command com;

		for(int i = 0; i < clientRequests.size(); i++)
		{		
			uc = credFac.createCredentials(credSubject.get(i));

			if(uc != null)
			{
				com = comFac.createCommand(clientRequests.get(i), uc, patients, log);

				if(com != null)
				{
					System.out.println(com.execute());
				}
				else
				{
					System.out.println("Invalid request: " + clientRequests.get(i));
				}
			}
		}
		System.exit(0);
	}

	private static ServerSocketFactory getServerSocketFactory(String type) {
		if (type.equals("TLS")) {
			SSLServerSocketFactory ssf = null;
			try { // set up key manager to perform server authentication
				SSLContext ctx = SSLContext.getInstance("TLS");
				KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
				TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
				KeyStore ks = KeyStore.getInstance("JKS");
				KeyStore ts = KeyStore.getInstance("JKS");
				char[] password = "password".toCharArray();

				ks.load(new FileInputStream("serverkeystore"), password);  // keystore password (storepass)
				ts.load(new FileInputStream("servertruststore"), password); // truststore password (storepass)
				kmf.init(ks, password); // certificate password (keypass)
				tmf.init(ts);  // possible to use keystore as truststore here
				ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
				ssf = ctx.getServerSocketFactory();
				return ssf;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return ServerSocketFactory.getDefault();
		}
		return null;
	}
}
