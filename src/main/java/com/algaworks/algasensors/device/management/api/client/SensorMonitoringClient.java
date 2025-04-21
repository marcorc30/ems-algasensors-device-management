package com.algaworks.algasensors.device.management.api.client;

import io.hypersistence.tsid.TSID;

public interface SensorMonitoringClient {

    void enablMonitoring(TSID sensorId);
    void disableMonitoring(TSID sensorId);

}
