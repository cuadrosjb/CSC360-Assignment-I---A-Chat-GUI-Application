package edu.ecsu.csc360;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Dialog for displaying the configuration.
 *
 */
class JConfigDialog extends JDialog  {
	private static final long serialVersionUID = 1L;
    private JButton okButton;
    private JButton cancelButton;
	private final JPanel contentPanel = new JPanel();
	private JTextField providerUrlField;
	private JTextField tcfField;
	private JTextField topicField;
	private JTextField ctxField;

//    String user;

    /**
     * SimpleChatDialog constructor.
     * @param f Parent frame.
     */
    public JConfigDialog(JFrame f)  {
    	super(f, "JChat: Configuration", true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{117, 108, 86, 0};
		gbl_contentPanel.rowHeights = new int[]{20, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblInitialContextFactory = new JLabel("Initial Context Factory");
			GridBagConstraints gbc_lblInitialContextFactory = new GridBagConstraints();
			gbc_lblInitialContextFactory.fill = GridBagConstraints.VERTICAL;
			gbc_lblInitialContextFactory.anchor = GridBagConstraints.EAST;
			gbc_lblInitialContextFactory.insets = new Insets(0, 0, 5, 5);
			gbc_lblInitialContextFactory.gridx = 0;
			gbc_lblInitialContextFactory.gridy = 0;
			contentPanel.add(lblInitialContextFactory, gbc_lblInitialContextFactory);
		}
		{
			ctxField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.gridwidth = 2;
			gbc_textField.insets = new Insets(0, 0, 5, 5);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 1;
			gbc_textField.gridy = 0;
			contentPanel.add(ctxField, gbc_textField);
			ctxField.setColumns(10);
		}
		{
			JLabel lblProviderUrl = new JLabel("Provider URL");
			lblProviderUrl.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblProviderUrl = new GridBagConstraints();
			gbc_lblProviderUrl.anchor = GridBagConstraints.EAST;
			gbc_lblProviderUrl.insets = new Insets(0, 0, 5, 5);
			gbc_lblProviderUrl.gridx = 0;
			gbc_lblProviderUrl.gridy = 1;
			contentPanel.add(lblProviderUrl, gbc_lblProviderUrl);
		}
		{
			providerUrlField = new JTextField();
			GridBagConstraints gbc_textField_1 = new GridBagConstraints();
			gbc_textField_1.gridwidth = 2;
			gbc_textField_1.insets = new Insets(0, 0, 5, 5);
			gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_1.gridx = 1;
			gbc_textField_1.gridy = 1;
			contentPanel.add(providerUrlField, gbc_textField_1);
			providerUrlField.setColumns(10);
		}
		{
			JLabel lblTopicConnectionFactory = new JLabel("Topic Connection Factory");
			GridBagConstraints gbc_lblTopicConnectionFactory = new GridBagConstraints();
			gbc_lblTopicConnectionFactory.anchor = GridBagConstraints.WEST;
			gbc_lblTopicConnectionFactory.insets = new Insets(0, 0, 5, 5);
			gbc_lblTopicConnectionFactory.gridx = 0;
			gbc_lblTopicConnectionFactory.gridy = 2;
			contentPanel.add(lblTopicConnectionFactory, gbc_lblTopicConnectionFactory);
		}
		{
			tcfField = new JTextField();
			GridBagConstraints gbc_textField_2 = new GridBagConstraints();
			gbc_textField_2.gridwidth = 2;
			gbc_textField_2.insets = new Insets(0, 0, 5, 5);
			gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_2.gridx = 1;
			gbc_textField_2.gridy = 2;
			contentPanel.add(tcfField, gbc_textField_2);
			tcfField.setColumns(10);
		}
		{
			JLabel lblTopic = new JLabel("Topic");
			lblTopic.setHorizontalAlignment(SwingConstants.LEFT);
			GridBagConstraints gbc_lblTopic = new GridBagConstraints();
			gbc_lblTopic.anchor = GridBagConstraints.EAST;
			gbc_lblTopic.insets = new Insets(0, 0, 0, 5);
			gbc_lblTopic.gridx = 0;
			gbc_lblTopic.gridy = 3;
			contentPanel.add(lblTopic, gbc_lblTopic);
		}
		{
			topicField = new JTextField();
			GridBagConstraints gbc_textField_3 = new GridBagConstraints();
			gbc_textField_3.gridwidth = 2;
			gbc_textField_3.insets = new Insets(0, 0, 0, 5);
			gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_3.gridx = 1;
			gbc_textField_3.gridy = 3;
			contentPanel.add(topicField, gbc_textField_3);
			topicField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
    	this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    	setResizable(false);
    }

    /**
     * Return 'OK' button
     */
    public JButton getOKButton()  {
    	return (okButton);
    }
    
    /**
     * Return 'Cancel' button
     */
    public JButton getCancelButton()  {
    	return (cancelButton);
    }
    
    public void setCtxName(String s) {
    	if (ctxField == null)
    		return;
    	ctxField.setText(s);
    }
    
    /**
     * Set chat topic
     * @param s chat topic
     */
    public void setProviderUrl(String s)  {
    	if (providerUrlField == null)
    		return;
    	providerUrlField.setText(s);
    }
    /**
     * Return chat TopicConnectionFactory
     */
    public void setTcfName(String s)  {
    	if (tcfField == null)
    		return;
    	tcfField.setText(s);
    }
}