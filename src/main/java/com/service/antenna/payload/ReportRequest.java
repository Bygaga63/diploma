package com.service.antenna.payload;

import com.service.antenna.domain.Status;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReportRequest {
    private Date period;
    private List<Long> breakdownType;
    private Long user;
    private Status status;
}
