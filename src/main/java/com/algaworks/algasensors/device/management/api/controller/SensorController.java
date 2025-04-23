package com.algaworks.algasensors.device.management.api.controller;

import com.algaworks.algasensors.device.management.api.client.SensorMonitoringClient;
import com.algaworks.algasensors.device.management.api.model.SensorDetailOutput;
import com.algaworks.algasensors.device.management.api.model.SensorInput;
import com.algaworks.algasensors.device.management.api.model.SensorMonitoringOutput;
import com.algaworks.algasensors.device.management.api.model.SensorOutput;
import com.algaworks.algasensors.device.management.common.IdGenerator;
import com.algaworks.algasensors.device.management.domain.model.Sensor;
import com.algaworks.algasensors.device.management.domain.model.SensorId;
import com.algaworks.algasensors.device.management.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor //elimina a necessidade do autowired
public class SensorController {

//    @Autowired
    private final SensorRepository repository;
    private final SensorMonitoringClient sensorMonitoringClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorController.class);


    @GetMapping
    public Page<SensorOutput> search(@PageableDefault Pageable pageable){

        Page<Sensor> sensors = repository.findAll(pageable);

        return sensors.map(sensor -> convertToModel(sensor));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorOutput create(@RequestBody SensorInput input){

        Sensor sensor = Sensor.builder()
                .id(new SensorId(IdGenerator.generateTSID()))
                .name(input.getName())
                .ip(input.getIp())
                .location(input.getLocation())
                .protocol(input.getProtocol())
                .model(input.getModel())
                .enabled(false)
                .build();

        Sensor sensor1 = repository.save(sensor);

        return convertToModel(sensor1);


    }

    @GetMapping("{sensorId}")
    @ResponseStatus(HttpStatus.OK)
    public SensorOutput getOne(@PathVariable TSID sensorId){


        LOGGER.info("sensorId passado " + sensorId);


        Sensor sensor = repository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        return convertToModel(sensor);

    }

    /*
    Metodo traz os detalhes do monotiramento
     */
    @GetMapping("{sensorId}/detail")
    @ResponseStatus(HttpStatus.OK)
    public SensorDetailOutput getOneWithDetail(@PathVariable TSID sensorId){

        Sensor sensor = repository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        SensorMonitoringOutput monitoringOutput = sensorMonitoringClient.getDetail(sensorId);
        SensorOutput sensorOutput = convertToModel(sensor);

        return SensorDetailOutput.builder()
                .monitoring(monitoringOutput)
                .sensor(sensorOutput)
                .build();

    }


    @DeleteMapping("{sensorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable TSID sensorId){

        Sensor sensor = repository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        repository.delete(sensor);

        sensorMonitoringClient.disableMonitoring(sensorId);

    }


    @PutMapping("{sensorId}")
    @ResponseStatus(HttpStatus.OK)
    public SensorOutput alterar(@RequestBody SensorInput sensorInput, @PathVariable TSID sensorId ){



        Sensor sensor = repository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        sensor = Sensor.builder()
                .id(sensor.getId())
                .name(sensorInput.getName())
                .location(sensorInput.getLocation())
                .protocol(sensorInput.getProtocol())
                .ip(sensorInput.getIp())
                .model(sensorInput.getModel())
                .enabled(false)
                .build();

//        Sensor sensorAlterado = repository.save(sensor);

        return convertToModel(sensor);
    }

    @PutMapping("{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void habilitaSensor(@PathVariable TSID sensorId){

        Sensor sensor = repository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        sensor.setEnabled(true);

        repository.save(sensor);

        sensorMonitoringClient.enableMonitoring(sensorId);


    }


    @DeleteMapping("{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desabilitaSensor(@PathVariable TSID sensorId){

        Sensor sensor = repository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        sensor.setEnabled(false);

        repository.save(sensor);

        sensorMonitoringClient.disableMonitoring(sensorId);


    }

    private SensorOutput convertToModel(Sensor sensor) {
        SensorOutput sensorOutput = SensorOutput.builder()
                .id(sensor.getId().getValue())
                .name(sensor.getName())
                .ip(sensor.getIp())
                .location(sensor.getLocation())
                .protocol(sensor.getProtocol())
                .model(sensor.getModel())
                .enabled(sensor.getEnabled())
                .build();
        return sensorOutput;
    }



}
