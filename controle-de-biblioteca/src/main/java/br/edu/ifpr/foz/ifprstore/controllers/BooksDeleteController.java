package br.edu.ifpr.foz.ifprstore.controllers;

import br.edu.ifpr.foz.ifprstore.repositories.BookRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/books/delete")
public class BooksDeleteController extends HttpServlet {

    BookRepository repository = new BookRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Obtem o parâmetro "id" que está na url, depois da ?
        Integer id = Integer.valueOf(req.getParameter("id"));

        //executa a operaçao de exclusão no bando de dados.
        repository.delete(id);

        //redireciona a requisição novamente para tela de listagem de vendedores
        resp.sendRedirect("http://localhost:8080/IFPRStore/books");

    }

}
