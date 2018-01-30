import java.net.*;
import java.io.*;

// Continuously reads from message queue for a particular client,
// forwarding to the client.

public class ServerSender extends Thread {
	//private MessageQueue clientQueue;
	srvUser user;
	private PrintStream client;
	boolean m_run = true;

	public ServerSender(srvUser user, PrintStream c) {
		this.user = user;
		client = c;
	}

	public void run() {
		while (m_run) {
		
			//	Message msg = clientQueue.take(); // Matches EEEEE in ServerReceiver
		      Message msg = user.getMessageQueue().take();
			client.println(msg); // Matches FFFFF in ClientReceiver
		}
	}

	public void stopSSender() {
		m_run = false;
		client.println("quit");

	}

	public void logSSender() {
		m_run = false;

	}
}
