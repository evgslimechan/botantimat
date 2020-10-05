package ru.mgkit.antimat.server;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import ru.mgkit.antimat.word.BadwordManager;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class Server {

    private ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
    private final HttpServer server;

    public Server(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);
        server.createContext("/", new MyHttpHandler());
        server.setExecutor(threadPoolExecutor);
    }


    public void startServer() {
        server.start();
        System.out.println("ok");
    }

    public void stopServer() {
        server.stop(3000);
    }


    private class MyHttpHandler implements HttpHandler {


        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String prevjson = new BufferedReader(
                    new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            JSONObject jo = new JSONObject(prevjson);
            handleResponse(httpExchange, jo);
        }


        public void reqHandler(final JSONObject jo) {
            threadPoolExecutor.execute(() -> {
                String type = jo.getString("type");
                switch (type) {
                    case ("message_new"):
                        String mess = jo.getJSONObject("object").getJSONObject("message").getString("text");
                        int conv_mess_id = jo.getJSONObject("object").getJSONObject("message").getInt("conversation_message_id");
                        String peer_id = "" + jo.getJSONObject("object").getJSONObject("message").getInt("peer_id");
                        String chat_id = peer_id.substring(peer_id.lastIndexOf('0'));
                        int id = jo.getJSONObject("object").getJSONObject("message").getInt("from_id");

                        System.out.println();
                        System.out.println(id);
                        if (BadwordManager.Instance.isBad(mess)) {
                            System.out.println(" ");

                            HttpPost httppost = new HttpPost("https://api.vk.com/method/execute");

                            Map<String, String> nameValuePairs = new HashMap<>();

                            nameValuePairs.put("code", "return API.messages.delete({delete_for_all: 1, " +
                                    "message_ids: API.messages.getByConversationMessageId({" +
                                    "peer_id: " + peer_id + ", " +
                                    "conversation_message_ids: " + conv_mess_id + "}).items@.id});");
                            nameValuePairs.put("access_token", "b812149a91dab2c56cc7b0051a2a5693c46a9414c64c9ddaf146198561f76ecb5e2c17e650b375a703ed8");
                            nameValuePairs.put("v", "5.100");


                            System.out.println(nameValuePairs.get("code"));
                            System.out.println(post("https://api.vk.com/method/execute", nameValuePairs));
                        }

                        break;
                }
            });
        }


        private void handleResponse(HttpExchange httpExchange, JSONObject jo) throws IOException {
            OutputStream outputStream = httpExchange.getResponseBody();
            StringBuilder htmlBuilder = new StringBuilder();

            System.out.println(jo);

            String responce = "";

            if (jo.getString("type").equals("confirmation")) {
                responce = "dcb4dec6";
            } else {
                responce = "ok";
                reqHandler(jo);
            }


            httpExchange.sendResponseHeaders(200, responce.length());
            outputStream.write(responce.getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }


    public static String get(String url) {
        return CompletableFuture.supplyAsync(() -> {
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpGet get = new HttpGet(url);
                HttpResponse response = client.execute(get);
                return EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }).join();
    }

    public static String get(String url, Map<String, String> args) {
        return CompletableFuture.supplyAsync(() -> {
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpGet get = new HttpGet(url + "?" + args.entrySet().parallelStream()
                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                        .collect(Collectors.joining("&"))
                );
                HttpResponse response = client.execute(get);
                return EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }).join();
    }

    public static String post(String url) {
        return CompletableFuture.supplyAsync(() -> {
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpPost post = new HttpPost(url);
                HttpResponse response = client.execute(post);
                return EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }).join();
    }

    public static String post(String url, Map<String, String> args) {
        return CompletableFuture.supplyAsync(() -> {
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpPost post = new HttpPost(url);
                post.setEntity(new UrlEncodedFormEntity(args.entrySet().parallelStream()
                        .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList()), "UTF-8"));
                HttpResponse response = client.execute(post);
                return EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }).join();
    }
}
