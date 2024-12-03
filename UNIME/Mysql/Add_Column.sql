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
