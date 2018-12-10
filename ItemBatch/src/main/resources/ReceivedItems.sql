CREATE TABLE received_items(
   id int not null auto_increment,
   item_nbr int not null ,
   rcvd_qty int not null,
   po_Num int not null,
   timeStamp time not null,
   primary key(id)
);
INSERT INTO received_items(item_nbr, rcvd_qty, po_Num, timeStamp) values (1234,4567,7890, CURRENT_TIMESTAMP);
INSERT INTO received_items(item_nbr, rcvd_qty, po_Num, timeStamp) values (1234,4567,7890, CURRENT_TIMESTAMP);
INSERT INTO received_items(item_nbr, rcvd_qty, po_Num, timeStamp) values (1234,4567,7890, CURRENT_TIMESTAMP);