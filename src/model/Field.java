package model;

// to describe playing field
public class Field {
    private Cell[][] field;

    public Field() {
        field = new Cell[10][10];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = new Cell();
            }
        }
    }

    public Cell[][] getField() {
        return field;
    }

    public void generateField() {
        int mine = 0;
        while(mine < 10) {
            int n = (int) (Math.random() * 10);
            int m = (int) (Math.random() * 10);
            if (field[n][m].getValue() == 0) {
                field[n][m].setValue(-1);
                mine++;
            }
        }

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j].getValue() != -1) {
                    if (i>0 && j>0 && field[i - 1][j - 1].getValue() == -1) field[i][j].setValue(field[i][j].getValue() + 1);
                    if (i>0 && field[i - 1][j].getValue() == -1) field[i][j].setValue(field[i][j].getValue() + 1);
                    if (j>0 && field[i][j - 1].getValue() == -1) field[i][j].setValue(field[i][j].getValue() + 1);
                    if (i>0 && j<9 && field[i - 1][j + 1].getValue() == -1) field[i][j].setValue(field[i][j].getValue() + 1);
                    if (i<9 && j>0 && field[i + 1][j - 1].getValue() == -1) field[i][j].setValue(field[i][j].getValue() + 1);
                    if (j<9 && field[i][j + 1].getValue() == -1) field[i][j].setValue(field[i][j].getValue() + 1);
                    if (i<9 && field[i + 1][j].getValue() == -1) field[i][j].setValue(field[i][j].getValue() + 1);
                    if (i<9 && j<9 && field[i + 1][j + 1].getValue() == -1) field[i][j].setValue(field[i][j].getValue() + 1);
                }
                field[i][j].setId(i*10 + j + 1);
            }
        }
    }

}
