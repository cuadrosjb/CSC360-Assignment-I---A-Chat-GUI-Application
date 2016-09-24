package edu.ecsu.csc360;

/**
 * Object representing a message sent by chat application.
 * We use this class and wrap a javax.jms.ObjectMessage
 * around it instead of using a javax.jms.TextMessage
 * because a simple string is not sufficient. We want
 * be able to to indicate that a message is one of these 
 * types:
 *  join message  ('Hi, I just joined')
 *  regular message  (For regular chat messages)
 *  leave message  ('Bye, I'm leaving')
 *
 */
class ChatObjMessage implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int    type = JChatMessageTypes.NORMAL;
	private String  sender,
	message;

	/**
	 * ChatObjMessage constructor. Construct a message with the given
	 * sender and message.
	 * @param sender Message sender
	 * @param type Message type
	 * @param message The message to send
	 */
	public ChatObjMessage(String sender, int type, String message)  {
		this.sender = sender;
		this.type = type;
		this.message = message;
	}

	/**
	 * Returns message sender.
	 */
	public String getSender()  {
		return (sender);
	}

	/**
	 * Returns message type
	 */
	public int getType()  {
		return (type);
	}

	/**
	 * Sets the message string
	 * @param message The message string
	 */
	public void setMessage(String message)  {
		this.message = message;
	}
	/**
	 * Returns the message string
	 */
	public String getMessage()  {
		return (message);
	}
}