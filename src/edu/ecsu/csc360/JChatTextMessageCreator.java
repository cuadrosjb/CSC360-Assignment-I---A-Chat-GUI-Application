package edu.ecsu.csc360;

import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;


class JChatTextMessageCreator implements 
      JChatMessageCreator {
    private static String MSG_SENDER_PROPNAME =  "SIMPLECHAT_MSG_SENDER";
    private static String MSG_TYPE_PROPNAME =  "SIMPLECHAT_MSG_TYPE";

    public Message createChatMessage(Session session, String sender, 
          int type, String text)  {
    	TextMessage    txtMsg = null;

        try  {
            txtMsg = session.createTextMessage();
            txtMsg.setStringProperty(MSG_SENDER_PROPNAME, sender);
            txtMsg.setIntProperty(MSG_TYPE_PROPNAME, type);
            txtMsg.setText(text);
        } catch (Exception ex)  {
        	System.err.println("Caught exception while creating message: " + ex);
        }

        return (txtMsg);
    }

    public boolean isUsable(Message msg)  {
    	if (msg instanceof TextMessage)  {
    		return (true);
    	}

    	return (false);
    }

    public int getChatMessageType(Message msg)  {
    	int  type = JChatMessageTypes.BADTYPE;

    	try  {
    		TextMessage  txtMsg = (TextMessage)msg;
    		type = txtMsg.getIntProperty(MSG_TYPE_PROPNAME);
    	} catch (Exception ex)  {
    		System.err.println("Caught exception: " + ex);
    	}

    	return (type);
    }

    public String getChatMessageSender(Message msg)  {
    	String  sender = null;

    	try  {
    		TextMessage  txtMsg = (TextMessage)msg;
    		sender = txtMsg.getStringProperty(MSG_SENDER_PROPNAME);
    	} catch (Exception ex)  {
    		System.err.println("Caught exception: " + ex);
    	}

    	return (sender);
    }

    public String getChatMessageText(Message msg)  {
    	String  text = null;

    	try  {
    		TextMessage  txtMsg = (TextMessage)msg;
    		text = txtMsg.getText();
    	} catch (Exception ex)  {
    		System.err.println("Caught exception: " + ex);
    	}

    	return (text);
    }
}