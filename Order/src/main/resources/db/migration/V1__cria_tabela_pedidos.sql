CREATE TABLE orders (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  datetime datetime NOT NULL,
  status varchar(15) NOT NULL,
  msg varchar(255),
  PRIMARY KEY (id)
)