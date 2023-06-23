package com.example.demo1.exception;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateInputException;
import org.thymeleaf.exceptions.TemplateProcessingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(InvalidDefinitionException.class)
    public ResponseEntity<?> handleInvalidDefinitionException(InvalidDefinitionException e)
    {
        LOGGER.warn("handleInvalidDefinitionException()-GlobalExceptionHandler.class");
        return ResponseEntity.status(500).body("Internal Server Error");
    }
    //Thymeleaf exception
    @ExceptionHandler(org.attoparser.ParseException.class)
    public String handleParseException(org.attoparser.ParseException e)
    {
        LOGGER.error("handleParseException()-GlobalExceptionHandler.class");
        return "redirect:/403";
    }
    @ExceptionHandler(TemplateProcessingException.class)
    public String handleTemplateProcessingException(TemplateProcessingException e)
    {
        LOGGER.error("handleTemplateProcessingException()-GlobalExceptionHandler.class");
        return "redirect:/403";
    }
    @ExceptionHandler(TemplateInputException.class)
    public String handleTemplateInputException(TemplateInputException e)
    {
        LOGGER.error("handleTemplateInputException()-GlobalExceptionHandler.class");
        return "redirect:/403";
    }
    //Thymeleaf exception end
    @ExceptionHandler(SpelEvaluationException.class)
    public ResponseEntity<?> handleSpelEvaluationException(SpelEvaluationException e)
    {
        LOGGER.warn("handleSpelEvaluationException()-GlobalExceptionHandler.class-[Spring expression language evaluatuion]");
        return ResponseEntity.status(500).body("Internal Server Error");
    }
    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<?> handlePropertyReferenceException(PropertyReferenceException e)
    {
        LOGGER.warn("handlePropertyReferenceException()-GlobalExceptionHandler.class-[Property mapping failed]");
        return ResponseEntity.badRequest().body("Internal Server Error");
    }
    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedGlobalExceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response)
    {
        LOGGER.warn("handleAccessDeniedGlobalExceptionHandler()-GlobalExceptionHandler.class");
        try {
            response.sendRedirect("/accessdenied");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public void handlePageNotFound(Exception e)
    {
        LOGGER.warn("handlePageNotFound()-GlobalExceptionHandler.class");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public void handleUsernameNotFoundException(Exception e)
    {
        LOGGER.warn("handleUsernameNotFoundException()-GlobalExceptionHandler.class");
    }

    @ExceptionHandler(MysqlDataTruncation.class)
    public void handleMysqlDataTruncation(Exception e)
    {
        LOGGER.warn("handleMysqlDataTruncation()-GlobalExceptionHandler.class-[Data lenght too long for save to MYSQL]");
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        LOGGER.warn("handleMaxSizeException()-GlobalExceptionHandler.class-[File too large]");
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File too large!");
    }


/*    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(RuntimeException e) {
        Throwable cause = e.getCause();
        if(cause == null) {
            System.out.println("null");
        }
        else if(cause instanceof NoHandlerFoundException) {
            System.out.println("NoHandlerFoundException-NoHandlerFoundException");
        }
        else if(cause instanceof AccessDeniedHandler) {
            System.out.println("access denied"+e.getMessage());
        }
        else {
            System.out.println("default");
        }
    }*/
}
