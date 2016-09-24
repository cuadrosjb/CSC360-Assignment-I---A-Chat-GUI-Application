package edu.ecsu.csc360;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Dialog for querying the chat user name and chat topic.
 *
 */
class JChatDialog extends JDialog  {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final static int  MSG_TYPE_UNDEFINED  = -1;
    public final static int  MSG_TYPE_OBJECT    = 0;
    public final static int  MSG_TYPE_TEXT    = 1;
    public final static int  MSG_TYPE_MAP    = 2;
    public final static int  MSG_TYPE_BYTES    = 3;
    public final static int  MSG_TYPE_STREAM    = 4;

    private JTextField  nameF, topicF;
    private JButton connectButton;
	private JButton  cancelButton;

    /**
     * SimpleChatDialog constructor.
     * @param f Parent frame.
     */
    public JChatDialog(JFrame f)  {
    	super(f, "JChat: Connection Information", true);
    	init();
    	setResizable(false);
    }

    /**
     * Return 'Connect' button
     */
    public JButton getConnectButton()  {
    	return (connectButton);
    }
    /**
     * Return 'Cancel' button
     */
    public JButton getCancelButton()  {
    	return (cancelButton);
    }

    /**
     * Return chat user name entered.
     */
    public String getChatUserName()  {
    	if (nameF == null)
    		return (null);
    	return (nameF.getText());
    }
    /**
     * Set chat user name.
     * @param s chat user name
     */
    public void setChatUserName(String s)  {
    	if (nameF == null)
    		return;
    	nameF.setText(s);
    }

    /**
     * Set chat topic
     * @param s chat topic
     */
    public void setChatTopicName(String s)  {
    	if (topicF == null)
    		return;
    	topicF.setText(s);
    }
    /**
     * Return chat topic
     */
    public String getChatTopicName()  {
    	if (topicF == null)
    		return (null);
    	return (topicF.getText());
    }

    /*
     * Init GUI elements.
     */
    private void init()  {
    	JPanel      p, dummyPanel, labelPanel, valuePanel;
    	GridBagLayout    labelGbag, valueGbag;
    	GridBagConstraints      labelConstraints, valueConstraints;
    	JLabel      chatNameLabel, chatTopicLabel;
    	int      i, j;

    	p = new JPanel();
    	p.setLayout(new BorderLayout());

    	dummyPanel = new JPanel();
    	dummyPanel.setLayout(new BorderLayout());

    	/***/
    	labelPanel = new JPanel();
    	labelGbag = new GridBagLayout();
    	labelConstraints = new GridBagConstraints();
    	labelPanel.setLayout(labelGbag);
    	j = 0;

    	valuePanel = new JPanel();
    	valueGbag = new GridBagLayout();
    	valueConstraints = new GridBagConstraints();
    	valuePanel.setLayout(valueGbag);
    	i = 0;

    	chatNameLabel = new JLabel("Chat User Name:  ", JLabel.RIGHT);
    	chatTopicLabel = new JLabel("Chat Topic:  ", JLabel.RIGHT);

    	labelConstraints.gridx = 0;
    	labelConstraints.gridy = j++;
    	labelConstraints.weightx = 1.0;
    	labelConstraints.weighty = 1.0;
    	labelConstraints.anchor = GridBagConstraints.EAST;
    	labelGbag.setConstraints(chatNameLabel, labelConstraints);
    	labelPanel.add(chatNameLabel);

    	labelConstraints.gridy = j++;
    	labelGbag.setConstraints(chatTopicLabel, labelConstraints);
    	labelPanel.add(chatTopicLabel);

    	nameF = new JTextField(20);
    	topicF = new JTextField(20);

    	valueConstraints.gridx = 0;
    	valueConstraints.gridy = i++;
    	valueConstraints.weightx = 1.0;
    	valueConstraints.weighty = 1.0;
    	valueConstraints.anchor = GridBagConstraints.WEST;
    	valueGbag.setConstraints(nameF, valueConstraints);
    	valuePanel.add(nameF);
    	valueConstraints.gridy = i++;
    	valueGbag.setConstraints(topicF, valueConstraints);
    	valuePanel.add(topicF);
    	dummyPanel.add("West", labelPanel);
    	dummyPanel.add("Center", valuePanel);
    	p.add("North", dummyPanel);
    	dummyPanel = new JPanel();
    	connectButton = new JButton("Connect");
    	cancelButton = new JButton("Cancel");
    	dummyPanel.add(connectButton);
    	dummyPanel.add(cancelButton);
    	p.add("South", dummyPanel);
    	add(p);
    	pack();
    }
}