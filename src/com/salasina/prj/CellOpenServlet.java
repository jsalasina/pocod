package com.salasina.prj;


import com.google.gson.Gson;
import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

@WebServlet("/cellopen")
//to open cells and end the game if necessary
public class CellOpenServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Cell> cells = new ArrayList<>();
        int count = Integer.parseInt(req.getParameter("cellsOpen"));

        int id = Integer.parseInt(req.getParameter("id"));
        try {
            if (FieldDB.getCell(id).getValue() == -1) { //if cell contains a bomb
                cells = FieldDB.allBombs(); //return all bombs
//                FieldDB.dropTable(); //and end the game
            } else {
                try {
                    openCell(id, cells);  //otherwise return all cells to open
                } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        count += cells.size();
//        if(count == 90) {    //if the player opened all clear cells
//            try {
//                FieldDB.dropTable();  //he's a good guy, just end game
//            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
//                e.printStackTrace();
//            }
//        }
        String json = new Gson().toJson(cells);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    //to open cells
    private void openCell(int id, ArrayList<Cell> cells) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Cell cell = FieldDB.getCell(id);
        FieldDB.setCellChecked(id);
        cells.add(cell);

        //if the cell values 0, all nearest cells should be opened
        if (cell.getValue() == 0){
            if(id > 10 && !FieldDB.cellStatus(id-10)) {  //up
                openCell(id-10, cells);
            }
            if(id > 10 && (id-10)%10 != 1 && !FieldDB.cellStatus(id-11)) { //up-left
                openCell(id-11, cells);
            }
            if(id > 10 && (id-10)%10 != 0 && !FieldDB.cellStatus(id-9)) { //up-right
                openCell(id-9, cells);
            }
            if(id%10 != 1 && !FieldDB.cellStatus(id-1)){ //left
                openCell(id-1, cells);
            }
            if(id%10 != 0 && !FieldDB.cellStatus(id+1)){ //right
                openCell(id+1, cells);
            }
            if(id < 91 && !FieldDB.cellStatus(id+10)){ //down
                openCell(id+10, cells);
            }
            if(id < 91 &&(id+10)%10 != 1 && !FieldDB.cellStatus(id+9)){ //down-left
                openCell(id+9, cells);
            }
            if(id < 91 &&(id+10)%10 != 0 && !FieldDB.cellStatus(id+11)){ //down-right
                openCell(id+11, cells);
            }
        }
    }
}
