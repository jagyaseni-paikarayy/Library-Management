CREATE DATABASE library_management;
use library_management;


-- =========================
-- 1. MEMBERS TABLE
-- =========================

CREATE TABLE Members (
    member_id INT PRIMARY KEY AUTO_INCREMENT,
    member_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15)
);


-- =========================
-- 2. AUTHORS TABLE
-- =========================

CREATE TABLE Authors (
    author_id INT PRIMARY KEY AUTO_INCREMENT,
    author_name VARCHAR(100) NOT NULL
);


-- =========================
-- 3. BOOKS TABLE
-- =========================

CREATE TABLE Books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    author_id INT NOT NULL,
    category VARCHAR(50),
    total_copies INT DEFAULT 1,
    available_copies INT DEFAULT 1,

    FOREIGN KEY (author_id)
        REFERENCES Authors(author_id)
);


-- =========================
-- 4. BORROWINGS TABLE
-- =========================

CREATE TABLE Borrowings (
    borrowing_id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE,

    FOREIGN KEY (member_id)
        REFERENCES Members(member_id),

    FOREIGN KEY (book_id)
        REFERENCES Books(book_id)
);


-- =========================
-- INSERT MEMBERS
-- =========================

INSERT INTO Members (member_name, email, phone)
VALUES
('Rahul Kumar', 'rahul@gmail.com', '9876543210'),
('Priya Sharma', 'priya@gmail.com', '9876543211'),
('Aman Das', 'aman@gmail.com', '9876543212');


-- =========================
-- INSERT AUTHORS
-- =========================

INSERT INTO Authors (author_name)
VALUES
('R. K. Narayan'),
('Chetan Bhagat'),
('George Orwell');


-- =========================
-- INSERT BOOKS
-- =========================

INSERT INTO Books
(title, author_id, category, total_copies, available_copies)
VALUES
('Malgudi Days', 1, 'Fiction', 3, 2),
('Five Point Someone', 2, 'Fiction', 4, 3),
('1984', 3, 'Novel', 2, 1);


-- =========================
-- INSERT BORROWINGS
-- =========================

INSERT INTO Borrowings
(member_id, book_id, borrow_date, return_date)
VALUES
(1, 1, '2026-09-20', NULL),
(2, 2, '2026-09-21', '2026-09-25'),
(3, 3, '2026-09-22', NULL);


-- =========================
-- CHECK ALL TABLES
-- =========================

SELECT * FROM Members;

SELECT * FROM Authors;

SELECT * FROM Books;

SELECT * FROM Borrowings;