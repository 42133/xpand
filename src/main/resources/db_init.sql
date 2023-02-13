INSERT INTO movies(id, title, date, rank, revenue) VALUES (1, 'Knock at the Cabin', '2023-02-02', 1, 14127170);
INSERT INTO movies(id, title, date, rank, revenue) VALUES (2, '80 for Brady', '2023-02-02', 2, 12701640);
INSERT INTO movies(id, title, date, rank, revenue) VALUES (3, 'Avatar: The Way of Water', '2022-12-15',3, 11335304);

INSERT INTO actors(id, name, birth_date, gender) VALUES (1, 'Tom Cruise', '1962-07-13', 'Male');
INSERT INTO actors(id, name, birth_date, gender) VALUES (2, 'Sandra Bullock', '1964-07-26','Female');
INSERT INTO actors(id, name, birth_date, gender) VALUES (3, 'Alba Batista', '1997-07-10', 'Female');
INSERT INTO actors(id, name, birth_date, gender) VALUES (4, 'Diogo Morgado', '1981-01-17','Male');

INSERT INTO movies_actors(movie_id, actor_id) VALUES (1,1);
INSERT INTO movies_actors(movie_id, actor_id) VALUES (3,2);
INSERT INTO movies_actors(movie_id, actor_id) VALUES (3,4);


