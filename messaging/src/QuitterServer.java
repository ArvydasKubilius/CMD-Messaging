import java.io.*;

// Repeatedly reads recipient's nickname and text from the user in two
// separate lines, sending them to the server (read by ServerReceiver
// thread).

public class QuitterServer extends Thread {

	ServerSender cSender;
	ServerReceiver cReceiver;
	ClientTable cTable;
	String clientName;
	boolean m_run = true;
	boolean hasQuit = false;

	QuitterServer(ServerSender cSender, ServerReceiver cReceiver, ClientTable cTable, String clientName) {
		this.cSender = cSender;
		this.cReceiver = cReceiver;
		this.cTable = cTable;
		this.clientName = clientName;
	}

	public void run() {
		// So that we can use the method readLine:

		while (m_run) {

			if (cReceiver.checkSRun()) {
				cSender.stopSSender();
				cReceiver.stopSReceiver();
				Report.behaviour(clientName + " disconnected");
				cTable.removeQueue(clientName);
				ClientPasw.removeLog(clientName);
				m_run = false;
			}
			if (cReceiver.checkSLog()) {
				cSender.stopSSender();
				cReceiver.stopSReceiver();
				Report.behaviour(clientName + " disconnected");
				cTable.removeQueue(clientName);
				ClientPasw.removeLog(clientName);

				m_run = false;
				String[] rngd = new String[1];
				rngd[0] = "localhost";
				//Client.main(rngd);

			}
		}

	}

	public void stopSender() {
		m_run = false;
	}

	public boolean checkRun() {
		return hasQuit;
	}
}
