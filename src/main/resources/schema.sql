DROP TABLE IF EXISTS T_CUST_POINT;
CREATE TABLE T_CUST_POINT (
    CUST_POINT_IDX INT AUTO_INCREMENT                  NOT NULL PRIMARY KEY,
    CUST_NO        INT                                 NOT NULL,
    GIVE_PNT_AMT   INT                                 NOT NULL,
    USE_PNT_AMT    INT                                 NOT NULL DEFAULT 0,
    LEFT_PNT_AMT   INT                                 NOT NULL,
    EXT_DT         TIMESTAMP                           NOT NULL,
    REG_DT         TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPD_DT         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS T_CUST_POINT_HST;
CREATE TABLE T_CUST_POINT_HST (
    CUST_POINT_HST_IDX INT AUTO_INCREMENT                     NOT NULL PRIMARY KEY,
    CUST_POINT_IDX     INT                                    NOT NULL,
    GBN                VARCHAR(10)                            NOT NULL,
    HST_DESC           VARCHAR(200),
    PNT_AMT            INT          NOT NULL,
    ORD_NO             INT,
    DEL_YN             CHAR         NOT NULL DEFAULT 'N'      NOT NULL,
    REG_DT             TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPD_DT             TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);