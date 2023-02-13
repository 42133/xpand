package com.xpand.movie.utils.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class MessageBuilder<T> {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private TranslateMessage translateMessage;

    public Message<T> success(HttpStatus statusCode, T data, String i18n, String... params) {
        String translated = translateMessage.applyInternationalization(i18n, messageSource, params);
        return new Message<T>(statusCode, data, translated);
    }

}
