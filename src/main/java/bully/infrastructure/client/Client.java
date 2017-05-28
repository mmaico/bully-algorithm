package bully.infrastructure.client;


import bully.domain.model.comunication.Request;
import bully.domain.model.machine.Machine;
import bully.domain.service.language.To;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.util.Optional;

import static bully.domain.model.comunication.Request.MESSAGE_TYPE;
import static bully.domain.model.comunication.Request.ORIGIN;

public class Client {

    private static final String IP = "{ip}";
    private static final String MACHINE_ID = "{id}";
    private static final String PORT = "{port}";
    private static final String HTTP_SCHEMA = "http://{ip}:{port}/";
    private static final String BODY_SCHEMA ="{\"id\":\"{id}\"}";


    public static Optional<HttpResponse> post(Machine from, To to, Request.RequestEnum requestEnum) {
        String url = HTTP_SCHEMA.replace(IP, to.getMachine().getIp())
                .replace(PORT, String.valueOf(to.getMachine().getPort()));

        try {
            HttpResponse httpResponse = org.apache.http.client.fluent.Request.Get(url)
                .addHeader(MESSAGE_TYPE, requestEnum.getValue())
                .addHeader(ORIGIN, BODY_SCHEMA.replace(MACHINE_ID, from.getId()))
                .connectTimeout(5000)
                .socketTimeout(5000)
                .execute().returnResponse();

            return Optional.of(httpResponse);
        } catch (IOException e) {
            System.out.println("Error Connection: " + e + " url: " + url +" From: "+ from.getId() + " to: " + to.getMachine().getAlias());
            return Optional.empty();
        }
    }
}
