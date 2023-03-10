package com.algaworks.algamoney.api.event;

//import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class ResouceCreatedEvent extends ApplicationEvent {

    private HttpServletResponse response;
    private Long id;

    public ResouceCreatedEvent(Object source, HttpServletResponse response, Long id) {
        super(source);
        this.response = response;
        this.id = id;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Long getId() {
        return id;
    }
}
