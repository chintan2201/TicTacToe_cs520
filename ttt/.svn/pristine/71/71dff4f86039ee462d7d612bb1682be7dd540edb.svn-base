package ttt.service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JoinListService{
	
	 private static final ObjectMapper objectMapper = new ObjectMapper();

	 List<String> usernames;
	 
	 List<DeferredResult<String>> results;
	 
	 public JoinListService()
    {
        usernames = new ArrayList<String>();
        results = new ArrayList<DeferredResult<String>>();
    }
	 
	   public List<String> getUsernames()
	    {
	        return usernames;
	    }

	    public void add( String username )
	    {
	        usernames.add( username );
	        processDeferredResults();
	    }

	    public void remove( String username )
	    {
	        usernames.remove( username );
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
	           
	            objectMapper.writeValue( sw, usernames );
	           
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
