
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

class Client {

	static boolean quitAct = false;
	static String nickname = "";

	public static void main(String[] args) {
		Console console = System.console();

		Scanner scanner = new Scanner(System.in);
		///
		// "a".compareTo("b");
		String hostname = args[0];
		PrintStream toServer = null;
		BufferedReader fromServer = null;
		Socket server = null;
		// Check correct usage:
		if (args.length != 1) {
			Report.errorAndGiveUp("Usage: java Client server-hostname");
		}
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				if (!(nickname.equals("")))
					ClientPasw.removeLog(nickname);
			}
		});
		int n = 0;
		while (n < 1) {
			System.out.println("Would you like to register or login?");

			String firstLine = "";
			firstLine = scanner.next();
			if (firstLine.equals("login")) {
				outP("Enter your username");
				String nickN = scanner.next();
				if (!(ClientPasw.checkN(nickN))) {
					outP("name doesn't exist in the database");
				} else if (ClientPasw.checkLog(nickN)) {
					outP("User already logged in");
				} else {
					String passwP = new String(console.readPassword(
							"Enter your password(Note, I made it so you cannot see the password you are typing, for security reasons): "));
					if (ClientPasw.checkP(nickN, passwP)) {
						outP("Succesfully logged in");
						ClientPasw.addLog(nickN);
						nickname = nickN;
						n = 1;
					} else {
						outP("Your password is incorrect, try again ");
					}
				}
			} else if (firstLine.equals("register")) {
				outP("Please enter your desired nickname");
				String nickN = scanner.next();
				if (ClientPasw.checkN(nickN)) {
					outP("name already exists in the database, try to log in");
				} else {
					outP("Please enter your desired password");
					String passwP = scanner.next();
					if (ClientPasw.add(nickN, passwP)) {
						outP("Succesfully registered");
					} else {
						outP("nickname already taken");
					}
				}
			} else if (firstLine.equals("quit")) {
				quitAct = true;
				n = 1;
			} else {
				System.out.println("You need to enter either login or register ");
			}
		}

		if (!(quitAct)) {
			try {
				server = new Socket(hostname, Port.number); // Matches AAAAA in
															// Server.java
				toServer = new PrintStream(server.getOutputStream());
				fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
			} catch (UnknownHostException e) {
				Report.errorAndGiveUp("Unknown host: " + hostname);
			} catch (IOException e) {
				Report.errorAndGiveUp("The server doesn't seem to be running " + e.getMessage());
			}
			toServer.println(nickname);
			// Open sockets:
			// Tell the server what my nickname is:
			// Matches BBBBB in Server.java
			// Create two client threads of a diferent nature:
			ClientSender sender = new ClientSender(nickname, toServer);
			ClientReceiver receiver = new ClientReceiver(fromServer);
			Quitter quitThread = new Quitter(sender, receiver);
			// Run them in parallel:
			sender.start();
			receiver.start();
			quitThread.start();
			// Wait for them to end and close sockets.
			try {
				sender.join();
				receiver.join();
				server.close();
				toServer.close();
				fromServer.close();
			} catch (IOException e) {
				Report.errorAndGiveUp("Something wrong " + e.getMessage());
			} catch (InterruptedException e) {
				Report.errorAndGiveUp("Unexpected interruption " + e.getMessage());
			}
		}
	}

	public static void outP(String n) {
		System.out.println(n);
	}
}