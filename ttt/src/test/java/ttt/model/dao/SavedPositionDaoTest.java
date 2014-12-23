package ttt.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import ttt.model.Game;
import ttt.model.Player;
import ttt.model.SavedPosition;

@Test(groups = "PlayerDaoTest")
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SavedPositionDaoTest extends AbstractTransactionalTestNGSpringContextTests {

	  @Autowired
	  SavedPositionDao savedPositionDao;
	  
	  @Autowired
	  GameDao gameDao;

	  Player p = new Player();
	    
	    @Test
	    public void getUserAIWinLoss()
	    {
	    	int t=0;
	    	int x = 0;
	    	p.setPlayer_id(1);
	    	for(Game game: gameDao.getSavedGames(p))
	    	{
	    		int gid = game.getGame_id();
	    		List<SavedPosition> position  = savedPositionDao.getPosition(gid);
	    		
	    		for(int i=0;i<position.size();i++)
	    		{
	    			if(position.get(i).isIsAiPlayerPosition()==false)
	    			{
	    				if(position.get(i).getPosition().equals("1-1"))
	    				{
	    					x = 1;
	    				}
	    			}
	    			if(x == 1)
	    			{
	    				if(position.get(i).getPosition().equals("2-2"))
	    				{
	    					t++;
	    				}
	    			}
	    		}
	    		//System.out.println(gid);
	    		/*for(int i=0;i<position.size();i++)
	    		{
	    			t++;
	    		}*/
	    	}
	    	
	        assert t==1;
	    }
}
