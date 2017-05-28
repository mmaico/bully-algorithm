package bully.infrastructure.server;

import bully.domain.model.comunication.Response;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Class for HTTP request parsing as defined by RFC 2612:
 *
 * Request = Request-Line ; Section 5.1 (( general-header ; Section 4.5 |
 * request-header ; Section 5.3 | entity-header ) CRLF) ; Section 7.1 CRLF [
 * message-body ] ; Section 4.3
 *
 * @author izelaya
 *
 */
public class HttpHelper {

    private static final Map<Response.Status, String> statusResponse = new HashMap<>();

    static {
        statusResponse.put(Response.Status.OK, "HTTP/1.1 200 OK\r\n");
        statusResponse.put(Response.Status.BAD_REQUEST, "HTTP/1.1 400 Bad Request\r\n");
        statusResponse.put(Response.Status.INTERNAL_ERROR, "HTTP/1.1 500 Internal Server Error\r\n");
    }
    private String _requestLine;
    private final Hashtable<String, String> _requestHeaders;
    private final StringBuffer _messagetBody;
    private final BufferedReader request;

    public HttpHelper(BufferedReader request) {
        _requestHeaders = new Hashtable<>();
        _messagetBody = new StringBuffer();
        this.request = request;
        try {
            parseRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse and HTTP request.
     *
     * @param request
     *            String holding http request.
     * @throws IOException
     *             If an I/O error occurs reading the input stream.
     */
    public void parseRequest() throws IOException {
        BufferedReader reader = request;
        setRequestLine(reader.readLine()); // Request-Line ; Section 5.1

        String header = reader.readLine();
        while (header.length() > 0) {
            appendHeaderParameter(header);
            header = reader.readLine();
        }

        String bodyLine = reader.readLine();
        while (bodyLine != null) {
            appendMessageBody(bodyLine);
            bodyLine = reader.readLine();
        }

    }

    /**
     *
     * 5.1 Request-Line The Request-Line begins with a method token, followed by
     * the Request-URI and the protocol version, and ending with CRLF. The
     * elements are separated by SP characters. No CR or LF is allowed except in
     * the final CRLF sequence.
     *
     * @return String with Request-Line
     */
    public String getRequestLine() {
        return _requestLine;
    }

    private void setRequestLine(String requestLine)  {
        if (requestLine == null || requestLine.length() == 0) {
            throw new RuntimeException("Invalid Request-Line: " + requestLine);
        }
        _requestLine = requestLine;
    }

    private void appendHeaderParameter(String header) {
        int idx = header.indexOf(":");
        if (idx == -1) {
            throw new RuntimeException("Invalid Header Parameter: " + header);
        }
        _requestHeaders.put(header.substring(0, idx), header.substring(idx + 1, header.length()));
    }

    public void writeResponse(Response response, DataOutputStream outToClient) throws IOException {

        outToClient.writeBytes(statusResponse.get(response.getStatus()));
        outToClient.writeBytes("Date: "+new Date()+"\r\n");
        outToClient.writeBytes("Content-Length: "+ response.getContent().length() + 1 +"\r\n");

        outToClient.writeBytes("Server: Bully Algorithm Server v1.0\r\n");
        outToClient.writeBytes("Content-Type: text/html; charset=UTF-8\r\n");

        outToClient.writeBytes(response.getContent());

    }

    /**
     * The message-body (if any) of an HTTP message is used to carry the
     * entity-body associated with the request or response. The message-body
     * differs from the entity-body only when a transfer-coding has been
     * applied, as indicated by the Transfer-Encoding header field (section
     * 14.41).
     * @return String with message-body
     */
    public String getMessageBody() {
        return _messagetBody.toString();
    }

    public Hashtable<String, String> getHeaders() {
        return _requestHeaders;
    }

    private void appendMessageBody(String bodyLine) {
        _messagetBody.append(bodyLine).append("\r\n");
    }

    /**
     * For list of available headers refer to sections: 4.5, 5.3, 7.1 of RFC 2616
     * @param headerName Name of header
     * @return String with the value of the header or null if not found.
     */
    public String getHeaderParam(String headerName){
        return _requestHeaders.get(headerName);
    }

}
