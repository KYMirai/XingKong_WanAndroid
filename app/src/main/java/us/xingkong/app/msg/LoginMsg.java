package us.xingkong.app.msg;

public class LoginMsg {
    final public String account;
    final public String password;

    public LoginMsg(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
