package ttt.model.dao;
import java.util.List;

import ttt.model.Game;
import ttt.model.Player;

public interface PlayerDao {

	
	
	Player getPlayer(String username);
	
	List<Player> getPlayers(String username);
	
	boolean credentialcheck(String username , String password);
	
	Player SavePlayer(Player player);
	
	boolean addplayergame(List<Game> glist,int pid);
	
	boolean addplayersavedgame(List<Game> glist,int pid);
	
}
