

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class mGroups {

  private Map<String, groupContr> groups = new ConcurrentHashMap<>();
  String grpF = "Groups.txt";

  public mGroups() {
    readGroups();
    updateFile();
  }

  public void addUser(String grpN, String s) {
    (groups.get(grpN)).addM(s);
  }
  public String groupsList() {
	    return groups.toString();
	  }

	  public groupContr getGroup(String grpN) {
	    return groups.get(grpN);
	  }

	  public void updateFile() {
	    try (FileOutputStream fileOut = new FileOutputStream(grpF);
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);) {
	      out.writeObject(groups);
	    } catch (IOException e) {
	      Report.errorAndGiveUp("Could not update users");
	    }
	  }

	  @SuppressWarnings("unchecked")
	private void readGroups() {
	    try (FileInputStream fileIn = new FileInputStream(grpF);
	        ObjectInputStream in = new ObjectInputStream(fileIn);) {
	      groups = (Map<String, groupContr>) in.readObject();
	    } catch (IOException | ClassNotFoundException e1) {
	      Report.error("file not found");
	    }
	  }

  public void creatGroup(String grpN, String s) {
    groupContr groupContr = new groupContr(grpN);
    groupContr.addA(s);
    groupContr.addM(s);
    groups.put(grpN, groupContr);
  }

  public boolean groupExits(String grpN) {
    return groups.containsKey(grpN);
  }


}

