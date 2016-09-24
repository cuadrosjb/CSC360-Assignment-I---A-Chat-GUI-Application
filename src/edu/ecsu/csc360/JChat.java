package edu.ecsu.csc360;

public class JChat {
	/**
	 * @param args  Arguments passed via the command line. 
	 */
	public static void main(String[] args) {
		JChatGUI scg = new JChatGUI();
		JChatModel scm = new JChatModel();
		new JChatController(scg, scm);
	}
}
