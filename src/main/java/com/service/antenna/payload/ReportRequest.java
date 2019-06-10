package com.service.antenna.payload;

import com.service.antenna.domain.Status;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class ReportRequest {
    private Date start;
    private Date end;
    private Set<Long> breakdownType;
    private Set<Long> users;
    private Status status;
}
