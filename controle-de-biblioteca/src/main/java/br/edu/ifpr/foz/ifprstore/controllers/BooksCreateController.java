package br.edu.ifpr.foz.ifprstore.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import br.edu.ifpr.foz.ifprstore.models.Author;
import br.edu.ifpr.foz.ifprstore.models.Book;
import br.edu.ifpr.foz.ifprstore.repositories.AuthorRepository;
import br.edu.ifpr.foz.ifprstore.repositories.BookRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/books/create")
public class BooksCreateController extends HttpServlet {
    BookRepository repository = new BookRepository();
    AuthorRepository authorRepository = new AuthorRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Author> authors = authorRepository.getAll();
        req.setAttribute("Authors", authors);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/books-create.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("field_name");
        //String email = req.getParameter("field_email");
        LocalDate birthDate = LocalDate.parse(req.getParameter("field_birthDate"));
        //Double baseSalary = Double.valueOf(req.getParameter("field_baseSalary"));
        //Integer departmentId = Integer.valueOf(req.getParameter("field_department"));
        Integer authorId = Integer.valueOf(req.getParameter("field_department"));

        Author author = new Author();
        author.setId(authorId);

        Book book = new Book();
        book.setName(name);
        book.setDate(birthDate);
        book.setAuthor(author);

        repository.insert(book);

        resp.sendRedirect(req.getContextPath() + "/books");

    }
}
