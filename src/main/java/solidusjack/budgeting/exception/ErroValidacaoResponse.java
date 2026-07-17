package solidusjack.budgeting.exception;

import java.util.Map;

public record ErroValidacaoResponse(
    String status,
    String mensagem,
    Map<String, String> camposComErro
) {
	
}