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
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;


public class JChatController implements MessageListener, ActionListener  {
	
	private static final Logger Jlog = Logger.getLogger("JChat");
	
	TopicConnectionFactory tcf;

	
	ConnectionFactory connectionFactory;
	
	String initialFactoryName = null;
	String providerUrl = null;
	String topicCfName = null;
	
	TopicConnection		connection;
	TopicSession		session;
	TopicSession		asyncSession;
	
	TopicPublisher		msgPublisher;
	TopicSubscriber     msgConsumer;
	
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
		
//			_scg.jcp.newMessage(_scg.scd.getChatUserName(), JChatMessageTypes.NORMAL, ((TextMessage)msg).getText());
			_scg.jcp.newMessage((String)msg.getObjectProperty("user"), JChatMessageTypes.NORMAL, (String)msg.getObjectProperty("msg"));
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void doConnect()  {
		Jlog.info("doConnect");
		_scg.connectItem.setEnabled(false);
		_scg.disconnectItem.setEnabled(true);
		
		
		
		try{
			Jlog.info("initializing tcf and qcf");
			
			InitialContext ctx = new InitialContext();
			
			tcf = (TopicConnectionFactory) ctx.lookup("ConnectionFactory");
			
			connection = tcf.createTopicConnection();

			
			session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE); //Publisher
			asyncSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);//subscriber
			
			topic = (Topic) ctx.lookup("MyTopic");
			
			msgPublisher = session.createPublisher(topic);
			msgConsumer = asyncSession.createSubscriber(topic, null, true);
			
			msgConsumer.setMessageListener(this);
			
			connection.start();
			
			createChatSession(topicName);
			
			connected = true;
			
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
		
		connected = false;

		_scg.connectItem.setEnabled(true);
		_scg.disconnectItem.setEnabled(false);
		_scg.jcp.setEnabled(false);
		
		setConnectedToChatSession(false);
		
		destroyChatSession();
		
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
			Message msg = session.createTextMessage();
			msg.setObjectProperty("user", _scg.scd.getChatUserName());
			msg.setObjectProperty("msg", " has joined the chat room.");
			msgPublisher.publish(msg);
			
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
		
		try {
			Message msg = session.createTextMessage();
			msg.setObjectProperty("user", _scg.scd.getChatUserName());
			msg.setObjectProperty("msg", " has left the chat room.");
			msgPublisher.publish(msg);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private boolean createChatSession(String topicStr) {
		Jlog.info("createChatSession with topicStr: " + topicStr);
		
		sendJoinMessage();

		return false;  // or true
	}

	private void sendChatMessage()  {
		Jlog.info("Send Chat Message");
		
		if(connectedToChatSession()){
			try {
				Message msg = session.createTextMessage(_scg.jcp.getMessage());
				msg.setObjectProperty("user", _scg.scd.getChatUserName());
				msg.setObjectProperty("msg", ((TextMessage)msg).getText());
				msgPublisher.publish(msg);
				_scg.jcp.newMessage(_scg.scd.getChatUserName(), JChatMessageTypes.NORMAL, ((TextMessage)msg).getText());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void destroyChatSession()  {
		Jlog.info("Destroy Chat Session");
		
		try{
			msgPublisher.close();
			msgConsumer.close();
			session.close();
			asyncSession.close();
			connection.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}

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
