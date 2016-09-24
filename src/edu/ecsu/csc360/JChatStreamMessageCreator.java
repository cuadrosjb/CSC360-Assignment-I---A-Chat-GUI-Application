package edu.ecsu.csc360;

import javax.jms.Message;
import javax.jms.Session;
import javax.jms.StreamMessage;

class JChatStreamMessageCreator implements 
JChatMessageCreator {

	public Message createChatMessage(Session session, String sender, 
			int type, String text)  {
		StreamMessage    streamMsg = null;

		try  {
			byte b[];

			streamMsg = session.createStreamMessage();
			streamMsg.writeInt(type);
			streamMsg.writeString(sender);

			if (text == null)  {
				text = "";
			}
			streamMsg.writeString(text);
		} catch (Exception ex)  {
			System.err.println("Caught exception while creating message: " + ex);
		}

		return (streamMsg);
	}

	public boolean isUsable(Message msg)  {
		if (msg instanceof StreamMessage)  {
			return (true);
		}

		return (false);
	}

	public int getChatMessageType(Message msg)  {
		int  type = JChatMessageTypes.BADTYPE;

		try  {
			StreamMessage  streamMsg = (StreamMessage)msg;
			type = streamMsg.readInt();
		} catch (Exception ex)  {
			System.err.println("getChatMessageType(): Caught exception: " + ex);
		}

		return (type);
	}

	public String getChatMessageSender(Message msg)  {
		String  sender = null;

		try  {
			StreamMessage  streamMsg = (StreamMessage)msg;
			sender = streamMsg.readString();
		} catch (Exception ex)  {
			System.err.println("getChatMessageSender(): Caught exception: " + ex);
		}

		return (sender);
	}

	public String getChatMessageText(Message msg)  {
		String  text = null;

		try  {
			StreamMessage  streamMsg = (StreamMessage)msg;
			text = streamMsg.readString();
		} catch (Exception ex)  {
			System.err.println("getChatMessageText(): Caught exception: " + ex);
		}

		return (text);
	}

}

