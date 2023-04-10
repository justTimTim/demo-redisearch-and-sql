package com.nlmk.trace.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopCauses {
    private String cause;
    private Integer count;
}
