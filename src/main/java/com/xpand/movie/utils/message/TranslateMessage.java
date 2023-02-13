package com.xpand.movie.utils.message;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TranslateMessage {

    public String applyInternationalization(String messageToTranslate,  MessageSource messageSource, Object ... args) {
        Locale locale = LocaleContextHolder.getLocale();

        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof String) {
                    String toConvert = String.valueOf(args[i]);
                    try {
                        args[i] = messageSource.getMessage(toConvert, null, locale);
                    } catch (Exception ignored) {
                    }
                }
            }
        }

        return messageSource.getMessage(messageToTranslate, args, locale);
    }
}
