package me.yassine.ensetdemospringangular.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.boot.autoconfigure.web.WebProperties;

import javax.annotation.processing.Generated;
import java.time.LocalDate;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Payment {

    @Id @UuidGenerator
    private String id;
    @Temporal(TemporalType.DATE)
    private LocalDate date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private PaymentTypeEnum type;
    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum status;
    private String receipt;
    @ManyToOne
    private Student student;
}
