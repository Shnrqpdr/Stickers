import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

import modules.StickersGenerator;
import utils.JsonParser;

public class App {
    public static void main(String[] args) throws Exception {

        String url = "https://alura-filmes.herokuapp.com/conteudos";
        URI endereco = URI.create(url);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        var generator = new StickersGenerator();
        for (Map<String, String> filme : listaDeFilmes) {

            String urlImagem = filme.get("image");

            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeSticker = filme.get("title");

            try {
                generator.generate(inputStream, nomeSticker);
            } catch (Exception e) {
                System.out.println("Filme inválido: " + e);
            }

            System.out.println(nomeSticker);

        }
    }
}
