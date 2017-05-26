package bully.domain.model.comunication;


import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkAddress {

  public static String getIp() {
    try {
      InetAddress localHost = InetAddress.getLocalHost();
      return localHost.getHostAddress();
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
  }
}
