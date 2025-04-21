package com.algaworks.algasensors.device.management.api.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
Classe abaixo preparada para tratar erros do Client Rest.
Essa classe não pode tratar as excecoes do SimpleClientHttpRequestFactoryn (java.net). Para tratar essas exceções
é necessário criar um exceptionhandler (classe ApiExceptionHandler)
 */


@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class SensorMonitoringClientBadGatewayException extends RuntimeException{
}
