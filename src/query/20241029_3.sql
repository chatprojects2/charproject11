
CREATE TABLE zipcode_t
(
    UID_NO               NUMBER(5) NOT NULL ,
    ZIPCODE              NUMBER(6) NOT NULL ,
    ZDO                  VARCHAR2(30) NOT NULL ,
    SIGU                 VARCHAR2(30) NOT NULL ,
    DONG                 VARCHAR2(30) NULL ,
    RI   MEM_ADDR                VARCHAR2(30) NULL ,
    BUNGI                VARCHAR2(30) NULL ,
    APTNAME              VARCHAR2(50) NULL ,
    UPD_DATE             VARCHAR2(20) NULL ,
    ADDRESS              VARCHAR2(200) NULL 
)


CREATE UNIQUE INDEX zipcode_no_pk ON zipcode_t
(UID_NO   ASC)


ALTER TABLE zipcode_t
    ADD CONSTRAINT  zipcode_no_pk PRIMARY KEY (UID_NO)
