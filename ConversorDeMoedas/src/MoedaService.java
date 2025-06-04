import java.net.URI;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MoedaService {
    private static final String API_KEY = "94a3dc5fe812b0c5546dc3d0";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    // Método que faz a conversão usando API externa
    public Conversao converter(String de, String para, double valor) {
        String url = BASE_URL + API_KEY + "/latest/" + de;

        try {
            // Criar cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Criar requisição HTTP GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            // Enviar requisição e receber resposta como String
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse JSON usando Gson
            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

            // Verifica se o resultado foi sucesso
            if (!json.get("result").getAsString().equals("success")) {
                System.out.println("❌ Erro na resposta da API.");
                return null;
            }

            // Pega o objeto conversion_rates com as taxas
            JsonObject rates = json.getAsJsonObject("conversion_rates");

            if (!rates.has(para)) {
                System.out.println("❌ Moeda de destino não encontrada.");
                return null;
            }

            double taxa = rates.get(para).getAsDouble();
            double convertido = valor * taxa;

            // Retorna um objeto Conversao com valor original e convertido
            return new Conversao(valor, convertido);

        } catch (Exception e) {
            System.out.println("❌ Erro na requisição: " + e.getMessage());
            return null;
        }
    }
}

