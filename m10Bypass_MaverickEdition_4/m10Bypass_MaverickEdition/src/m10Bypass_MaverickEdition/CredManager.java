package m10Bypass_MaverickEdition;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CredManager implements Serializable{
	
	private CredManager me = this;
	
	private String remoteHost = "www.m10cpu.com";
	private String remotePort = "22";
	private String bypassHost = "127.0.0.1";
	private String bypassPort = "8080";
	private boolean attemptFix = true;
	private String username = "PineappleProxyUser";
	private String password = "pineapplehead2016";
	private File pineapplePrefs;
	private String vNum = "1.7";
	
	public static String findOpenServer(){

		//This method returns the address of an open m10NodeServer for SSH Connections
		try{
		//1st Connect to m10NodeServer(Master)
		Socket socket = new Socket("127.0.0.1",8085);
		if(socket.isConnected()){
			System.out.println("Connected");
		}
		//2nd ask for Server with minimal connections
		DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
	
			writer.writeBytes("S");
			writer.flush();
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String ip = inFromServer.readLine();
			System.out.println(ip);
			return ip;
		}
		catch(Exception e){
			//All else fails, return www.m10cpu.com
			return "www.m10cpu.com";
		}
		}
	
	
	public CredManager(File pineapplePrefs1) {
		try{
			pineapplePrefs = pineapplePrefs1;
			FileInputStream is = new FileInputStream(pineapplePrefs1);
			
			ObjectInputStream ois = new ObjectInputStream(is);
			
			CredManager newMe = (CredManager)ois.readObject();
			this.setAttemptFix(newMe.getAttemptFix());
			this.setBypassHost(newMe.getBypassHost());
			this.setBypassPort(newMe.getBypassPort());
			this.setPassword(newMe.getPassword());
			this.setRemoteHost(newMe.getRemoteHost());
			this.setRemotePort(newMe.getRemotePort());
			this.setUsername(newMe.getUsername());
			this.setAttemptFix(newMe.getAttemptFix());
			ois.close();
		
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("Error importing preferences");
		}
	}
	
	public CredManager() {
		pineapplePrefs = new File("./PineapplePreferences.mtp");
		remoteHost = "www.m10cpu.com";
		remotePort = "22";
		bypassHost = "127.0.0.1";
		bypassPort = "8080";
		attemptFix = true;
		username = "PineappleProxyUser";
		password = "pineapplehead2016";
	}

	public String getVersion(){
		return vNum;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getRemoteHost(){
		return remoteHost;
	}
	
	public String getRemotePort(){
		return remotePort;

	}
	
	public String getBypassHost(){
		return bypassHost;
	}
	
	public String getBypassPort(){
		return bypassPort;
	}
	
	public boolean getAttemptFix(){
		return attemptFix;
	}
	
	
	
	
	
	public void setUsername(String input){
		username = input;
	}
	
	public void setPassword(String input){
		password = input;
	}
	
	public void setRemoteHost(String input){
		 remoteHost = input;
	}
	
	public void setRemotePort(String input){
		 remotePort = input;

	}
	
	public void setBypassHost(String input){
		 bypassHost = input;
	}
	
	public void setBypassPort(String input){
		 bypassPort = input;
	}
	
	public void setAttemptFix(boolean input){
		 attemptFix = input;
	}

	public void save() {
		try{
		/**ENCRYPTION GARBAGE USE MAYBE LATER
		byte[] keyBytes = "12432897245567".getBytes();
		byte[] ivBytes = ")_FfA-sQzbC~F-/N3==Ys_rKCTj#9Z;jq".getBytes();
		SecretKeySpec key = new SecretKeySpec(keyBytes,"DES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		Cipher mCipher;

		mCipher = Cipher.getInstance("DES/CTR/NoPadding","BC");
		mCipher.init(Cipher.ENCRYPT_MODE, key,ivSpec);
		
		SealedObject objectToWrite = new SealedObject(me,mCipher);
		objectToWrite.getObject(mCipher);
		//ObjectOutputStream oos = new OutputStream(new File("./prefs.conf"));
		**/
			
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pineapplePrefs));
		oos.writeObject(me);
		oos.close();
		System.out.println("File Saved Successfully to "+ pineapplePrefs.getAbsolutePath());
			
			
		}catch(Exception e){
			
			System.out.println("Error Saving File!");
			e.printStackTrace();
		};
		
	}
	
	
}
