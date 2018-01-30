import java.net.*;
import java.util.Set;
import java.io.*;

// Gets messages from client and puts them in a queue, for another
// thread to forward to the appropriate client.

public class ServerReceiver extends Thread {
	private String nickname;
	private BufferedReader myClient;
	private ClientTable clientTable;
	srvUser clnt;
	mGroups mGroups;
	///AccountService accountService;
	boolean ifQuit = false;
	boolean m_run = true;
	boolean ifLog = false;

	public ServerReceiver(String n, BufferedReader c, ClientTable t, srvUser clnt, mGroups mGroups) {
		nickname = n;
		myClient = c;
		clientTable = t;
		this.clnt = clnt;
		this.mGroups = mGroups;


	}

	public void run() {
	    clnt.setUsername(nickname);

	    clientTable.addUser(clnt);

		try {
			while (m_run) {
				String command = myClient.readLine();

				if (command.equals("quit")) {
					ifQuit = true;
					return;
				} else if (command.equals("logout")) {
					ifLog = true;
					return;
				} else {
					try {

						handle(command);
					} catch (throwMsg e) {
						replyUser(e.getMessage());
					}
				}
			}
			// myClient.close();
		} catch (IOException e) {
			Report.error("Something went wrong with the client " + clnt.getUsername() + " " + e.getMessage());
		}
	}

	private void handle(String command) throws IOException, throwMsg {
		switch (command) {
		case "quit":
			ifQuit = true;
			break;
		
		case "groupContr join":
			reqst();
			break;
		case "groupContr request":
			showGrReq();
			break;
		case "groupContr approve":
			aprJoinGroup();
			break;
		case "message":
			String msgg = myClient.readLine();
			sendMessage(msgg);
			break;
		case "groupContr message":
			grMsg();
			break;
		case "logout":
			ifLog = true;
			break;
		case "create groupContr":
			String groupName = myClient.readLine();

			grpCreate(groupName);
			break;
		case "groupContr add member":

			addM();
			break;
		case "groupContr add admin":
			addA();
			break;
		case "groupContr leave":
			grLeave();
			break;
		case "groupContr remove":
			admRem();
			break;

		}
	}

	/*
	 * try { while (m_run) {
	 * 
	 * String recipient = myClient.readLine(); // Matches CCCCC in //
	 * ClientSender.java // String text = myClient.readLine(); // Matches DDDDD
	 * in // ClientSender.java if (recipient != null) { if
	 * (recipient.equals("quit")) { ifQuit = true; } else if
	 * (recipient.equals("logout")) { ifLog = true; } else if
	 * (recipient.equals("message")) {
	 * 
	 * } else if (recipient.equals("create groupContr")) {
	 * 
	 * try { groups.grpCreate(myClient.readLine()); } catch
	 * (throwMsg e) { System.out.println(e.getMessage()); } } if (recipient
	 * != null && text != null) { Message msg = new Message(myClientsName,
	 * text); MessageQueue recipientsQueue = clientTable.getQueue(recipient); //
	 * Matches // EEEER // in // ServerSenser.java if (recipientsQueue != null)
	 * { recipientsQueue.offer(msg); } else { if (!((recipient.equals("quit") ||
	 * (recipient.equals("logout")))))
	 * Report.error("Message for unexistent client " + recipient + ": " + text);
	 * } } else return; } } } catch (IOException e) {
	 * Report.error("Something went wrong with the client " + myClientsName +
	 * " " + e.getMessage()); // No point in trying to close sockets. Just give
	 * up. // We end this thread (we don't do System.exit(1)). }
	 */
	private synchronized void replyUser2(String string) {
		clnt.getMessageQueue().offer(new Message("server", string));
	}

	public synchronized void stopSReceiver() {
		m_run = false;
		ifLog = false;

	}

	public synchronized boolean checkSRun() {
		return ifQuit;
	}

	public synchronized boolean checkSLog() {
		return ifLog;
	}

	private synchronized void replyUser(String string) {
		clnt.getMessageQueue().offer(new Message("server", string));
	}

	////////////////
	

	public synchronized void reqst() throws IOException, throwMsg {
		String n = myClient.readLine();
		groupContr groupContr = getGroup(n);
		System.out.println(n);
		groupContr.addR(nickname);
		replyUser("Request is sent");
	}
	public synchronized void sendMessage(String recipient) throws IOException, throwMsg {
		String text = myClient.readLine();
		if (recipient != null && text != null) {
			Message msg = new Message(nickname, text);
			MessageQueue recipientsQueue = clientTable.getQueue(recipient);
			System.out.println(nickname);
			if (recipientsQueue != null) {
				recipientsQueue.offer(msg);
			} else {
				Report.error("Message for unexistent client " + recipient + ": " + text);
				throw new throwMsg("Message cannot be sent to unexistent or offline client");
			}
		}
	}

	public synchronized void grMsg() throws IOException, throwMsg {
		groupContr groupContr = getGroup(myClient.readLine());
		if (!groupContr.isInG(nickname))
			throw new throwMsg("You are not a member, message cannot be sent.");
		String text = myClient.readLine();
		Set<String> recipients = groupContr.getMembersS();
		for (String recipient : recipients) {
			if (recipient != null && text != null) {
				Message msg = new Message(nickname + " to group " + groupContr.getN(), text);
				MessageQueue recipientsQueue = clientTable.getQueue(recipient);
				if (recipientsQueue != null) {
					recipientsQueue.offer(msg);
				}
			}
		}
	}

	public synchronized void addM() throws IOException, throwMsg {
		groupContr group1 = getGroup(myClient.readLine());
		if (!group1.isA(nickname))
			throw new throwMsg("You are not admin of the group");
		String addUsername = myClient.readLine();
		if (!(ClientPasw.checkN(nickname)))
			throw new throwMsg("Such a client does not exist.");
		group1.addM(addUsername);
		replyUser(addUsername + ": is added to the group " + group1.getN() + ".");
	}

	public synchronized void grpCreate(String groupname) throws IOException, throwMsg {
		mGroups.creatGroup(groupname, nickname);
		replyUser("Sucess. created group:" + groupname + ".");
	}
	public synchronized void aprJoinGroup() throws IOException, throwMsg {
		groupContr groupContr = getGroup(myClient.readLine());
		if (!groupContr.isA(nickname))
			throw new throwMsg("You are not an admin");
		String username = myClient.readLine();
		if (!groupContr.isR(username))
			throw new throwMsg("There is no such username");
		groupContr.approveR(username);
		replyUser("You are now part of the group");
	}


	public synchronized void showGrReq() throws IOException, throwMsg {

		groupContr groupContr = getGroup(myClient.readLine());
		if (!groupContr.isA(nickname))
			throw new throwMsg("You are not admin");
		replyUser("List :" + groupContr.showR() + ".");
	}

	public synchronized void admRem() throws IOException, throwMsg {
		groupContr groupContr = getGroup(myClient.readLine());
		if (!groupContr.isA(nickname))
			throw new throwMsg("You are not admin of the group");

		String username = myClient.readLine();
		if (groupContr.isInG(username))
			throw new throwMsg("Person is not in group");
		groupContr.removeU(username);
		replyUser("A person is removed from the group");
	}

	private synchronized groupContr getGroup(String groupName) throws throwMsg {
		groupContr groupContr = mGroups.getGroup(groupName);
		if (groupContr == null)
			throw new throwMsg("group does not exit.");
		return groupContr;
	}

	public synchronized void grLeave() throws IOException, throwMsg {
		groupContr groupContr = getGroup(myClient.readLine());
		groupContr.removeU(nickname);
		replyUser("You are removed from the group");
	}

	public synchronized void addA() throws IOException, throwMsg {
		groupContr groupContr = getGroup(myClient.readLine());
		if (!groupContr.isA(nickname))
			throw new throwMsg("You are not admin of the groupContr");
		String username = myClient.readLine();
		if (!groupContr.isInG(username))
			throw new throwMsg(
					"Person is not part of the group");
		groupContr.addA(username);
		replyUser(username + " :Person is added as admin.");
	}




}
