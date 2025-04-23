package com.algaworks.algasensors.device.management.api.client.impl;

import com.algaworks.algasensors.device.management.api.client.RestClientFactory;
import com.algaworks.algasensors.device.management.api.client.SensorMonitoringClient;
import com.algaworks.algasensors.device.management.api.client.SensorMonitoringClientBadGatewayException;
import com.algaworks.algasensors.device.management.api.model.SensorMonitoringOutput;
import io.hypersistence.tsid.TSID;
import org.hibernate.annotations.Comment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

//@Component
public class SensorMonitoringClienteImpl implements SensorMonitoringClient {
//    para criar um RestClient podemos instanciar diretamente um RestClient create passando a url
//    private final RestClient restClient = RestClient.create("http://localhost:8082");


//    outra forma, e criando um builder
    private RestClient restClient;

    public SensorMonitoringClienteImpl(RestClientFactory factory) {
        this.restClient = factory.temperatureMonitoringRestClient();
    }



    @Override
    public void enableMonitoring(TSID sensorId) {
        this.restClient.put()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();

    }

    @Override
    public void disableMonitoring(TSID sensorId) {
        this.restClient.delete()
                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
                .retrieve()
                .toBodilessEntity();

    }

    @Override
    public SensorMonitoringOutput getDetail(TSID sensorId) {
        return this.restClient.get()
                .uri("/api/sensors/{sensorId}/monitoring", sensorId)
                .retrieve()
                .body(SensorMonitoringOutput.class);


    }
}
