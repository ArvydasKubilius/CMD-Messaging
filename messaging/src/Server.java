// Usage:
//        java Server
//
// There is no provision for ending the server gracefully.  It will
// end if (and only if) something exceptional happens.

import java.net.*;
import java.util.Set;
import java.io.*;

public class Server  {
	static int n = 0;


	public static void main(String[] args) {

		// This table will be shared by the server threads:
		ClientTable clientTable = new ClientTable();

		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(Port.number);
		} catch (IOException e) {
			Report.errorAndGiveUp("Couldn't listen on port " + Port.number);
		}

		try {
			// We loop for ever, as servers usually do.
			while (true) {
				// Listen to the socket, accepting connections from new clients:
				Socket socket = serverSocket.accept(); // Matches AAAAA in
														// Client.java

				// This is so that we can use readLine():
				BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// outToClient.writeObject(clientTable);
				// outToClient.flush();
				// We ask the client what its name is:
				String clientName = fromClient.readLine(); // Matches BBBBB in
															// Client.java

				Report.behaviour(clientName + " connected");

				// We add the client to the table:
				//clientTable.add(clientName);
		        srvUser user = new srvUser();
		        user.setUsername(clientName);
		        mGroups service = new mGroups();

				// We create and start a new thread to read from the client:
				ServerReceiver sRec = new ServerReceiver(clientName, fromClient, clientTable, user, service);
				sRec.start();

				// We create and start a new thread to write to the client:
				PrintStream toClient = new PrintStream(socket.getOutputStream());
				//ServerSender sSen = new ServerSender(clientTable.getQueue(clientName), toClient);
				ServerSender sSen = new ServerSender(user, toClient);
				sSen.start();

				QuitterServer quitte = new QuitterServer(sSen, sRec, clientTable, clientName);
				quitte.start();
			}
		} catch (IOException e) {
			// Lazy approach:
			Report.error("IO error " + e.getMessage());
			// A more sophisticated approach could try to establish a new
			// connection. But this is beyond this simple exercise.
		}
	}

	public static boolean addS(String nickname, String passw) {
		return ClientPasw.add(nickname, passw);
	}

	public synchronized static int incr() {
		n = n + 1;
		//System.out.println(n);
		return n;
	}

	public static boolean checkSP(String nickname, String passw) {
		return ClientPasw.checkP(nickname, passw);
	}
}
