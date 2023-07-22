package com.Electronics.Store.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PageableResponse<T> {

    private List<T> content;   //we decide type<T>
    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPage;
    private boolean lastPage;

}
