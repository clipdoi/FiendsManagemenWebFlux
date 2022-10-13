CREATE TABLE IF NOT EXISTS public."user"(
	id bigint generated always as identity primary key,
	email varchar(320) NOT NULL
);


CREATE TABLE IF NOT EXISTS public.user_relationship(
	email_id int8 NOT NULL,
	friend_id int8 NOT NULL,
	status varchar(50) NOT NULL,
	CONSTRAINT friend_relationship_pk PRIMARY KEY (email_id, friend_id, status),
	CONSTRAINT friend_relationship_email_id_foreign FOREIGN KEY (email_id) REFERENCES public."user"(id),
	CONSTRAINT friend_relationship_friend_id_foreign FOREIGN KEY (friend_id) REFERENCES public."user"(id)
);

INSERT INTO public."user"(email)
VALUES('hongson@gmail.com'),
('minhthong@gmail.com'),
('saomai@gmail.com'),
('nguyenquang@gmail.com'),
('kienca@gmail.com'),
('ngoctu@gmail.com'),
('phamquan@gmail.com'),
('huynhkhanh@gmail.com'),
('nguyenvu@gmail.com'),
('nguyenphi@gmail.com');


INSERT INTO public."user_relationship"(email_id, friend_id, status)
VALUES(1, 2, 'FRIEND'),
(1, 3, 'FRIEND'),
(1, 4, 'FRIEND'),
(2, 1, 'FRIEND'),
(3, 1, 'FRIEND'),
(4, 1, 'FRIEND'),
(2, 3, 'FRIEND'),
(2, 5, 'FRIEND'),
(2, 4, 'FRIEND'),
(3, 2, 'FRIEND'),
(5, 2, 'FRIEND'),
(4, 2, 'FRIEND');