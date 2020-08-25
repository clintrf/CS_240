package familymap.client.Model;

public class DisplayRow {
    String topRow;
    String bottomRow;
    String type;
    String IdOfRow;

    public DisplayRow(String topRow, String bottomRow, String type, String Id){
        this.topRow = topRow;
        this.bottomRow = bottomRow;
        this.type = type;
        this.IdOfRow = Id;
    }

    public String getTopRow(){
        return topRow;
    }

    public String getBottomRow(){
        return bottomRow;
    }

    public String getType(){
        return type;
    }

    public String getIdOfRow() {
        return IdOfRow;
    }

}