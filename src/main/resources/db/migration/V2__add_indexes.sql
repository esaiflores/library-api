CREATE INDEX IF NOT EXISTS idx_loans_student_id ON loans(student_id);
CREATE INDEX IF NOT EXISTS idx_loans_book_id ON loans(book_id);
CREATE INDEX IF NOT EXISTS idx_loans_returned_date ON loans(returned_date);
CREATE INDEX IF NOT EXISTS idx_books_title ON books(title);