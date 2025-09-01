package com.uade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class List {
    private Integer tlistId;
    private Integer listId;
    private Integer itemId;
    private Integer quantity;
}
