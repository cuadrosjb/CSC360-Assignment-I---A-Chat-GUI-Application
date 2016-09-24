package edu.ecsu.csc360;

import javax.jms.Message;
import javax.jms.Session;

interface JChatMessageCreator  {
	public Message createChatMessage(Session session, String sender, 
			int type, String text);
	public boolean isUsable(Message msg);
	public int getChatMessageType(Message msg);
	public String getChatMessageSender(Message msg);
	public String getChatMessageText(Message msg);
}