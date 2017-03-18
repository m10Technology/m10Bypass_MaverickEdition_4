package m10Bypass_MaverickEdition;

import java.awt.event.ItemListener;
import java.net.URL;
import java.net.URLConnection;
import java.net.*;
import com.sshtools.net.SocketTransport;
import com.sshtools.ssh.PasswordAuthentication;
import com.sshtools.ssh.SshClient;
import com.sshtools.ssh.SshConnector;
import com.sshtools.ssh.SshTransport;

import socks.ProxyServer;
import socks.server.ServerAuthenticatorNone;

public class Bypass implements Runnable{
	
	public static String name;
	public static String pass;
	private static SshClient mClient;
	public boolean isRunning = true;
	private static NetworkProxy myProxy;
	private static SshConnector con;
	private static SshTransport mTransport;
	private static ProxyServer socks;
	private static MainWindowGUI myParent;
	
	public Bypass(String username,String password,NetworkProxy mProxy,MainWindowGUI p){
		name = username;
		pass = password;
		myProxy = mProxy;
		myParent = p;
	}

	public void run(){
		try{
			System.out.println("Running Bypass...");
			runBypass(name, pass);
		}catch(Exception e){
			System.out.println("run() Broken");
		}
	}
	
	@SuppressWarnings("static-access")
	void runBypass(String uName,String passWd){
		try{
		con = SshConnector.createInstance();
		mTransport = new SocketTransport(myParent.mCreds.getRemoteHost(), Integer.parseInt(myParent.mCreds.getRemotePort()));
		mClient = con.connect(mTransport, uName);
		PasswordAuthentication password = new PasswordAuthentication();
		password.setPassword(passWd);
		mClient.authenticate(password);
		if(mClient.isConnected() && mClient.isAuthenticated()){
			System.out.println("Connected");
			myParent.setStatus("Connected");


			if(NetworkProxy.checkOS()==1){
				myProxy.enableProxyLinux();
			}else if(NetworkProxy.checkOS()==2){
				myProxy.enableProxyMac();
			}else{
				myProxy.enableProxyWindows();
			}
			socks = new ProxyServer(new ServerAuthenticatorNone(), mClient);
			socks.start(Integer.parseInt(myParent.mCreds.getBypassPort()));

		}

		}
		catch(BindException e){
			System.out.println("Address in Use");
			this.stopBypass();
			myParent.makeNewBypass();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public void stopBypass(){
		try{
			if(NetworkProxy.checkOS()==1){
				myProxy.disableProxyLinux();
			}else if(NetworkProxy.checkOS()==2){
				myProxy.disableProxyMac();
			}else if(NetworkProxy.checkOS()==3){
				myProxy.disableProxyWindows();
			}
			socks.stop();
			mClient.disconnect();
			mClient.exit();
			mTransport.close();
			
			
			mClient = null;
			mTransport = null;
			socks = null;
			con = null;
			System.out.println("Bypass Stopped");
	
		}catch(Exception e){
			
		}
		
	}

	
	
	
}
