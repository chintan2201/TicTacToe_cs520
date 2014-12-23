package ttt.model.dao.jpa;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ttt.model.Game;
import ttt.model.Player;
import ttt.model.dao.PlayerDao;

@Repository
public class PlayerDaoImpl  implements PlayerDao{
	@PersistenceContext
    private EntityManager entityManager;

	
	
    @Override
    public Player getPlayer( String username )
    {	
    	
    	return entityManager.createQuery("from Player where username = :username", Player.class).setParameter("username", username).getSingleResult();
    
    }

    @Override
    public List<Player> getPlayers(String username)
    {
        return entityManager.createQuery( "from Player where username = '"+username+"'", Player.class )
            .getResultList();
        
    }

	@Override
	public boolean credentialcheck(String username, String password) {
		
		List<Player> player = entityManager.createQuery("from Player where username = :username and password=:password", Player.class).setParameter("username", username).setParameter("password", password).getResultList();
		if(player.size() == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	@Transactional
	public Player SavePlayer(Player player) {
		
		return entityManager.merge(player); 
	}

	@Override
	@Transactional
	public boolean addplayergame(List<Game> glist,int pid) {
		Player p = new Player();
		p = entityManager.find(Player.class, pid);
	List<Game> g = p.getPlayedgames();
	System.out.println(g.size());
	p.setPlayedgames(glist);
		entityManager.merge(p);
		return true;
		/*if(g.size()==0){
			p.setPlayedgames(glist);
			entityManager.merge(p);
			return true;
		}
		if(g.size()>0){
			for(int i=0;i<g.size();i++){
				if(glist.get(0).getGame_id()!=g.get(i).getGame_id()){
					//Player p1= new Player();
					//p1.setPlayer_id(pid);
					p.setPlayedgames(glist);
					entityManager.persist(p);
					
					return true;
				}
			}
	}*/
		//return true;
	}

	@Override
	@Transactional
	public boolean addplayersavedgame(List<Game> glist, int pid) {
		Player p = new Player();
		p = entityManager.find(Player.class, pid);
		p.setSavedgames(glist);
		entityManager.merge(p);
		
		return true;
	}

}

