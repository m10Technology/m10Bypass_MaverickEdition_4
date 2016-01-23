package m10Bypass_MaverickEdition;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.prefs.*;
public class NetworkProxy {
	
	
	//Linux Proxy Commands
	public void enableProxyLinux(){
		String setHost = "gsettings set org.gnome.system.proxy.socks host \"127.0.0.1\"";
	    String setPort = "gsettings set org.gnome.system.proxy.socks port 8080";
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
		String setProxySettings = "networksetup -setsocksfirewallproxy Ethernet 127.0.0.1 8080 off";
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
		String setProxyOff = "networksetup -setsocksfirewallproxystate Ethernet off";
		try{
		Process proc = Runtime.getRuntime().exec(setProxyOff);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	
	//Windows Proxy Commands
	public void enableProxyWindows(){
		try{
			Runtime rt = Runtime.getRuntime();
			rt.exec("cmd.exe /c start enable.vbs");
			rt.exec("cmd.exe /c start inetcpl.cpl ,4");
			refresh();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void refresh()
	{	
		try{
			Robot robot = new Robot();
			Thread.sleep(500);
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_L);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_L);
			Thread.sleep(500);
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_F4);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_F4);
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_F4);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_F4);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void disableProxyWindows(){
		try{
		Runtime rt = Runtime.getRuntime();
		rt.exec("cmd.exe /c start disable.vbs");
		rt.exec("cmd.exe /c start inetcpl.cpl ,4");
		refresh();
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
		else if(myOS.contains("Windows")){
			return 3;
		}
		return 0;
		
	}

}
