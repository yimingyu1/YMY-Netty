package cn.mingyu.netty.example.protocoltcp;

/**
 * @author yimingyu
 * @date 2022/01/26
 */
public class ProtocolMessage {
    private int len;
    private byte[] bytes;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
