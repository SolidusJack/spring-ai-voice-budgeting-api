package solidusjack.budgeting.config;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class TransactionTools {

    public record GastoRequest(
        @JsonProperty(value = "categoria", required = true) String categoria,
        @JsonProperty(value = "valor", required = true) double valor
    ) {}

    public record GastoResponse(String status, String mensagem) {}

    @Tool(description = "Acione esta função sempre que o usuário informar que fez um novo gasto ou despesa. Ela registrará o valor e a categoria no sistema.")
    public GastoResponse registrarGastoNoSistema(GastoRequest request) {
        
        System.out.println(">>> [SISTEMA] Salvando no banco: Categoria = " + request.categoria() + " | Valor = R$ " + request.valor());
        
        return new GastoResponse("SUCESSO", "Transação salva no banco de dados.");
    }
}