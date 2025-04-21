package com.algaworks.algasensors.device.management;

import com.algaworks.algasensors.device.management.domain.model.SensorId;
import org.junit.jupiter.api.Test;


public class TSIDTest {

    @Test
    void testarRetorno(){
        String valor = "0KDEYS92WEAXF";
        SensorId sensorId = new SensorId(valor);
        System.out.println("*************************");
        System.out.println("valor " + sensorId);
    }
}
