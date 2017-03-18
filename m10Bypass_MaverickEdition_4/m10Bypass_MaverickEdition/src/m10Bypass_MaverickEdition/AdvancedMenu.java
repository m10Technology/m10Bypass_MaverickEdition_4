package m10Bypass_MaverickEdition;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;

public class AdvancedMenu {

	private static JFrame frame;
	private static JTextField txtRemotehost;
	private static JTextField txtPortremote;
	private static JTextField txtBypasshost;
	private static JTextField txtBypassport;
	private static JTextField txtPasswordbox1;
	private static JTextField txtUsernamebox;
	private static MainWindowGUI myParent;
	private static CredManager mCreds;
	private static JCheckBox chckbxAttemptNetworkFix;
	
	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
	 */
	public static void run(MainWindowGUI mParent,CredManager c) {
		myParent = mParent;
		mCreds = c;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdvancedMenu window = new AdvancedMenu();
					initialize();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */

	public static void setStuff(){
		txtUsernamebox.setText(mCreds.getUsername());
		txtPasswordbox1.setText(mCreds.getPassword());
		txtRemotehost.setText(mCreds.getRemoteHost());
		txtPortremote.setText(mCreds.getRemotePort());
		txtBypasshost.setText(mCreds.getBypassHost());
		txtBypassport.setText(mCreds.getBypassPort());
		if(mCreds.getAttemptFix()){
			 chckbxAttemptNetworkFix.setSelected(true);
		}else{
			chckbxAttemptNetworkFix.setSelected(false);

		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 586, 272);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JLabel lblWarningDoNot = new JLabel("WARNING: DO  NOT  CHANGE  THESE  SETTINGS  UNLESS  YOU  KNOW  WHAT  THEY  DO!!");
		springLayout.putConstraint(SpringLayout.NORTH, lblWarningDoNot, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblWarningDoNot, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblWarningDoNot);
		
		JLabel lblRemoteHost = new JLabel("Remote Host:");
		springLayout.putConstraint(SpringLayout.NORTH, lblRemoteHost, 40, SpringLayout.SOUTH, lblWarningDoNot);
		springLayout.putConstraint(SpringLayout.WEST, lblRemoteHost, 0, SpringLayout.WEST, lblWarningDoNot);
		frame.getContentPane().add(lblRemoteHost);
		
		txtRemotehost = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtRemotehost, 6, SpringLayout.SOUTH, lblRemoteHost);
		springLayout.putConstraint(SpringLayout.WEST, txtRemotehost, 0, SpringLayout.WEST, lblWarningDoNot);
		txtRemotehost.setText("");
		frame.getContentPane().add(txtRemotehost);
		txtRemotehost.setColumns(10);
		
		JLabel lblRemotePort = new JLabel("Remote Port:");
		springLayout.putConstraint(SpringLayout.NORTH, lblRemotePort, 19, SpringLayout.SOUTH, txtRemotehost);
		springLayout.putConstraint(SpringLayout.WEST, lblRemotePort, 0, SpringLayout.WEST, lblWarningDoNot);
		frame.getContentPane().add(lblRemotePort);
		
		txtPortremote = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtPortremote, 6, SpringLayout.SOUTH, lblRemotePort);
		springLayout.putConstraint(SpringLayout.WEST, txtPortremote, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtPortremote, 72, SpringLayout.WEST, frame.getContentPane());
		txtPortremote.setText("22");
		frame.getContentPane().add(txtPortremote);
		txtPortremote.setColumns(10);
		
		JLabel lblBypassHost = new JLabel("Bypass Host:");
		springLayout.putConstraint(SpringLayout.NORTH, lblBypassHost, 0, SpringLayout.NORTH, lblRemoteHost);
		springLayout.putConstraint(SpringLayout.WEST, lblBypassHost, 93, SpringLayout.EAST, lblRemoteHost);
		frame.getContentPane().add(lblBypassHost);
		
		txtBypasshost = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtBypasshost, 0, SpringLayout.NORTH, txtRemotehost);
		springLayout.putConstraint(SpringLayout.WEST, txtBypasshost, 0, SpringLayout.WEST, lblBypassHost);
		txtBypasshost.setText("127.0.0.1");
		frame.getContentPane().add(txtBypasshost);
		txtBypasshost.setColumns(10);
		
		JLabel lblBypassPort = new JLabel("Bypass Port:");
		springLayout.putConstraint(SpringLayout.NORTH, lblBypassPort, 0, SpringLayout.NORTH, lblRemotePort);
		springLayout.putConstraint(SpringLayout.WEST, lblBypassPort, 0, SpringLayout.WEST, lblBypassHost);
		frame.getContentPane().add(lblBypassPort);
		
		txtBypassport = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtBypassport, 0, SpringLayout.NORTH, txtPortremote);
		springLayout.putConstraint(SpringLayout.WEST, txtBypassport, 0, SpringLayout.WEST, lblBypassHost);
		txtBypassport.setText("8080");
		frame.getContentPane().add(txtBypassport);
		txtBypassport.setColumns(10);
		
		chckbxAttemptNetworkFix = new JCheckBox("Attempt Network Fix on Startup");
		springLayout.putConstraint(SpringLayout.NORTH, chckbxAttemptNetworkFix, 6, SpringLayout.SOUTH, lblWarningDoNot);
		springLayout.putConstraint(SpringLayout.EAST, chckbxAttemptNetworkFix, -10, SpringLayout.EAST, frame.getContentPane());
		chckbxAttemptNetworkFix.setSelected(true);
		frame.getContentPane().add(chckbxAttemptNetworkFix);
		
		JButton btnApply = new JButton("Apply");
		btnApply.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Apply was clicked
				myParent.mCreds.setRemoteHost(txtRemotehost.getText());
				myParent.mCreds.setRemotePort(txtPortremote.getText());
				myParent.mCreds.setBypassHost(txtBypasshost.getText());
				myParent.mCreds.setBypassPort(txtBypassport.getText());
				myParent.mCreds.setUsername(txtUsernamebox.getText());
				myParent.mCreds.setPassword(txtPasswordbox1.getText());
				if(chckbxAttemptNetworkFix.isSelected()){
					myParent.mCreds.setAttemptFix(true);
				}
				else if(!chckbxAttemptNetworkFix.isSelected()){
					myParent.mCreds.setAttemptFix(false);
				}
				myParent.mCreds.save();
				
			}
		});
		frame.getContentPane().add(btnApply);
		
		JButton btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, btnOk, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnApply, 0, SpringLayout.NORTH, btnOk);
		springLayout.putConstraint(SpringLayout.EAST, btnApply, -6, SpringLayout.WEST, btnOk);
		springLayout.putConstraint(SpringLayout.EAST, btnOk, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnOk);
		
		JLabel lblUsername = new JLabel("Username:");
		springLayout.putConstraint(SpringLayout.NORTH, lblUsername, 0, SpringLayout.NORTH, lblRemoteHost);
		springLayout.putConstraint(SpringLayout.WEST, lblUsername, 0, SpringLayout.WEST, chckbxAttemptNetworkFix);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		springLayout.putConstraint(SpringLayout.NORTH, lblPassword, 0, SpringLayout.NORTH, lblRemotePort);
		springLayout.putConstraint(SpringLayout.WEST, lblPassword, 80, SpringLayout.EAST, lblBypassPort);
		frame.getContentPane().add(lblPassword);
		
		txtPasswordbox1 = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtPasswordbox1, 0, SpringLayout.NORTH, txtPortremote);
		springLayout.putConstraint(SpringLayout.WEST, txtPasswordbox1, 0, SpringLayout.WEST, chckbxAttemptNetworkFix);
		springLayout.putConstraint(SpringLayout.EAST, txtPasswordbox1, 0, SpringLayout.EAST, btnApply);
		txtPasswordbox1.setText("pineapplehead2016");
		frame.getContentPane().add(txtPasswordbox1);
		txtPasswordbox1.setColumns(10);
		
		txtUsernamebox = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtUsernamebox, 6, SpringLayout.SOUTH, lblUsername);
		springLayout.putConstraint(SpringLayout.WEST, txtUsernamebox, 22, SpringLayout.EAST, txtBypasshost);
		springLayout.putConstraint(SpringLayout.EAST, txtUsernamebox, -91, SpringLayout.EAST, frame.getContentPane());
		txtUsernamebox.setText("PineappleProxyUser");
		frame.getContentPane().add(txtUsernamebox);
		txtUsernamebox.setColumns(10);
		
		setStuff();
	}
}
