package com.expense.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Paging {
    private long totalElement;
    private int totalPage;
    private int size;
}
