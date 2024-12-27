
 
 CREATE TABLE appointment_history (
	appointment_id INT PRIMARY KEY, 
    patient_username VARCHAR(255) NOT NULL,
    doctor_username VARCHAR(255) NOT NULL,
    department_id INT NOT NULL,
    patient_name VARCHAR(255) NOT NULL,
	doctor_name VARCHAR(255) NOT NULL,
    doctortimework_year  INT NOT NULL,
    week_of_year INT NOT NULL,
	day_of_week VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
	service_name VARCHAR(255) NOT NULL,
    employee_id INT NOT NULL, 
	employee_name VARCHAR(255) NOT NULL,
    appointment_status VARCHAR(255)NOT NULL,
    appointment_note VARCHAR(255)
 );
 ALTER TABLE appointment_history CHANGE COLUMN employee_id employee_id INT;
  ALTER TABLE appointment_history CHANGE COLUMN employee_name employee_name  VARCHAR(255);