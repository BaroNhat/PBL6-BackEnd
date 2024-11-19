USE unimehospital;
ALTER TABLE patient DROP COLUMN patient_name ;
ALTER TABLE doctor DROP COLUMN doctor_name;
ALTER TABLE employee DROP COLUMN employee_name;
ALTER Table service ADD service_image  VARCHAR(255) Not NULL ;
ALTER TABLE user ADD user_name VARCHAR(255) NOT NULL;
