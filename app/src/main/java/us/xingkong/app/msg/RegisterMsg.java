package us.xingkong.app.msg;

public class RegisterMsg {
    final public String account;
    final public String password;
    //final public String repassword;

    public RegisterMsg(String account, String password/*, String repassword*/) {
        this.account = account;
        this.password = password;
        //this.repassword = repassword;
    }
}
