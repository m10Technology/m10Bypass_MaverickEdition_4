package m10Bypass_MaverickEdition;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.prefs.*;
import org.lantern.win.*;

public class NetworkProxy {
	
	private CredManager mCreds;
	//private WinProxy winProxy;
	public NetworkProxy(CredManager c){
		mCreds = c;
		//winProxy = new WinProxy();
	}
	
	
	//Linux Proxy Commands
	public void enableProxyLinux(){
		String setHost = "gsettings set org.gnome.system.proxy.socks host \""+mCreds.getBypassHost()+"\"";
	    String setPort = "gsettings set org.gnome.system.proxy.socks port "+mCreds.getBypassPort();
		String setMode = "gsettings set org.gnome.system.proxy mode \"manual\"";
		try{
			Process proc = Runtime.getRuntime().exec(setHost);
			Process proc2 = Runtime.getRuntime().exec(setPort);
			Process proc3 = Runtime.getRuntime().exec(setMode);
			System.out.println("Network Proxy Set Successfully");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void disableProxyLinux(){
		try{
			Process proc = Runtime.getRuntime().exec("gsettings set org.gnome.system.proxy mode \"none\"");
		}
		catch(Exception e){
			
			e.printStackTrace();
			
		}
	}

	//OSX Proxy Commands
	public void enableProxyMac(){

		String setProxySettings = "networksetup -setsocksfirewallproxy Wi-Fi "+mCreds.getBypassHost() +" "+ mCreds.getBypassPort()+" off";
		String setProxyOn = "networksetup -setsocksfirewallproxystate on";
		
		try{
			System.out.println("Settings Proxy Settings");
			Process proc = Runtime.getRuntime().exec(setProxySettings);
			System.out.println("Network Proxy Set Successfully");
			return;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void disableProxyMac(){
		String setProxyOff = "networksetup -setsocksfirewallproxystate Wi-Fi off";
		try{
		Process proc = Runtime.getRuntime().exec(setProxyOff);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	
	//Windows Proxy Commands
	public void enableProxyWindows(){
		String myProxy = "socks=localhost:8080";
	//	winProxy.proxy(myProxy);		
	}
	public void disableProxyWindows(){
	//	winProxy.unproxy();
	}
	
	
	public static void refresh()
	{	
		try{
			Robot robot = new Robot();
			Thread.sleep(500);
			robot.keyPress(KeyEvent.VK_ALT);
			Thread.sleep(150);
			robot.keyPress(KeyEvent.VK_L);
			Thread.sleep(150);
			robot.keyRelease(KeyEvent.VK_ALT);
			Thread.sleep(150);
			robot.keyRelease(KeyEvent.VK_L);
			Thread.sleep(500);
			robot.keyPress(KeyEvent.VK_ALT);
			Thread.sleep(150);
			robot.keyPress(KeyEvent.VK_F4);
			Thread.sleep(150);
			robot.keyRelease(KeyEvent.VK_ALT);
			Thread.sleep(150);
			robot.keyRelease(KeyEvent.VK_F4);
			Thread.sleep(150);
			robot.keyPress(KeyEvent.VK_ALT);
			Thread.sleep(150);
			robot.keyPress(KeyEvent.VK_F4);
			Thread.sleep(150);
			robot.keyRelease(KeyEvent.VK_ALT);
			Thread.sleep(150);
			robot.keyRelease(KeyEvent.VK_F4);
			Thread.sleep(150);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	
	
	
	
	//General Proxy Commands
	public void disableProxy() {
		if(checkOS() == 0){
			System.out.println("Unknown OS Type! Halting m10Bypass");
			System.exit(0);
		}
		else if(checkOS() == 1){
			System.out.println("Linux System Detected...");
			this.disableProxyLinux();
			System.out.println("OS Proxy Settings returned to normal.");
		}
		else if(checkOS() == 2){
			System.out.println("OSX System Detected...");
			this.disableProxyMac();
			System.out.println("OS Proxy Settings returned to normal.");
		}
		else if(checkOS() == 3){
			System.out.println("Windows System Detected...");
			this.disableProxyWindows();
			System.out.println("OS Proxy Settings returned to normal.");
		}
	}
	
	public static int checkOS(){
		String myOS = System.getProperty("os.name");
		if(myOS.equals("Linux")){
			return 1;
		}
		else if(myOS.equals("Mac OS X")){
			return 2;
		}
		else if(myOS.contains("indows")){
			return 3;
		}
		return 0;
		
	}

}
