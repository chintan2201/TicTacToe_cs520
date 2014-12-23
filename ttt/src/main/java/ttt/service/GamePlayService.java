package ttt.service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import ttt.model.GamePlay;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GamePlayService {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	HashMap<String,GamePlay> games;
	List<DeferredResult<HashMap<String,GamePlay>>> results;
	
	public GamePlayService()
    {
        games = new HashMap<String,GamePlay>();
        results = new ArrayList<DeferredResult<HashMap<String,GamePlay>>>();
    }
	
	 public HashMap<String,GamePlay> getGames()
    {
        return games;
    }

	    public void add(String gamename,GamePlay g)
	    {
	        games.put(gamename, g);
	        processDeferredResults();
	    }

	    public void remove( String gamename )
	    {
	        games.remove(gamename);
	        processDeferredResults();
	    }

	    public void addResult( DeferredResult<HashMap<String,GamePlay>> result )
	    {
	        results.add( result );
	    }

	    private void processDeferredResults()
	    {
	        // convert username list to json
	    	StringWriter sw = new StringWriter();
	    	HashMap<String,GamePlay> json= new HashMap<String,GamePlay>();
	        try
	        {
	           // json = objectMapper.writeValueAsString( games );
	        	json = games;
	        }
	        catch( Exception e )
	        {
	            
	        }
	       
	        //HashMap<String,GamePlay> json = (HashMap<String,GamePlay>)sw.toString();
	        // process queued results
	        synchronized(results){
		       for(DeferredResult<HashMap<String,GamePlay>> result : results)
		       {
		    	   	result.setResult(json);
		       }
	        }
	       results.clear();
	    }
}
