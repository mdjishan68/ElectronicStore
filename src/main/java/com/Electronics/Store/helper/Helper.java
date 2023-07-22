package com.Electronics.Store.helper;

import com.Electronics.Store.dtos.PageableResponse;
import com.Electronics.Store.dtos.UserDto;
import com.Electronics.Store.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static <U,V>PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {

        //U= entity; V= Dto;
        List<U> entity = page.getContent();
        List<V> dtoList = entity.stream().map(object -> new ModelMapper().map(object,type)).collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPage(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;

    }
}
