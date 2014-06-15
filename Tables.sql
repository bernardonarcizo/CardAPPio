CREATE TABLE Category(
	cat_id serial PRIMARY KEY,
	cat_name VARCHAR(45) NOT NULL,
	cat_image VARCHAR(45)
);

CREATE TABLE Product(
	prod_id serial PRIMARY KEY,
	cat_id INT,
	prod_name VARCHAR(45) NOT NULL,
	prod_description VARCHAR(45),
	prod_image VARCHAR(45),
	prod_price REAL NOT NULL,
	FOREIGN KEY (cat_id) REFERENCES Category(cat_id)
);

CREATE TABLE Bill(
	bill_id serial PRIMARY KEY,
	bill_table VARCHAR(45),
	bill_status VARCHAR(45),
	bill_open_time TIMESTAMP,
	bill_close_time TIMESTAMP,
	bill_payment_method VARCHAR(45),
	bill_device_id VARCHAR(45),
);

CREATE TABLE Orders(
	orders_id serial PRIMARY KEY,
	prod_id INT REFERENCES Product(prod_id),
	bill_id INT REFERENCES bill(bill_id),
	ord_quantity INT,
	ord_unity_price FLOAT,
	ord_status VARCHAR(45),
	ord_time TIMESTAMP
);