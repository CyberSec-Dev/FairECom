package msg;

import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

public class SignBankMsg extends Message {

    /**
     *
     */
    private static final long serialVersionUID = 236835547091180416L;
    private ArrayList<byte[]> msg;
    private ArrayList<byte[]> signClient;
    //private RSAPublicKey publicKeyClient;
    private ArrayList<byte[]> signVendor1;
    private ArrayList<byte[]> signVendor2;
    //private RSAPublicKey publicKeyVendor;
    private ArrayList<byte[]> signBank1;
    private ArrayList<byte[]> signBank2;
    //private RSAPublicKey publicKeyBank;

    public SignBankMsg(ArrayList<byte[]> msg, ArrayList<byte[]> signClient, ArrayList<byte[]> signVendor1, ArrayList<byte[]> signVendor2, ArrayList<byte[]> signBank1, ArrayList<byte[]> signBank2) {
        this.msg = msg;
        this.signClient = signClient;
        this.signVendor1 = signVendor1;
        this.signVendor2 = signVendor2;
        this.signBank1 = signBank1;
        this.signBank2 = signBank2;
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

    public ArrayList<byte[]> getSignBank1() {
        return signBank1;
    }

    public void setSignBank1(ArrayList<byte[]> signBank1) {
        this.signBank1 = signBank1;
    }

    public ArrayList<byte[]> getSignBank2() {
        return signBank2;
    }

    public void setSignBank2(ArrayList<byte[]> signBank2) {
        this.signBank2 = signBank2;
    }
}