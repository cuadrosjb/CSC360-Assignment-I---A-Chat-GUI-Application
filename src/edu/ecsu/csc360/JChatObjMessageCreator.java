package edu.ecsu.csc360;

import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

class JChatObjMessageCreator implements 
	JChatMessageCreator {
	public Message createChatMessage(Session session, String sender, 
			int type, String text)  {
		ObjectMessage    objMsg = null;
		ChatObjMessage  sMsg;

		try  {
			objMsg = session.createObjectMessage();
			sMsg = new ChatObjMessage(sender, type, text);
			objMsg.setObject(sMsg);
		} catch (Exception ex)  {
			System.err.println("Caught exception while creating message: " + ex);
		}

		return (objMsg);
	}

	public boolean isUsable(Message msg)  {
		try  {
			ChatObjMessage  sMsg = getSimpleChatMessage(msg);
			if (sMsg == null)  {
				return (false);
			}
		} catch (Exception ex)  {
			System.err.println("Caught exception: " + ex);
		}

		return (true);
	}

	public int getChatMessageType(Message msg)  {
		int  type = JChatMessageTypes.BADTYPE;

		try  {
			ChatObjMessage  sMsg = getSimpleChatMessage(msg);
			if (sMsg != null)  {
				type = sMsg.getType();
			}
		} catch (Exception ex)  {
			System.err.println("Caught exception: " + ex);
		}

		return (type);
	}

	public String getChatMessageSender(Message msg)  {
		String    sender = null;

		try  {
			ChatObjMessage  sMsg = getSimpleChatMessage(msg);
			if (sMsg != null)  {
				sender = sMsg.getSender();
			}
		} catch (Exception ex)  {
			System.err.println("Caught exception: " + ex);
		}

		return (sender);
	}

	public String getChatMessageText(Message msg)  {
		String      text = null;

		try  {
			ChatObjMessage  sMsg = getSimpleChatMessage(msg);
			if (sMsg != null)  {
				text = sMsg.getMessage();
			}
		} catch (Exception ex)  {
			System.err.println("Caught exception: " + ex);
		}

		return (text);
	}

	private ChatObjMessage getSimpleChatMessage(Message msg)  {
		ObjectMessage    objMsg;
		ChatObjMessage  sMsg = null;

		if (!(msg instanceof ObjectMessage))  {
			System.err.println("SimpleChatObjMessageCreator: Message received not of type ObjectMessage!");
			return (null);
		}

		objMsg = (ObjectMessage)msg;

		try  {
			sMsg = (ChatObjMessage)objMsg.getObject();
		} catch (Exception ex)  {
			System.err.println("Caught exception: " + ex);
		}

		return (sMsg);
	}
}
