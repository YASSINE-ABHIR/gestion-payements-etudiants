package me.yassine.ensetdemospringangular.repository;

import me.yassine.ensetdemospringangular.domain.Payment;
import me.yassine.ensetdemospringangular.domain.PaymentStatusEnum;
import me.yassine.ensetdemospringangular.domain.PaymentTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,String> {
    List<Payment> findByStudentCode(String studentCode);
    List<Payment> findByStatus(PaymentStatusEnum paymentStatus);
    List<Payment> findByType(PaymentTypeEnum paymentType);

}
