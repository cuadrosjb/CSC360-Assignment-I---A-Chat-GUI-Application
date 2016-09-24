package edu.ecsu.csc360;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class JChatPanel extends JPanel {

	private static final long serialVersionUID = 1L;
    
	private JButton sendButton;   
    private JLabel msgsLabel;
    private JLabel sendMsgLabel;
    private JTextArea  displayMsgText;
    private JTextArea  sendMsgText;
    String userName;
    String destName;
    String msgType;

    /**
     * JChatPanel constructor
     */
    public JChatPanel()  {
    	init();
    }
    
    /**
     * Set the chat username
     * @param userName Chat userName
     */
    public void setUserName(String userName)  {
    	this.userName = userName;
    	sendButton.setText("Send Message as " + userName);
    }
 
    /**
     * Sets the topic name.
     * @param destName Chat topic name
     */
 /*
    public void setDestName(String destName)  {
    	this.destName = destName;
//    	destLabel.setText("Topic: " + destName);
    }
*/
    public void setMsgType(String msgType)  {
    	this.msgType = msgType;
//    	msgTypeLabel.setText("Outgoing Msg Type: " + msgType);
    }

    /**
     * Returns the 'Send' button.
     */
    public JButton getSendButton()  {
    	return(sendButton);
    }

    /**
     * Clears the chat message text area.
     */
    public void clear()  {
    	displayMsgText.setText("");
    }

    /**
     * Appends the passed message to the chat message text area.
     * @param msg Message to display
     */
    public void newMessage(String sender, int type, String text)  {
    	switch (type)  {
    	case JChatMessageTypes.NORMAL:
    		displayMsgText.append(sender +  ": " + text + "\n");
    		break;

    	case JChatMessageTypes.JOIN:
    		displayMsgText.append("*** " +  sender +  " has joined chat session\n");
    		break;

    	case JChatMessageTypes.LEAVE:
    		displayMsgText.append("*** " +  sender +  " has left chat session\n");
    		break;

    	default:
    	}
    }

    /**
     * Sets the string to display on the chat message textarea
     * @param s String to display
     */
    public void setMessage(String s)  {
    	sendMsgText.setText(s);
    }
    /**
     * Returns the contents of the chat message textarea
     */
    public String getMessage()  {
    	return (sendMsgText.getText());
    }

    /*
     * Init JChatPanel GUI elements.
     */
    private void init()  {
    	JPanel  infoPanel;
    	
    	infoPanel = new JPanel();
    	infoPanel.setLayout(new BorderLayout(0, 0));
    	msgsLabel = new JLabel("Messages in chat:");
    	displayMsgText = new JTextArea(14, 40);
    	displayMsgText.setEditable(false);
    	infoPanel.add("North", msgsLabel);
    	infoPanel.add("Center", displayMsgText);
    	add("North", infoPanel);

    	infoPanel = new JPanel();
    	infoPanel.setLayout(new BorderLayout(0, 0));
    	sendMsgLabel = new JLabel("Type Message:");
    	sendMsgText = new JTextArea(12, 20);
    	sendMsgText.setLineWrap(true);
    	sendButton = new JButton("Send");
    	infoPanel.add("North", sendMsgLabel);
    	infoPanel.add("Center", sendMsgText);
    	infoPanel.add("South", sendButton);
    	add("South", infoPanel);
    }
}