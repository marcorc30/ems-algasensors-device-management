package com.algaworks.algasensors.device.management.api.config.web;

import com.algaworks.algasensors.device.management.api.client.SensorMonitoringClientBadGatewayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.channels.ClosedChannelException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    /*
        tratando exceçoes de erro de comunicacao.
        ProblemDetail trata o corpo da resposta
        Ex:
            {
                "timestamp": "2025-04-21T22:56:30.499+00:00",
                "status": 500,
                "error": "Internal Server Error",
                "path": "/api/sensors/0KFQQW7GGECRR/enable"
            }
     */

    @ExceptionHandler({
            SocketTimeoutException.class,
            ConnectException.class,
            ClosedChannelException.class
    })
    public ProblemDetail handle(IOException e){

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.GATEWAY_TIMEOUT);

        problemDetail.setTitle("Gateway timeout");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("/errors/gateway-timeout"));


        return problemDetail;

    }

    /*

     */

    @ExceptionHandler(SensorMonitoringClientBadGatewayException.class)
    public ProblemDetail handle(SensorMonitoringClientBadGatewayException e){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_GATEWAY);

        problemDetail.setTitle("Bad Gateway");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("/errors/bad-gateway"));


        return problemDetail;



    }


}
