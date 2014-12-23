package ttt.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="savedposition")
public class SavedPosition implements Serializable{
	
	public static final long serialVersionUID = 1L;
	
	 @Id
	 @GeneratedValue
	 private int savedgameposition_id;

	 private  String position;
	 
	 private boolean IsAiPlayerPosition; 
	 
	 @ManyToOne
	 @JoinColumn(name="game_id")
	 private Game game_id;
	 
	public SavedPosition()
   	{
			
	}

	public int getSavedgameposition_id() {
		return savedgameposition_id;
	}

	public void setSavedgameposition_id(int savedgameposition_id) {
		this.savedgameposition_id = savedgameposition_id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public boolean isIsAiPlayerPosition() {
		return IsAiPlayerPosition;
	}

	public void setIsAiPlayerPosition(boolean isAiPlayerPosition) {
		IsAiPlayerPosition = isAiPlayerPosition;
	}

	public Game getGame_id() {
		return game_id;
	}

	public void setGame_id(Game game_id) {
		this.game_id = game_id;
	}

	
	
}
