package ttt.web.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.spi.http.HttpContext;








import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import ttt.model.Game;
import ttt.model.GamePlay;
import ttt.model.Player;
import ttt.model.SavedPosition;
import ttt.model.dao.GameDao;
import ttt.model.dao.PlayerDao;
import ttt.model.dao.SavedPositionDao;
import ttt.service.GameListService;
import ttt.service.GamePlayService;
import ttt.service.HostListService;
import ttt.service.JoinListService;

@Controller
public class PlayerController  {

	    boolean checkentryAI  = false;
	    List<String> lp1 = null;
		List<String> lp2 = null;
		Date starttimeforAI = null;
		Date savetimefroAI = null;
		int playeridentity = 0;
		String gameinterrupt = null;
		//String gamenamewithunder = null;
	    
	    @Autowired
	    PlayerDao playerDao;
	    
	    @Autowired
	    GameDao gameDao;
	    
	    @Autowired
	    SavedPositionDao savedpositionDao;

	    @Autowired
	    JoinListService jls;

	    @Autowired
	    HostListService hls;
	    
	    @Autowired
	    GameListService gls;
	    
	    @Autowired
	    GamePlayService gps;
	    //Spring Security
	    @RequestMapping(value="/login1", method = RequestMethod.GET)
	    public String login1(ModelMap models,HttpSession session)
	    {
	    	return "login1";
	    }
	    
	    @RequestMapping(value="/loginfailed", method = RequestMethod.GET)
		public String loginerror(ModelMap model) {
	 
			model.addAttribute("error", "true");
			return "login1";
	 
		}
	    
	    @RequestMapping(value="/player/logout.html", method = RequestMethod.GET)
		public String logout(ModelMap models,HttpSession session,@RequestParam String m,@RequestParam String type) {
	    
	    	if(m.equals("intt") && type.equals("ap"))
    		{
	    		if(session.getAttribute("savetime") == null)
	    		{
	    			Game ga = new Game();
	    			Player p = playerDao.getPlayer(session.getAttribute("username").toString());
	    			ga = gameDao.getGamewithstime(p,starttimeforAI);
	    			
	    			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		    		   //get current date time with Date()
		    		   Date sdate = new Date();
			    		try {
							ga.setEndtime(dateFormat.parse(dateFormat.format(sdate)));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		ga.setLoss_player(p);
		    		ga.setTie(false);
		    		ga.setSave(false);
		    		gameDao.SaveGame(ga);
	    		}
	    		else
	    		{
	    			Game g = new Game();
	    			Player p = playerDao.getPlayer(session.getAttribute("username").toString());
	    				//g = gameDao.getGameBySaveTime((Date)(session.getAttribute("savetime")), p);
	    			g = gameDao.getGamebyId((Integer)session.getAttribute("savetime"));
	    				g.setSavetime(null);
	    			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		    		   //get current date time with Date()
		    		   Date sdate = new Date();
			    		try {
							g.setEndtime(dateFormat.parse(dateFormat.format(sdate)));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		
			    		    g.setLoss_player(p);
				    		g.setTie(false);
				    		g.setSave(false);
			    		
		    		gameDao.SaveGame(g);
    				
    				//2. playersavedgames table update
		    		List<Game> gsavelist = new ArrayList<Game>();
	    			gsavelist = gameDao.getSavedGames(p);
	    			playerDao.addplayersavedgame(gsavelist,p.getPlayer_id());
	    			//3
	    			List<SavedPosition> positions = savedpositionDao.getPosition(g.getGame_id());
	    			for(int i=0;i< positions.size();i++)
	    			{
	    				savedpositionDao.deleteRecord(positions.get(i).getSavedgameposition_id());
	    			}
	    		}
	    	}
	    	
	    	session.invalidate();
		   // models.put( "player", new Player() );
			return "redirect:/j_spring_security_logout";
			
	 
		}
	    
	    @RequestMapping(value="/welcome", method = RequestMethod.GET)
		public String printWelcome(ModelMap model,HttpSession session,Principal principal) {
	 
			String name = principal.getName();
			session.setAttribute("username", name);
			return "redirect:/player/GameHome.html";
	 
		}
	    
	    ///
	    @RequestMapping(value= "/player/Login.html", method = RequestMethod.GET)
	    public String LoginDisplay(ModelMap models,HttpSession session,@RequestParam String m,@RequestParam String type)
	    {
	    	if(m.equals("intt") && type.equals("ap"))
    		{
	    		if(session.getAttribute("savetime") == null)
	    		{
	    			Game ga = new Game();
	    			Player p = playerDao.getPlayer(session.getAttribute("username").toString());
	    			ga = gameDao.getGamewithstime(p,starttimeforAI);
	    			
	    			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		    		   //get current date time with Date()
		    		   Date sdate = new Date();
			    		try {
							ga.setEndtime(dateFormat.parse(dateFormat.format(sdate)));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		ga.setLoss_player(p);
		    		ga.setTie(false);
		    		ga.setSave(false);
		    		gameDao.SaveGame(ga);
	    		}
	    		else
	    		{
	    			Game g = new Game();
	    			Player p = playerDao.getPlayer(session.getAttribute("username").toString());
	    				//g = gameDao.getGameBySaveTime((Date)(session.getAttribute("savetime")), p);
	    			g = gameDao.getGamebyId((Integer)session.getAttribute("savetime"));
	    				g.setSavetime(null);
	    			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		    		   //get current date time with Date()
		    		   Date sdate = new Date();
			    		try {
							g.setEndtime(dateFormat.parse(dateFormat.format(sdate)));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		
			    		    g.setLoss_player(p);
				    		g.setTie(false);
				    		g.setSave(false);
			    		
		    		gameDao.SaveGame(g);
    				
    				//2. playersavedgames table update
		    		List<Game> gsavelist = new ArrayList<Game>();
	    			gsavelist = gameDao.getSavedGames(p);
	    			playerDao.addplayersavedgame(gsavelist,p.getPlayer_id());
	    			//3
	    			List<SavedPosition> positions = savedpositionDao.getPosition(g.getGame_id());
	    			for(int i=0;i< positions.size();i++)
	    			{
	    				savedpositionDao.deleteRecord(positions.get(i).getSavedgameposition_id());
	    			}
	    		}
	    	}
	    	
	    	session.invalidate();
		    models.put( "player", new Player() );
	        return "player/Login";
	    }
	    
	    @RequestMapping(value = "/player/Login.html", method = RequestMethod.POST)
	    public String LoginCheck( @ModelAttribute("player") Player player,HttpSession session ,ModelMap models)
	    {
	        boolean test = playerDao.credentialcheck(player.getUsername(), player.getPassword());
	        if(test == true)
	        {
	        	session.setAttribute("username", player.getUsername());
	        	models.put("player", player.getUsername());
	        	return "redirect:GameHome.html";
	        }
	        else
	        {
	        	 return "player/Login";
	        }
	    }
	    
	    @RequestMapping("/player/GameHome.html")
	    public String GameHome(ModelMap models,HttpSession session)
	    {
	    	/*if(session.getAttribute("username")==null)
	    	{
	    		return "redirect:login1.html";
	    	}
	    	else
	    	{
	    		*/
	    		models.put( "player", session.getAttribute("username"));
	       		return "player/GameHome";
	    	//}
	    }
	    
	    @RequestMapping(value= "/Registration", method = RequestMethod.GET)
	    public String RegistrationDisplay(ModelMap models)
	    {
		    models.put( "Play", new Player() );
	        return "Registration";
	    }
	    
	    @RequestMapping(value="/Registration", method = RequestMethod.POST)
	    public String RegistrationProcess(@ModelAttribute("Play") Player player)
	    {
	    	if(player.getUsername()!= "" && player.getPassword()!= "" && player.getEmail()!= "")
	    	{
	    		playerDao.SavePlayer(player);
	    		return "redirect:login1.html";
	    	}
	    	else
	    	{
	    		return "Registration";
	    	}
	    	
	    }
	    
	    @SuppressWarnings("unchecked")
		@RequestMapping(value= "/player/GameWithAI.html", method = RequestMethod.GET)
	    public String GameWithAIDisplay(ModelMap models,HttpSession session,ModelMap model,@RequestParam String m)
	    {
	    	/*if(session.getAttribute("username")==null)
	    	{
	    		return "redirect:login1.html";
	    	}
	    	else
	    	{*/
	    		if(m.equals("intt"))
	    		{
	    			if(session.getAttribute("savetime") == null)
	    			{
		    			Game ga = new Game();
		    			Player p = playerDao.getPlayer(session.getAttribute("username").toString());
		    			ga = gameDao.getGamewithstime(p,starttimeforAI);
		    			
		    			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			    		   //get current date time with Date()
			    		   Date sdate = new Date();
				    		try {
								ga.setEndtime(dateFormat.parse(dateFormat.format(sdate)));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			    		ga.setLoss_player(p);
			    		ga.setTie(false);
			    		ga.setSave(false);
			    		gameDao.SaveGame(ga);			    		
	    			}
	    			else
	    			{
	    				Game g = new Game();
		    			Player p = playerDao.getPlayer(session.getAttribute("username").toString());
		    				//g = gameDao.getGameBySaveTime((Date)(session.getAttribute("savetime")), p);
		    			g = gameDao.getGamebyId((Integer)session.getAttribute("savetime"));
		    				g.setSavetime(null);
		    			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			    		   //get current date time with Date()
			    		   Date sdate = new Date();
				    		try {
								g.setEndtime(dateFormat.parse(dateFormat.format(sdate)));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			    		
				    		    g.setLoss_player(p);
					    		g.setTie(false);
					    		g.setSave(false);
				    		
			    		gameDao.SaveGame(g);
	    				
	    				//2. playersavedgames table update
			    		List<Game> gsavelist = new ArrayList<Game>();
		    			gsavelist = gameDao.getSavedGames(p);
		    			playerDao.addplayersavedgame(gsavelist,p.getPlayer_id());
		    			//3
		    			List<SavedPosition> positions = savedpositionDao.getPosition(g.getGame_id());
		    			for(int i=0;i< positions.size();i++)
		    			{
		    				savedpositionDao.deleteRecord(positions.get(i).getSavedgameposition_id());
		    			}
						
	    		}
	    	}
	   	
	    		//////
	    		if(m.equals("savegame"))
	    		{
	    			//1. game table update
	    			Game g = new Game();
	    			Player p = playerDao.getPlayer(session.getAttribute("username").toString());
	    			
	    			if(session.getAttribute("savetime") == null)
	    			{
	    				g = gameDao.getGamewithstime(p,starttimeforAI);
	    		
	    			//	g= gameDao.getGameBySaveTime((Date)session.getAttribute("savetime"), p);
	    			
	    			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		    		   //get current date time with Date()
		    		   Date sdate = new Date();
			    		try {
							g.setSavetime(dateFormat.parse(dateFormat.format(sdate)));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		
			    		
				    		g.setTie(false);
				    		g.setSave(true);
			    		
		    		gameDao.SaveGame(g);
		    		session.removeAttribute("savetime");
	    			}
	    			else
	    			{
	    				//g= gameDao.getGameBySaveTime((Date)session.getAttribute("savetime"), p);
	    				g = gameDao.getGamebyId((Integer)session.getAttribute("savetime"));
		    			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			    		   //get current date time with Date()
		    			 System.out.print(g.getGame_id());
			    		   Date sdate = new Date();
				    		try {
								g.setSavetime(dateFormat.parse(dateFormat.format(sdate)));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				    		
			    		gameDao.SaveGame(g);
	    			}
		    		//2. playersavedgames table update
		    		List<Game> gsavelist = new ArrayList<Game>();
	    			gsavelist = gameDao.getSavedGames(p);
	    			playerDao.addplayersavedgame(gsavelist,p.getPlayer_id());
	    			//3
	    			
					List<String> p1 = (List<String>)session.getAttribute("list1");
	    			List<String> ai = (List<String>)session.getAttribute("list2");
	    			List<SavedPosition> positions = savedpositionDao.getPosition(g.getGame_id());
	    			for(int i=0;i< positions.size();i++)
	    			{
	    				savedpositionDao.deleteRecord(positions.get(i).getSavedgameposition_id());
	    			}
	    			/*List<SavedPosition> s1 = savedpositionDao.getPosition(g.getGame_id());
	    			if(s1.size() != 0)
	    			{
		    			for(int a=0;a<s1.size();a++)
	    				{
		    				for(int i=0 ; i< p1.size() ; i++)
		    				{
		    					if(!s1.get(a).getPosition().equals(p1.get(i)) && s1.get(a).isIsAiPlayerPosition() == false)
		    					{
				    				SavedPosition s1p = new SavedPosition();
				    				s1p.setGame_id(g);
				    				s1p.setIsAiPlayerPosition(false);
				    				s1p.setPosition(p1.get(i));
				    				savedpositionDao.SavePositions(s1p);
		    					}
		    				}
		    			}
		    			for(int a=0;a<s1.size();a++)
	    				{
			    			for(int j=0 ; j< ai.size() ; j++)
			    			{
			    				if(!s1.get(a).getPosition().equals(ai.get(j)) && s1.get(a).isIsAiPlayerPosition() == true)
			    				{
			    					SavedPosition s2p = new SavedPosition();
			    					s2p.setGame_id(g);
			    					s2p.setIsAiPlayerPosition(true);
			    					s2p.setPosition(ai.get(j));
			    					savedpositionDao.SavePositions(s2p);
			    				}
			    			}
	    				}
	    			}
	    			else
	    			{*/
	    				for(int i=0 ; i< p1.size() ; i++)
		    			{
		    				SavedPosition s3 = new SavedPosition();
		    				s3.setGame_id(g);
		    				s3.setIsAiPlayerPosition(false);
		    				s3.setPosition(p1.get(i));
		    				savedpositionDao.SavePositions(s3);
		    			}
		    			for(int j=0 ; j< ai.size() ; j++)
		    			{
		    				SavedPosition s2 = new SavedPosition();
		    				s2.setGame_id(g);
		    				s2.setIsAiPlayerPosition(true);
		    				s2.setPosition(ai.get(j));
		    				savedpositionDao.SavePositions(s2);
		    			}
	    			//}
	    			
	    			
	    		}
	    		///////
	    		session.removeAttribute("savetime");
	    		session.removeAttribute("list1");
	    		session.removeAttribute("list2");
	    		checkentryAI = false;
	    		model.put("player", session.getAttribute("username"));
	    		return "player/GameWithAI";
	    	//}
	    }
	    
	    @SuppressWarnings("unchecked")
		@RequestMapping(value= "/player/PlayWithAI.html", method = RequestMethod.GET)
	    public String GameWithAIDisplay(@RequestParam Integer r,@RequestParam Integer c,ModelMap models,HttpSession session)
	    {
	    	/*if(session.getAttribute("username")==null)
	    	{
	    		return "redirect:login1.html";
	    	}
	    	else
	    	{*/
	    		String msg = null;
	    		if(r!=0 && c!=0)
	    		{
	    		String uname = (String)session.getAttribute("username");
	    		models.put("player", uname);
	    		
	    		//set game parameters for staring entry in Game model
	    		if(checkentryAI == false)
	    		{
		    		Game g = new Game();
		    		List<Player> p = playerDao.getPlayers(uname);
		    		if (p.size() == 1)
		    		{ for(Player pl1 : p ) {g.setPlayer1(pl1);}}
		    		//g.setPlayer2(null);
		    		   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		    		   //get current date time with Date()
		    		   Date sdate = new Date();
				    		try {
				    			starttimeforAI = dateFormat.parse(dateFormat.format(sdate));
								g.setStarttime(starttimeforAI);
								
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				    //g.setEndtime(null);
				    gameDao.SaveGame(g);		
				    checkentryAI = true;
				   Player player = playerDao.getPlayer(uname);
	    			//g = gameDao.getGamewithstime(player,starttimeforAI);
				   
	    			List<Game> glist = new ArrayList<Game>();
	    			glist = gameDao.getAllGames(player);
	    			playerDao.addplayergame(glist,player.getPlayer_id());
	    		}
			    //
	    		int row1 = r;
	    		int col1 = c;
	    		
	    		// Player 1 -> X
	    		lp1 = (List<String>)session.getAttribute("list1");
	    		if (lp1 == null) {
	    			lp1 = new ArrayList<String>();
	    		}
	    		String p1 = row1 + "-" + col1;
	    		lp1.add(p1);

	    		// Player 2 - O
	    		lp2 = (List<String>) session.getAttribute("list2");
	    		if (lp2 == null) {
	    			lp2 = new ArrayList<String>();
	    		}
	    		String p2 = null;
	    		if (lp2.size() != 0) {
	    			if (lp2.size() != 1) {

	    				for (int i = 1; i <= 3; i++) {
	    					if (p2 == null) {
	    						if (lp2.contains(i + "-1") && lp2.contains(i + "-2")
	    								&& !lp1.contains(i + "-3")) {
	    							p2 = i + "-3";
	    						} else if (lp2.contains(i + "-2")
	    								&& lp2.contains(i + "-3")
	    								&& !lp1.contains(i + "-1")) {
	    							p2 = i + "-1";
	    						} else if (lp2.contains(i + "-1")
	    								&& lp2.contains(i + "-3")
	    								&& !lp1.contains(i + "-2")) {
	    							p2 = i + "-2";
	    						}
	    					}
	    				}
	    				// ----------
	    				if (p2 == null) {
	    					for (int i = 1; i <= 3; i++) {
	    						if (p2 == null) {
	    							if (lp2.contains("1-" + i)
	    									&& lp2.contains("2-" + i)
	    									&& !lp1.contains("3-" + i)) {
	    								p2 = "3-" + i;
	    							} else if (lp2.contains("2-" + i)
	    									&& lp2.contains("3-" + i)
	    									&& !lp1.contains("1-" + i)) {
	    								p2 = "1-" + i;
	    							} else if (lp2.contains("1-" + i)
	    									&& lp2.contains("3-" + i)
	    									&& !lp1.contains("2-" + i)) {
	    								p2 = "2-" + i;
	    							}
	    						}
	    					}
	    				}
	    				// -----------------
	    				if (p2 == null) {
	    					if (lp2.contains("1-1") && lp2.contains("2-2")
	    							&& !lp1.contains("3-3")) {
	    						p2 = "3-3";
	    					} else if (lp2.contains("2-2") && lp2.contains("3-3")
	    							&& !lp1.contains("1-1")) {
	    						p2 = "1-1";
	    					} else if (lp2.contains("1-1") && lp2.contains("3-3")
	    							&& !lp1.contains("2-2")) {
	    						p2 = "2-2";
	    					}
	    					// --------------------
	    					else if (lp2.contains("1-3") && lp2.contains("2-2")
	    							&& !lp1.contains("3-1")) {
	    						p2 = "3-1";
	    					} else if (lp2.contains("2-2") && lp2.contains("3-1")
	    							&& !lp1.contains("1-3")) {
	    						p2 = "1-3";
	    					} else if (lp2.contains("1-3") && lp2.contains("3-1")
	    							&& !lp1.contains("2-2")) {
	    						p2 = "2-2";
	    					}
	    				}
	    				if (p2 == null) {
	    					for (int i = 1; i <= 3; i++) {
	    						if (p2 == null) {
	    							if (lp1.contains(i + "-1")
	    									&& lp1.contains(i + "-2")
	    									&& !lp2.contains(i + "-3")) {
	    								p2 = i + "-3";
	    							} else if (lp1.contains(i + "-2")
	    									&& lp1.contains(i + "-3")
	    									&& !lp2.contains(i + "-1")) {
	    								p2 = i + "-1";
	    							} else if (lp1.contains(i + "-1")
	    									&& lp1.contains(i + "-3")
	    									&& !lp2.contains(i + "-2")) {
	    								p2 = i + "-2";
	    							}
	    						}
	    					}
	    					// ----------
	    					if (p2 == null) {
	    						for (int i = 1; i <= 3; i++) {
	    							if (p2 == null) {
	    								if (lp1.contains("1-" + i)
	    										&& lp1.contains("2-" + i)
	    										&& !lp2.contains("3-" + i)) {
	    									p2 = "3-" + i;
	    								} else if (lp1.contains("2-" + i)
	    										&& lp1.contains("3-" + i)
	    										&& !lp2.contains("1-" + i)) {
	    									p2 = "1-" + i;
	    								} else if (lp1.contains("1-" + i)
	    										&& lp1.contains("3-" + i)
	    										&& !lp2.contains("2-" + i)) {
	    									p2 = "2-" + i;
	    								}
	    							}
	    						}
	    					}
	    					// -----------------
	    					if (p2 == null) {
	    						if (lp1.contains("1-1") && lp1.contains("2-2")
	    								&& !lp2.contains("3-3")) {
	    							p2 = "3-3";
	    						} else if (lp1.contains("2-2") && lp1.contains("3-3")
	    								&& !lp2.contains("1-1")) {
	    							p2 = "1-1";
	    						} else if (lp1.contains("1-1") && lp1.contains("3-3")
	    								&& !lp2.contains("2-2")) {
	    							p2 = "2-2";
	    						}
	    						// --------------------
	    						else if (lp1.contains("1-3") && lp1.contains("2-2")
	    								&& !lp2.contains("3-1")) {
	    							p2 = "3-1";
	    						} else if (lp1.contains("2-2") && lp1.contains("3-1")
	    								&& !lp2.contains("1-3")) {
	    							p2 = "1-3";
	    						} else if (lp1.contains("1-3") && lp1.contains("3-1")
	    								&& !lp2.contains("2-2")) {
	    							p2 = "2-2";
	    						}
	    					}
	    				}
	    				if (p2 == null) {
	    					List<String> lptemp2 = new ArrayList<String>();
	    					for (int i = 1; i <= 3; i++) {
	    						for (int j = 1; j <= 3; j++) {
	    							String x = i + "-" + j;
	    							if (!lp1.contains(x) && !lp2.contains(x)) {
	    								lptemp2.add(x);
	    							}
	    						}
	    					}
	    					Random ran = new Random();
	    					if (lptemp2.size() != 0) {
	    						int index = ran.nextInt(lptemp2.size() - 1);
	    						p2 = lptemp2.get(index);
	    					}
	    				}
	    			} else {
	    				for (int i = 1; i <= 3; i++) {
	    					if (p2 == null) {
	    						if (lp1.contains(i + "-1") && lp1.contains(i + "-2")
	    								&& !lp2.contains(i + "-3")) {
	    							p2 = i + "-3";
	    						} else if (lp1.contains(i + "-2")
	    								&& lp1.contains(i + "-3")
	    								&& !lp2.contains(i + "-1")) {
	    							p2 = i + "-1";
	    						} else if (lp1.contains(i + "-1")
	    								&& lp1.contains(i + "-3")
	    								&& !lp2.contains(i + "-2")) {
	    							p2 = i + "-2";
	    						}
	    					}
	    				}
	    				// ----------
	    				if (p2 == null) {
	    					for (int i = 1; i <= 3; i++) {
	    						if (p2 == null) {
	    							if (lp1.contains("1-" + i)
	    									&& lp1.contains("2-" + i)
	    									&& !lp2.contains("3-" + i)) {
	    								p2 = "3-" + i;
	    							} else if (lp1.contains("2-" + i)
	    									&& lp1.contains("3-" + i)
	    									&& !lp2.contains("1-" + i)) {
	    								p2 = "1-" + i;
	    							} else if (lp1.contains("1-" + i)
	    									&& lp1.contains("3-" + i)
	    									&& !lp2.contains("2-" + i)) {
	    								p2 = "2-" + i;
	    							}
	    						}
	    					}
	    				}
	    				// -----------------
	    				if (p2 == null) {
	    					if (lp1.contains("1-1") && lp1.contains("2-2")
	    							&& !lp2.contains("3-3")) {
	    						p2 = "3-3";
	    					} else if (lp1.contains("2-2") && lp1.contains("3-3")
	    							&& !lp2.contains("1-1")) {
	    						p2 = "1-1";
	    					} else if (lp1.contains("1-1") && lp1.contains("3-3")
	    							&& !lp2.contains("2-2")) {
	    						p2 = "2-2";
	    					}
	    					// --------------------
	    					else if (lp1.contains("1-3") && lp1.contains("2-2")
	    							&& !lp2.contains("3-1")) {
	    						p2 = "3-1";
	    					} else if (lp1.contains("2-2") && lp1.contains("3-1")
	    							&& !lp2.contains("1-3")) {
	    						p2 = "1-3";
	    					} else if (lp1.contains("1-3") && lp1.contains("3-1")
	    							&& !lp2.contains("2-2")) {
	    						p2 = "2-2";
	    					}
	    				}
	    				// ---------
	    				if (p2 == null) {
	    					List<String> lptemp1 = new ArrayList<String>();
	    					for (int i = 1; i <= 3; i++) {
	    						for (int j = 1; j <= 3; j++) {
	    							String x = i + "-" + j;
	    							if (!lp1.contains(x) && !lp2.contains(x)) {
	    								lptemp1.add(x);
	    							}
	    						}
	    					}
	    					Random ran = new Random();
	    					if (lptemp1.size() != 0) {
	    						int index = ran.nextInt(lptemp1.size() - 1);
	    						p2 = lptemp1.get(index);
	    					}
	    				}
	    			}
	    		} else {
	    			List<String> lptemp = new ArrayList<String>();
	    			for (int i = 1; i <= 3; i++) {
	    				for (int j = 1; j <= 3; j++) {
	    					String x = i + "-" + j;
	    					if (!lp1.contains(x)) {
	    						lptemp.add(x);
	    					}
	    				}
	    			}
	    			Random ran = new Random();
	    			if (lptemp.size() != 0) {
	    				int index = ran.nextInt(lptemp.size() - 1);
	    				p2 = lptemp.get(index);
	    			}
	    		}
	    		lp2.add(p2);
	    		// ----------------
	    		for (int i = 1; i <= 3; i++) {
	    			if (msg == null) {
	    				if (lp1.contains(i + "-1") && lp1.contains(i + "-2")
	    						&& lp1.contains(i + "-3")) {
	    					msg = "You Won..!!";
	    				}
	    				if (lp1.contains("1-"+i) && lp1.contains("2-"+i)
	    						&& lp1.contains("3-"+i)) {
	    					msg = "You Won..!!";
	    				}
	    			}
	    		}
	    		if (msg == null) {
	    			if (lp1.contains("1-1") && lp1.contains("2-2")
	    					&& lp1.contains("3-3")) {
	    				msg = "You Won..!!";
	    			}
	    			if (lp1.contains("1-3") && lp1.contains("2-2")
	    					&& lp1.contains("3-1")) {
	    				msg = "You Won..!!";
	    			}
	    		}
	    		// ----------
	    		if (msg == null) {
	    			for (int i = 1; i <= 3; i++) {
	    				if (msg == null) {
	    					if (lp2.contains(i + "-1") && lp2.contains(i + "-2")
	    							&& lp2.contains(i + "-3")) {
	    						msg = "I Won..!!";
	    					}
	    					if (lp2.contains("1-"+i) && lp2.contains("2-"+i)
		    						&& lp2.contains("3-"+i)) {
		    					msg = "I Won..!!";
		    				}
	    				}
	    			}
	    			if (msg == null) {
	    				if (lp2.contains("1-1") && lp2.contains("2-2")
	    						&& lp2.contains("3-3")) {
	    					msg = "I Won..!!";
	    				}
	    				if (lp2.contains("1-3") && lp2.contains("2-2")
	    						&& lp2.contains("3-1")) {
	    					msg = "I Won..!!";
	    				}
	    			}
	    			if (msg == null) {
	    				int count = 0;
	    				for (int i = 1; i <= 3; i++) {
	    					for (int j = 1; j <= 3; j++) {
	    						String z = i + "-" + j;
	    						if (lp1.contains(z) || lp2.contains(z)) {
	    							count++;
	    						}
	    					}
	    				}
	    				if (count == 9) {
	    					msg = "Game Tied..!!";
	    				}
	    			}
	    		}
	    		// -------------------
	    		if (msg != null) {
	    			if (msg.equals("You Won..!!")) {
	    				lp2.remove(lp2.size() - 1);
	    			}
	    		}
	    	
	    		if(msg != null)
	    		{
		    		if(msg.equals("You Won..!!"))
		    		{
		    			Game ga = new Game();
		    			Player p = playerDao.getPlayer(uname);
		    			if(session.getAttribute("savetime") == null)
		    			{
			    			ga = gameDao.getGamewithstime(p,starttimeforAI);
		    			
		    			
			    			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				    		   //get current date time with Date()
				    		   Date sdate = new Date();
					    		try {
									ga.setEndtime(dateFormat.parse(dateFormat.format(sdate)));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				    		ga.setWin_player(p);
				    		ga.setTie(false);
				    		ga.setSave(false);
				    		gameDao.SaveGame(ga);
		    			}
			    		else
		    			{
		    				//ga = gameDao.getGameBySaveTime((Date)session.getAttribute("savetime"), p);
			    			ga = gameDao.getGamebyId((Integer)session.getAttribute("savetime"));
			    			ga.setSavetime(null);
		    				 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				    		   //get current date time with Date()
				    		   Date sdate = new Date();
					    		try {
									ga.setEndtime(dateFormat.parse(dateFormat.format(sdate)));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				    		ga.setWin_player(p);
				    		ga.setTie(false);
				    		ga.setSave(false);
				    		gameDao.SaveGame(ga);
				    		
				    		//2
				    		List<Game> gsavelist = new ArrayList<Game>();
			    			gsavelist = gameDao.getSavedGames(p);
			    			playerDao.addplayersavedgame(gsavelist,p.getPlayer_id());
		    			
			    			//3
			    			List<SavedPosition> positions = savedpositionDao.getPosition(ga.getGame_id());
			    			for(int i=0;i< positions.size();i++)
			    			{
			    				savedpositionDao.deleteRecord(positions.get(i).getSavedgameposition_id());
			    			}
		    			}
				    		
			    			
		    		}
		    		if(msg.equals("I Won..!!"))
		    		{
		    			Game ga = new Game();
		    			Player p = playerDao.getPlayer(uname);
		    			if(session.getAttribute("savetime") == null)
		    			{
			    		
			    			ga = gameDao.getGamewithstime(p,starttimeforAI);
			    			
			    			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				    		   //get current date time with Date()
				    		   Date sdate = new Date();
					    		try {
									ga.setEndtime(dateFormat.parse(dateFormat.format(sdate)));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				    		ga.setLoss_player(p);
				    		ga.setTie(false);
				    		ga.setSave(false);
				    		gameDao.SaveGame(ga);
		    			}
		    			else
		    			{
		    			
			    			
			    			//ga =gameDao.getGameBySaveTime((Date)session.getAttribute("savetime"), p);
		    				ga = gameDao.getGamebyId((Integer)session.getAttribute("savetime"));
		    				ga.setSavetime(null);
			    			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				    		   //get current date time with Date()
				    		   Date sdate = new Date();
					    		try {
									ga.setEndtime(dateFormat.parse(dateFormat.format(sdate)));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				    		ga.setLoss_player(p);
				    		ga.setTie(false);
				    		ga.setSave(false);
				    		gameDao.SaveGame(ga);
		    				//2
				    		List<Game> gsavelist = new ArrayList<Game>();
			    			gsavelist = gameDao.getSavedGames(p);
			    			playerDao.addplayersavedgame(gsavelist,p.getPlayer_id());
		    			
			    			//3
			    			List<SavedPosition> positions = savedpositionDao.getPosition(ga.getGame_id());
			    			for(int i=0;i< positions.size();i++)
			    			{
			    				savedpositionDao.deleteRecord(positions.get(i).getSavedgameposition_id());
			    			}
		    			}
			    		
		    		}
		    		if(msg.equals("Game Tied..!!"))
		    		{
		    			Game ga = new Game();
		    			Player p = playerDao.getPlayer(uname);
		    			if(session.getAttribute("savetime") == null)
		    			{
			    			
			    			ga = gameDao.getGamewithstime(p,starttimeforAI);
			    			
			    			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				    		   //get current date time with Date()
				    		   Date sdate = new Date();
					    		try {
									ga.setEndtime(dateFormat.parse(dateFormat.format(sdate)));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				    		ga.setTie(true);
				    		ga.setSave(false);
				    		gameDao.SaveGame(ga);
		    			}
		    			else
		    			{
		    				
			    			
			    			//ga =gameDao.getGameBySaveTime((Date)session.getAttribute("savetime"), p);
		    				ga = gameDao.getGamebyId((Integer)session.getAttribute("savetime"));
		    				ga.setSavetime(null);
			    			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				    		   //get current date time with Date()
				    		   Date sdate = new Date();
					    		try {
									ga.setEndtime(dateFormat.parse(dateFormat.format(sdate)));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				    		ga.setTie(true);
				    		ga.setSave(false);
				    		gameDao.SaveGame(ga);
				    		
				    		//2
				    		List<Game> gsavelist = new ArrayList<Game>();
			    			gsavelist = gameDao.getSavedGames(p);
			    			playerDao.addplayersavedgame(gsavelist,p.getPlayer_id());
		    			
			    			//3
			    			List<SavedPosition> positions = savedpositionDao.getPosition(ga.getGame_id());
			    			for(int i=0;i< positions.size();i++)
			    			{
			    				savedpositionDao.deleteRecord(positions.get(i).getSavedgameposition_id());
			    			}
		    			}
		    		}
	    		}
	    		
	    		// ----------------
	    		session.setAttribute("list2", lp2);
	    		session.setAttribute("list1", lp1);
	    		}
	    		models.put("message", msg);
	    		models.put("lp1", session.getAttribute("list1"));
	    		models.put("lp2", session.getAttribute("list2"));
	    		//
	    		return "player/PlayWithAI";
	    	//}
	    }
	    
	    @SuppressWarnings("deprecation")
		@RequestMapping(value= "/player/GameHistory.html", method = RequestMethod.GET)
	    public String GameHistoryDisplay(ModelMap models,HttpSession session,Principal principal)
	    {
	    	/*if(session.getAttribute("username")==null)
	    	{
	    		return "redirect:login1.html";
	    	}
	    	else
	    	{*/
	    	String un = (String)session.getAttribute("username");
	    	Player p = playerDao.getPlayer(un);
		    //1
	    	List<Game> allcompletedgames = gameDao.getAllCompletedGames(p);
	    	//2
	    	List<Game> oneplayercompletedgames = gameDao.get1PlayerCompletedgames(p);
	    	//3
	    	List<Game> twoplayercompletedgames = gameDao.get2PlayerCompletedgames(p);
	    	//4
	    	List<Game> wingamesagainst_ai = new ArrayList<Game>();
	    	for(int i=0;i<oneplayercompletedgames.size();i++)
	    	{
	    		if(oneplayercompletedgames.get(i).getWin_player() == p)
	    		{
	    			wingamesagainst_ai.add(oneplayercompletedgames.get(i));
	    		}
	    	}
	    	 DecimalFormat df2 = new DecimalFormat("###.##");
	    	double rateagainst_ai=0;
	    	if(oneplayercompletedgames.size()!=0)
	    	{
	    		
	    		 rateagainst_ai = (double)(wingamesagainst_ai.size()*100)/(double)oneplayercompletedgames.size();
	    		 
	    	}
	    	//5
	    	List<Game> wingamesagainst_hu = new ArrayList<Game>();
	    	for(int i=0;i<twoplayercompletedgames.size();i++)
	    	{
	    		if(twoplayercompletedgames.get(i).getWin_player() == p)
	    		{
	    			wingamesagainst_hu.add(twoplayercompletedgames.get(i));
	    		}
	    	}
	    	double rateagainst_hu=0 ;
	    	if(twoplayercompletedgames.size()!=0)
	    	{
	    		 rateagainst_hu = (double)(wingamesagainst_hu.size()*100)/(double)twoplayercompletedgames.size();
	    		 
	    	}
	    	//6
	    	List<Game> all = gameDao.getAllCompletedGames(p);
	    	List<Game> thismonth = new ArrayList<Game>();
	    	HashMap<Integer,String> gamelength = new HashMap<Integer,String>();
	    	Calendar c = Calendar.getInstance();
	    	int year = c.get(Calendar.YEAR);
	    	int month = c.get(Calendar.MONTH);
	    	for(int j=0;j<all.size();j++)
	    	{
	    		Calendar ds = Calendar.getInstance();
	    		Calendar de = Calendar.getInstance();
	    		if(all.get(j).getEndtime() != null)
	    		{
		    		ds.setTime(all.get(j).getStarttime());
		    		de.setTime(all.get(j).getEndtime());
		    		if(de.get(Calendar.MONTH) == month && de.get(Calendar.YEAR) == year)
		    		{
		    			thismonth.add(all.get(j));
		    		}
		    		long mss = ds.getTimeInMillis();
		    		long mse = de.getTimeInMillis();
		    		long diff = mse-mss;
		    		//long diffMinutes = diff / 1000;
		    		///
		    		long dd = diff / (24 * 60 * 60 * 1000);
		    		diff = diff - (dd*24*60*60*1000); 
		    		long dh = diff / (60 * 60 * 1000);
		    	    diff = diff - (dh * 60 * 60 * 1000 );
		    		long dm = diff / (60 * 1000);
		    		diff = diff - (dm * 60 * 1000);
		    		long dsec = diff / 1000;
		            String gl = dd+"days "+dh+"hours "+dm+"minutes "+dsec+"seconds ";
		    		////
		    		gamelength.put(all.get(j).getGame_id(), gl);
	    		}
	    	}
	    	
	    	Collections.sort(thismonth, new Comparator<Game>() {
	    		  public int compare(Game o1, Game o2) {
	    		      return o1.getEndtime().compareTo(o2.getEndtime());
	    		  }
	    		});
	    	
	    	models.put("uname", un);
	    	models.put("allcompletedgames", allcompletedgames.size());
	    	models.put("oneplayercompletedgames", oneplayercompletedgames.size());
	    	models.put("twoplayercompletedgames", twoplayercompletedgames.size());
	    	models.put("rateagainst_ai", df2.format(rateagainst_ai));
	    	models.put("rateagainst_hu", df2.format(rateagainst_hu));
	    	models.put("thismonth", thismonth);
	    	models.put("gamelength", gamelength);
	        return "player/GameHistory";
	    	//}
	    }
	    
	    
	    @RequestMapping(value= "/player/ResumeGame.html")
	    public String ResumeGameDisplay(@RequestParam Integer date,ModelMap models,HttpSession session)
	    {
	    	/*if(session.getAttribute("username")==null)
	    	{
	    		return "redirect:login1.html";
	    	}
	    	else
	    	{*/
	    //date => gameid parameter
	    	if(date == 0)
	    	{
		    	String user = (String) session.getAttribute("username");
			    models.put( "uname", session.getAttribute("username"));
			    Player p = playerDao.getPlayer(user);
			    List<Game> ga =  gameDao.getSavedGames(p);
			    models.put("ga", ga);
		        return "player/ResumeGame";
	   		}
	    	else
	    	{
	    		Game g = new Game();
		    	String user = (String) session.getAttribute("username");
		    	Player p = playerDao.getPlayer(user);
		         DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				/*try {
					Date d = null;
					d = dateFormat.parse(date);
					g = gameDao.getGameBySaveTime(d, p);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				g = gameDao.getGamebyId(date);
		    	List<SavedPosition> sp = savedpositionDao.getPosition(g.getGame_id());
		    	List<String> p1 = new ArrayList<String>();
		    	List<String> ai = new ArrayList<String>();
		    	for(int i=0;i<sp.size();i++)
		    	{
		    		if(sp.get(i).isIsAiPlayerPosition() == false)
		    		{
		    			p1.add(sp.get(i).getPosition());
		    		}
		    		else
		    		{
		    			ai.add(sp.get(i).getPosition());
		    		}
		    	}
		    
		    		// savetime = game id
					session.setAttribute("savetime", date);
				
		    	checkentryAI=true;
		    	
		    	session.setAttribute("list1", p1);
		    	session.setAttribute("list2", ai);
		        return "redirect:PlayWithAI.html?r=0&c=0";
	    	}
	    	//}	
	    }
	    
	    //Two Player Game Code...
	    //
	    
	    @RequestMapping(value= "/player/TwoPlayerHome.html", method = RequestMethod.GET)
	    public String TwoPlayerHomeDisplay(ModelMap models,HttpSession session,HttpServletRequest req)
	    {
	    	/*if(session.getAttribute("username")==null)
	    	{
	    		return "redirect:login1.html";
	    	}
	    	else
	    	{*/
		    	String uname = (String)session.getAttribute("username");
		    	models.put("un", uname);
		        return "player/TwoPlayerHome";
	    	//}
	    }
	    
	  
	   // join list display
	    boolean IsjoinList = false;
	    boolean IshostList = false;
	    
	    @RequestMapping("/player/joinlist.json")
	    public String wosJson( ModelMap models ,HttpServletRequest req)
	    {
	    	if(jls.getUsernames() != null)
	    	{
	    		models.put( "usernamesjoin", jls.getUsernames() );
	    	}
	        return "jsonView";
	    }
	    
	    @RequestMapping("/player/joinlist.deferred.json")
	    @ResponseBody
	    public DeferredResult<String> jlsDeferred()
	    {
	        DeferredResult<String> result = new DeferredResult<String>();
	        jls.addResult( result );
	        return result;
	    }
	    
	    //Host list display
	    @RequestMapping("/player/hostlist.json")
	    public String hostJson( ModelMap models ,HttpServletRequest req)
	    {
	    	if(hls.getUsernames() != null)
	    	{
	        	models.put( "usernameshost", hls.getUsernames() );
	    	}
	        return "jsonView";
	    }
	    
	    @RequestMapping("/player/hostlist.deferred.json")
	    @ResponseBody
	    public DeferredResult<String> hlsDeferred()
	    {
	        DeferredResult<String> result = new DeferredResult<String>();
	        hls.addResult( result );
	        return result;
	    }
	    
	    //Host a game
	    @SuppressWarnings("unchecked")
		@RequestMapping(value= "/player/HostGame.html", method = RequestMethod.GET)
	    public String HostGameProcess(ModelMap models,HttpSession session,HttpServletRequest req)
	    {
	    	/*if(session.getAttribute("username")==null)
	    	{
	    		return "redirect:login1.html";
	    	}
	    	else
	    	{*/
		    	String uname = (String)session.getAttribute("username");
		    	
		    	
		    	String infomsg = null;
		    	String gamemsg = null;
		    	String hostplayer = null;
		    	String joinplayer = null;
		    	if(jls.getUsernames().size() == 0)
		    	{
		    		infomsg = "Waiting for another player to join the game ...!!";
		    		//add the username in host list
			    	if(!hls.getUsernames().contains(uname))
			    		hls.add(uname);
		    	}
		    	else
		    	{
		    		hostplayer = uname;
		    		joinplayer = jls.getUsernames().get(0);
		    		if(!gls.getGames().contains(hostplayer+"-"+joinplayer));
		    			gls.add(hostplayer, joinplayer);
	    			if(jls.getUsernames().contains(joinplayer))
			    	{
			    		jls.remove(joinplayer);
			    	}
			    	if(hls.getUsernames().contains(hostplayer))
		    		{
			    		hls.remove(hostplayer);
		    		}
		    		//gamemsg =  joinplayer+" has joined the game. Please make your move."; 
		    		playeridentity=1;
		    		infomsg = null;
		    	}
		    	
		    	
		    	models.put("playerid", playeridentity);
		    	models.put("gamemsg", gamemsg);
		    	models.put("infomsg", infomsg);
		    	models.put("un", uname);
		    	
		    		models.put("gamenamewithunder", hostplayer+"_"+joinplayer);
		    	
		    	
		        return "player/TwoPlayerGameBoard";
	    	//}
	    }
	    
	    //Join a game
	    @SuppressWarnings("unchecked")
		@RequestMapping(value= "/player/JoinGame.html", method = RequestMethod.GET)
	    public String JoinGameProcess(ModelMap models,HttpSession session,HttpServletRequest req)
	    {
	    	/*if(session.getAttribute("username")==null)
	    	{
	    		return "redirect:login1.html";
	    	}
	    	else
	    	{*/
		    	String uname = (String)session.getAttribute("username");
		    	
		  
		    	String infomsg = null;
		    	String gamemsg = null;
		    	String hostplayer = null;
		    	String joinplayer = null;
		    	if(hls.getUsernames().size() == 0)
		    	{
		    		infomsg = "Waiting for another player to host the game ...!!";
		    		//add the username in join list
			    	if(!jls.getUsernames().contains(uname))
			    		jls.add(uname);
		    	}
		    	else
		    	{
		    		joinplayer = uname;
		    		hostplayer = hls.getUsernames().get(0);
		    		if(!gls.getGames().contains(hostplayer+"-"+joinplayer));
		    			gls.add(hostplayer, joinplayer);
	    			if(jls.getUsernames().contains(joinplayer))
			    	{
			    		jls.remove(joinplayer);
			    	}
			    	if(hls.getUsernames().contains(hostplayer))
		    		{
			    		hls.remove(hostplayer);
		    		}
		    		playeridentity = 2;
		    		infomsg = null;
		    	}
		    	
		    	models.put("oppPl", session.getAttribute("oppPl"));
		    	models.put("gintt", session.getAttribute("gintt"));
		    	models.put("gamemsg", gamemsg);
		    	models.put("infomsg", infomsg);
		    	models.put("un", uname);
		    	models.put("gameslist", gls.getGames());
		    		models.put("gamenamewithunder", hostplayer+"_"+joinplayer);
		    	
		    	
		        return "player/TwoPlayerGameBoard";
	    	//}
	    }
	    
	    // Game Detail JSON
	    @RequestMapping("/player/gamelist.json")
	    public String gameJson( ModelMap models ,HttpServletRequest req)
	    {
	    	if(gls.getGames() != null)
	    	{
	        	models.put( "games", gls.getGames() );
	    	}
	        return "jsonView";
	    }
	    
	    @RequestMapping("/player/gamelist.deferred.json")
	    @ResponseBody
	    public DeferredResult<String> glsDeferred()
	    {
	        DeferredResult<String> result = new DeferredResult<String>();
	        gls.addResult( result );
	        return result;
	    }
	    
	    //For exiting from two player game
	    @RequestMapping("/player/ExitGame.html")
	    public String ExitGameProcess(ModelMap models,HttpSession session,@RequestParam String c)
	    {
	    	/*if(session.getAttribute("username")==null)
	    	{
	    		return "redirect:login1.html";
	    	}
	    	else
	    	{*/
	    		String uname = (String) session.getAttribute("username");
	    		models.put( "un", session.getAttribute("username"));
	    		if(jls.getUsernames().contains(uname))
		    	{
		    		jls.remove(uname);
		    	}
		    	if(hls.getUsernames().contains(uname))
	    		{
		    		hls.remove(uname);
	    		}
		    	for(int i=0;i<gls.getGames().size();i++)
		    	{
		    		String[] g = gls.getGames().get(i).split("-");
		    		if(g[0].equals(uname) || g[1].equals(uname))
		    		{
		    			gls.remove(g[0], g[1]);
		    			break;
		    			
		    		}
		    	}
		    	String key = (String)session.getAttribute("gamenamewithunder");
	    		gps.remove(key);
	    		
	       		if(c.equals("gh"))
	       		{
	       			return "redirect:/player/GameHome.html";
	       		}
	       		else if(c.equals("th"))
	       		{
	       			return "redirect:/player/TwoPlayerHome.html";
	       		}
	       		else 
	       		{
	       			return "redirect:/player/logout.html?m=NewGame&type=FirstTime";
	       		}
	    	//}
	    }
	 
/////////////////////////
	    //Play Game with AI without Login
	    @RequestMapping(value= "/DisplayGame.html", method = RequestMethod.GET)
	    public String DisplayGame(ModelMap models,HttpSession session)
	    {
	    	session.invalidate();
	        return "DisplayGame";
	    }
	    @SuppressWarnings("unchecked")
		@RequestMapping(value= "/Play.html", method = RequestMethod.GET)
	    public String Play(ModelMap models,HttpSession session,@RequestParam Integer r,@RequestParam Integer c)
	    {
	    	int row1 = r;
			int col1 = c;
			String msg = null;
			// Player 1 -> X
			lp1 = (List<String>) session.getAttribute("list1");
			if (lp1 == null) {
				lp1 = new ArrayList<String>();
			}
			String p1 = row1 + "-" + col1;
			lp1.add(p1);

			// Player 2 - O
			lp2 = (List<String>) session.getAttribute("list2");
			if (lp2 == null) {
				lp2 = new ArrayList<String>();
			}
			String p2 = null;
			if (lp2.size() != 0) {
				if (lp2.size() != 1) {
					if (lp2.contains("1-1") && lp2.contains("1-2") && !lp1.contains("1-3")) {
							p2 = "1-3";
					} else if (lp2.contains("1-2") && lp2.contains("1-3") && !lp1.contains("1-1")) {
							p2 = "1-1";
					} else if (lp2.contains("1-1") && lp2.contains("1-3") && !lp1.contains("1-2")) {
							p2 = "1-2";
					}

					else if (lp2.contains("2-1") && lp2.contains("2-2") && !lp1.contains("2-3")) {
							p2 = "2-3";
					} else if (lp2.contains("2-2") && lp2.contains("2-3") && !lp1.contains("2-1")) {
							p2 = "2-1";
					} else if (lp2.contains("2-1") && lp2.contains("2-3") && !lp1.contains("2-2")) {
							p2 = "2-2";
					}

					else if (lp2.contains("3-1") && lp2.contains("3-2") && !lp1.contains("3-3")) {
							p2 = "3-3";
					} else if (lp2.contains("3-2") && lp2.contains("3-3") && !lp1.contains("3-1")) {
							p2 = "3-1";
					} else if (lp2.contains("3-1") && lp2.contains("3-3") && !lp1.contains("3-2")) {
							p2 = "3-2";
					}
					// ----------
					if (lp2.contains("1-1") && lp2.contains("2-1") && !lp1.contains("3-1")) {
							p2 = "3-1";
					} else if (lp2.contains("2-1") && lp2.contains("3-1") && !lp1.contains("1-1")) {
							p2 = "1-1";
					} else if (lp2.contains("1-1") && lp2.contains("3-1") && !lp1.contains("2-1")) {
							p2 = "2-1";
					}

					else if (lp2.contains("1-2") && lp2.contains("2-2") && !lp1.contains("3-2")) {
							p2 = "3-2";
					} else if (lp2.contains("2-2") && lp2.contains("3-2") && !lp1.contains("1-2")) {
							p2 = "1-2";
					} else if (lp2.contains("1-2") && lp2.contains("3-2") && !lp1.contains("2-2")) {
							p2 = "2-2";
					}

					else if (lp2.contains("1-3") && lp2.contains("2-3") && !lp1.contains("3-3")) {
							p2 = "3-3";
					} else if (lp2.contains("2-3") && lp2.contains("3-3") && !lp1.contains("1-3")) {
							p2 = "1-3";
					} else if (lp2.contains("1-3") && lp2.contains("3-3") && !lp1.contains("2-3")) {
							p2 = "2-3";
					}
					// -----------------
					else if (lp2.contains("1-1") && lp2.contains("2-2") && !lp1.contains("3-3")) {
							p2 = "3-3";
					} else if (lp2.contains("2-2") && lp2.contains("3-3") && !lp1.contains("1-1")) {
							p2 = "1-1";
					} else if (lp2.contains("1-1") && lp2.contains("3-3") && !lp1.contains("2-2")) {
							p2 = "2-2";
					}
					// --------------------
					else if (lp2.contains("1-3") && lp2.contains("2-2") && !lp1.contains("3-1")) {
							p2 = "3-1";
					} else if (lp2.contains("2-2") && lp2.contains("3-1") && !lp1.contains("1-3")) {
							p2 = "1-3";
					} else if (lp2.contains("1-3") && lp2.contains("3-1") && !lp1.contains("2-2")) {
							p2 = "2-2";
					}

					if (p2 == null) {
						if (lp1.contains("1-1") && lp1.contains("1-2") && !lp2.contains("1-3")) {
								p2 = "1-3";
						} else if (lp1.contains("1-2") && lp1.contains("1-3") && !lp2.contains("1-1")) {
								p2 = "1-1";
						} else if (lp1.contains("1-1") && lp1.contains("1-3") && !lp2.contains("1-2")) {
								p2 = "1-2";
						}

						else if (lp1.contains("2-1") && lp1.contains("2-2") && !lp2.contains("2-3")) {
								p2 = "2-3";
						} else if (lp1.contains("2-2") && lp1.contains("2-3") && !lp2.contains("2-1")) {
								p2 = "2-1";
						} else if (lp1.contains("2-1") && lp1.contains("2-3") && !lp2.contains("2-2")) {
								p2 = "2-2";
						}

						else if (lp1.contains("3-1") && lp1.contains("3-2") && !lp2.contains("3-3")) {
								p2 = "3-3";
						} else if (lp1.contains("3-2") && lp1.contains("3-3") && !lp2.contains("3-1")) {
								p2 = "3-1";
						} else if (lp1.contains("3-1") && lp1.contains("3-3") && !lp2.contains("3-2")) {
								p2 = "3-2";
						}
						// ----------
						else if (lp1.contains("1-1") && lp1.contains("2-1") && !lp2.contains("3-1")) {
								p2 = "3-1";
						} else if (lp1.contains("2-1") && lp1.contains("3-1") && !lp2.contains("1-1")) {
								p2 = "1-1";
						} else if (lp1.contains("1-1") && lp1.contains("3-1") && !lp2.contains("2-1")) {
								p2 = "2-1";
						}

						else if (lp1.contains("1-2") && lp1.contains("2-2") && !lp2.contains("3-2")) {
								p2 = "3-2";
						} else if (lp1.contains("2-2") && lp1.contains("3-2") && !lp2.contains("1-2")) {
								p2 = "1-2";
						} else if (lp1.contains("1-2") && lp1.contains("3-2") && !lp2.contains("2-2")) {
								p2 = "2-2";
						}

						else if (lp1.contains("1-3") && lp1.contains("2-3") && !lp2.contains("3-3")) {
								p2 = "3-3";
						} else if (lp1.contains("2-3") && lp1.contains("3-3") && !lp2.contains("1-3")) {
								p2 = "1-3";
						} else if (lp1.contains("1-3") && lp1.contains("3-3") && !lp2.contains("2-3")) {
								p2 = "2-3";
						}
						// -----------------
						else if (lp1.contains("1-1") && lp1.contains("2-2") && !lp2.contains("3-3")) {
								p2 = "3-3";
						} else if (lp1.contains("2-2") && lp1.contains("3-3") && !lp2.contains("1-1")) {
								p2 = "1-1";
						} else if (lp1.contains("1-1") && lp1.contains("3-3") && !lp2.contains("2-2")) {
								p2 = "2-2";
						}
						// --------------------
						else if (lp1.contains("1-3") && lp1.contains("2-2") && !lp2.contains("3-1")) {
								p2 = "3-1";
						} else if (lp1.contains("2-2") && lp1.contains("3-1") && !lp2.contains("1-3")) {
								p2 = "1-3";
						} else if (lp1.contains("1-3") && lp1.contains("3-1") && !lp2.contains("2-2")) {
								p2 = "2-2";
						}

					}

					if (p2 == null) {
						List<String> lptemp2 = new ArrayList<String>();
						for (int i = 1; i <= 3; i++) {
							for (int j = 1; j <= 3; j++) {
								String x = i + "-" + j;
								if (!lp1.contains(x) && !lp2.contains(x)) {
									lptemp2.add(x);
								}
							}
						}
						Random ran = new Random();
						if (lptemp2.size() != 0) {
							int index = ran.nextInt(lptemp2.size() - 1);
							p2 = lptemp2.get(index);
						}
					}
				} else {
					if (lp1.contains("1-1") && lp1.contains("1-2") && !lp2.contains("1-3")) {
							p2 = "1-3";
					} else if (lp1.contains("1-2") && lp1.contains("1-3") && !lp2.contains("1-1")) {
							p2 = "1-1";
					} else if (lp1.contains("1-1") && lp1.contains("1-3") && !lp2.contains("1-2")) {
							p2 = "1-2";
					}

					else if (lp1.contains("2-1") && lp1.contains("2-2") && !lp2.contains("2-3")) {
							p2 = "2-3";
					} else if (lp1.contains("2-2") && lp1.contains("2-3") && !lp2.contains("2-1")) {
							p2 = "2-1";
					} else if (lp1.contains("2-1") && lp1.contains("2-3") && !lp2.contains("2-2")) {
							p2 = "2-2";
					}

					else if (lp1.contains("3-1") && lp1.contains("3-2") && !lp2.contains("3-3")) {
							p2 = "3-3";
					} else if (lp1.contains("3-2") && lp1.contains("3-3") && !lp2.contains("3-1")) {
							p2 = "3-1";
					} else if (lp1.contains("3-1") && lp1.contains("3-3") && !lp2.contains("3-2")) {
							p2 = "3-2";
					}
					// ----------
					else if (lp1.contains("1-1") && lp1.contains("2-1") && !lp2.contains("3-1")) {
							p2 = "3-1";
					} else if (lp1.contains("2-1") && lp1.contains("3-1") && !lp2.contains("1-1")) {
							p2 = "1-1";
					} else if (lp1.contains("1-1") && lp1.contains("3-1") && !lp2.contains("2-1")) {
							p2 = "2-1";
					}

					else if (lp1.contains("1-2") && lp1.contains("2-2") && !lp2.contains("3-2")) {
							p2 = "3-2";
					} else if (lp1.contains("2-2") && lp1.contains("3-2") && !lp2.contains("1-2")) {
							p2 = "1-2";
					} else if (lp1.contains("1-2") && lp1.contains("3-2") && !lp2.contains("2-2")) {
							p2 = "2-2";
					}

					else if (lp1.contains("1-3") && lp1.contains("2-3") && !lp2.contains("3-3")) {
							p2 = "3-3";
					} else if (lp1.contains("2-3") && lp1.contains("3-3") && !lp2.contains("1-3")) {
							p2 = "1-3";
					} else if (lp1.contains("1-3") && lp1.contains("3-3") && !lp2.contains("2-3")) {
							p2 = "2-3";
					}
					// -----------------
					else if (lp1.contains("1-1") && lp1.contains("2-2") && !lp2.contains("3-3")) {
							p2 = "3-3";
					} else if (lp1.contains("2-2") && lp1.contains("3-3") && !lp2.contains("1-1")) {
							p2 = "1-1";
					} else if (lp1.contains("1-1") && lp1.contains("3-3") && !lp2.contains("2-2")) {
							p2 = "2-2";
					}
					// --------------------
					else if (lp1.contains("1-3") && lp1.contains("2-2") && !lp2.contains("3-1")) {
							p2 = "3-1";
					} else if (lp1.contains("2-2") && lp1.contains("3-1") && !lp2.contains("1-3")) {
							p2 = "1-3";
					} else if (lp1.contains("1-3") && lp1.contains("3-1") && !lp2.contains("2-2")) {
							p2 = "2-2";
					}
					// ---------
					if (p2 == null) {
						List<String> lptemp1 = new ArrayList<String>();
						for (int i = 1; i <= 3; i++) {
							for (int j = 1; j <= 3; j++) {
								String x = i + "-" + j;
								if (!lp1.contains(x) && !lp2.contains(x)) {
									lptemp1.add(x);
								}
							}
						}
						Random ran = new Random();
						if (lptemp1.size() != 0) {
							int index = ran.nextInt(lptemp1.size() - 1);
							p2 = lptemp1.get(index);
						}
					}
				}
			} else {
				List<String> lptemp = new ArrayList<String>();
				for (int i = 1; i <= 3; i++) {
					for (int j = 1; j <= 3; j++) {
						String x = i + "-" + j;
						if (!lp1.contains(x)) {
							lptemp.add(x);
						}
					}
				}
				Random ran = new Random();
				if (lptemp.size() != 0) {
					int index = ran.nextInt(lptemp.size() - 1);
					p2 = lptemp.get(index);
				}
			}

			lp2.add(p2);

			// ----------------
			if (lp1.contains("1-1") && lp1.contains("1-2") && lp1.contains("1-3")) {
				msg = "You Won..!!";
			}
			if (lp1.contains("2-1") && lp1.contains("2-2") && lp1.contains("2-3")) {
				msg = "You Won..!!";
			}
			if (lp1.contains("3-1") && lp1.contains("3-2") && lp1.contains("3-3")) {
				msg = "You Won..!!";
			}

			if (lp1.contains("1-1") && lp1.contains("2-1") && lp1.contains("3-1")) {
				msg = "You Won..!!";
			}
			if (lp1.contains("1-2") && lp1.contains("2-2") && lp1.contains("3-2")) {
				msg = "You Won..!!";
			}
			if (lp1.contains("1-3") && lp1.contains("2-3") && lp1.contains("3-3")) {
				msg = "You Won..!!";
			}

			if (lp1.contains("1-1") && lp1.contains("2-2") && lp1.contains("3-3")) {
				msg = "You Won..!!";
			}
			if (lp1.contains("1-3") && lp1.contains("2-2") && lp1.contains("3-1")) {
				msg = "You Won..!!";
			}

			// ----------
			if (msg == null) {
				if (lp2.contains("1-1") && lp2.contains("1-2")
						&& lp2.contains("1-3")) {
					msg = "I Won..!!";
				}
				if (lp2.contains("2-1") && lp2.contains("2-2")
						&& lp2.contains("2-3")) {
					msg = "I Won..!!";
				}
				if (lp2.contains("3-1") && lp2.contains("3-2")
						&& lp2.contains("3-3")) {
					msg = "I Won..!!";
				}

				if (lp2.contains("1-1") && lp2.contains("2-1")
						&& lp2.contains("3-1")) {
					msg = "I Won..!!";
				}
				if (lp2.contains("1-2") && lp2.contains("2-2")
						&& lp2.contains("3-2")) {
					msg = "I Won..!!";
				}
				if (lp2.contains("1-3") && lp2.contains("2-3")
						&& lp2.contains("3-3")) {
					msg = "I Won..!!";
				}

				if (lp2.contains("1-1") && lp2.contains("2-2")
						&& lp2.contains("3-3")) {
					msg = "I Won..!!";
				}
				if (lp2.contains("1-3") && lp2.contains("2-2")
						&& lp2.contains("3-1")) {
					msg = "I Won..!!";
				}

				if (msg == null) {
					int count = 0;
					for (int i = 1; i <= 3; i++) {
						for (int j = 1; j <= 3; j++) {
							String z = i + "-" + j;
							if (lp1.contains(z) || lp2.contains(z)) {
								count++;
							}
						}
					}
					if (count == 9) {
						msg = "Game Tied..!!";
					}
				}
			}
			// -------------------
			if (msg != null) {
				if (msg.equals("You Won..!!")) {
					lp2.remove(lp2.size() - 1);
				}
			}
			// ----------------
			session.setAttribute("list2", lp2);
			session.setAttribute("list1", lp1);
			models.put("message", msg);
			models.put("lp1", lp1);
			models.put("lp2", lp2);
			
	        return "Play";
	    }
	    ///////////////////////
	    
	    
	    
		//Two Player Game play start
	    @SuppressWarnings("null")
		@RequestMapping(value= "/player/StartTwoPlayer.html", method = RequestMethod.GET)
	    public String StartTwoPlayer(ModelMap models,HttpSession session,@RequestParam Integer r,@RequestParam Integer c)
	    {
	    		
	    	String uname = (String)session.getAttribute("username");
	    	HashMap<String,GamePlay> gmap = new HashMap<String,GamePlay>(); 
	    	List<String> gl = gls.getGames();
	    	String gamename = null;
	    	boolean IsHost = false;
	    	String pos = r+"-"+c;
	    	List<String> hostposition = new ArrayList<String>();
	    	List<String> joinposition = new ArrayList<String>();
	    	Game game = new Game();
	    	Player p1 = new Player();
	    	Player p2 = new Player();
	    	GamePlay gp =new GamePlay();
	    	String gamenamewithunder = null;
	    	String msg = null;
	    	
	    	//Find the name of the game
	    	for(int i=0;i<gl.size();i++)
	    	{
	    		String[] x = gl.get(i).split("-");
	    		if(uname.equals(x[0]))
	    		{
	    			IsHost = true;
	    		}
	    		if(uname.equals(x[1]))
	    		{
	    			IsHost = false;
	    		}
	    		if(uname.equals(x[0]) || uname.equals(x[1]))
	    		{
	    			p1 = playerDao.getPlayer(x[0]);
	    			p2 = playerDao.getPlayer(x[1]);
	    			gamename = gl.get(i);
	    			gamenamewithunder = gamename.replace("-", "_");
	    			break;
	    		}
	    	}
	    	
	    	//Get the position of host and join player
	    	gmap = gps.getGames();
	    	if(gmap.size() != 0)
	    	{
	    		gp = gmap.get(gamenamewithunder);
	    		if(gp == null)
	    		{
	    			gp = new GamePlay();
	    		}
	    	}
	    	else
	    	{
	    		gp = new GamePlay();
	    	}
	    	
	    		
	    	if(gp.getGamename() == null)
	    	{
	    		// First time
	    		gp.setGamename(gamename);
	    		if(IsHost == true)
		    	{
		    		hostposition.add(pos);
		    		gp.setHostposition(hostposition);
		    		gp.setIsHostTurn(false);
		    	}
		    	else
		    	{
		    		joinposition.add(pos);
		    		gp.setJoinposition(joinposition);
		    		gp.setIsHostTurn(true);
		    	}
	    		
	    		game.setPlayer1(p1);
    			game.setPlayer2(p2);
	    		// set the start time of the game.
	    		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    		   //get current date time with Date()
	    		   Date sdate = new Date();
			    		try {
			    			Date start = dateFormat.parse(dateFormat.format(sdate));
							game.setStarttime(start);
							gp.setStarttime(start);
							
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    	game.setSave(false);
			    	game.setTie(false);
			    	gameDao.SaveGame(game);
			    	
			    		Player player = playerDao.getPlayer(p1.getUsername());
		    			List<Game> glist = new ArrayList<Game>();
		    			glist = gameDao.getalltwoplayergames(player);
		    			playerDao.addplayergame(glist,player.getPlayer_id());
		    			Player player2 = playerDao.getPlayer(p2.getUsername());
		    			List<Game> glist2 = new ArrayList<Game>();
		    			glist2 = gameDao.getalltwoplayergames(player2);
		    			playerDao.addplayergame(glist2,player2.getPlayer_id());
		    			
	    	}
	    	else
	    	{
	    		// second time
	    		//gp.setGamename(gamename);
		    	if(IsHost == true)
		    	{
		    		hostposition = gp.getHostposition();
		    		hostposition.add(pos);
		    		gp.setHostposition(hostposition);
		    		gp.setIsHostTurn(false);
		    	}
		    	else
		    	{
		    		joinposition = gp.getJoinposition();
		    		if(joinposition == null)
		    		{
		    			joinposition = new ArrayList<String>();
		    		}
		    		joinposition.add(pos);
		    		gp.setJoinposition(joinposition);
		    		gp.setIsHostTurn(true);
		    	}
		    	
		    	// ----------------
	    		for (int i = 1; i <= 3; i++) {
	    			if (msg == null) {
	    				if (hostposition.contains(i + "-1") && hostposition.contains(i + "-2")
	    						&& hostposition.contains(i + "-3")) {
	    					msg = p1.getUsername()+" Won the match";
	    				}
	    				if (hostposition.contains("1-"+i) && hostposition.contains("2-"+i)
	    						&& hostposition.contains("3-"+i)) {
	    					msg = p1.getUsername()+" Won the match";
	    				}
	    			}
	    		}
	    		if (msg == null) {
	    			if (hostposition.contains("1-1") && hostposition.contains("2-2")
	    					&& hostposition.contains("3-3")) {
	    				msg = p1.getUsername()+" Won the match";
	    			}
	    			if (hostposition.contains("1-3") && hostposition.contains("2-2")
	    					&& hostposition.contains("3-1")) {
	    				msg = p1.getUsername()+" Won the match";
	    			}
	    		}
	    		// ----------
	    		if (msg == null) {
	    			for (int i = 1; i <= 3; i++) {
	    				if (msg == null) {
	    					if (joinposition.contains(i + "-1") && joinposition.contains(i + "-2")
	    							&& joinposition.contains(i + "-3")) {
	    						msg = p2.getUsername()+" Won the match";
	    					}
	    					if (joinposition.contains("1-"+i) && joinposition.contains("2-"+i)
		    						&& joinposition.contains("3-"+i)) {
	    						msg = p2.getUsername()+" Won the match";
		    				}
	    				}
	    			}
	    			if (msg == null) {
	    				if (joinposition.contains("1-1") && joinposition.contains("2-2")
	    						&& joinposition.contains("3-3")) {
	    					msg = p2.getUsername()+" Won the match";
	    				}
	    				if (joinposition.contains("1-3") && joinposition.contains("2-2")
	    						&& joinposition.contains("3-1")) {
	    					msg = p2.getUsername()+" Won the match";
	    				}
	    			}
	    			if (msg == null) {
	    				int count = 0;
	    				joinposition = gp.getJoinposition();
	    				for (int i = 1; i <= 3; i++) {
	    					for (int j = 1; j <= 3; j++) {
	    						String z = i + "-" + j;
	    						if (hostposition.contains(z) || joinposition.contains(z)) {
	    							count++;
	    						}
	    					}
	    				}
	    				if (count == 9) {
	    					msg = "Game Tied..!!";
	    				}
	    			}
	    			///
		    	
	    			}
	    		if(msg !=null)
	    		{
		    		if(msg.equals(p1.getUsername()+" Won the match"))
		    		{
		    			//gps.remove(gamenamewithunder);
		    			//gls.remove(p1.getUsername(), p2.getUsername());
		    			game = gameDao.getGamebystTimeTwoPl(p1, p2, gp.getStarttime());
		    					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		    					//get current date time with Date()
		    			 		Date sdate = new Date();
					    		try {
					    			Date endtime = dateFormat.parse(dateFormat.format(sdate));
									game.setEndtime(endtime);
									
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		    			game.setWin_player(p1);
		    			game.setLoss_player(p2);
		    			game.setTie(false);
			    		game.setSave(false);
			    		gp.setResult(msg);
			    		gameDao.SaveGame(game);
		    		}
		    		if(msg.equals(p2.getUsername()+" Won the match"))
		    		{
		    			//gps.remove(gamenamewithunder);
		    			//gls.remove(p1.getUsername(), p2.getUsername());
		    			game = gameDao.getGamebystTimeTwoPl(p1, p2, gp.getStarttime());
		    					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		    					//get current date time with Date()
		    			 		Date sdate = new Date();
					    		try {
					    			Date endtime = dateFormat.parse(dateFormat.format(sdate));
									game.setEndtime(endtime);
									
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		    			game.setWin_player(p2);
		    			game.setLoss_player(p1);
		    			game.setTie(false);
			    		game.setSave(false);
			    		gp.setResult(msg);
			    		gameDao.SaveGame(game);
		    		}
		    		if(msg.equals("Game Tied..!!"))
		    		{
		    			//gps.remove(gamenamewithunder);
		    			//gls.remove(p1.getUsername(), p2.getUsername());
		    			game = gameDao.getGamebystTimeTwoPl(p1, p2, gp.getStarttime());
		    					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		    					//get current date time with Date()
		    			 		Date sdate = new Date();
					    		try {
					    			Date endtime = dateFormat.parse(dateFormat.format(sdate));
									game.setEndtime(endtime);
									
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		    		
		    			game.setTie(true);
			    		game.setSave(false);
			    		gp.setResult(msg);
			    		gameDao.SaveGame(game);
		    		}
	    		}
	    	
	    	}
	    	// Add game object and game name to hashmap of GamePlayService
	    	gps.add(gamenamewithunder, gp);
	    	models.put("un", uname);
	    	models.put("gamename",gamename);
	    	models.put("gamenamewithunder", gamenamewithunder);
	    	session.setAttribute("gamenamewithunder", gamenamewithunder);
	    	models.put("resultmsg", msg);
	        return "/player/TwoPlayerGameBoard";
	    }
	    
	   
	    
	    @RequestMapping("/player/gameplaydata.json")
	    public String GamePlayData( ModelMap models ,HttpServletRequest req)
	    {
	    	if(gps.getGames() != null)
	    	{
	        	models.put( "gamedata", gps.getGames() );
	    	}
	        return "jsonView";
	    }
	    
	    @RequestMapping("/player/gameplaydata.deferred.json")
	    @ResponseBody
	    public DeferredResult<HashMap<String,GamePlay>> gpsDeferred()
	    {
	        DeferredResult<HashMap<String,GamePlay>> result = new DeferredResult<HashMap<String,GamePlay>>();
	        gps.addResult( result );
	        return result;
	    }

}
