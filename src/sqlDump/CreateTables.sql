create database library_mgmt2;
use library_mgmt2;
drop table if exists book_authors;
drop table  if exists fines;
drop table  if exists book_loans;
drop table  if exists borrower;
drop table  if exists  book;
drop table  if exists authors;
commit;
CREATE TABLE  book  (
   Isbn  varchar(10) NOT NULL,
   Isbn13  varchar(13) NOT NULL,
   Title  varchar(300) DEFAULT NULL,
   Cover  varchar(400) DEFAULT NULL,
   Publisher  varchar(300) DEFAULT NULL,
   Pages  int(11) DEFAULT NULL,
   Availability  int(11) DEFAULT 1,
  PRIMARY KEY ( Isbn )
);

CREATE TABLE  authors  (
   Author_id  int(11) NOT NULL AUTO_INCREMENT,
   Name  varchar(200) DEFAULT NULL,
  PRIMARY KEY ( Author_id )
);

CREATE TABLE  book_authors  (
   Author_id  int(11) NOT NULL,
   Isbn  char(10) NOT NULL,
  PRIMARY KEY ( Author_id , Isbn ),
  FOREIGN KEY ( Author_id ) REFERENCES  authors  ( Author_id ),
  FOREIGN KEY ( Isbn ) REFERENCES  book  ( Isbn )
);
COMMIT;
CREATE TABLE  borrower  (
   card_id  int(6) unsigned zerofill NOT NULL auto_increment,
   SSN  varchar(15) NOT NULL UNIQUE,  
   first_name  VARCHAR(500) NOT NULL,
   last_name   VARCHAR(500) NOT NULL,
   email       VARCHAR(500) DEFAULT NULL,
   address     VARCHAR(500) NOT NULL,
   city        VARCHAR(500) NOT NULL,
   state       VARCHAR(500) NOT NULL,
   Phone  varchar(15) DEFAULT NULL,
  PRIMARY KEY ( card_id )
) ;


CREATE TABLE  book_loans  (
   Loan_id  int(11) NOT NULL AUTO_INCREMENT,
   Isbn  varchar(10) DEFAULT NULL,
   Card_id  int(6) unsigned zerofill DEFAULT NULL,
   Date_out  datetime DEFAULT CURRENT_TIMESTAMP,
   Date_due  datetime DEFAULT NULL,
   Date_in  datetime DEFAULT NULL,
  PRIMARY KEY ( Loan_id ),
  FOREIGN KEY ( Card_id ) REFERENCES  borrower  ( card_id ),
  FOREIGN KEY ( Isbn ) REFERENCES  book  ( Isbn )
);

CREATE TABLE  fines  (
   Loan_id  int(11) NOT NULL,
   Fine_amt  decimal(10,2) DEFAULT NULL,
   Paid  int(1) DEFAULT NULL,
  PRIMARY KEY ( Loan_id ),
  FOREIGN KEY ( Loan_id ) REFERENCES  book_loans  ( Loan_id )
);