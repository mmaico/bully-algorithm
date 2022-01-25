package bully.infrastructure.server;


import bully.domain.model.comunication.Request;
import bully.domain.model.comunication.Response;
import bully.domain.service.ReceivedMessagesService;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CustomHTTPServer {



  private final ReceivedMessagesService listener;

  public CustomHTTPServer(ReceivedMessagesService listener) {
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
                String responseJson = new GsonBuilder().create().toJson(response);
                httpExchange.sendResponseHeaders(response.getStatus().getCode(), responseJson.getBytes().length);

                response.getHeaders().forEach((key, value) -> httpExchange.getResponseHeaders().add(key.toLowerCase(), value));

                responseBody.write(responseJson.getBytes(StandardCharsets.UTF_8));

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

}
