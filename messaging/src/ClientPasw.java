import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ClientPasw {
	static BufferedReader inputFile = null;
	static PrintWriter outputFile = null;
	static String line = "";
	static int n = 0;
	static String[] nickAr = new String[100];
	static String[] passAr = new String[100];
	String K;
	String V;
	static Hashtable<String, String> group = new Hashtable<String, String>();
	
	private static ConcurrentMap<String, String> queueTable = new ConcurrentHashMap<String, String>();

	// The following overrides any previously existing nickname, and
	// hence the last client to use this nickname will get the messages
	// for that nickname, and the previously exisiting clients with that
	// nickname won't be able to get messages. Obviously, this is not a
	// good design of a messaging system. So I don't get full marks:

	public static void changeP(String nickname, String passw) {
		try {
			inputFile = new BufferedReader(new FileReader("Secret.txt"));
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			while ((line = inputFile.readLine()) != null) {
				String[] splitStr = line.split("\\s+");
				nickAr[n] = splitStr[0];
				passAr[n] = splitStr[1];

				if (nickAr[n].equals(nickname)) {
					passAr[n] = passw;
				}
				n = n + 1;
				
			}
			outputFile = new PrintWriter(new FileWriter("Secret.txt"));
			for (int i = 0; i < n; i++) {
				outputFile.println(nickAr[i] + " " + passAr[i]);
			}
			n = 0;
			inputFile.close();
			outputFile.close();
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	public static boolean add(String nickname, String passw) {

		////
		try {
			inputFile = new BufferedReader(new FileReader("Secret.txt"));
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			while ((line = inputFile.readLine()) != null) {
				String[] splitStr = line.split("\\s+");
				nickAr[n] = splitStr[0];
				passAr[n] = splitStr[1];
				n = n + 1;
				queueTable.put(splitStr[0], splitStr[1]);
			}
			if (checkN(nickname)) {
				return false;
			} else {
				queueTable.put(nickname, passw);
				outputFile = new PrintWriter(new FileWriter("Secret.txt"));
				for (int i = 0; i < n; i++) {
					outputFile.println(nickAr[i] + " " + passAr[i]);
				}
				n = 0;
				outputFile.println(nickname + " " + passw);
				inputFile.close();
				outputFile.close();
				return true;
			}
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		////
		return true;
	}

	// Returns null if the nickname is not in the table:
	public static boolean checkN(String nickname) {
		try {
			inputFile = new BufferedReader(new FileReader("Secret.txt"));
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			while ((line = inputFile.readLine()) != null) {
				String[] splitStr = line.split("\\s+");
				queueTable.put(splitStr[0], splitStr[1]);
			}
			inputFile.close();
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		return queueTable.containsKey(nickname);
	}

	public static String checkGroups(String nickname) {
		
        ArrayList<String> myArr = new ArrayList<String>();
        group.clear();

		try {
			inputFile = new BufferedReader(new FileReader("Groups.txt"));
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			while ((line = inputFile.readLine()) != null) {
				String[] splitStr = line.split("\\s+");
				int k = splitStr.length ;
				for (int i = 1; i < k; i++){
					group.put(splitStr[0],splitStr[i]);
					if(splitStr[i].equals(nickname)){
						myArr.add(splitStr[0]);
					}
				}
			}
			inputFile.close();
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		return myArr.toString();
	}
	
	public static void destroyLog() {
		try {
			inputFile = new BufferedReader(new FileReader("OnlineUsers.txt"));
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			outputFile = new PrintWriter(new FileWriter("OnlineUsers.txt"));
			outputFile.println("");
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}

	}

	public static void removeLog(String nickname) {
		try {
			inputFile = new BufferedReader(new FileReader("OnlineUsers.txt"));
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			while ((line = inputFile.readLine()) != null) {
				if (!(line.equals(nickname))) {
					nickAr[n] = line;
					n = n + 1;
					System.out.println(n);
				}
			}
			outputFile = new PrintWriter(new FileWriter("OnlineUsers.txt"));
			for (int i = 0; i < n; i++) {
				outputFile.println(nickAr[i]);
			}
			n = 0;
			inputFile.close();
			outputFile.close();

		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	public static boolean checkLog(String nickname) {
		queueTable.clear();
		try {
			inputFile = new BufferedReader(new FileReader("OnlineUsers.txt"));
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			while ((line = inputFile.readLine()) != null) {
				queueTable.put(line, "1");
			}
			inputFile.close();
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		return queueTable.containsKey(nickname);
	}

	public static void addLog(String nickname) {
		try {
			inputFile = new BufferedReader(new FileReader("OnlineUsers.txt"));
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			while ((line = inputFile.readLine()) != null) {

				// line = MessageDigest.getInstance(line);
				nickAr[n] = line;
				n = n + 1;
				System.out.println(n);
				queueTable.put(line, "1");
			}
			outputFile = new PrintWriter(new FileWriter("OnlineUsers.txt"));
			for (int i = 0; i < n; i++) {
				outputFile.println(nickAr[i]);
			}
			n = 0;
			outputFile.println(nickname);
			inputFile.close();
			outputFile.close();

		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	public static boolean checkP(String nickname, String passw) {
		try {
			inputFile = new BufferedReader(new FileReader("Secret.txt"));
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		try {
			while ((line = inputFile.readLine()) != null) {
				String[] splitStr = line.split("\\s+");
				queueTable.put(splitStr[0], splitStr[1]);
			}
			inputFile.close();
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		return (queueTable.get(nickname)).equals(passw);
	}

	public void removeQueue(String nickname) {
		queueTable.remove(nickname);
	}
}