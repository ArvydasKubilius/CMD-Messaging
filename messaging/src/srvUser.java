


public class srvUser {

  private MessageQueue queue = new MessageQueue();
  private String username;

  public srvUser() {
    username = null;

  }

  public synchronized MessageQueue getMessageQueue() {
    return queue;
  }

  public void setUsername(String username) {
    this.username = username;

  }

 
  public String getUsername() {
    return username;
  }

}
