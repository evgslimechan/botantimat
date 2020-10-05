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
    HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 80), 0);
    String[] mat = {"6ля", "6лядь", "6лять", "b3ъeб", "cock", "cunt", "e6aль", "ebal", "eblan", "eбaл", "eбaть", "eбyч", "eбать", "eбёт", "eблантий", "fuck", "fucker", "fucking", "xyёв", "xyй", "xyя", "xуе", "хуй", "xую", "zaeb", "zaebal", "zaebali", "zaebat", "архипиздрит", "ахуел", "ахуеть", "бздение", "бздеть", "бздех", "бздецы", "бздит", "бздицы", "бздло", "бзднуть", "бздун", "бздунья", "бздюха", "бздюшка", "бздюшко", "бля", "блябу", "блябуду", "бляд", "бляди", "блядина", "блядище", "блядки", "блядовать", "блядство", "блядун", "блядуны", "блядунья", "блядь", "блядюга", "блять", "вафел", "вафлёр", "взъебка", "взьебка", "взьебывать", "въеб", "въебался", "въебенн", "въебусь", "въебывать", "выблядок", "выблядыш", "выеб", "выебать", "выебен", "выебнулся", "выебон", "выебываться", "выпердеть", "высраться", "выссаться", "вьебен", "гавно", "гавнюк", "гавнючка", "гамно", "гандон", "гнид", "гнида", "гниды", "говенка", "говенный", "говешка", "говназия", "говнецо", "говнище", "говно", "говноед", "говнолинк", "говночист", "говнюк", "говнюха", "говнядина", "говняк", "говняный", "говнять", "гондон", "доебываться", "долбоеб", "долбоёб", "долбоящер", "дрисня", "дрист", "дристануть", "дристать", "дристун", "дристуха", "дрочелло", "дрочена", "дрочила", "дрочилка", "дрочистый", "дрочить", "дрочка", "дрочун", "е6ал", "е6ут", "еб твою мать", "ёб твою мать", "ёбaн", "ебaть", "ебyч", "ебал", "ебало", "ебальник", "ебан", "ебанамать", "ебанат", "ебаная", "ёбаная", "ебанический", "ебанный", "ебанныйврот", "ебаное", "ебануть", "ебануться", "ёбаную", "ебаный", "ебанько", "ебарь", "ебат", "ёбат", "ебатория", "ебать", "ебать-копать", "ебаться", "ебашить", "ебёна", "ебет", "ебёт", "ебец", "ебик", "ебин", "ебись", "ебическая", "ебки", "ебла", "еблан", "ебливый", "еблище", "ебло", "еблыст", "ебля", "ёбн", "ебнуть", "ебнуться", "ебня", "ебошить", "ебская", "ебский", "ебтвоюмать", "ебун", "ебут", "ебуч", "ебуче", "ебучее", "ебучий", "ебучим", "ебущ", "ебырь", "елда", "елдак", "елдачить", "жопа", "жопу", "заговнять", "задрачивать", "задристать", "задрота", "зае6", "заё6", "заеб", "заёб", "заеба", "заебал", "заебанец", "заебастая", "заебастый", "заебать", "заебаться", "заебашить", "заебистое", "заёбистое", "заебистые", "заёбистые", "заебистый", "заёбистый", "заебись", "заебошить", "заебываться", "залуп", "залупа", "залупаться", "залупить", "залупиться", "замудохаться", "запиздячить", "засерать", "засерун", "засеря", "засирать", "засрун", "захуячить", "заябестая", "злоеб", "злоебучая", "злоебучее", "злоебучий", "ибанамат", "ибонех", "изговнять", "изговняться", "изъебнуться", "ипать", "ипаться", "ипаццо", "Какдвапальцаобоссать", "конча", "курва", "курвятник", "лох", "лошарa", "лошара", "лошары", "лошок", "лярва", "малафья", "манда", "мандавошек", "мандавошка", "мандавошки", "мандей", "мандень", "мандеть", "мандища", "мандой", "манду", "мандюк", "минет", "минетчик", "минетчица", "млять", "мокрощелка", "мокрощёлка", "мразь", "мудak", "мудaк", "мудаг", "мудак", "муде", "мудель", "мудеть", "муди", "мудил", "мудила", "мудистый", "мудня", "мудоеб", "мудозвон", "мудоклюй", "на хер", "на хуй", "набздел", "набздеть", "наговнять", "надристать", "надрочить", "наебать", "наебет", "наебнуть", "наебнуться", "наебывать", "напиздел", "напиздели", "напиздело", "напиздили", "пизди", "насрать", "настопиздить", "нахер", "нахрен", "нахуй", "нахуйник", "не ебет", "не ебёт", "невротебучий", "невъебенно", "нехира", "нехрен", "Нехуй", "нехуйственно", "ниибацо", "ниипацца", "ниипаццо", "ниипет", "никуя", "нихера", "нихуя", "обдристаться", "обосранец", "обосрать", "обосцать", "обосцаться", "обсирать", "объебос", "обьебать обьебос", "однохуйственно", "опездал", "опизде", "опизденивающе", "остоебенить", "остопиздеть", "отмудохать", "отпиздить", "отпиздячить", "отпороть", "отъебись", "охуевательский", "охуевать", "охуевающий", "охуел", "охуенно", "охуеньчик", "охуеть", "охуительно", "охуительный", "охуяньчик", "охуячивать", "охуячить", "очкун", "падла", "падонки", "падонок", "паскуда", "педерас", "педик", "педрик", "педрила", "педрилло", "педрило", "педрилы", "пездень", "пездит", "пездишь", "пездо", "пездят", "пердануть", "пердеж", "пердение", "пердеть", "пердильник", "перднуть", "пёрднуть", "пердун", "пердунец", "пердунина", "пердунья", "пердуха", "пердь", "переёбок", "пернуть", "пёрнуть", "пи3д", "пи3де", "пи3ду", "пиzдец", "пидар", "пидарaс", "пидарас", "пидарасы", "пидары", "пидор", "пидорасы", "пидорка", "пидорок", "пидоры", "пидрас", "пизда", "пиздануть", "пиздануться", "пиздарваньчик", "пиздато", "пиздатое", "пиздатый", "пизденка", "пизденыш", "пиздёныш", "пиздеть", "пиздец", "пиздит", "пиздить", "пиздиться", "пиздишь", "пиздища", "пиздище", "пиздобол", "пиздоболы", "пиздобратия", "пиздоватая", "пиздоватый", "пиздолиз", "пиздонутые", "пиздорванец", "пиздорванка", "пиздострадатель", "пизду", "пиздуй", "пиздун", "пиздунья", "пизды", "пиздюга", "пиздюк", "пиздюлина", "пиздюля", "пиздят", "пиздячить", "писбшки", "писька", "писькострадатель", "писюн", "писюшка", "по хуй", "по хую", "подговнять", "подонки", "подонок", "подъебнуть", "подъебнуться", "поебать", "поебень", "поёбываает", "поскуда", "посрать", "потаскуха", "потаскушка", "похер", "похерил", "похерила", "похерили", "похеру", "похрен", "похрену", "похуй", "похуист", "похуистка", "похую", "придурок", "приебаться", "припиздень", "припизднутый", "припиздюлина", "пробзделся", "проблядь", "проеб", "проебанка", "проебать", "промандеть", "промудеть", "пропизделся", "пропиздеть", "пропиздячить", "раздолбай", "разхуячить", "разъеб", "разъеба", "разъебай", "разъебать", "распиздай", "распиздеться", "распиздяй", "распиздяйство", "распроеть", "сволота", "сволочь", "сговнять", "секель", "серун", "серька", "сестроеб", "сикель", "сила", "сирать", "сирывать", "соси", "спиздел", "спиздеть", "спиздил", "спиздила", "спиздили", "спиздит", "спиздить", "срака", "сраку", "сраный", "сранье", "срать", "срун", "ссака", "ссышь", "стерва", "страхопиздище", "сука", "суки", "суходрочка", "сучара", "сучий", "сучка", "сучко", "сучонок", "сучье", "сцание", "сцать", "сцука", "сцуки", "сцуконах", "сцуль", "сцыха", "сцышь", "съебаться", "сыкун", "трахае6", "трахаеб", "трахаёб", "трахатель", "ублюдок", "уебать", "уёбища", "уебище", "уёбище", "уебищное", "уёбищное", "уебк", "уебки", "уёбки", "уебок", "уёбок", "урюк", "усраться", "ушлепок", "х_у_я_р_а", "хyё", "хyй", "хyйня", "хамло", "хер", "херня", "херовато", "херовина", "херовый", "хитровыебанный", "хитрожопый", "хуeм", "хуе", "хуё", "хуевато", "хуёвенький", "хуевина", "хуево", "хуевый", "хуёвый", "хуек", "хуёк", "хуел", "хуем", "хуенч", "хуеныш", "хуенький", "хуеплет", "хуеплёт", "хуепромышленник", "хуерик", "хуерыло", "хуесос", "хуесоска", "хуета", "хуетень", "хуею", "хуи", "хуй", "хуйком", "хуйло", "хуйня", "хуйрик", "хуище", "хуля", "хую", "хуюл", "хуя", "хуяк", "хуякать", "хуякнуть", "хуяра", "хуясе", "хуячить", "целка", "чмо", "чмошник", "чмырь", "шалава", "шалавой", "шараёбиться", "шлюха", "шлюхой", "шлюшка", "ябывает"};



    Map<String, List<String>> matChange = new HashMap<>();


    public Server() throws IOException {
        server.createContext("/", new MyHttpHandler());
        server.setExecutor(threadPoolExecutor);
        matChange.put("а", Arrays.asList("а", "a", "@"));
        matChange.put("б", Arrays.asList("б", "6", "b"));
        matChange.put("в", Arrays.asList("в", "b", "v"));
        matChange.put("г", Arrays.asList("г", "r", "g"));
        matChange.put("д", Arrays.asList("д", "d", "g"));
        matChange.put("е", Arrays.asList("е", "e"));
        matChange.put("ё", Arrays.asList("ё", "е", "e"));
        matChange.put("ж", Arrays.asList("ж", "zh", "*"));
        matChange.put("з", Arrays.asList("з", "3", "z"));
        matChange.put("и", Arrays.asList("и", "u", "i"));
        matChange.put("й", Arrays.asList("й", "u", "y", "i"));
        matChange.put("к", Arrays.asList("к", "k", "i", "|"));
        matChange.put("л", Arrays.asList("л", "l", "ji"));
        matChange.put("м", Arrays.asList("м", "m"));
        matChange.put("н", Arrays.asList("н", "h", "n"));
        matChange.put("о", Arrays.asList("о", "o", "0"));
        matChange.put("п", Arrays.asList("п", "n", "p"));
        matChange.put("р", Arrays.asList("р", "r", "p"));
        matChange.put("с", Arrays.asList("с", "c", "s"));
        matChange.put("т", Arrays.asList("т", "m", "t"));
        matChange.put("у", Arrays.asList("у", "y", "u"));
        matChange.put("ф", Arrays.asList("ф", "f"));
        matChange.put("х", Arrays.asList("х", "x", "h", "к", "k", "}{"));
        matChange.put("ц", Arrays.asList("ц", "c", "u,"));
        matChange.put("ч", Arrays.asList("ч", "ch"));
        matChange.put("ш", Arrays.asList("ш", "sh"));
        matChange.put("щ", Arrays.asList("щ", "sch"));
        matChange.put("ь", Arrays.asList("ь", "b"));
        matChange.put("ы", Arrays.asList("ы", "bi"));
        matChange.put("ъ", Arrays.asList("ъ"));
        matChange.put("э", Arrays.asList("э", "е", "e"));
        matChange.put("ю", Arrays.asList("ю", "io"));
        matChange.put("я", Arrays.asList("я", "ya"));
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
                        String[] subMess = mess.split(" ");
                        //boolean t = false;
                        for (int i = 0; i < subMess.length; i++) {
                            String a = subMess[i];
                            System.out.println(a);
                            for (int j = 0; j < mat.length; j++) {
                                String a2 = mat[j];

                                if (a.equalsIgnoreCase(a2)) {



                                    JSONObject del = new JSONObject();

                                    del.put("message_ids", "API.messages.getByConversationMessageId({peer_id:" + peer_id + ", conversation_message_ids:" + conv_mess_id + "}).items@.id");
                                    //del.put("group_id", chat_id);
                                    del.put("delete_for_all", 1);


                                    System.out.println(del);



                                    //HttpGet httpget = new HttpGet("https://api.vk.com/method/execute?code=" + del
                                    //        + "&access_token=4a0ba548a055e91094b560b1a736843214921a088b07f00619d71fdb53e6c9409c9df613c1280dd815285&v=5.124");


                                    HttpPost httppost = new HttpPost("https://api.vk.com/method/execute");

                                    Map<String, String> NameValuePair = new HashMap<>();

                                    /*List<NameValuePair> urlParameters = new ArrayList<>();
                                    urlParameters.add(new BasicNameValuePair("code", "return API.messages.delete("+ del +");"));
                                    urlParameters.add(new BasicNameValuePair("access_token", "4a0ba548a055e91094b560b1a736843214921a088b07f00619d71fdb53e6c9409c9df613c1280dd815285"));
                                    urlParameters.add(new BasicNameValuePair("v", "5.124"));
*/
                                    NameValuePair.put("code", "return API.messages.delete("+ del +");");
                                    NameValuePair.put("access_token", "b812149a91dab2c56cc7b0051a2a5693c46a9414c64c9ddaf146198561f76ecb5e2c17e650b375a703ed8");
                                    NameValuePair.put("v", "5.100");


                                    System.out.println(post("https://api.vk.com/method/execute", NameValuePair));



                                   /* HttpGet httpget = new HttpGet("https://api.vk.com/method/messages.removeChatUser?chat_id=" + chat_id
                                            + "&user_id=" + id + "&member_id=" + id + "&access_token=4a0ba548a055e91094b560b1a736843214921a088b07f00619d71fdb53e6c9409c9df613c1280dd815285&v=5.124");

                                    try {
                                        HttpResponse httpresponse = httpclient.execute(httpget);




                                        System.out.println(httpresponse.getStatusLine().getStatusCode());

                                        BufferedReader io = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent()));

                                        System.out.println(io.lines().collect(Collectors.joining("\n")));




                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }*/

                                    break;
                                }
                            }
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
