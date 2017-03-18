package m10Bypass_MaverickEdition;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class ConnectionCheck implements Runnable {

	
	private static MainWindowGUI myParent;
	private static String uName;
	private static String passWd;
	public boolean isRunning = true;
	private String previousResult = "";
	
	public ConnectionCheck(MainWindowGUI mParent, String name,String pass){
		uName = name;
		passWd = pass;
		myParent = mParent;
	}
	
	@Override
	public void run() {
		int round = 0;
		try{Thread.sleep(2500);}catch(Exception e){}
		 while(isRunning){
     		try{Thread.sleep(85);}catch(Exception e){e.printStackTrace();}
			 System.out.println("Going Round " + round);
	        	if(checkConnection()){
	        		if(previousResult.equals("Bad")){
	        			myParent.makeNewBypass();
	        		}
	        		System.out.println("Good");
	        		previousResult = "Good";
	        	}
	        	else{
	        		//System.out.println("Bad");
	        		myParent.setStatus("Waiting for network...");
	        		myParent.mBypass.stopBypass();
	        		myParent.bypassThread = null;
	        		myParent.makeNewBypass();
	        		previousResult = "Bad";
	        	}
	        	round++;
	        }
					
	}

	


	private static boolean checkConnection(){
		  Socket sock = new Socket();
		    InetSocketAddress addr = new InetSocketAddress("www.m10cpu.com",80);
		    try {
		        sock.connect(addr,3000);
		        return true;
		    } catch (IOException e) {
		        return false;
		    } finally {
		        try {sock.close();}
		        catch (IOException e) {}
		    }
	}
	
	
	
}