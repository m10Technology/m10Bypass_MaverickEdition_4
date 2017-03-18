package m10Bypass_MaverickEdition;

import com.sshtools.net.SocketTransport;
import com.sshtools.sftp.*;
import com.sshtools.ssh.PasswordAuthentication;
import com.sshtools.ssh.SshConnector;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;

import org.omg.CORBA.portable.InputStream;

import com.sshtools.ssh.SshClient;
import com.sshtools.ssh.SshTransport;
import com.sshtools.util.Base64.OutputStream;

public class UpdateManager implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CredManager mCreds;
	
	public UpdateManager(CredManager c){
		mCreds = c;
	}
	
	public void updateMac(){
		String myRoot = new File("../../../..").getAbsolutePath();
		System.out.println("You are running Version " + mCreds.getVersion());
		System.out.println("Latest version online is "+ getLatestVersion());
		double myVersion = Double.parseDouble(mCreds.getVersion());
		double latestVersion = Double.parseDouble(getLatestVersion());
		if(myVersion >= latestVersion){
			System.out.println("Version already at latest");
			return;
		}
		else{
			System.out.println("Downloading Update Package...");
			doDownload();
			
		}
		
	}
	
	public void updateWindows(){
		
	}
	
	public void updateLinux(){
		
	}

	
	
	
	
	
	
	
	
	transient SftpFile myFile;
	
	
	public void doDownload(){
		try{
			System.out.println("Catfish!");
		SshConnector con = SshConnector.createInstance();
			System.out.println("Catfish!");
		SshTransport mTransport = new SocketTransport("www.m10cpu.com", 22);
			System.out.println("Catfish!");
		SshClient mClient = con.connect(mTransport, "administrator");
			System.out.println("Catfish!");
		PasswordAuthentication password = new PasswordAuthentication();
			System.out.println("Catfish!");
		password.setPassword("MgMSyN432");
			System.out.println("Catfish!");
		mClient.authenticate(password);
			System.out.println("Catfish!");
		SftpClient myFTP = new SftpClient(mClient);
		
		
		//Download Update Script
		myFile = myFTP.openFile("/var/www/html/Downloads/PineappleProxy/Latest/update.sh");
		SftpFileInputStream is = new SftpFileInputStream(myFile); 
		FileOutputStream os = new FileOutputStream(new File("/home/garrett/Desktop/update.sh"));
		byte[] buffer = new byte[1024];
		is.read(buffer);
		for(byte b:buffer){
			os.write(b);
		}


		//Download Update Zip
		myFile = myFTP.openFile("/var/www/html/Downloads/PineappleProxy/Latest/update.zip");
		is = new SftpFileInputStream(myFile); 
		os = new FileOutputStream(new File("/home/garrett/Desktop/update.zip"));
		buffer = new byte[1024];
		is.read(buffer);
		for(byte b:buffer){
			os.write(b);
		}
		
		//Process proc = Runtime.getRuntime().exec("/Applications/Utilities/Terminal.app/Contents/MacOS/Terminal /bin/bash /Users/garrett/Desktop/update.sh");
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public String getLatestVersion(){
		try{
        URL checkURL = new URL("http://www.m10cpu.com/pineappleproxy/currentversion.html");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(checkURL.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            return inputLine;
        in.close();

		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Failed to retrieve current version from m10. Check your internet connection");
		}
		return null;
	}
	
}
