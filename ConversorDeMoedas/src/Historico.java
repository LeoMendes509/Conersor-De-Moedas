import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Historico {
    private List<String> registros;

    public Historico() {
        this.registros = new ArrayList<>();
    }

    // Adiciona registro formatado no histórico
    public void adicionarRegistro(String origem, String destino, double valorOrigem, double valorConvertido) {
        String registro = String.format("%.2f %s → %.2f %s", valorOrigem, origem, valorConvertido, destino);
        registros.add(registro);
    }

    // Verifica se há registros
    public boolean temRegistros() {
        return !registros.isEmpty();
    }

    // Gera arquivo HTML com todo o histórico salvo
    public boolean gerarArquivoHtml() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html lang=\"pt-BR\">\n<head>\n");
        html.append("<meta charset=\"UTF-8\">\n<title>Histórico de Conversões</title>\n");
        html.append("<style>\nbody {font-family: Arial, sans-serif; margin: 20px;}\n");
        html.append("h1 {color: #2E8B57;}\nul {list-style-type: none; padding: 0;}\n");
        html.append("li {background-color: #F0F8FF; margin: 8px 0; padding: 10px; border-radius: 5px;}\n");
        html.append("</style>\n</head>\n<body>\n");
        html.append("<h1>📄 Histórico de Conversões de Moedas</h1>\n<ul>\n");

        for (String registro : registros) {
            html.append("<li>").append(registro).append("</li>\n");
        }

        html.append("</ul>\n</body>\n</html>");

        try (FileWriter writer = new FileWriter("historico-conversoes.html")) {
            writer.write(html.toString());
            return true;
        } catch (IOException e) {
            System.out.println("❌ Erro ao criar arquivo HTML: " + e.getMessage());
            return false;
        }
    }
}
