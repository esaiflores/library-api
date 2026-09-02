package com.esai.library_api.repository;
import java.util.List;
import com.esai.library_api.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByStudentId(Long studentId);
    List<Loan> findByBookId(Long bookId);
    List<Loan> findByReturnedDateIsNull();
}