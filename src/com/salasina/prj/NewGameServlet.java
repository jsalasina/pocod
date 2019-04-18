package com.salasina.prj;

import model.Field;
import model.FieldDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@WebServlet("/game")
// generate new playing field
public class NewGameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Field field = new Field();
        field.generateField();
        try {
            //if the previous player left the game before ending, drop the table first
            FieldDB.dropTable();
            FieldDB.createTable();
            FieldDB.insert(field);  //insert new field into database
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}