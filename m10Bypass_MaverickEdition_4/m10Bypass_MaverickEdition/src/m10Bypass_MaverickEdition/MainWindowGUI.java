package m10Bypass_MaverickEdition;

import java.awt.EventQueue;

import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.SpringLayout;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;



import javax.swing.JToggleButton;

public class MainWindowGUI {

	private JFrame frmPinappleProxy;
	public static CredManager mCreds;
	public static NetworkProxy mProxy;
	private static JLabel lblStatus;
	private MainWindowGUI jarod = this;
	private ConnectionCheck checker;
	private JToggleButton bypassButton;
	
	public static File pineapplePrefs = new File("./PineapplePreferences.mtp");
	public Bypass mBypass;
	public Thread bypassThread;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(pineapplePrefs.exists()){
						mCreds = new CredManager(pineapplePrefs);
						mProxy = new NetworkProxy(mCreds);
						
					}
					else{
						mCreds = new CredManager();
						mProxy = new NetworkProxy(mCreds);
					}
					mCreds.setRemoteHost(CredManager.findOpenServer());
					MainWindowGUI window = new MainWindowGUI();
					window.frmPinappleProxy.setVisible(true);
					if(mCreds.getAttemptFix()){
					setStatus("Fixing...");
					mProxy.disableProxy();
					}
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
		frmPinappleProxy.setBounds(100, 100, 371, 358);
		frmPinappleProxy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmPinappleProxy.getContentPane().setLayout(springLayout);
		frmPinappleProxy.setResizable(false);
		frmPinappleProxy.addWindowListener(new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    {
		    	if(bypassButton.isSelected()){
			    	mProxy.disableProxy();
			    	mBypass.stopBypass();
		    	}
		    	System.out.println("Closing...");
		    	System.exit(0);
		    }
		});
		try{
		final BufferedImage pineappleOn = ImageIO.read(getClass().getResourceAsStream("Pineapple.png"));
	    final BufferedImage buttonIcon = ImageIO.read(getClass().getResourceAsStream("PineappleOff.png"));
	    
		bypassButton = new JToggleButton(new ImageIcon(buttonIcon));
		springLayout.putConstraint(SpringLayout.NORTH, bypassButton, 79, SpringLayout.NORTH, frmPinappleProxy.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, bypassButton, 89, SpringLayout.WEST, frmPinappleProxy.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, bypassButton, -10, SpringLayout.SOUTH, frmPinappleProxy.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, bypassButton, -86, SpringLayout.EAST, frmPinappleProxy.getContentPane());
	    bypassButton.setBorderPainted(false);
	    bypassButton.setFocusPainted(false);
	    bypassButton.setContentAreaFilled(true);
		bypassButton.addItemListener(new ItemListener() {
			   public void itemStateChanged(ItemEvent ev) {
				      if(ev.getStateChange()==ItemEvent.SELECTED){
				    	//Do Bypass Stuff
				        System.out.println("Starting Bypass..");
				        setStatus("Starting Bypass");
				        bypassButton.setIcon(new ImageIcon(pineappleOn));     
				        String username = mCreds.getUsername();
				        String password = mCreds.getPassword();
				        makeNewBypass();
				        
				        //Do Connection Check Stuff
					    checker = new ConnectionCheck(jarod,mCreds.getUsername(),mCreds.getPassword());
						Thread connectionThread = new Thread(checker);
						connectionThread.start();


				      } else if(ev.getStateChange()==ItemEvent.DESELECTED){
				    	checker.isRunning = false;
				    	checker = null;
				    	mBypass.stopBypass();
				        System.out.println("Waiting...");
				        setStatus("Waiting");
				        bypassButton.setIcon(new ImageIcon(buttonIcon));
				      }
				   }
				});
		frmPinappleProxy.getContentPane().add(bypassButton);
		
		}catch(Exception e){e.printStackTrace();}
		
		lblStatus = new JLabel("Status:");
		springLayout.putConstraint(SpringLayout.NORTH, lblStatus, 10, SpringLayout.NORTH, frmPinappleProxy.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblStatus, 10, SpringLayout.WEST, frmPinappleProxy.getContentPane());
		frmPinappleProxy.getContentPane().add(lblStatus);
		
		JButton btnUpdate = new JButton("Update");
		springLayout.putConstraint(SpringLayout.SOUTH, btnUpdate, -6, SpringLayout.NORTH, bypassButton);
		springLayout.putConstraint(SpringLayout.EAST, btnUpdate, -10, SpringLayout.EAST, frmPinappleProxy.getContentPane());
		btnUpdate.setVisible(false);
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UpdateManager updateMan = new UpdateManager(mCreds);
				updateMan.updateLinux();
				
			}
		});
		frmPinappleProxy.getContentPane().add(btnUpdate);
		
		JButton btnNewButton = new JButton("Advanced");
		springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton, -5, SpringLayout.NORTH, btnUpdate);
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton, -10, SpringLayout.EAST, frmPinappleProxy.getContentPane());
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdvancedMenu a = new AdvancedMenu();
				a.run(jarod,mCreds);
			}
		});
		frmPinappleProxy.getContentPane().add(btnNewButton);
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

	
	public void makeNewBypass() {
        mBypass = new Bypass(mCreds.getUsername(),mCreds.getPassword(),mProxy,jarod);
        bypassThread = new Thread(mBypass);
		System.out.println("Just Made a nice new Bypass");
        bypassThread.start();
	}
	
}
