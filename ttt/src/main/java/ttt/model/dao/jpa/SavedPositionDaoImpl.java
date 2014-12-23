package ttt.model.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ttt.model.Game;
import ttt.model.SavedPosition;
import ttt.model.dao.SavedPositionDao;

@Repository
public class SavedPositionDaoImpl implements SavedPositionDao{
	
	@PersistenceContext
    private EntityManager entityManager;
	
	


	@Override
	public List<SavedPosition> getPosition(int gameid) {
		
		return  entityManager.createQuery("from SavedPosition where game_id="+gameid+"",SavedPosition.class).getResultList();//, SavedPosition.class).getResultList();
		

	}


	@Override
	@Transactional 
	public SavedPosition SavePositions(SavedPosition s) {
		// TODO Auto-generated method stub
		return entityManager.merge(s);
	}


	@Override
	@Transactional
	public boolean deleteRecord(int sid) {
	
		SavedPosition sp = entityManager.find(SavedPosition.class, sid);
		entityManager.remove(sp);
		//entityManager.createQuery("Delete from SavedPosition where savedgameposition_id = "+sid+"", SavedPosition.class).executeUpdate();
		return false;
	}


	
	


}
