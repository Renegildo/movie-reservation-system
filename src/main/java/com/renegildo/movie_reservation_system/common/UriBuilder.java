package com.renegildo.movie_reservation_system.common;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class UriBuilder {
    public static URI buildUri(String baseUrl, Long id) {
        return ServletUriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
