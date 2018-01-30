

public class throwMsg extends Exception {

  private static final long serialVersionUID = 1L;
  private final String msg;
  public throwMsg(String msg) {
    this.msg = msg;
  }

  @Override
  public String getMessage() {
    return msg;
  }
}
