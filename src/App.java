import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

import utils.JsonParser;

public class App {
    public static void main(String[] args) throws Exception {

        String url = "https://alura-filmes.herokuapp.com/conteudos";
        URI endereco = URI.create(url);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // System.out.println(body);

        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados
        for (Map<String, String> filme : listaDeFilmes) {
            System.out.println("Titulo do filme: " + filme.get("title"));
            System.out.println("Pôster do filme: " + filme.get("image"));
            System.out.println("Rating do filme: " + filme.get("imDbRating"));
            System.out.println();
        }
    }
}
