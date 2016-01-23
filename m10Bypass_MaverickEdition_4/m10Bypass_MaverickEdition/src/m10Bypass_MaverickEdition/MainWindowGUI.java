package m10Bypass_MaverickEdition;

import java.awt.EventQueue;

import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JList;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JToggleButton;

public class MainWindowGUI {

	private JFrame frmPinappleProxy;
	private static NetworkProxy mProxy = new NetworkProxy();
	private Bypass mBypass;
	private static JLabel lblStatus;
	private static boolean isRunning = false;
	private static ConnectionCheck checker;
	private MainWindowGUI me = this;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindowGUI window = new MainWindowGUI();
					window.frmPinappleProxy.setVisible(true);
					setStatus("Fixing...");
					mProxy.disableProxy();
					setStatus("Waiting...");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindowGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPinappleProxy = new JFrame();
		frmPinappleProxy.setTitle("Pineapple Proxy");
		frmPinappleProxy.setBounds(100, 100, 335, 241);
		frmPinappleProxy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmPinappleProxy.getContentPane().setLayout(springLayout);
		
		JToggleButton bypassButton = new JToggleButton("Bypass");
		bypassButton.addItemListener(new ItemListener() {
			   public void itemStateChanged(ItemEvent ev) {				     
				   
				   CredManager mCreds = new CredManager();
				   String username = mCreds.getUsername();
				   String password = mCreds.getPassword();	 
				   
				   mBypass = new Bypass(username,password,mProxy,me);
			       Thread bypassThread = new Thread(mBypass);
				     
			         if(ev.getStateChange()==ItemEvent.SELECTED){
				        System.out.println("Starting Bypass..");
				        setStatus("Starting Bypass");
				        checker = new ConnectionCheck(mBypass,username,password);
				        Thread checkThread = new Thread(checker);
				        checkThread.start();
				        bypassThread.start();



				      } else if(ev.getStateChange()==ItemEvent.DESELECTED){
				    	checker.stopChecking();
				        System.out.println("Waiting...");
				        setStatus("Waiting");
				        mBypass.stopBypass();
				      }
				   }
				});
		springLayout.putConstraint(SpringLayout.SOUTH, bypassButton, -39, SpringLayout.SOUTH, frmPinappleProxy.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, bypassButton, -123, SpringLayout.EAST, frmPinappleProxy.getContentPane());
		frmPinappleProxy.getContentPane().add(bypassButton);
		
		lblStatus = new JLabel("Status:");
		springLayout.putConstraint(SpringLayout.NORTH, lblStatus, 10, SpringLayout.NORTH, frmPinappleProxy.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblStatus, 10, SpringLayout.WEST, frmPinappleProxy.getContentPane());
		frmPinappleProxy.getContentPane().add(lblStatus);
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
	
	public static void setStatus(String input){
		lblStatus.setText("Status: " + input);
	}
	
	
}
