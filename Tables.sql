CREATE TABLE category(
	cat_id serial PRIMARY KEY,
	cat_name VARCHAR(45) NOT NULL,
	cat_image VARCHAR(45)
);

CREATE TABLE product(
	prod_id serial PRIMARY KEY,
	cat_id INT,
	prod_name VARCHAR(45) NOT NULL,
	prod_description TEXT,
	prod_image VARCHAR(45),
	prod_price FLOAT NOT NULL,
	FOREIGN KEY (cat_id) REFERENCES Category(cat_id)
);

CREATE TABLE bill(
	bill_id serial PRIMARY KEY,
	bill_table VARCHAR(45),
	bill_status INT,
	bill_open_time TIMESTAMP,
	bill_close_time TIMESTAMP,
	bill_payment_method VARCHAR(45),
	bill_device_id VARCHAR(45)
);

CREATE TABLE orders(
	ord_id serial PRIMARY KEY,
	prod_id INT REFERENCES Product(prod_id),
	bill_id INT REFERENCES bill(bill_id),
	ord_quantity INT,
	ord_unity_price FLOAT,
	ord_status INT,
	ord_time TIMESTAMP
);