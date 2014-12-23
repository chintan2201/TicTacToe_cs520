package ttt.model.dao.jpa;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ttt.model.Game;
import ttt.model.Player;
import ttt.model.dao.GameDao;

@Repository
public class GameDaoImpl implements GameDao{

	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public List<Game> getGamesAgainstAI(Player user) {
		int playerid = user.getPlayer_id();
		return entityManager.createQuery("from Game where player1 = "+playerid+" and player2 = null and savetime is null", Game.class).getResultList();
	}

	@Override
	@PreAuthorize("principal.username == #user.username")
	public List<Game> getSavedGames(Player user) {
		// TODO Auto-generated method stub
		int playerid = user.getPlayer_id();
		return entityManager.createQuery("from Game where player1 = "+playerid+" and player2 = null and savetime is not null", Game.class).getResultList();
	}

	@Override
	@Transactional
	public Game SaveGame(Game game) {
		return entityManager.merge(game);
	}

	@Override
	public Game getGamewithstime(Player p,Date starttimeforAI) {
		
		return entityManager.createQuery("from Game where player1="+p.getPlayer_id()+"and starttime = :st",Game.class).setParameter("st", starttimeforAI).getSingleResult();
	}

	@Override
	public List<Game> getAllGames(Player p) {
		
		return entityManager.createQuery("from Game where player1=:pl", Game.class).setParameter("pl", p).getResultList();
	}

	@Override
	public List<Game> getAllCompletedGames(Player p) {
		// TODO Auto-generated method stub
		return entityManager.createQuery("from Game where (player1="+p.getPlayer_id()+"or player2="+p.getPlayer_id()+") and isSave = false",Game.class).getResultList();
	}

	@Override
	public List<Game> get1PlayerCompletedgames(Player p) {
		// TODO Auto-generated method stub
		return entityManager.createQuery("from Game where (player1="+p.getPlayer_id()+"and player2 = null) and isSave = false",Game.class).getResultList();
	}

	@Override
	public List<Game> get2PlayerCompletedgames(Player p) {
		// TODO Auto-generated method stub
		return entityManager.createQuery("from Game where  player2 is not null and (player1="+p.getPlayer_id()+" or player2 = "+p.getPlayer_id()+") and isSave = false and endtime is not null",Game.class).getResultList();
	}

	@Override
	public Game getGameBySaveTime(Date Savetime, Player p1) {
		// TODO Auto-generated method stub
		
		return entityManager.createQuery("from Game where player1="+p1.getPlayer_id()+" and savetime=:savet", Game.class).setParameter("savet",Savetime).getSingleResult();
	}

	@Override
	public Game getGamebyId(int id) {
		
		return entityManager.find(Game.class, id);
	}

	@Override
	public Game getGamebystTimeTwoPl(Player p1, Player p2, Date starttime) {
		return entityManager.createQuery("from Game where player1="+p1.getPlayer_id()+"and player2="+p2.getPlayer_id()+"and starttime = :st",Game.class).setParameter("st", starttime).getSingleResult();
	}

	@Override
	public List<Game> getalltwoplayergames(Player p) {
		return entityManager.createQuery("from Game where player1=:pl or player2 =:pl", Game.class).setParameter("pl", p).getResultList();
	}

}
