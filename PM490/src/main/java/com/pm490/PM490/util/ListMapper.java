package com.pm490.PM490.util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ListMapper<S,T> {
    @Autowired
    public ModelMapper mapper;

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass){
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
