import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

// Repeatedly reads firstL's nickname and text from the user in two
// separate lines, sending them to the server (read by ServerReceiver
// thread).
public class ClientSender extends Thread {
	private String nickname;
	private PrintStream server;
	boolean m_run = true;
	boolean hasQuit = false;
	boolean hasLog = false;
	boolean m_prelog = true;

	ClientSender(String nickname, PrintStream server) {
		this.nickname = nickname;
		this.server = server;
	}

	public void run() {
		// So that we can use the method readLine:
		BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
		try {
		      while (m_run) {
		          String line = user.readLine();
		          if (line.equals("quit")) {
		        	hasQuit = true;
		          }
		          if (line.equals("logout")){
		        	  hasLog = true;
		          }
		          server.println(line);
		        }
			/*
			// Then loop forever sending messages to firstLs via the server:
			while (m_run) {
				String firstL = user.readLine();
				// Matches CCCCC in ServerReceiver.java
				if (firstL.equals("quit")) {
					hasQuit = true;
					server.println(firstL);
					return;
				} else if (firstL.equals("logout")) {
					hasLog = true;
					server.println(firstL);
					return;
				} else if (firstL.equals("message")) {
					String recipient = user.readLine();
					server.println(recipient);
					String text = user.readLine();
					server.println(text); // Matches DDDDD in
											// ServerReceiver.java
				} else if (firstL.equals("change password")) {
					System.out.println("type in your new password");
					String tempP = user.readLine();
					ClientPasw.changeP(nickname, tempP);
					System.out.println("Your new password is set");
				} else if (firstL.equals("groups")){
					boolean tarp = true;
					while (tarp){
						//System.out.println("Type either 'my groups', 'join group' or 'create group'");
						String secondL = user.readLine();

						if(secondL.equals("my groups")){
							System.out.println(ClientPasw.checkGroups(nickname));
							tarp = false;
						} else if (secondL.equals("join group")){
							
							tarp = false;
						} else if ( secondL.equals("create group")){
							server.println("create group");
							System.out.println("what is your desired groups name?");
							String groupL = user.readLine();
							server.println(groupL);
							
							tarp = false ;
							
						} else if (secondL.equals("add member")){
							
						
						} else if (secondL.equals("add admin")){
						
					
						} else if (secondL.equals("message")){
					
				
						} else if (secondL.equals("")){
				
			
			}
					}}		
	{
		System.out.println("Type in either quit, logout or message");
	}}
 */	}
		catch(IOException e)
	{
			Report.errorAndGiveUp("Communication broke in ClientSender" + e.getMessage());
		}
	}

	public void stopSender() {
		m_run = false;
		server.println("quit");
	}

	public void logSender() {
		m_run = false;
		// server.println("logout");
		hasLog = false;
	}

	public boolean checkRun() {
		return hasQuit;
	}

	public boolean checkLog() {
		return hasLog;
	}
}