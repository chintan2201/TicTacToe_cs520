create sequence hibernate_sequence minvalue 100;
--1
      create table players (
        player_id integer primary key,
        username varchar(255) unique not null,
        password varchar(255) not null,
        email varchar(255) 
    );
    
    create table games (
        game_id integer primary key,
        player1 integer references players(player_id),
        player2 integer references players(player_id),
        starttime timestamp,
        endtime timestamp,
        win_player integer references players(player_id),
        loss_player integer references players(player_id),
        isSave boolean not null,
        isTie boolean not null,
        savetime timestamp
    );
    
    
     create table playerplayedgames (
        players_player_id integer not null references players(player_id),
        playerplayedgames_game_id integer not null references games(game_id)
    );
    
    create table playersavedgames (
        players_player_id integer not null references players(player_id),
        playersavedgames_game_id integer not null references games(game_id)
    );

      create table savedposition (
        savedgameposition_id integer primary key,
        IsAiPlayerPosition boolean not null,
        position varchar(255),
        game_id integer references games(game_id)
    );
    
    create table authorities (
	    username    varchar(255) not null references players(username),
	    authority   varchar(255) default 'ROLE_USER'
	);


     --2
    insert into players values(1,'cysun','abcd','cysun@localhost.localdomain');
	insert into players values(2,'cshah','abcd','cshah@gmail.com');
	
	insert into authorities values('cysun','ROLE_USER');
	insert into authorities values('cshah','ROLE_USER');
     --3
    insert into games values(1,1,null,'2014-05-02 13:01:07.892','2014-05-02 13:05:10.892',1,null,'f','f',null);
    insert into games values(2,1,null,'2014-05-02 13:10:10.892','2014-05-02 13:15:20.892',null,1,'f','f',null);
    insert into games values(3,1,null,'2014-05-02 13:20:10.892',null,null,null,'t','f','2014-05-02 13:22:10.892');
    insert into games values(4,1,2,'2014-05-02 13:10:10.892','2014-05-02 13:15:20.892',2,1,'f','f',null);
  	
  	insert into playerplayedgames values(1,1);
  	insert into playerplayedgames values(1,2);
  	insert into playerplayedgames values(1,3);
  	insert into playerplayedgames values(1,4);
  	insert into playerplayedgames values(2,4);
  	
  	insert into playersavedgames values(1,3);
  	
     --4
     insert into savedposition values(1,'f','1-1',3);
     insert into savedposition values(2,'t','2-2',3);
     
     --Trigger authorities table insert while inserting in players table
     -- 1. creating a function for inserting authority for particular player
     CREATE FUNCTION insertdata() returns trigger as $testref$
	    BEGIN
	    IF (TG_OP='INSERT') THEN
	      INSERT INTO authorities (username) values (NEW.username);
	      return NEW;
	    END IF;
	    END;
    $testref$ LANGUAGE plpgsql;
    CREATE TRIGGER testref AFTER INSERT ON players
  	FOR EACH ROW 
  	EXECUTE PROCEDURE insertdata();
     
