USE library_management;

-- 1. Display all members
SELECT * FROM Members;

-- 2. Display all authors
SELECT * FROM Authors;

-- 3. Display all books
SELECT * FROM Books;

-- 4. Display all borrowing records
SELECT * FROM Borrowings;

-- 5. Display complete borrowing information
SELECT
    Borrowings.borrowing_id,
    Members.member_name,
    Books.title,
    Authors.author_name,
    Borrowings.borrow_date,
    Borrowings.return_date
FROM Borrowings
JOIN Members
    ON Borrowings.member_id = Members.member_id
JOIN Books
    ON Borrowings.book_id = Books.book_id
JOIN Authors
    ON Books.author_id = Authors.author_id;

-- 6. Display books that are currently borrowed
SELECT
    Members.member_name,
    Books.title,
    Borrowings.borrow_date
FROM Borrowings
JOIN Members
    ON Borrowings.member_id = Members.member_id
JOIN Books
    ON Borrowings.book_id = Books.book_id
WHERE Borrowings.return_date IS NULL;