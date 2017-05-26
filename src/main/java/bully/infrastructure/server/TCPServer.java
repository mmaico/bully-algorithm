package bully.infrastructure.server;


import bully.domain.model.comunication.Request;
import bully.domain.model.comunication.Response;
import bully.domain.service.ReceivedMessagesService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TCPServer {

  private static final Map<Response.Status, String> statusResponse = new HashMap<>();

  static {
    statusResponse.put(Response.Status.OK, "HTTP/1.1 200 OK\r\n");
    statusResponse.put(Response.Status.BAD_REQUEST, "HTTP/1.1 400 Bad Request\r\n");
    statusResponse.put(Response.Status.INTERNAL_ERROR, "HTTP/1.1 500 Internal Server Error\r\n");
  }

  private final ReceivedMessagesService listener;

  public TCPServer(ReceivedMessagesService listener) {
    this.listener = listener;
  }

  public void initServer(int port) {
    new Thread(() -> {
      try {
        final ServerSocket socketServer = new ServerSocket(port);
        while(true) {
          final Socket connectionSocket = socketServer.accept();
          final BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
          final DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
          final String body = inFromClient.readLine();
          final Response response = listener.messageReceived(new Request(body));
          outToClient.writeBytes(statusResponse.get(response.getStatus()));
          outToClient.writeBytes("Date: "+new Date()+"\r\n");
          outToClient.writeBytes("Content-Length: "+ response.getContent().length() + 1 +"\r\n");

          outToClient.writeBytes("Server: Bully Algorithm Server v1.0\r\n");
          outToClient.writeBytes("Content-Type: text/html; charset=UTF-8\r\n");

          outToClient.writeBytes(response.getContent());

          outToClient.close();
        }
      } catch (Exception e) {
        System.out.println(e);
      }
    }).start();

  }

}
