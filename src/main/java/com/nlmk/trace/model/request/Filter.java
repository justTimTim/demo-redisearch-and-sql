package com.nlmk.trace.model.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Filter {
    private List<String> workPlace;
    private List<LocalDateTime> beginDate;
    private List<String> cause;
}
