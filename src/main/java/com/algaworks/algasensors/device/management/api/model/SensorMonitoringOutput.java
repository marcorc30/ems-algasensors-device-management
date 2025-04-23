package com.algaworks.algasensors.device.management.api.model;

import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder

public class SensorMonitoringOutput {

    private TSID tsid;
    private Double lastTemperature;
    private OffsetDateTime updateAt;
    private Boolean enable;
}
