INSERT INTO users (username, password) 
    VALUES 
    ('admin', sha1('admin')), 
    ('bob', sha1('bob'));