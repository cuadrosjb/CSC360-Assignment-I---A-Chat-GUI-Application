package edu.ecsu.csc360;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

class JChatMapMessageCreator implements 
			JChatMessageCreator {
	private static String MAPMSG_SENDER_PROPNAME =  "SIMPLECHAT_MAPMSG_SENDER";
	private static String MAPMSG_TYPE_PROPNAME =  "SIMPLECHAT_MAPMSG_TYPE";
	private static String MAPMSG_TEXT_PROPNAME =  "SIMPLECHAT_MAPMSG_TEXT";

	public Message createChatMessage(Session session, String sender, 
			int type, String text)  {
		MapMessage    mapMsg = null;

		try  {
			mapMsg = session.createMapMessage();
			mapMsg.setInt(MAPMSG_TYPE_PROPNAME, type);
			mapMsg.setString(MAPMSG_SENDER_PROPNAME, sender);
			mapMsg.setString(MAPMSG_TEXT_PROPNAME, text);
		} catch (Exception ex)  {
			System.err.println("Caught exception while creating message: " + ex);
		}

		return (mapMsg);
	}

	public boolean isUsable(Message msg)  {
		if (msg instanceof MapMessage)  {
			return (true);
		}

		return (false);
	}

	public int getChatMessageType(Message msg)  {
		int  type = JChatMessageTypes.BADTYPE;

		try  {
			MapMessage  mapMsg = (MapMessage)msg;
			type = mapMsg.getInt(MAPMSG_TYPE_PROPNAME);
		} catch (Exception ex)  {
			System.err.println("Caught exception: " + ex);
		}

		return (type);
	}

	public String getChatMessageSender(Message msg)  {
		String  sender = null;

		try  {
			MapMessage  mapMsg = (MapMessage)msg;
			sender = mapMsg.getString(MAPMSG_SENDER_PROPNAME);
		} catch (Exception ex)  {
			System.err.println("Caught exception: " + ex);
		}

		return (sender);
	}

	public String getChatMessageText(Message msg)  {
		String  text = null;

		try  {
			MapMessage  mapMsg = (MapMessage)msg;
			text = mapMsg.getString(MAPMSG_TEXT_PROPNAME);
		} catch (Exception ex)  {
			System.err.println("Caught exception: " + ex);
		}

		return (text);
	}
}
