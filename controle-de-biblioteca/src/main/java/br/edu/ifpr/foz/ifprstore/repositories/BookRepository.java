package br.edu.ifpr.foz.ifprstore.repositories;

import br.edu.ifpr.foz.ifprstore.connection.ConnectionFactory;
import br.edu.ifpr.foz.ifprstore.exceptions.DatabaseException;
import br.edu.ifpr.foz.ifprstore.models.Author;
import br.edu.ifpr.foz.ifprstore.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

        private Connection connection;

        public BookRepository() {

            connection = ConnectionFactory.getConnection();

        }

        public List<Book> getBooks() {

            List<Book> books = new ArrayList<>();

            try {

                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM book");

                while (result.next()) {

                    Book book = instantiateBook(result, null);

                    books.add(book);

                }

                result.close();


            } catch (SQLException e) {

                throw new RuntimeException(e);

            } finally {
                ConnectionFactory.closeConnection();
            }


            return books;
        }

        public Book insert(Book book) {

            String sql = "INSERT INTO book (Name, Date, IdAuthor) VALUES (?, ?, ?)";

            try {
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                statement.setString(1, book.getName());
                statement.setDate(2, Date.valueOf(book.getDate()));
                statement.setInt(3, 1);

                Integer rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {

                    ResultSet id = statement.getGeneratedKeys();

                    id.next();

                    Integer bookId = id.getInt(1);

                    System.out.println("Rows inserted: " + rowsInserted);
                    System.out.println("Id: " + bookId);

                    book.setId(bookId);

                }


            } catch (Exception e) {
                throw new DatabaseException(e.getMessage());
            }

            return book;
        }

        public void update(Book book) {

            String sql = "UPDATE book SET Name = ?, Date = ?, IdAuthor = ? WHERE (book.Id = ?)";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, book.getName());
                statement.setDate(2, Date.valueOf(book.getDate()));
                statement.setInt(3, book.getAuthor().getId());
                statement.setInt(4, book.getId());
                statement.executeUpdate();
                int rowsAffected = statement.executeUpdate();

                System.out.println("Rows affected: " + rowsAffected);

            } catch (SQLException e) {
                throw new DatabaseException(e.getMessage());
            }

        }

        public void delete(Integer id) {

            String sql = "DELETE FROM book WHERE Id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setInt(1, id);
                Integer rowsDeleted = statement.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Rows deleted: " + rowsDeleted);
                }

            } catch (Exception e) {
                throw new DatabaseException(e.getMessage());
            } finally {
                ConnectionFactory.closeConnection();
            }
        }

        public Book getById(Integer id) {

            Book book;
            Author author;

            String sql = "SELECT book.*,author.Name as AutName " +
                    "FROM book " +
                    "INNER JOIN author " +
                    "ON book.idAuthor = author.Id " +
                    "WHERE book.Id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {

                    author = instantiateAuthor(resultSet);
                    book = instantiateBook(resultSet, author);


                } else {
                    throw new DatabaseException("Livro não encontrado");
                }

            } catch (SQLException e) {
                throw new DatabaseException(e.getMessage());
            }

            return book;
        }

        public Book instantiateBook(ResultSet resultSet, Author author) throws  SQLException {
            Book book = new Book();
            book.setId(resultSet.getInt("Id"));
            book.setName(resultSet.getString("Name"));
            book.setDate(resultSet.getDate("Date").toLocalDate());
            book.setAuthor(author);
            return book;
        }

        public Author instantiateAuthor(ResultSet resultSet) throws  SQLException {
            Author author = new Author();

            author.setId(resultSet.getInt("AuthorId"));
            author.setName(resultSet.getString("AutName"));
            return author;
        }

    }

