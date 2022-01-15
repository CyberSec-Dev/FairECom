package msg;

import java.io.Serializable;

public abstract class Message implements Serializable {

    private static final long serialVersionUID = 7255290532066692850L;
    private boolean keepConnection = false;
    public int seqId = 0;

    public void setKeepConnection(boolean keepConnection) {
        this.keepConnection = keepConnection;
    }

    public boolean keepConnection() {
        return keepConnection;
    }
}
