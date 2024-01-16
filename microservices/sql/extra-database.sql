# Sets up an extra database
create database extra_database;

# Creates a user with all privileges on this database, with a certain password
grant all privileges on extra_database.* to 'youruser'@'%' identified by 'yoursecret';
