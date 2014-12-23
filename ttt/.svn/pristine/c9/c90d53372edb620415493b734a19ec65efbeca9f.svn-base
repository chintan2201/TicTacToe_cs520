package ttt.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import ttt.model.Game;
import ttt.model.Player;

@Test(groups = "PlayerDaoTest")
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class GameDaoTest extends AbstractTransactionalTestNGSpringContextTests {

	  @Autowired
	  GameDao gameDao;

	   Player p = new Player();
	    
	    @Test
	    public void getUserAIWinLoss()
	    {
	    	int t=0;
	    	p.setPlayer_id(1);
	    	for(Game game: gameDao.getGamesAgainstAI(p))
	    	{
	    		if(game.getWin_player()==null && game.getLoss_player()!= null)
	    		{
	    			if(game.isTie()==false)
	    				t++;
	    		}
	    		if(game.getWin_player()!=null && game.getLoss_player()== null)
	    		{
	    			if(game.isTie()==false)
	    				t++;
	    		}
	    	}
	    	
	        assert t==2;
	    }
}
