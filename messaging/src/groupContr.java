

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class groupContr implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Set<String> strM;
	private final Set<String> strA;
	private final Set<String> strR;
	private final String groupName;

	public groupContr(String groupName) {
		this.groupName = groupName;
		strM = new HashSet<>();
		strA = new HashSet<>();
		strR = new HashSet<>();
	}

	public synchronized void removeU(String s) {
		strM.remove(s);
		if (strA.contains(s)) {
			strA.remove(s);
		}

	}

	public synchronized void addR(String s) {
		strR.add(s);
	}

	public synchronized String showR() {
		return strR.toString();
	}

	public synchronized void addM(String s) {
		strM.add(s);
	}

	public synchronized boolean isInG(String s) {
		return strM.contains(s);

	}

	public synchronized Set<String> getMembersS() {
		return strM;
	}

	public synchronized boolean isA(String s) {
		return strA.contains(s);
	}

	public synchronized void removeR(String s) {
		strR.remove(s);
	}

	public synchronized boolean isR(String s) {
		return strR.contains(s);
	}

	public synchronized String getN() {
		return groupName;
	}

	public synchronized void addA(String s) {
		strA.add(s);
	}

	public synchronized void approveR(String s) {
		addM(s);
		removeR(s);
	}

}
