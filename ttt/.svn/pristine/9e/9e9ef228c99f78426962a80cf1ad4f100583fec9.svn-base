package ttt.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="games")
public class Game implements Serializable{

	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int game_id;
	
	@ManyToOne
	@JoinColumn(name="player1")
	private Player player1;
	
	@ManyToOne
	@JoinColumn(name="player2")
	private Player player2;
	
	private Date starttime;
	
	private Date endtime;
	
	@ManyToOne
	@JoinColumn(name="win_player")
	private Player win_player;
	
	@ManyToOne
	@JoinColumn(name="loss_player")
	private Player loss_player;
	
	private boolean isTie;
	
	private boolean isSave ;
	
	private Date savetime;
	
	
	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}


	public Player getWin_player() {
		return win_player;
	}

	public void setWin_player(Player win_player) {
		this.win_player = win_player;
	}

	public Player getLoss_player() {
		return loss_player;
	}

	public void setLoss_player(Player loss_player) {
		this.loss_player = loss_player;
	}

	public boolean isTie() {
		return isTie;
	}

	public void setTie(boolean isTie) {
		this.isTie = isTie;
	}


	public boolean isSave() {
		return isSave;
	}

	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Date getSavetime() {
		return savetime;
	}

	public void setSavetime(Date savetime) {
		this.savetime = savetime;
	}

	
	
	
	
}
