package com.stepan.verticles;

import com.stepan.common.ContentType;
import com.stepan.common.XmlConverter;
import com.stepan.dto.Receipt;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.json.Json;

import java.util.Date;
import java.util.Random;

/**
 * Created by stepanm on 18.1.2016.
 */
public class ClientVerticle extends AbstractVerticle {

    private HttpClientOptions options = null;
    private HttpClient client = null;

    @Override
    public void start() throws Exception {
        options = new HttpClientOptions();
        options.setDefaultHost("localhost").setDefaultPort(config().getInteger("http.port",8080)).setKeepAlive(true);
        client = vertx.createHttpClient(options);

        vertx.setPeriodic(2000, event -> {
            System.out.println("****NEW REQUEST****");
            HttpClientRequest req = client.post("/", response -> {
                System.out.println("[CLIENT] Received response with status code: " + response.statusCode());
                response.bodyHandler(body -> {
                    System.out.println("[CLIENT] Received a response body: " + body);
                });
            });

            req.putHeader("user-agent", "IDEA Vertx client");


            Receipt generatedReceipt = generateJsonOrXmlObject();
            if (generatedReceipt.getContentType().equals(ContentType.JSON)){
                req.putHeader("content-type", "application/json");
            } else if (generatedReceipt.getContentType().equals(ContentType.XML)){
                req.putHeader("content-type", "xml");
            }
            String encodedReceipt = encodeReceipt(generatedReceipt);

            if (encodedReceipt != null){
                req.putHeader("content-length", Integer.toString(encodedReceipt.length()));
                req.write(encodedReceipt).end();
            }
        });

    }

    private String encodeReceipt(Receipt receipt){
        if (receipt.getContentType().equals(ContentType.JSON)){
            return Json.encode(receipt);
        } else if (receipt.getContentType().equals(ContentType.XML)){
            Receipt receiptXML = new Receipt();
            receiptXML.setId(receipt.getId());
            receiptXML.setDate(receipt.getDate());
            receiptXML.setContentType(receipt.getContentType());
            receiptXML.setData(receipt.getData());
            return XmlConverter.objectToXml(receiptXML, Receipt.class);
        } else {
            return null;
        }
    }
    private Receipt generateJsonOrXmlObject(){
        Receipt receipt = generateReceipt();
        Random random = new Random();
        boolean generatedValue = random.nextBoolean();
        if (generatedValue){
            receipt.setContentType(ContentType.JSON);
        } else {
            receipt.setContentType(ContentType.XML);
        }
        return receipt;
    }

    private Receipt generateReceipt() {
        Receipt generatedReceipt = new Receipt();
        Random random = new Random();
        Date date = new Date();
        String dataMessage = "This is body of receipt";
        byte[] data = new byte[dataMessage.length()];

        generatedReceipt.setId(random.nextLong());
        date.setTime(random.nextLong());
        generatedReceipt.setDate(date);
        generatedReceipt.setData(dataMessage.getBytes());
        return generatedReceipt;
    }
}
