package m10Bypass_MaverickEdition;

import java.net.URL;
import java.net.URLConnection;

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
	private static NetworkProxy myProxy;
	private static SshConnector con;
	private static SshTransport mTransport;
	private static ProxyServer socks;
	private static ConnectionCheck checker;
	
	public Bypass(String username,String password,NetworkProxy mProxy){
		name = username;
		pass = password;
		myProxy = mProxy;

	}

	public void run(){
		try{
			System.out.println("Running Bypass...");
			checker = new ConnectionCheck(this,name,pass);
			Thread connectionThread = new Thread(checker);
			connectionThread.start();
			runBypass(name, pass);

			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	static void runBypass(String uName,String passWd){
		try{
		con = SshConnector.createInstance();
		mTransport = new SocketTransport("www.m10cpu.com", 22);
		mClient = con.connect(mTransport, uName);
		PasswordAuthentication password = new PasswordAuthentication();
		password.setPassword(passWd);
		mClient.authenticate(password);
		System.out.println("Connected");
		
		//CHANGE THIS SO IT WILL TURN ALL THE THINGS ON NOT JUST MAC!!!!!!!!
		//myProxy.disableProxy();
		
		socks = new ProxyServer(new ServerAuthenticatorNone(), mClient);
		socks.start(8080);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void stopBypass(){

		try{
			mClient.disconnect();
			mClient.exit();
			mTransport.close();
			socks.stop();

		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Connection Severed...");
	}

	
	
	
}
