package com.esai.library_api.service;

import com.esai.library_api.model.Book;
import com.esai.library_api.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book createBook(Book book) {
        book.setAddedDate(LocalDate.now());
        book.setAvailableCopies(book.getTotalCopies());
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id).map(existing -> {
            existing.setTitle(updatedBook.getTitle());
            existing.setAuthor(updatedBook.getAuthor());
            existing.setGenre(updatedBook.getGenre());
            existing.setIsbn(updatedBook.getIsbn());
            existing.setNotes(updatedBook.getNotes());
            existing.setTotalCopies(updatedBook.getTotalCopies());
            return bookRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}