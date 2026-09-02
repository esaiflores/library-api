package com.esai.library_api.service;

import com.esai.library_api.model.Book;
import com.esai.library_api.model.Loan;
import com.esai.library_api.repository.BookRepository;
import com.esai.library_api.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    public Loan createLoan(Loan loan) {
        Book book = bookRepository.findById(loan.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getAvailableCopies() < 1) {
            throw new RuntimeException("No available copies");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        loan.setLentDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));
        return loanRepository.save(loan);
    }

    public Loan returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        Book book = bookRepository.findById(loan.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        loan.setReturnedDate(LocalDate.now());
        return loanRepository.save(loan);
    }

    public List<Loan> getActiveLoans() {
        return loanRepository.findAll().stream()
                .filter(loan -> loan.getReturnedDate() == null)
                .toList();
    }
}