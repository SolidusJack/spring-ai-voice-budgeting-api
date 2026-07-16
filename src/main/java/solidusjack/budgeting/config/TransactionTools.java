package solidusjack.budgeting.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import solidusjack.budgeting.model.Gasto;
import solidusjack.budgeting.repository.GastoRepository;

@Component
public class TransactionTools {

    private final GastoRepository repository;

    public TransactionTools(GastoRepository repository) {
        this.repository = repository;
    }

    public record GastoResponse(String status, String mensagem) {}
    
    @Tool(description = "Use esta ferramenta para registrar um novo gasto. Você deve passar uma única String no formato exato: 'nome_da_categoria;valor'. Exemplo: 'gasolina;50.0' ou 'alimentacao;32.50'")
    public GastoResponse registrarGastoNoSistema(String dados) {
        try {
            String[] partes = dados.split(";");
            String categoria = partes[0].trim();
            double valor = Double.parseDouble(partes[1].trim());

            Gasto novoGasto = new Gasto(categoria, valor);
            repository.save(novoGasto);

            return new GastoResponse("SUCESSO", "Gasto de R$ " + valor + " em '" + categoria + "' salvo com sucesso!");
        } catch (Exception e) {
            return new GastoResponse("ERRO", "Falha ao processar os dados de salvamento. Certifique-se de enviar no formato 'categoria;valor'.");
        }
    }

    @Tool(description = "Use esta ferramenta sempre que o usuário perguntar quanto ele gastou, pedir o histórico de gastos, ou quiser saber o saldo atual das despesas.")
    public String listarGastosDoSistema() {
        List<Gasto> gastos = repository.findAll();
        
        if (gastos.isEmpty()) {
            return "Nenhum gasto foi registrado no sistema ainda.";
        }

        String historico = gastos.stream()
            .map(g -> "- Categoria: " + g.getCategoria() + " | Valor: R$ " + g.getValor())
            .collect(Collectors.joining("\n"));

        double total = gastos.stream().mapToDouble(Gasto::getValor).sum();

        return "Histórico de gastos no banco de dados:\n" + historico + "\n\nTotal acumulado: R$ " + total;
    }
}