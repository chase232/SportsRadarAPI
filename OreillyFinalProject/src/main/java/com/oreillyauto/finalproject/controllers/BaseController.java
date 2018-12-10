package com.oreillyauto.finalproject.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class BaseController {
    private static final Logger log = Logger.getLogger(BaseController.class);

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String resolveException(final Exception ex, final WebRequest request, final HttpServletResponse response) {
        // This is to make sure Spring Security handles this error
        final int MAX_BUILDER_SIZE = 256;
        final StringBuilder trace = new StringBuilder(MAX_BUILDER_SIZE).append("Unhandled exception:\r\n").append(ex.toString());
        for (final StackTraceElement ste : ex.getStackTrace()) {
            trace.append("\r\n\t").append(ste.toString());
        }
        log.error(trace.toString());
        System.out.println(trace.toString());
        return "errorPage";
    }
}
