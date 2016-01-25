package com.stepan.common;

import com.stepan.dto.Receipt;
import com.stepan.dto.Request;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.Json;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by stepanm on 19.1.2016.
 */
public class CommandHandler {
    private final HttpServerRequest request;
    private final Request req;
    private Map<Long, Receipt> persistedReceipts = new HashMap<>();
    private final String outputMessage = "Object persisted.";

    public CommandHandler(HttpServerRequest request, Request req) {
        this.request = request;
        this.req = req;
    }

    public HttpServerRequest handleCommand(Handler<Request> callBack) {
        request.bodyHandler(handleBody -> {
            System.out.println("[SERVER] Received body: " + handleBody);
            req.setBody(handleBody.getBytes());
            Receipt receipt = new Receipt();
            if (req.getHeaders().get("content-type").equals("application/json")) {
                receipt = Json.decodeValue(handleBody.getString(0, handleBody.length()), Receipt.class);
                req.setSuccessfullyParsed(true);
                callBack.handle(req);
            } else if (req.getHeaders().get("content-type").equals("xml")) {
                Receipt receiptXML = (Receipt) XmlConverter.xmlToObject(handleBody.getString(0, handleBody.length()), Receipt.class);
                receipt.setId(receiptXML.getId());
                receipt.setDate(receiptXML.getDate());
                receipt.setContentType(receiptXML.getContentType());
                receipt.setData(receiptXML.getData());
                req.setSuccessfullyParsed(true);
                callBack.handle(req);

            } else {
                System.out.println("[SERVER] Received object with unknown content type.");
                req.setSuccessfullyParsed(false);
                callBack.handle(req);
            }

            System.out.println("[SERVER] Deserialized receipt");
            System.out.println("[SERVER] id: " + receipt.getId());
            System.out.println("[SERVER] date: " + receipt.getDate());
            if (receipt.getData() != null) {
                System.out.println("[SERVER] data: " + new String(receipt.getData()));
            }

            persistedReceipts.put(receipt.getId(), receipt);
            request.response().headers().set("content-length", Integer.toString(outputMessage.length()));
            request.response().setStatusCode(201);
            request.response().end(outputMessage);
            System.out.println("[SERVER] Persisted objects so far: " + persistedReceipts.size());
            if (persistedReceipts.size() % 10 == 0) {
                System.out.println("Test print after 10 objects persisted");
                System.out.println("-------------------------------------");
                for (Map.Entry<Long, Receipt> row : persistedReceipts.entrySet()) {
                    System.out.println("Receipt id: " + row.getKey() + " date: " + row.getValue().getDate() + " contentType: " + row.getValue().getContentType() + " data: " + new String(row.getValue().getData()));
                }
            }

        });
        return request;
    }
}
