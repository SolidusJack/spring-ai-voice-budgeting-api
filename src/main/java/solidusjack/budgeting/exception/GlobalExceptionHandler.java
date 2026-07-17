package solidusjack.budgeting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErroValidacaoResponse tratarErrosDeValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nomeCampo = ((FieldError) error).getField();
            String mensagemErro = error.getDefaultMessage();
            erros.put(nomeCampo,mensagemErro);
        });

        return new ErroValidacaoResponse(
            "ERRO_VALIDACAO",
            "Um ou mais campos contêm dados inválidos.",
            erros
        );
    }
}
