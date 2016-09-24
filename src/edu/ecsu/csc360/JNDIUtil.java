package edu.ecsu.csc360;
//JMS classes
import javax.jms.JMSException;
import javax.jms.TopicConnectionFactory;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
//JNDI classes
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;

//Standard Java classes
import java.util.Hashtable;

/**
* 
* A wrapper class for JNDI calls
*
*/
public class JNDIUtil
{
	private Context context;
	
	public JNDIUtil(String icf, String url) throws JMSException, NamingException
	{
		Hashtable<String, String> environment = new Hashtable<String, String>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, icf );	
		environment.put(Context.PROVIDER_URL, url);
		context= new InitialContext( environment );
	}
	
	/**
	 * @param ObjName Object Name to be retrieved
	 * @return Retrieved Object
	 * @throws NamingException
	 */
	private Object getObjectByName(String ObjName) throws NamingException
	{
		return context.lookup( ObjName );
	}
	
	/**
	 * @param factoryName Factory Name
	 * @return ConnectionFactory object
	 * @throws NamingException
	 */
	public ConnectionFactory getConnectionFactory(String factoryName) throws NamingException
	{
		return (ConnectionFactory) getObjectByName(factoryName);
	}
	
	public TopicConnectionFactory getTopicConnectionFactory(String factoryName) throws NamingException {
		return (TopicConnectionFactory) getObjectByName(factoryName);
	}

	/**
	 * @param destinationName Destination Name
	 * @return ConnectionFactory object
	 * @throws NamingException
	 */
	public Destination getDestination(String destinationName) throws NamingException
	{
		return (Destination) getObjectByName(destinationName);
	}
	
}
