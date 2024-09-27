package br.edu.ifpr.foz.ifprstore.repositories;

import br.edu.ifpr.foz.ifprstore.connection.ConnectionFactory;
import br.edu.ifpr.foz.ifprstore.exceptions.DatabaseException;
import br.edu.ifpr.foz.ifprstore.exceptions.DatabaseIntegrityException;
import br.edu.ifpr.foz.ifprstore.models.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepository {

        Connection connection;

        public AuthorRepository(){
            connection = ConnectionFactory.getConnection();
        }


        public List<Author> getAll() {

            List<Author> authors = new ArrayList<>();

            String sql = "SELECT * FROM author";

            try {

                Statement statement = connection.createStatement();

                ResultSet result = statement.executeQuery(sql);

                while (result.next()) {

                    Author author = new Author();
                    author.setId(result.getInt("Id"));
                    author.setName(result.getString("Name"));
                    authors.add(author);
                }


            }catch (SQLException e){
                throw new DatabaseException(e.getMessage());
            }

            return authors;

        }


        public void delete(Integer id){

            String sql = "DELETE FROM author WHERE Id = ?";

            try {

                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id);

                Integer rowsDeleted = statement.executeUpdate();

                if(rowsDeleted > 0){
                    System.out.println("Rows deleted: " + rowsDeleted);
                }

            } catch (Exception e){
                throw new DatabaseIntegrityException(e.getMessage());
            }

        }


    }

