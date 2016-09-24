package edu.ecsu.csc360;
/*
import java.awt.Button;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
*/


import javax.jms.BytesMessage;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

public class JChatModel {

	boolean connected = false;

	String  outgoingMsgTypeString;
	int     outgoingMsgType;

	JChatMessageCreator  outgoingMsgCreator;
	JChatMessageCreator  txtMsgCreator,  objMsgCreator, mapMsgCreator, bytesMsgCreator, streamMsgCreator;


	public JChatMessageCreator getMessageCreator(int type)  {
		switch (type)  {
		case JChatDialog.MSG_TYPE_TEXT:
			if (txtMsgCreator == null)  {
				txtMsgCreator = new JChatTextMessageCreator();
			}
			return (txtMsgCreator);

		case JChatDialog.MSG_TYPE_OBJECT:
			if (objMsgCreator == null)  {
				objMsgCreator = new JChatObjMessageCreator();
			}
			return (objMsgCreator);

		case JChatDialog.MSG_TYPE_MAP:
			if (mapMsgCreator == null)  {
				mapMsgCreator = new JChatMapMessageCreator();
			}
			return (mapMsgCreator);

		case JChatDialog.MSG_TYPE_BYTES:
			if (bytesMsgCreator == null)  {
				bytesMsgCreator = new JChatBytesMessageCreator();
			}
			return (bytesMsgCreator);

		case JChatDialog.MSG_TYPE_STREAM:
			if (streamMsgCreator == null)  {
				streamMsgCreator = new JChatStreamMessageCreator();
			}
			return (streamMsgCreator);
		}

		return (null);
	}

	public JChatMessageCreator getMessageCreator(Message msg)  {
		if (msg instanceof TextMessage)  {
			if (txtMsgCreator == null)  {
				txtMsgCreator = new JChatTextMessageCreator();
			}
			return (txtMsgCreator);
		} else if (msg instanceof ObjectMessage)  {
			if (objMsgCreator == null)  {
				objMsgCreator = new JChatObjMessageCreator();
			}
			return (objMsgCreator);
		} else if (msg instanceof MapMessage)  {
			if (mapMsgCreator == null)  {
				mapMsgCreator = new JChatMapMessageCreator();
			}
			return (mapMsgCreator);
		} else if (msg instanceof BytesMessage)  {
			if (bytesMsgCreator == null)  {
				bytesMsgCreator = new JChatBytesMessageCreator();
			}
			return (bytesMsgCreator);
		} else if (msg instanceof StreamMessage)  {
			if (streamMsgCreator == null)  {
				streamMsgCreator = new JChatStreamMessageCreator();
			}
			return (streamMsgCreator);
		}
		return (null);
	}
}
