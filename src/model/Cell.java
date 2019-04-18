package model;

//this bean describes database row
public class Cell{
    private int id;
    private int value;
    private boolean status;
    private boolean flag;

    public Cell(){
        id = 0;
        value = 0;
        flag = false;
        }

    public void setId(int id) {
        this.id = id;
        }
    public int getId(){
        return id;
    }
    public int getValue() {
        return value;
        }

    public void setValue(int value) {
        this.value = value;
    }
    public void setStatus(boolean status){
            this.status = status;
    }
    public boolean getStatus(){
        return status;
    }
    public boolean getFlag(){
        return flag;
    }
    public void setFlag(boolean flag){
        this.flag = flag;
    }
}

