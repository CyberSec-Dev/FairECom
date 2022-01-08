package msg;

import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

public class SignVendorMsg extends Message{
    private static final long serialVersionUID = 6455375473616086653L;
    private ArrayList<byte[]> msg;
    private ArrayList<byte[]> signClient;
    private RSAPublicKey publicKeyClient;
    private ArrayList<byte[]> signVendor1;
    private ArrayList<byte[]> signVendor2;
    private RSAPublicKey publicKeyVendor;

    public SignVendorMsg(ArrayList<byte[]> msg, ArrayList<byte[]> signClient, RSAPublicKey publicKeyClient, ArrayList<byte[]> signVendor1, ArrayList<byte[]> signVendor2, RSAPublicKey publicKeyVendor) {
        this.msg = msg;
        this.signClient = signClient;
        this.publicKeyClient = publicKeyClient;
        this.signVendor1 = signVendor1;
        this.signVendor2 = signVendor2;
        this.publicKeyVendor = publicKeyVendor;
    }

    public ArrayList<byte[]> getMsg() {
        return msg;
    }

    public void setMsg(ArrayList<byte[]> msg) {
        this.msg = msg;
    }

    public ArrayList<byte[]> getSignClient() {
        return signClient;
    }

    public void setSignClient(ArrayList<byte[]> signClient) {
        this.signClient = signClient;
    }

    public RSAPublicKey getPublicKeyClient() {
        return publicKeyClient;
    }

    public void setPublicKeyClient(RSAPublicKey publicKeyClient) {
        this.publicKeyClient = publicKeyClient;
    }

    public ArrayList<byte[]> getSignVendor1() {
        return signVendor1;
    }

    public void setSignVendor1(ArrayList<byte[]> signVendor1) {
        this.signVendor1 = signVendor1;
    }

    public ArrayList<byte[]> getSignVendor2() {
        return signVendor2;
    }

    public void setSignVendor2(ArrayList<byte[]> signVendor2) {
        this.signVendor2 = signVendor2;
    }

    public RSAPublicKey getPublicKeyVendor() {
        return publicKeyVendor;
    }

    public void setPublicKeyVendor(RSAPublicKey publicKeyVendor) {
        this.publicKeyVendor = publicKeyVendor;
    }
}
