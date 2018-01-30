
// Each nickname has a different incomming-message queue.

import java.util.concurrent.*;

public class ClientTable {

	private ConcurrentMap<String, MessageQueue> queueTable = new ConcurrentHashMap<String, MessageQueue>();

	

	public void addUser(srvUser s) {
		//queueTable.put(nickname, new MessageQueue());
	    queueTable.put(s.getUsername(), s.getMessageQueue());

	}

	// Returns null if the nickname is not in the table:
	public synchronized MessageQueue getQueue(String nickname) {
		//System.out.println("ciuvako" + nickname);
		return queueTable.get(nickname);
	}

	public void removeQueue(String nickname) {
		queueTable.remove(nickname);
	}
}
