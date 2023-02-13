package com.xpand.movie.utils.message;

import com.xpand.movie.exception.CustomErrorResponse;
import com.xpand.movie.exception.MovieApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.*;

import static com.xpand.movie.utils.message.Internationalization.GENERIC_EXCEPTION;

@ControllerAdvice
public class MessageHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource source;

    private final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    @ResponseBody
    @ExceptionHandler(MovieApiException.class)
    ResponseEntity<CustomErrorResponse> handleMovieApiException(MovieApiException ex) {
        return handle(ex);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    ResponseEntity<CustomErrorResponse> handleException(Exception ex) {
        logger.error("Exception: {}", ex.getMessage());

        Locale locale = LocaleContextHolder.getLocale();
        Object[] args = new Object[0];

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(source.getMessage(GENERIC_EXCEPTION.getLabel(), args, locale));
        errors.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<CustomErrorResponse> handle(MovieApiException exception) {
        Locale locale = LocaleContextHolder.getLocale();
        Object[] args = exception.getErrorArguments();

        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof String) {
                    String toConvert = String.valueOf(args[i]);
                    try {
                        args[i] = source.getMessage(toConvert, null, locale);
                    } catch (Exception ignored) {
                    }
                }
            }
        }

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError( source.getMessage(exception.getErrorMessage(),args,
                exception.getErrorCode() + " - No Message", locale));
        errors.setStatus(exception.getErrorCode().value());

        logger.error("Exception: {}", errors.getError());

        return new ResponseEntity<>(errors, exception.getErrorCode());
    }
}
