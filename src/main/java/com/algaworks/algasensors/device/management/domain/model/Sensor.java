package com.algaworks.algasensors.device.management.domain.model;

import com.algaworks.algasensors.device.management.common.IdGenerator;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Sensor {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "BIGINT"))
    private SensorId id;
    private String name;
    private String ip;
    private String location;
    private String protocol;
    private String model;
    private Boolean enabled;


//    @PrePersist
//    public void prePersist(){
//        id = IdGenerator.generateTSID();
//    }

}
