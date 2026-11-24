package br.com.delivery.frota.exceptions;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 400 - JSON malformado ou tipo/formato inválido
    @ExceptionHandler({
        HttpMessageNotReadableException.class,
        MethodArgumentTypeMismatchException.class,
        CampoNaoInformadoException.class
    })
    public ResponseEntity<ApiErrorResponse> handleBadRequest(Exception ex) {
        if (ex instanceof CampoNaoInformadoException campoEx) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorResponse.of(campoEx.getMessage()));
        }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiErrorResponse.of("Requisicao invalida ou JSON malformado"));
    }

    // 400 - Validação de campos (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .findFirst()
            .map(err -> err.getField() + ": " + err.getDefaultMessage())
            .orElse("Campo invalido");

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiErrorResponse.of(mensagem));
    }

    // 404 - Recurso inexistente
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(RecursoNaoEncontradoException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiErrorResponse.of(ex.getMessage()));
    }

    // 409 - Conflito de estado
    @ExceptionHandler(ConflitoEstadoException.class)
    public ResponseEntity<ApiErrorResponse> handleConflict(ConflitoEstadoException ex) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ApiErrorResponse.of(ex.getMessage()));
    }

    // 422 - Regra de negócio violada
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessRule(RegraNegocioException ex) {
        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(ApiErrorResponse.of(ex.getMessage()));
    }

    // 503 - Serviço de outra equipe fora do ar
    @ExceptionHandler(ServicoIndisponivelException.class)
    public ResponseEntity<ApiErrorResponse> handleServiceUnavailable(ServicoIndisponivelException ex) {
        return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(ApiErrorResponse.of(ex.getMessage()));
    }

    // 504 - Serviço de outra equipe não respondeu a tempo
    @ExceptionHandler({
        TempoEsgotadoServicoException.class,
        TimeoutException.class,
        SocketTimeoutException.class
    })
    public ResponseEntity<ApiErrorResponse> handleGatewayTimeout(Exception ex) {
        String msg = ex instanceof TempoEsgotadoServicoException
            ? ex.getMessage()
            : "Servico externo nao respondeu a tempo";

        return ResponseEntity
            .status(HttpStatus.GATEWAY_TIMEOUT)
            .body(ApiErrorResponse.of(msg));
    }

    // 500 - Erro interno
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiErrorResponse.of("Erro interno no servidor"));
    }
}
