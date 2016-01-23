package m10Bypass_MaverickEdition;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class ConnectionCheck implements Runnable {

	
	private static Bypass myParent;
	private static String uName;
	private static String passWd;
	private static boolean isRunning = true;
	
	public ConnectionCheck(Bypass mParent, String name,String pass){
		uName = name;
		passWd = pass;
		myParent = mParent;
	}
	
	@Override
	public void run() {
		 isRunning = true;
		 while(isRunning){
	        	if(checkConnection()){
	        		try{
	        		Thread.sleep(500);
	        		}catch(Exception e){e.printStackTrace();}

	        	}
	        	else{
			        System.out.println("Connection Lost, Restarting");
			        //setStatus("Connection Lost, Restarting");
	        		myParent.stopBypass();
	        		try{
	        			Thread.sleep(2000);
	        		}catch(Exception e){e.printStackTrace();}
	        		myParent.runBypass(uName,passWd);
	        		
	        	}
	        }
			
		
	}

	
	public void stopChecking(){
		isRunning = false;
	}

	private static boolean checkConnection(){
		try{
		InetAddress mAddress = InetAddress.getByName("www.m10cpu.com");
		if(mAddress.isReachable(2000)){
			return true;
		}
		return false;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
		
	}
	
	
	
}
