package ttt.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;


@Test(groups = "PlayerDaoTest")
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PlayerDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    PlayerDao playerDao;

    // TestNG -1 There is one user with the username cysun.
    
    @Test
    public void getPlayer()
    {
    	assert playerDao.getPlayers("cysun").size() == 1;
    }
}
