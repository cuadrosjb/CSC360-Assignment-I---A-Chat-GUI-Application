package edu.ecsu.csc360;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.InitialContext;


public class JChatController implements MessageListener, ActionListener  {
	
	private static final Logger Jlog = Logger.getLogger("JChat");
	
	TopicConnectionFactory tcf;
//	QueueConnectionFactory qcf;
	
	ConnectionFactory connectionFactory;
	
	String initialFactoryName = null;
	String providerUrl = null;
	String topicCfName = null;
	
	TopicConnection		connection;
	TopicSession		session;
	TopicSession		asyncSession;
	
	MessageProducer     msgProducer;
	MessageConsumer     msgConsumer;
	Topic               topic;

	boolean connected = false;
	String name, hostName, topicName;
//	String outgoingMsgTypeString;
//	int outgoingMsgType;
//	JChatMessageCreator  outgoingMsgCreator;
	private JChatGUI _scg;
	private JChatModel _scm;
//	String[] args = new String[2];
	
	/**
	 * JChatController constructor.
	 */
	public JChatController(JChatGUI scg, JChatModel scm)  {
		Jlog.info("JChaController constructor");
		_scg = scg;
		_scm = scm;
		/*
		 * Initialization
		 */
		
		_scg.sendButton.addActionListener(this);
		_scg.connectItem.addActionListener(this);
		_scg.disconnectItem.addActionListener(this);
		_scg.clearItem.addActionListener(this);
		_scg.exitItem.addActionListener(this);
		_scg.prefItem.addActionListener(this);
	}

	public void onMessage(Message msg) {
		try {
//			Jlog.info("onMessage called with: " + ((TextMessage)msg).getText());
			
			_scg.jcp.newMessage(_scg.scd.getChatUserName(), JChatMessageTypes.NORMAL, ((TextMessage)msg).getText());
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**
		 * Display chat message on message panel.
		 *
		 * @param msg message received
		 */

		// Display received message on JChatPanel
//		_scg.jcp.newMessage("user", JChatMessageTypes.NORMAL, "Hello World");
	}
	/*
	 * END INTERFACE MessageListener
	 */

	
	/*
	 * Performs the actual chat connect.
	 * The createChatSession() method does the real work
	 * here, creating:
	 *    Connection
	 *    Session
	 *    Topic
	 *    MessageConsumer
	 *    MessageProducer
	 */
	private void doConnect()  {
		Jlog.info("doConnect");
		_scg.connectItem.setEnabled(false);
		_scg.disconnectItem.setEnabled(true);
		
		
		
		try{
			Jlog.info("initializing tcf and qcf");
			
			InitialContext ctx = new InitialContext();
			
			tcf = (TopicConnectionFactory) ctx.lookup("ConnectionFactory");
//			qcf = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
			
//			connectionFactory 
			
			
			connection = tcf.createTopicConnection();
			//session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			asyncSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			
			
			topic = (Topic) ctx.lookup("MyTopic");
			
			
			
			
			msgConsumer = session.createConsumer(topic);
			msgProducer = session.createProducer(topic);
			
			msgConsumer.setMessageListener(this);
			
			createChatSession(topicName);
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	/*
	 * Disconnects from chat session.
	 * destroyChatSession() performs the JMS cleanup.
	 */
	private void doDisconnect()  {
		Jlog.info("doDisconnect");
		
		sendLeaveMessage();

		_scg.connectItem.setEnabled(true);
		_scg.disconnectItem.setEnabled(false);
		_scg.jcp.setEnabled(false);
	}

	/*
	 * These methods set/return a flag that indicates
	 * whether the application is currently involved in
	 * a chat session.
	 */
	private void setConnectedToChatSession(boolean b)  {
		connected = b;
	}
	private boolean connectedToChatSession()  {
		return (connected);
	}

	/*
	 * Exit application. Does some cleanup if
	 * necessary.
	 */
	private void exit()  {
		doDisconnect();
		System.exit(0);
	}


	/*
	 * Send a message to the chat room session to inform people
	 * you just joined the chat room.
	 */
	private void sendJoinMessage()  {
		Jlog.info("sendJoinMessage");
		 
		try {
			Message msg = session.createTextMessage(_scg.scd.getChatUserName() + " has joined the chat room.");
			onMessage(msg);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	/*
	 * Send a message to the chat room session to inform people
	 * you are leaving the chat room.
	 */
	private void sendLeaveMessage()  {
		Jlog.info("sendLeaveMessage");
		Message msg;
		try {
			msg = session.createTextMessage(_scg.scd.getChatUserName() + " has left the chat room.");
			onMessage(msg);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Create 'chat session'. This involves creating:
	 *    Connection
	 *    Session
	 *    Topic
	 *    MessageConsumer
	 *    MessageProducer
	 */
	private boolean createChatSession(String topicStr) {
		Jlog.info("createChatSession with topicStr: " + topicStr);
		
		sendJoinMessage();
		
		
		
		
		
		

		return false;  // or true
	}
	

	/*
	 * Send message using text that is currently in the JChatPanel
	 * object. The text message is obtained via scp.getMessage()
	 *
	 * An object of type ChatObjMessage is created containing the typed
	 * text. A JMS ObjectMessage is used to encapsulate this ChatObjMessage
	 * object.
	 */
	private void sendChatMessage()  {
		/*
		 * Sent out messages in the JPanel text area
		 * _scg.jcp.getMessage()
		 * 
		 */
		Jlog.info("Send Chat Message");
		
		try {
			Message msg = session.createTextMessage(_scg.jcp.getMessage());
			onMessage(msg);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	/*
	 * Destroy/close 'chat session'.
	 */
	private void destroyChatSession()  {
		Jlog.info("Destroy Chat Session");

	}

	/* 
	 * BEGIN INTERFACE ActionListener
	 */
	/**
	 * Detects the various UI actions and performs the relevant action:
	 * Connect menu item (on Chat menu): 	Connect to the chat room
	 * Disconnect menu item (on Chat menu): Disconnect from the chat room
	 * Connect button (on Connect dialog):  Connect to specified chat
	 * Cancel button (on Connect dialog):  Hide Connect dialog
	 * Send button:        Send message to chat
	 * Clear menu item (on Chat menu):    Clear chat text area
	 * Exit menu item (on Chat menu):    Exit application
	 * Preference menu item (on Configure menu): Set and save configuration
	 *
	 * @param ActionEvent UI event
	 */
	public void actionPerformed(ActionEvent e)  {
		Object obj = e.getSource();
		if (obj == _scg.connectItem)  {			
			_scg.scd.getConnectButton().addActionListener(this);
			_scg.scd.getCancelButton().addActionListener(this);
			_scg.setChatroomName();
		} else if (obj == _scg.disconnectItem)  {
			doDisconnect();
		} else if (obj == _scg.scd.getConnectButton())  {
			_scg.scd.setVisible(false);
			topicName = _scg.scd.getChatTopicName();
			name = _scg.scd.getChatUserName();
			doConnect();
			_scg.jcp.setUserName(name);
			_scg.jcp.setEnabled(true);
		} else if (obj == _scg.scd.getCancelButton())  {
			_scg.scd.setVisible(false);
		} else if (obj == _scg.sendButton)  {
			sendChatMessage();
		} else if (obj == _scg.clearItem)  {
			_scg.jcp.clear();
		} else if (obj == _scg.exitItem)  {
			exit();
		} else if (obj == _scg.prefItem) {
			_scg.jcd.getOKButton().addActionListener(this);
			_scg.jcd.getCancelButton().addActionListener(this);
			_scg.jcd.setCtxName(initialFactoryName);
			_scg.jcd.setProviderUrl(providerUrl);
			_scg.jcd.setTcfName(topicCfName);						
			_scg.displayConfigure();					
		} else if (obj == _scg.jcd.getOKButton()) {
			// Update the configuration information
			_scg.jcd.setVisible(false);
		} else if (obj == _scg.jcd.getCancelButton()) {
			_scg.jcd.setVisible(false);
		}
	}
	/* 
	 * END INTERFACE ActionListener
	 */	

	/*
	 * Display error. Right now all we do is dump to stderr.
	 */
	private void errorMessage(String s)  {
		Jlog.severe(s);
		System.err.println(s);
	}

}
