package com.coinbase.exchange.api.websocketfeed;

import com.coinbase.exchange.api.config.GdaxConfiguration;
import com.coinbase.exchange.api.exchange.Signature;
import com.coinbase.exchange.api.websocketfeed.message.HeartbeatMessage;
import com.coinbase.exchange.api.websocketfeed.message.Message;
import com.coinbase.exchange.api.websocketfeed.message.Subscribe;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

//TODO test
public class WebsocketFeed extends WebSocketClient {

    private static Logger LOG = LoggerFactory.getLogger(WebsocketFeed.class);

    private Signature signature;
    private String websocketUrl;
    private String passphrase;
    private String key;

    private ObjectMapper jsonMapper;

    private FluxSink<Message> observable;

    public WebsocketFeed(GdaxConfiguration config) {
        super(config.websocketUri());

        this.key = config.key();
        this.passphrase = config.passphrase();
        this.websocketUrl = config.websocketUrl();
        this.signature = config.signature();
        this.jsonMapper = new ObjectMapper();
    }

    public Flux<Message> subscribe(Subscribe subscribe) {
        if (!isOpen()) {
            this.connect();
        }

        send(signObject(subscribe));

        //TODO
        return null;
    }

    protected String signObject(Subscribe subscribeReq) {
        try {
            String subscribeJson = jsonMapper.writeValueAsString(subscribeReq);
            String timestamp = Instant.now().getEpochSecond() + "";
            String sign = signature.generate("", "GET", subscribeJson, timestamp);
            SignedSubscribeMessage signedMessage = new SignedSubscribeMessage(
                    subscribeReq.getProduct_ids(), subscribeReq.getChannels(), sign, passphrase, timestamp, key);
            return jsonMapper.writeValueAsString(signedMessage);
        } catch (Exception e) {
           LOG.error("error while signing subscribe request", e);
           throw new RuntimeException(e);
        }
    }

    protected  <T> T getObject(String json, TypeReference<T> type) {
        try {
            return jsonMapper.readValue(json, type);
        } catch (IOException e) {
            LOG.error("error while parsing json to {} , message: {}.", type.getType().getTypeName(), json);
            throw new RuntimeException(e);
        }
    }

    protected <T> T getObject(String json, Class<T> type) {
        try {
            return jsonMapper.readValue(json, type);
        } catch (IOException e) {
            LOG.error("error while parsing json to {} , message: {}.", type.getName(), json);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onMessage(String message) {
        Map<String, String > map = getObject(message, new TypeReference< Map<String, String>>(){});
        String type = map.get("type");
        if (type == null) {
            LOG.error("\"type\" is expected in every gdax message. skipping... : {}", message);
            return;
        }
        Message m = null;
        if (type.equals("heartbeat")) {
            m = getObject(message, HeartbeatMessage.class);
        } else if (type.equals("ticker")) {

        } else if (type.equals("snapshot")) {

        } else if (type.equals("l2update")) {

        } else if (type.equals("received")) {

        } else if (type.equals("open")) {

        } else if (type.equals("done")) {

        } else if (type.equals("match")) {

        } else if (type.equals("change")) {

        } else if (type.equals("margin_profile_update")) {

        } else if (type.equals("activate")) {

        }
        if (m != null) {
            observable.next(m);
        }
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        LOG.info("Websocket connection opened.");
    }

    @Override
    public void onClose(int code, String reason, boolean byRemote) {
        LOG.warn("websocket closed. CODE={}, REASON={}, BY_REMOTE={}", code, reason, byRemote);
    }

    @Override
    public void onError(Exception e) {
        LOG.error("Error in websocket feed", e);
    }
}

class SignedSubscribeMessage extends Subscribe {

    String signature;
    String passphrase;
    String timestamp;
    String apiKey;

    public SignedSubscribeMessage(String[] product_ids, String[] channels, String signature, String passphrase, String timestamp, String apiKey) {
        super(product_ids, channels);
        this.signature = signature;
        this.passphrase = passphrase;
        this.timestamp = timestamp;
        this.apiKey = apiKey;
    }

    @JsonGetter
    public String signature() {
        return signature;
    }

    public SignedSubscribeMessage setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    @JsonGetter
    public String passphrase() {
        return passphrase;
    }

    public SignedSubscribeMessage setPassphrase(String passphrase) {
        this.passphrase = passphrase;
        return this;
    }

    @JsonGetter
    public String timestamp() {
        return timestamp;
    }

    public SignedSubscribeMessage setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @JsonGetter
    public String apiKey() {
        return apiKey;
    }

    public SignedSubscribeMessage setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }
}
