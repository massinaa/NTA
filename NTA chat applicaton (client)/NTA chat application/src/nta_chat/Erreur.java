package nta_chat;

/**
 * Created by massina on 10/07/2017.
 */
public class Erreur {
    private String param;
    private String msg;
    private String value;

    @Override
    public String toString() {
        return "Erreur{" +
                "param='" + param + '\'' +
                ", msg='" + msg + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
