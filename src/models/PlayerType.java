package models;

public enum PlayerType {
    white, black;

    public PlayerType reverse(){
        if (this == white){
            return black;
        }else
            return white;
    }


}
