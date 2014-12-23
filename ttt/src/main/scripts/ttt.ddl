
    create table games (
        game_id int4 not null,
        endtime timestamp,
        isSave boolean not null,
        isTie boolean not null,
        savetime timestamp,
        starttime timestamp,
        loss_player int4,
        player1 int4,
        player2 int4,
        win_player int4,
        primary key (game_id)
    );

    create table playerplayedgames (
        players_player_id int4 not null,
        playerplayedgames_game_id int4 not null
    );

    create table players (
        player_id int4 not null,
        email varchar(255),
        password varchar(255),
        username varchar(255),
        primary key (player_id)
    );

    create table playersavedgames (
        players_player_id int4 not null,
        playersavedgames_game_id int4 not null
    );

    create table savedposition (
        savedgameposition_id int4 not null,
        IsAiPlayerPosition boolean not null,
        position varchar(255),
        game_id int4,
        primary key (savedgameposition_id)
    );

    alter table playerplayedgames 
        add constraint UK_7qbtx7l5wbvjq5esnn7g4dhei unique (playerplayedgames_game_id);

    alter table playersavedgames 
        add constraint UK_d79vi7vhjwfdef3vd3ovjqp50 unique (playersavedgames_game_id);

    alter table games 
        add constraint FK_rhofg5rwax57io7wkg6ohauqg 
        foreign key (loss_player) 
        references players;

    alter table games 
        add constraint FK_i7yk0yshppqtddgrc7gbd9hke 
        foreign key (player1) 
        references players;

    alter table games 
        add constraint FK_2eabwf5b3wtie9xcua0fcd2x3 
        foreign key (player2) 
        references players;

    alter table games 
        add constraint FK_4ljaj6m7q57gjfqnb18penrv5 
        foreign key (win_player) 
        references players;

    alter table playerplayedgames 
        add constraint FK_7qbtx7l5wbvjq5esnn7g4dhei 
        foreign key (playerplayedgames_game_id) 
        references games;

    alter table playerplayedgames 
        add constraint FK_1sfptem9kahb1ewm8embf8te4 
        foreign key (players_player_id) 
        references players;

    alter table playersavedgames 
        add constraint FK_d79vi7vhjwfdef3vd3ovjqp50 
        foreign key (playersavedgames_game_id) 
        references games;

    alter table playersavedgames 
        add constraint FK_8ygch2ktoc07y7gu2tf9ej1bu 
        foreign key (players_player_id) 
        references players;

    alter table savedposition 
        add constraint FK_fspdpm1pup0r7nsjk275rxqu3 
        foreign key (game_id) 
        references games;

    create sequence hibernate_sequence;
