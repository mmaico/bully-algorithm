package bully.infrastructure.server;


import bully.domain.model.comunication.Request;
import bully.domain.model.comunication.Response;
import bully.domain.service.ReceivedMessagesService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import sun.misc.IOUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TCPServer {



  private final ReceivedMessagesService listener;

  public TCPServer(ReceivedMessagesService listener) {
    this.listener = listener;
  }

  public void initServer(int port) {
      try {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        httpServer.createContext("/", (httpExchange) -> {
          new Thread(() -> {
            try {
              final OutputStream responseBody = httpExchange.getResponseBody();
              try {
                final Map<String, String> headers = new HashMap<>();
                httpExchange.getRequestHeaders().entrySet().forEach(entry ->
                        headers.put(entry.getKey().toLowerCase(), entry.getValue().get(0))
                );

                final Response response = this.listener.messageReceived(new Request("", headers));
                byte[] responseBytes = "OK".getBytes();

                httpExchange.sendResponseHeaders(response.getStatus().getCode(), responseBytes.length);

                response.getHeaders().forEach((key, value) -> httpExchange.getResponseHeaders().add(key.toLowerCase(), value));
                responseBody.write(responseBytes);

              } finally {
                responseBody.close();
                httpExchange.close();
              }
            } catch (IOException e) {
              System.out.println("THREAD Error==== " + e);
            }
          }).start();
      });
      httpServer.start();
    } catch (Exception e) {
      System.out.println("Error==== " + e);
    }

  }

  public static void main(String[] args) {
    TCPServer tcpServer = new TCPServer(new ReceivedMessagesService() {
      @Override
      public Response messageReceived(Request request) {
        System.out.println("Server listerner");
        return Response.waitingElectionResult();
      }
    });

    tcpServer.initServer(5555);
  }

}
