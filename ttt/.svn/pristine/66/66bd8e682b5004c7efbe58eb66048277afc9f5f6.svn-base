package ttt.service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class GameListService {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	List<String> games;
	List<DeferredResult<String>> results;
	
	public GameListService()
    {
        games = new ArrayList<String>();
        results = new ArrayList<DeferredResult<String>>();
    }
	
	 public List<String> getGames()
	    {
	        return games;
	    }

	    public void add( String player1,String player2 )
	    {
	    	String gamename = player1+"-"+player2;
	        games.add( gamename );
	        processDeferredResults();
	    }

	    public void remove( String player1,String player2 )
	    {
	    	String gamename = player1+"-"+player2;
	        games.remove( gamename );
	        processDeferredResults();
	    }

	    public void addResult( DeferredResult<String> result )
	    {
	        results.add( result );
	    }

	    private void processDeferredResults()
	    {
	        // convert username list to json
	    	StringWriter sw = new StringWriter();
	        try
	        {
	            objectMapper.writeValue( sw, games );
	        }
	        catch( Exception e )
	        {
	            
	        }
	        String json = sw.toString();
	        // process queued results
	       for(DeferredResult<String> result : results)
	       {
	    	   	result.setResult(json);
	       }
	       results.clear();
	    }

}
