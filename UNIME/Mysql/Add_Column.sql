USE unimehospital;
ALTER TABLE patient ADD patient_image VARCHAR(255);
ALTER TABLE doctor Change Column doctor_image doctor_image VARCHAR(255) NOT NULL;
ALTER TABLE employee Change Column employee_image employee_image VARCHAR(255) NOT NULL;
ALTER Table service ADD service_image  VARCHAR(255) Not NULL ;
