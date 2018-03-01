package com.coinbase.exchange.api.websocketfeed;

import com.coinbase.exchange.api.config.GdaxConfiguration;
import com.coinbase.exchange.api.exchange.Signature;
import com.coinbase.exchange.api.websocketfeed.message.HeartbeatMessage;
import com.coinbase.exchange.api.websocketfeed.message.Message;
import com.coinbase.exchange.api.websocketfeed.message.Subscribe;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Map;

public class WebsocketFeed {

    private static Logger LOG = LoggerFactory.getLogger(WebsocketFeed.class);

    private WebSocketClient client;
    private WebSocketSession session;

    private Signature signature;
    private String websocketUrl;
    private String passphrase;
    private String key;

    private ObjectMapper jsonMapper;

    private FluxSink<Message> observable;

    public WebsocketFeed(GdaxConfiguration config) {
        client = new ReactorNettyWebSocketClient();

        this.key = config.key();
        this.passphrase = config.passphrase();
        this.websocketUrl = config.websocketUrl();
        this.signature = config.signature();
        this.jsonMapper = new ObjectMapper();
    }

    public Flux<Message> subscribe(Subscribe subscribe) {
        connect();
        session.send(Mono.just(session.textMessage(signObject(subscribe))))
                .doOnError(e -> LOG.error("error while sending subscription message", e))
                .subscribe();

        return session.receive()
                .map(message -> convertMessage(message.getPayloadAsText()))
                .doOnError(throwable -> LOG.error("error on receiving messages", throwable));
    }

    private void connect() {
        client.execute(uri(websocketUrl), session -> {
            LOG.info("websocket connected {}", websocketUrl);
            this.session = session;
            return Mono.empty();
        }).doOnError(e -> {
            LOG.error("Error while connecting to websocket url {}", websocketUrl, e);
        }).block();
    }

    private URI uri(String websocketUrl) {
        try {
            return new URI(websocketUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
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

    protected Message convertMessage(String message) {
        Map<String, String > map = getObject(message, new TypeReference< Map<String, String>>(){});
        String type = map.get("type");
        if (type == null) {
            LOG.error("\"type\" is expected in every gdax message. skipping... : {}", message);
            return null;
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
        return m;
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
