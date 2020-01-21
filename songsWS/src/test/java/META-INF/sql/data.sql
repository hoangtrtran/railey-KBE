insert into Song(title, label, artist, released) values ('Fall On Me', 'Liam-Record', 'Christina Aguilera', '2019');
insert into Song(title, label, artist, released) values ('Make You Feel My Love', 'Liam-Record', 'Liam Grammy', '2020')
insert into User(userId, key, firstName, lastName) values ('testuser', 'hellonewday', 'Liam', 'Grammy');
insert into Songlist(ownerId, name, isPrivate) values ('testuser', 'liams Public', 'false');
insert into Songlist_song(songlist_id, song_id) values ('1', '1');