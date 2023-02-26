package dev.teamaggro;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.httpproxy.Body;
import io.vertx.httpproxy.ProxyRequest;
import io.vertx.httpproxy.ProxyResponse;

import java.util.concurrent.TimeUnit;

public class Common {

    public static void proxy(final HttpServerRequest req) {
        final String host = req.getHeader("host").toLowerCase();
        Backend b = new Backend("HTTP", "example.com", 80);
        ProxyRequest proxyRequest = ProxyRequest.reverseProxy(req);
        (b.getProtocol().equals("HTTPS") ? Reproducer.vertx.createHttpClient(new HttpClientOptions().setTrustAll(true).setVerifyHost(false).setSsl(true).setKeepAlive(false).setMaxRedirects(0).setIdleTimeout(100000).setConnectTimeout(15000).setIdleTimeoutUnit(TimeUnit.MILLISECONDS)) : Reproducer.vertx.createHttpClient(new HttpClientOptions().setKeepAlive(false).setMaxRedirects(0).setIdleTimeout(100000).setConnectTimeout(15000).setIdleTimeoutUnit(TimeUnit.MILLISECONDS))).request(proxyRequest.getMethod(), b.getPort(), b.getIp(), req.uri()).compose(request -> {
            try {
                if (proxyRequest.response().getBody() == null) { // Fixes a nullpointer exception
                    proxyRequest.response().setBody(Body.body(Buffer.buffer("")));
                }
                return proxyRequest.send(request);
            } catch (Exception e) {
                proxyRequest.release();
                try {
                    req.response().setStatusCode(500).end("Currently unable to connect to " + host + ", please try again later.");
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
                e.printStackTrace();
            }
            return null;
        }).onSuccess(ProxyResponse::send).onFailure(err -> {
            try {
                proxyRequest.release();
                req.response().setStatusCode(500).end("Currently unable to connect to " + host + ", please try again later.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
