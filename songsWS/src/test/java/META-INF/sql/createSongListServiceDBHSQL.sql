DROP TABLE IF EXISTS Song;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Songlist;
DROP TABLE IF EXISTS Songlist_song;

CREATE TABLE User (
	userId VARCHAR(50) GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1 PRIMARY KEY, 
	`key` VARCHAR(100) NOT NULL, 
	firstName VARCHAR(50) NOT NULL, 
	lastName VARCHAR(50) NOT NULL
	);
	
CREATE TABLE Song (
	`id` INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY, 
	`title` VARCHAR(50) NOT NULL, 
	`label` VARCHAR(50) NOT NULL, 
	`artist` VARCHAR(50) NOT NULL,
	`released` VARCHAR(50) NOT NULL
);



CREATE TABLE Songlist ( 
    id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
    ownerId VARCHAR(50) NOT NULL , 
    name VARCHAR(50), 
    isPrivate BIT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (ownerId) REFERENCES user(userId)
);
    
CREATE TABLE Songlist_song (
    songlist_id INT(11) NOT NULL, 
  	song_id INT(11) NOT NULL, 
   	CONSTRAINT fk_songlist FOREIGN KEY (songlist_id) REFERENCES songlist(id) ON DELETE CASCADE,
   	CONSTRAINT fk_song FOREIGN KEY (song_id) REFERENCES song(id) ON DELETE CASCADE
);