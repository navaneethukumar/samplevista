package com.spire.crm.restful.base.service;

import java.time.ZonedDateTime;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Custom {@link ContextResolver} for (de)serializing Java 8
 * {@link ZonedDateTime}
 * 
 * @author karthic.v
 *
 */
@Provider
public class CustomObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper MAPPER;

    public CustomObjectMapperContextResolver() {
	MAPPER = new ObjectMapper();
	MAPPER.registerModule(new JavaTimeModule());
	MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
	return MAPPER;
    }
}