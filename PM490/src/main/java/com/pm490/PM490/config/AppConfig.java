package com.pm490.PM490.config;

import com.pm490.PM490.util.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;

@Configuration

public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ListMapper listMapper(){
        return new ListMapper();
    }
/*
    @Bean
    public HttpHeaders httpHeaders() {
        return new HttpHeaders();
    }*/

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
