package me.yassine.ensetdemospringangular.web;

import me.yassine.ensetdemospringangular.domain.Payment;
import me.yassine.ensetdemospringangular.domain.PaymentStatusEnum;
import me.yassine.ensetdemospringangular.domain.PaymentTypeEnum;
import me.yassine.ensetdemospringangular.repository.PaymentRepository;

import me.yassine.ensetdemospringangular.service.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PaymentRestController {
    private PaymentRepository paymentRepository;
    private PaymentService paymentService;

    /**
     * Get all payments
     * @return List<Payment>
     */
    @GetMapping(path = "/payments/all")
    public List<Payment> allPayments(){
        return paymentRepository.findAll();
    }

    /**
     * Get all student payments by student code
     * @param code is a student code
     * @return List<Payment>
     */
    @GetMapping(path = "/payments/student/{code}")
    public List<Payment> allStudentPayments(@PathVariable String code) {
        return paymentRepository.findByStudentCode(code);
    }

    /**
     * Get the payment by its id
     * @param id is the payment id
     * @return Payment or null if doesn't exist
     */
    @GetMapping(path = "/payment")
    public Optional<Payment> paymentById(String id) {
        return paymentRepository.findById(id);
    }

    /**
     * Get all payments by the status
     * @param paymentStatus is the payment status
     * @return List<Payment>
     */
    @GetMapping(path = "/payments/status/{status}")
    public List<Payment> paymentByStatus(@PathVariable(name = "status") PaymentStatusEnum paymentStatus) {
        return paymentRepository.findByStatus(paymentStatus);
    }

    /**
     * Get all payments knowing the type
     * @param paymentType is the payment type
     * @return List<Payment>
     */
    @GetMapping(path = "/payments/type/{type}")
    public List<Payment> paymentByType(@PathVariable(name = "type") PaymentTypeEnum paymentType) {
        return paymentRepository.findByType(paymentType);
    }

    /**
     * Update the payment status
     * @param id is payment id
     * @param status is the new status
     * @return Optional<Payment>
     */
    @PutMapping("/payments/{id}")
    public ResponseEntity<Payment> paymentStatusUpdate(@PathVariable String id,@RequestParam PaymentStatusEnum status) {
        return paymentService.paymentStatusUpdate(id,status);
    }

    /**
     * Add new payment
     * @param studentCode is student code
     * @param amount is the payment amount
     * @param paymentType is the payment type
     * @param date is the payment date
     * @param file is the payment receipt
     * @return ResponseEntity<Payment>
     * @throws IOException in case exception on uploading receipt
     */
    @PostMapping(value = "/payments/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Payment> addNewPayment(String studentCode, double amount, PaymentTypeEnum paymentType, LocalDate date,
                                                 @Parameter(description = "File to upload") @RequestPart(value = "file")MultipartFile file) throws IOException {
        return paymentService.newPayment(studentCode,amount,paymentType,date,file);
    }

    /**
     * Get the payment file
     * @param paymentId is the payment id
     * @return byte[]
     * @throws IOException in case exception on reading receipt
     */
    @GetMapping(path = "/paymentFile/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable String paymentId) throws IOException {
        return paymentService.getPaymentFile(paymentId);
    }
}
