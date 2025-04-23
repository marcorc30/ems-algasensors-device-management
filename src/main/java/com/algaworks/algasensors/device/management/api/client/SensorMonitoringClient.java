package com.algaworks.algasensors.device.management.api.client;

import com.algaworks.algasensors.device.management.api.model.SensorMonitoringOutput;
import io.hypersistence.tsid.TSID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

/*
 Existem 2 formas de implementar rest client.
 Essa classe abaixo sem as anotaçoes @HttpExchange, @PutExchange, @DeleteExchange e @GetExchange vai precisar de
 uma classe de implementaçao dessa interface para prover os endpoints (SensorMonitoringClientImpl)

 Com as anotações mencionadas acima, nao precisamos da classe que implementa a interface, mas precisamos de uma classe
 de configuração que instancia essa interface nos controllers (RestClientConfig)
 */

@HttpExchange("/api/sensors/{sensorId}/monitoring")
public interface SensorMonitoringClient {

    @PutExchange("/enable")
    void enableMonitoring(@PathVariable TSID sensorId);

    @DeleteExchange("/enable")
    void disableMonitoring(@PathVariable  TSID sensorId);

    @GetExchange
    SensorMonitoringOutput getDetail(@PathVariable  TSID sensorId);

}
