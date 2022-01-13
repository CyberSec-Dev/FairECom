package msg;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSAKey1 extends  Message{
    private static final long serialVersionUID= 7657978693460991941L;
    private String  username;
    private RSAPrivateKey privateKey ;
    private RSAPublicKey publicKey;

    public RSAKey1(String username, RSAPrivateKey privateKey, RSAPublicKey publicKey) {
        this.username = username;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public RSAKey1(String username, RSAPublicKey publicKey) {
        this.username = username;
        this.publicKey = publicKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
