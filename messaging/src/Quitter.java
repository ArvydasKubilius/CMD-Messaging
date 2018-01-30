import java.io.*;

// Repeatedly reads recipient's nickname and text from the user in two
// separate lines, sending them to the server (read by ServerReceiver
// thread).

public class Quitter extends Thread {

	ClientSender cSender;
	ClientReceiver cReceiver;
	boolean m_run = true;
	boolean hasQuit = false;

	Quitter(ClientSender cSender, ClientReceiver cReceiver) {
		this.cSender = cSender;
		this.cReceiver = cReceiver;
	}

	public void run() {

		while (m_run) {
			if (cSender.isAlive()) {
				if (cSender.checkRun()) {
					cReceiver.stopReceiver();
					cSender.stopSender();
					m_run = false;
				}

				if (cSender.checkLog()) {
					cSender.stopSender();
					cReceiver.stopReceiver();
					String[] rngd = new String[1];
					rngd[0] = "localhost";
					Client.main(rngd);
					m_run = false;

				}
			}

		}

	}
}
