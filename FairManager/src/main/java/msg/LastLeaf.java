package msg;

public class LastLeaf {
    private byte[] hash;
    private int pos;

    public LastLeaf(byte[] hash, int pos) {
        this.hash = hash;
        this.pos = pos;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
