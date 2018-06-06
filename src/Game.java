/*
 * Class used to store info about a GMR game a player is involved in.
 */
import java.util.ArrayList;

public class Game {
    public String Name;
    public long GameId;
    public ArrayList<GamePlayer> Players;
    public Turn CurrentTurn;
}
