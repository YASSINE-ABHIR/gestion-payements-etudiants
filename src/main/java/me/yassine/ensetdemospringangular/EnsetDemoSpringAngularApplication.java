package me.yassine.ensetdemospringangular;

import me.yassine.ensetdemospringangular.domain.Payment;
import me.yassine.ensetdemospringangular.domain.PaymentStatusEnum;
import me.yassine.ensetdemospringangular.domain.PaymentTypeEnum;
import me.yassine.ensetdemospringangular.domain.Student;
import me.yassine.ensetdemospringangular.repository.PaymentRepository;
import me.yassine.ensetdemospringangular.repository.StudentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class EnsetDemoSpringAngularApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnsetDemoSpringAngularApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        return args -> {
            studentRepository.save(Student.builder()
                    .firstName("Yassine")
                    .lastName("Abhir")
                    .code("1210113571")
                    .email("yassine.abhir@edu.ma")
                    .programId("II-BDCC")
                    .build());
            studentRepository.save(Student.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .code("1210113571")
                    .email("john.doe@example.com")
                    .programId("II-BDCC")
                    .build());
            studentRepository.save(Student.builder()
                    .firstName("Alice")
                    .lastName("Smith")
                    .code("1425161765")
                    .email("alice.smith@example.com")
                    .programId("II-GLCID")
                    .build());
            studentRepository.save(Student.builder()
                    .firstName("Bob")
                    .lastName("Johnson")
                    .code("1210113534")
                    .email("bob.johnson@example.com")
                    .programId("SMC")
                    .build());
            studentRepository.save(Student.builder()
                    .firstName("Emily")
                    .lastName("Brown")
                    .code("1627365487")
                    .email("emily.brown@example.com")
                    .programId("SMAI")
                    .build());
            studentRepository.findAll().forEach(student -> {
                for (int i = 0; i < 4; i++) {
                    double random = Math.random();
                    Payment payment = Payment.builder()
                            .amount((Double) (random * 10000))
                            .student(student)
                            .date(LocalDate.now())
                            .type(random >= 0.75 ? PaymentTypeEnum.CASH : random >= 0.5 ? PaymentTypeEnum.CHECK : random >= 0.25 ? PaymentTypeEnum.DEPOSIT : PaymentTypeEnum.TRANSFER)
                            .status(random >= 0.66 ? PaymentStatusEnum.VALIDATED : random >= 0.33 ? PaymentStatusEnum.CREATED : PaymentStatusEnum.REJECTED )
                            .receipt("./static/recipes").build();
                    paymentRepository.save(payment);
                }
            });
        };
    }
}
