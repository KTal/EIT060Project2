package ClientPackage;
import java.net.*;
import java.io.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
import java.security.KeyStore;
import java.security.cert.*;
import java.math.BigInteger;

/*
 * This example shows how to set up a key manager to perform client
 * authentication.
 *
 * This program assumes that the client is not inside a firewall.
 * The application can be modified to connect to a server outside
 * the firewall by following SSLSocketClientWithTunneling.java.
 */
public class Client {

    public static void main(String[] args) throws Exception {
        String host = null;
        int port = -1;
        for (int i = 0; i < args.length; i++) {
            System.out.println("args[" + i + "] = " + args[i]);
        }
        if (args.length < 2) {
            System.out.println("USAGE: java client host port");
            System.exit(-1);
        }
        try { /* get input parameters */
            host = args[0];
            port = Integer.parseInt(args[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("USAGE: java client host port");
            System.exit(-1);
        }

        try { /* set up a key manager for client authentication */
            SSLSocketFactory factory = null;
            //Här har det gjorts en del förändringar!
            try {
                KeyStore ks = KeyStore.getInstance("JKS");
                KeyStore ts = KeyStore.getInstance("JKS");
                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
                TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
                SSLContext ctx = SSLContext.getInstance("TLS");
		
		//Here we "load" the keycard and check the password
		//Since we don't actually have real keycards it is represented by just using client key/trust-stores

		System.out.println("Read Keycard (Which in this case means give username):");
		BufferedReader passcheck = new BufferedReader(new InputStreamReader(System.in)); //So we can get input
		String username = passcheck.readLine();	
		//VI gör antagande att både trust/key store är username + truststore/keystore
		String userkey = new StringBuilder(username).append("keystore").toString();
		String usertrust = new StringBuilder(username).append("truststore").toString();
		
		//Endast här för testing reasons.
		System.out.println("So keystore is: " + userkey + "\n" + "And Truststore is: " + usertrust);

		//Dessa är flyttade hit för att testa direkt ifall trust/key stores existerar.
		FileInputStream keytest = new FileInputStream(userkey); //one option is clientkeystore atm!
		FileInputStream trusttest = new FileInputStream(usertrust);

		System.out.println("Give password: ");
		String passinput = passcheck.readLine();	//VI ANTAR här också att password är samma till key och trust
		char[] password = passinput.toCharArray();
		
                ks.load(keytest, password);  // keystore password (storepass)
				ts.load(trusttest, password); // truststore password (storepass);
				kmf.init(ks, password); // user password (keypass)
				tmf.init(ts); // keystore can be used as truststore here
				ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
                factory = ctx.getSocketFactory();
            } catch (Exception e) {
                throw new IOException(e.getMessage());
            }
            SSLSocket socket = (SSLSocket)factory.createSocket(host, port);
            System.out.println("\nsocket before handshake:\n" + socket + "\n");

            /*
             * send http request
             *
             * See SSLSocketClient.java for more information about why
             * there is a forced handshake here when using PrintWriters.
             */
            socket.startHandshake();

            SSLSession session = socket.getSession();
            X509Certificate cert = (X509Certificate)session.getPeerCertificateChain()[0];
            String subject = cert.getSubjectDN().getName();
	    String issuer = cert.getIssuerDN().getName();  //THIS HAS BEEN ADDED!!!!
	    BigInteger serialnumber = cert.getSerialNumber(); //THIS HAS BEEN ADDED
            System.out.println("certificate name (subject DN field) on certificate received from server:\n" + subject + "\n");
	    System.out.println("Issuer name (cert issuer DN field): " + issuer); //ALSO ADDED!!!!!!!!!
	    System.out.println("Certificate serial number: " + serialnumber); //THIS IS ALSO ADDED!!!
            System.out.println("socket after handshake:\n" + socket + "\n");
            System.out.println("secure connection established\n\n");

            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
			for (;;) {
                System.out.print(">");
                msg = read.readLine();
                if (msg.equalsIgnoreCase("quit")) {
				    break;
				}
                System.out.print("sending '" + msg + "' to server...");
                out.println(msg);
                out.flush();
                System.out.println("done");

                System.out.println("received '" + in.readLine() + "' from server\n");
            }
            in.close();
			out.close();
			read.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
