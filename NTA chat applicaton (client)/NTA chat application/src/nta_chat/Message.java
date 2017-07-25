package nta_chat;

/**
 * Created by massina on 24/07/2017.
 */
public class Message {
    private String msg;
    private String Idsender;
    private String Idreceiver;
    private String date;

    @Override
    public String toString() {
        return "Message{" +
                "msg='" + msg + '\'' +
                ", Idsender='" + Idsender + '\'' +
                ", Idreceiver='" + Idreceiver + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIdsender() {
        return Idsender;
    }

    public void setIdsender(String idsender) {
        Idsender = idsender;
    }

    public String getIdreceiver() {
        return Idreceiver;
    }

    public void setIdreceiver(String idreceiver) {
        Idreceiver = idreceiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
