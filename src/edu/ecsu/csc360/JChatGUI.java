package edu.ecsu.csc360;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

public class JChatGUI extends JFrame implements WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger Jlog = Logger.getLogger("JChat");
	JFrame frame;
	JChatPanel jcp;
	JChatDialog scd = null;
	JConfigDialog jcd = null;
	JMenuItem connectItem;
	JMenuItem disconnectItem;
	JMenuItem clearItem;
	JMenuItem exitItem;
	JMenuItem prefItem;
	JButton sendButton;
	JButton connectButton;
	JButton cancelButton;
	JButton okButton;
	String userName;
    String topicName;
    String hostName;
    String initialFactoryName;
    String providerUrl;
    String tcf;

	/*
	 * Create the application GUI.
	 */
	public JChatGUI() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
			Jlog.warning(e.getMessage());
		}
		frame = new JFrame("JChat");		
		JMenuBar menubar = createMenuBar();	
		frame.setJMenuBar(menubar);

		jcp = new JChatPanel();
		jcp.setUserName(userName);
//		jcp.setDestName(topicName);
		sendButton = jcp.getSendButton();
		scd = new JChatDialog(frame);
		jcd = new JConfigDialog(frame);
		frame.add(jcp);
		frame.pack();
		frame.setVisible(true);

		jcp.setEnabled(false);
	}

	/*
	 * Create menubar for application.
	 */
	private JMenuBar createMenuBar() {
		JMenuBar  mb = new JMenuBar();
		JMenu chatMenu;
		JMenu configMenu;

		chatMenu = (JMenu) mb.add(new JMenu("Chat"));
		connectItem = (JMenuItem) chatMenu.add(new JMenuItem("Connect ..."));
		disconnectItem = (JMenuItem) chatMenu.add(new JMenuItem("Disconnect"));
		clearItem = (JMenuItem) chatMenu.add(new JMenuItem("Clear Messages"));
		exitItem = (JMenuItem) chatMenu.add(new JMenuItem("Exit"));		
		disconnectItem.setEnabled(false);
		configMenu = mb.add(new JMenu("Configure"));
		prefItem = configMenu.add(new JMenuItem("Preference ..."));	
		return (mb);
	}


	/*
	 * Popup the JChatDialog to query the user for the chat user
	 * name and chat topic.
	 */
	public void setChatroomName()  {
		if (scd == null)  {
			scd = new JChatDialog(frame);
			connectButton = scd.getConnectButton();
			cancelButton = scd.getCancelButton();
		}
		scd.setChatUserName(userName);
		scd.setChatTopicName(topicName);
		scd.setVisible(true);
	}
	
	public void displayConfigure() {
		if (jcd == null) {
			jcd = new JConfigDialog(frame);
			okButton = jcd.getOKButton();
			cancelButton = jcd.getCancelButton();
		}	
		jcd.setVisible(true);		
	}
		
	/* 
	 * BEGIN INTERFACE WindowListener
	 */
	public void windowClosing(WindowEvent e)  {
		e.getWindow().dispose();
	}
	public void windowClosed(WindowEvent e)  {
		System.exit(0);
	}
	public void windowActivated(WindowEvent e)  { }
	public void windowDeactivated(WindowEvent e)  { }
	public void windowDeiconified(WindowEvent e)  { }
	public void windowIconified(WindowEvent e)  { }
	public void windowOpened(WindowEvent e)  { }
	/*
	 * END INTERFACE WindowListener
	 */
}
