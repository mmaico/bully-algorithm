package bully.domain.model.comunication;


import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;

public class Request {

    public static final String MESSAGE_TYPE = "message-type";
    public static final String ORIGIN = "origin";
    private final Map<String, String> headers;
    private final String body;

    public enum RequestEnum {
        IM_A_CANDIDATE("im-a-candidate"),
        NEW_LEADER("new-leader"),
        COMMAND_TO_EXECUTE("follower-command-to-execute"),
        IS_ALIVE("is-alive"),
        KILL("kill");

        private String value;

        RequestEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static RequestEnum getBy(String value) {
            return stream(values()).filter(item -> item.value.equalsIgnoreCase(value)).findFirst().get();
        }
    }

    public Request(String body, Map<String, String> headers) {
        this.body = body;
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

}
