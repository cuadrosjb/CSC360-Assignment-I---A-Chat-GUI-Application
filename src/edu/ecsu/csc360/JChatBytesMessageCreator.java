package edu.ecsu.csc360;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.Session;

class JChatBytesMessageCreator implements 
	JChatMessageCreator {

	public Message createChatMessage(Session session, String sender, 
			int type, String text)  {
		BytesMessage    bytesMsg = null;

		try  {
			byte  b[];

			bytesMsg = session.createBytesMessage();
			bytesMsg.writeInt(type);
			/*
			 * Write length of sender and text strings
			 */
			b = sender.getBytes();
			bytesMsg.writeInt(b.length);
			bytesMsg.writeBytes(b);

			if (text != null)  {
				b = text.getBytes();
				bytesMsg.writeInt(b.length);
				bytesMsg.writeBytes(b);
			} else  {
				bytesMsg.writeInt(0);
			}
		} catch (Exception ex)  {
			System.err.println("Caught exception while creating message: " + ex);
		}

		return (bytesMsg);
	}

	public boolean isUsable(Message msg)  {
		if (msg instanceof BytesMessage)  {
			return (true);
		}

		return (false);
	}

	public int getChatMessageType(Message msg)  {
		int  type = JChatMessageTypes.BADTYPE;

		try  {
			BytesMessage  bytesMsg = (BytesMessage)msg;
			type = bytesMsg.readInt();
		} catch (Exception ex)  {
			System.err.println("Caught exception: " + ex);
		}

		return (type);
	}

	public String getChatMessageSender(Message msg)  {
		String  sender = null;

		sender = readSizeFetchString(msg);

		return (sender);
	}

	public String getChatMessageText(Message msg)  {
		String  text = null;

		text = readSizeFetchString(msg);

		return (text);
	}

	private String readSizeFetchString(Message msg)  {
		String  stringData = null;

		try  {
			BytesMessage  bytesMsg = (BytesMessage)msg;
			int      length, needToRead;
			byte    b[];

			length = bytesMsg.readInt();

			if (length == 0)  {
				return ("");
			}

			b = new byte [length];

			/*
			 * Loop to keep reading until all the bytes are read in
			 */
			needToRead = length;
			while (needToRead > 0)  {
				byte tmpBuf[] = new byte [needToRead];
				int ret = bytesMsg.readBytes(tmpBuf);
				if (ret > 0)  {
					for (int i=0; i < ret; ++i)  {
						b[b.length - needToRead +i] = tmpBuf[i];
					}
					needToRead -= ret;
				}
			}

			stringData = new String(b);
		} catch (Exception ex)  {
			System.err.println("Caught exception: " + ex);
		}

		return (stringData);
	}
}