package ttt.model;

import java.util.Date;
import java.util.List;

public class GamePlay {

	public GamePlay()
	{}
	
	String gamename;
	
	List<String> hostposition;
	
	List<String> joinposition;
	
	boolean IsHostTurn;
	
	Date starttime;
	
	String result;

	public String getGamename() {
		return gamename;
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}

	public List<String> getHostposition() {
		return hostposition;
	}

	public void setHostposition(List<String> hostposition) {
		this.hostposition = hostposition;
	}

	public List<String> getJoinposition() {
		return joinposition;
	}

	public void setJoinposition(List<String> joinposition) {
		this.joinposition = joinposition;
	}

	

	public boolean isIsHostTurn() {
		return IsHostTurn;
	}

	public void setIsHostTurn(boolean isHostTurn) {
		IsHostTurn = isHostTurn;
	}


	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	
}
