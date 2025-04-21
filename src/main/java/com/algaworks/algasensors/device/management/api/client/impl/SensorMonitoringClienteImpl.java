package com.algaworks.algasensors.device.management.api.client.impl;

import com.algaworks.algasensors.device.management.api.client.SensorMonitoringClient;
import com.algaworks.algasensors.device.management.api.client.SensorMonitoringClientBadGatewayException;
import io.hypersistence.tsid.TSID;
import org.hibernate.annotations.Comment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
public class SensorMonitoringClienteImpl implements SensorMonitoringClient {
//    para criar um RestClient podemos instanciar diretamente um RestClient create passando a url
//    private final RestClient restClient = RestClient.create("http://localhost:8082");


//    outra forma, e criando um builder
    private RestClient restClient;

    public SensorMonitoringClienteImpl(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://localhost:8082")
                .requestFactory(generateClientHttpRequestFactory())
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    throw new SensorMonitoringClientBadGatewayException();
                })
                .build();
    }

//    configurando o cliente http padrao
    private ClientHttpRequestFactory generateClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //depois da conexão, aguarde  minutos para a resposta
        factory.setReadTimeout(Duration.ofSeconds(5));
        //aguarde  minutos para estabelecer a conexão
        factory.setConnectTimeout(Duration.ofSeconds(5));
        return factory;


    }

    @Override
    public void enablMonitoring(TSID sensorId) {
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
}
