package sw_workbook.spring.apiPayload.code.exception.handler;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sw_workbook.spring.apiPayload.ApiResponse;
import sw_workbook.spring.apiPayload.code.status.ErrorStatus;
import sw_workbook.spring.config.jwt.exception.JwtAuthenticationException;

import java.security.SignatureException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleJwtAuth(JwtAuthenticationException e) {
        log.warn("[JwtAuthenticationException] {}", e.getMessage());
        return buildErrorResponse(ErrorStatus._UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<Void>> handleExpiredJwt(ExpiredJwtException e) {
        log.warn("[ExpiredJwtException] {}", e.getMessage());
        return buildErrorResponse(ErrorStatus._UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidSignature(SignatureException e) {
        log.warn("[SignatureException] {}", e.getMessage());
        return buildErrorResponse(ErrorStatus._FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgNotValid(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn("[MethodArgumentNotValidException] {}", errorMessage);
        return buildCustomMessage(ErrorStatus._BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().iterator().next().getMessage();
        log.warn("[ConstraintViolationException] {}", errorMessage);
        return buildCustomMessage(ErrorStatus._BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Void>> handleBindException(BindException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn("[BindException] {}", errorMessage);
        return buildCustomMessage(ErrorStatus._BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnhandledException(Exception e) {
        log.error("[Unhandled Exception]", e);
        return buildErrorResponse(ErrorStatus._INTERNAL_SERVER_ERROR);
    }

    // 🔧 공통 응답 생성
    private ResponseEntity<ApiResponse<Void>> buildErrorResponse(ErrorStatus status) {
        return ResponseEntity
                .status(status.getHttpStatus())
                .body(ApiResponse.onFailure(status.getCode(), status.getMessage(), null));
    }

    private ResponseEntity<ApiResponse<Void>> buildCustomMessage(ErrorStatus status, String message) {
        return ResponseEntity
                .status(status.getHttpStatus())
                .body(ApiResponse.onFailure(status.getCode(), message, null));
    }
}