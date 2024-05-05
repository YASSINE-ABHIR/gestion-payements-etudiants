package me.yassine.ensetdemospringangular.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.yassine.ensetdemospringangular.domain.Payment;
import me.yassine.ensetdemospringangular.domain.PaymentStatusEnum;
import me.yassine.ensetdemospringangular.domain.PaymentTypeEnum;
import me.yassine.ensetdemospringangular.domain.Student;
import me.yassine.ensetdemospringangular.repository.PaymentRepository;
import me.yassine.ensetdemospringangular.repository.StudentRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class PaymentService {
    private PaymentRepository paymentRepository;
    private StudentRepository studentRepository;

    public ResponseEntity<Payment> newPayment(String studentCode, double amount, PaymentTypeEnum paymentType, LocalDate date,
                                              MultipartFile file) throws IOException {
        if (!file.getContentType().equals(MediaType.APPLICATION_PDF_VALUE)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Student> optionalStudent = studentRepository.findStudentByCode(studentCode);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            Path folderPath = Paths.get(System.getProperty("user.home"),"data","payments");
            if(!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }
            String fileName = UUID.randomUUID().toString();
            Path filePath = Paths.get(folderPath.toString(),fileName+".pdf");
            Files.copy(file.getInputStream(),filePath);
            Payment payment = Payment.builder()
                    .amount(amount)
                    .student(student)
                    .type(paymentType)
                    .date(date)
                    .status(PaymentStatusEnum.CREATED)
                    .receipt(filePath.toUri().toString())
                    .build();
            paymentRepository.save(payment);
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Payment> paymentStatusUpdate(String id, PaymentStatusEnum status) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            payment.setStatus(status);
            payment = paymentRepository.save(payment);
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public byte[] getPaymentFile(String paymentId) throws IOException {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isPresent()) {
            return Files.readAllBytes(Path.of(URI.create(optionalPayment.get().getReceipt())));
        }
        return null;
    }
}
