CREATE TABLE Category(
	cat_id INT PRIMARY KEY,
	cat_name VARCHAR(45) NOT NULL,
	cat_image VARCHAR(45) NOT NULL
);

CREATE TABLE Product(
	prod_id INT PRIMARY KEY,
	id_cat INT,
	pro_name VARCHAR(45) NOT NULL,
	pro_description VARCHAR(45),
	pro_image VARCHAR(45),
	pro_price INT NOT NULL,
	FOREIGN KEY (id_cat) REFERENCES Category(cat_id)
);

CREATE TABLE Bill(
	bill_id INT PRIMARY KEY,
	bill_table VARCHAR(45),
	bill_status VARCHAR(45),
	bill_open_time TIMESTAMP,
	bill_close_time TIMESTAMP,
	bill_payment_method VARCHAR(45)
);

CREATE TABLE Orders(
	orders_id INT PRIMARY KEY,
	id_prod INT REFERENCES Product(prod_id),
	id_bill INT REFERENCES bill(bill_id),
	ord_quantity INT,
	ord_unity_price FLOAT,
	ord_status VARCHAR(45),
	ord_time TIMESTAMP
);