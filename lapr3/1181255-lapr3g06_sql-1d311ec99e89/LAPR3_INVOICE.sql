 CREATE OR REPLACE PROCEDURE addInvoiceLine(username VARCHAR, p_month INTEGER, p_year INTEGER, p_cost FLOAT)
 IS
 temp_id INTEGER;
 check_user INTEGER;
 no_client EXCEPTION;
 BEGIN
    check_user := checkClient(username);
    IF(check_user = 0 ) THEN
        RAISE no_client;
    ELSE

        SELECT id_invoice INTO temp_id FROM invoice WHERE id_user=username AND "month"=p_month AND "year" = p_year;
        INSERT INTO INVOICE_LINE(id_invoice,"cost") VALUES (temp_id,p_cost);
 
    END IF;
        EXCEPTION WHEN NO_DATA_FOUND THEN
        INSERT INTO INVOICE(id_user,"month","year") VALUES (username,p_month,p_year);
        SELECT id_invoice INTO temp_id 
        FROM invoice
        WHERE id_user = username AND "month" = p_month AND "year" = p_year;
        INSERT INTO INVOICE_LINE(id_invoice,"cost") VALUES (temp_id,p_cost);

    WHEN no_client THEN
    raise_application_error(-20044, 'No user with this username in the system');  
        
 END;
 /
  
 CREATE OR REPLACE FUNCTION checkClient(p_user VARCHAR) RETURN INTEGER
 IS
 res INTEGER;
 BEGIN 
 SELECT COUNT(id_user) INTO res FROM "clients" WHERE id_user=p_user;
 RETURN(res);
 END;
 /

 CREATE OR REPLACE FUNCTION generateInvoice(username VARCHAR, mon INTEGER, yea INTEGER) RETURN FLOAT
 IS
    invoice_cost FLOAT;
 BEGIN
   SELECT SUM(l."cost") INTO invoice_cost FROM invoice i, invoice_line l WHERE i.id_user=username AND i."month" = mon AND i."year" = yea
   AND i.id_invoice = l.id_invoice;
   UPDATE invoice SET emission_date=SYSDATE WHERE id_user=username AND "month" = mon AND "year" = yea;
   IF(invoice_cost IS NULL) THEN
   RETURN(0);
   ELSE
   RETURN(invoice_cost);
   END IF;
 END;
 /

 
 
 