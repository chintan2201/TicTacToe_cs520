package ttt.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="players")
public class Player  implements Serializable{
	
	public static final long serialVersionUID = 1L;
	
	 @Id
	 @GeneratedValue
	 private int player_id;
	
	 private String username;
	
	 private String password;
	
	 private String email;
	
	 @OneToMany(cascade=CascadeType.ALL)
	 @JoinTable(name="playerplayedgames" , 
			 joinColumns={ @JoinColumn(name="players_player_id" ,referencedColumnName = "player_id", insertable = true, updatable = false)},
		     inverseJoinColumns={ @JoinColumn(name="playerplayedgames_game_id", referencedColumnName = "game_id",insertable = true,updatable = false) }
			 )
	 private List<Game> playerplayedgames;
	 
	 @OneToMany(cascade=CascadeType.ALL)
	 @JoinTable(name="playersavedgames")
	 private List<Game> playersavedgames;
	
	public Player()
	{
		
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Game> getPlayedgames() {
		return playerplayedgames;
	}

	public void setPlayedgames(List<Game> playedgames) {
		this.playerplayedgames = playedgames;
	}

	public List<Game> getSavedgames() {
		return playersavedgames;
	}

	public void setSavedgames(List<Game> savedgames) {
		this.playersavedgames = savedgames;
	}
	
	
	
	
	
}
