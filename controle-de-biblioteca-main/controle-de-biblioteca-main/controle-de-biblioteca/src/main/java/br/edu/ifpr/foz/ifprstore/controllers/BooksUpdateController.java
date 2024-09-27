package br.edu.ifpr.foz.ifprstore.controllers;

import br.edu.ifpr.foz.ifprstore.repositories.BookRepository;
import br.edu.ifpr.foz.ifprstore.models.Author;
import br.edu.ifpr.foz.ifprstore.models.Book;
import br.edu.ifpr.foz.ifprstore.repositories.AuthorRepository;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/books/update")
public class BooksUpdateController extends HttpServlet {

    BookRepository repository = new BookRepository();
    AuthorRepository authorRepository = new AuthorRepository();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer id =  Integer.valueOf(req.getParameter("id"));

        List<Author> authors = authorRepository.getAll();
        req.setAttribute("authors", authors);

        Book book = repository.getById(id);
        req.setAttribute("book", book);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/books-update.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Integer id = Integer.valueOf(req.getParameter("field_id"));
        String name = req.getParameter("field_name");
        LocalDate birthDate = LocalDate.parse(req.getParameter("field_birthDate"));
        Integer authorId = Integer.valueOf(req.getParameter("field_author"));
        String status = req.getParameter("field_status");

        Author author = new Author();
            author.setId(authorId);

        Book book = new Book();
            book.setId(id);
            book.setName(name);
            book.setDate(birthDate);
            book.setAuthor(author);
            book.setStatus(status);

        repository.update(book);

        resp.sendRedirect(req.getContextPath() + "/books");

}

}
