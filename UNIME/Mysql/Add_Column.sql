USE unimehospital;

ALTER TABLE patient ADD COLUMN patient_name VARCHAR(255) Not NULL ;
ALTER TABLE patient ADD COLUMN patient_image VARCHAR(255) Not NULL ;
ALTER TABLE doctor ADD COLUMN doctor_name VARCHAR(255) Not NULL ;
ALTER TABLE doctor ADD COLUMN doctor_image VARCHAR(255) Not NULL;
ALTER TABLE doctor ADD COLUMN doctor_description VARCHAR(255) Not NULL;
ALTER TABLE employee ADD COLUMN employee_name VARCHAR(255) Not NULL ;
ALTER TABLE employee ADD COLUMN employee_image VARCHAR(255) Not NULL ;

ALTER Table service ADD service_image  VARCHAR(255) Not NULL ;
ALTER TABLE user DROP user_image;

CREATE TABLE invalidated_token (
	id VARCHAR(255) PRIMARY KEY ,
    expiry_time DATETIME NOT NULL
);
CREATE TABLE notification (
    notification_id INT PRIMARY KEY AUTO_INCREMENT,
    recipient_id INT NOT NULL,
    recipient_type VARCHAR(50) NOT NULL,
    notification_message TEXT NOT NULL,
    related_object_type VARCHAR(50) NOT NULL,
    related_object_id INT,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP()
);
DROP TABLE notification;