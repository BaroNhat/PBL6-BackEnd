USE unimehospital;
-- disable foreign keys constraint
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE appointment;
TRUNCATE TABLE department;
TRUNCATE TABLE doctor;
TRUNCATE TABLE doctor_detail;
TRUNCATE TABLE doctor_service;
TRUNCATE TABLE doctor_timework;
TRUNCATE TABLE employee;
TRUNCATE TABLE patient;
TRUNCATE TABLE service;
TRUNCATE TABLE timework;
TRUNCATE TABLE user;

-- enable foreign keys constraint
SET FOREIGN_KEY_CHECKS = 1;
-- ================================================================ 
INSERT INTO DEPARTMENT (department_name, department_description)
VALUES 
    ('Răng-Hàm-Mặt', 'Chăm sóc và điều trị các vấn đề về răng miệng và hàm mặt'),
    ('Tim Mạch', 'Chuyên khám và điều trị các bệnh lý về tim mạch'),
    ('Nội Tiết', 'Điều trị các bệnh về nội tiết và rối loạn hormone'),
    ('Hô Hấp', 'Khám và điều trị các bệnh lý về đường hô hấp'),
    ('Tiêu Hóa', 'Chuyên điều trị các bệnh lý về tiêu hóa'),
    ('Thần Kinh', 'Chăm sóc và điều trị các bệnh về thần kinh'),
    ('Xương Khớp', 'Khám và điều trị các vấn đề về cơ xương khớp'),
    ('Sản Phụ Khoa', 'Chuyên về các dịch vụ sản khoa và phụ khoa'),
    ('Nhi Khoa', 'Khám và điều trị các bệnh cho trẻ em'),
    ('Da Liễu', 'Chăm sóc và điều trị các vấn đề về da liễu'),
    ('Mắt', 'Chuyên điều trị các bệnh về mắt'),
    ('Tai Mũi Họng', 'Khám và điều trị các bệnh lý về tai, mũi, họng'),
    ('Ung Bướu', 'Chuyên điều trị các bệnh về ung bướu và ung thư'),
    ('Thận Tiết Niệu', 'Khám và điều trị các bệnh lý về thận và đường tiết niệu'),
    ('Ngoại Khoa', 'Điều trị các bệnh cần phẫu thuật'),
    ('Chấn Thương Chỉnh Hình', 'Điều trị chấn thương và các vấn đề chỉnh hình'),
    ('Phục Hồi Chức Năng', 'Hỗ trợ phục hồi chức năng sau chấn thương và bệnh tật'),
    ('Dinh Dưỡng', 'Tư vấn và điều trị các vấn đề về dinh dưỡng'),
    ('Tâm Thần', 'Điều trị các rối loạn tâm thần và vấn đề tâm lý'),
    ('Huyết Học', 'Chăm sóc và điều trị các bệnh lý về máu');

INSERT INTO SERVICE (service_name, department_id, service_description, service_price, service_image)
VALUES 
    ('Khám răng miệng định kỳ', 1, 'Khám và làm sạch răng miệng định kỳ', 300000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Khám và siêu âm tim', 2, 'Khám tim mạch và thực hiện siêu âm tim', 500000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Tư vấn nội tiết', 3, 'Tư vấn các bệnh về nội tiết và rối loạn hormone', 400000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Khám phổi', 4, 'Khám và tư vấn các bệnh về đường hô hấp', 350000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Nội soi dạ dày', 5, 'Nội soi và điều trị các bệnh lý dạ dày', 600000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Điều trị đau đầu', 6, 'Khám và điều trị các vấn đề về thần kinh và đau đầu', 450000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Khám xương khớp', 7, 'Kiểm tra và điều trị các vấn đề về xương khớp', 300000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Tư vấn sản phụ khoa', 8, 'Khám và tư vấn cho phụ nữ về các vấn đề sản phụ khoa', 400000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Khám tổng quát trẻ em', 9, 'Khám sức khỏe tổng quát và điều trị bệnh cho trẻ em', 250000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Điều trị da liễu', 10, 'Điều trị các vấn đề về da liễu như mụn, viêm da', 200000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Khám mắt', 11, 'Kiểm tra và điều trị các bệnh về mắt', 300000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Điều trị viêm họng', 12, 'Khám và điều trị các vấn đề về tai, mũi, họng', 220000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Khám và điều trị ung bướu', 13, 'Chẩn đoán và điều trị các bệnh về ung bướu', 800000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Tư vấn thận và tiết niệu', 14, 'Khám và tư vấn các bệnh về thận và đường tiết niệu', 450000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Phẫu thuật ngoại khoa', 15, 'Thực hiện các phẫu thuật ngoại khoa cần thiết', 1200000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Điều trị chấn thương', 16, 'Điều trị chấn thương và các vấn đề chỉnh hình', 700000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Phục hồi chức năng sau chấn thương', 17, 'Giúp bệnh nhân phục hồi chức năng sau chấn thương', 600000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Tư vấn dinh dưỡng', 18, 'Cung cấp tư vấn và điều trị các vấn đề về dinh dưỡng', 200000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Điều trị tâm lý', 19, 'Điều trị các vấn đề về tâm lý và tâm thần', 500000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    ('Điều trị rối loạn máu', 20, 'Chăm sóc và điều trị các bệnh lý về máu', 650000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg');

INSERT INTO TIMEWORK (day_of_week, start_time, end_time)
VALUES 
    ('MONDAY', '08:00:00', '09:00:00'),
    ('MONDAY', '09:00:00', '10:00:00'),
    ('MONDAY', '10:00:00', '11:00:00'),
    ('MONDAY', '11:00:00', '12:00:00'),
    ('MONDAY', '13:00:00', '14:00:00'),
    ('MONDAY', '14:00:00', '15:00:00'),
    ('MONDAY', '16:00:00', '17:00:00'),
    
    ('TUESDAY', '08:00:00', '09:00:00'),
    ('TUESDAY', '09:00:00', '10:00:00'),
    ('TUESDAY', '10:00:00', '11:00:00'),
    ('TUESDAY', '11:00:00', '12:00:00'),
    ('TUESDAY', '13:00:00', '14:00:00'),
    ('TUESDAY', '14:00:00', '15:00:00'),
    ('TUESDAY', '15:00:00', '16:00:00'),
    ('TUESDAY', '16:00:00', '17:00:00'),

    ('WEDNESDAY', '08:00:00', '09:00:00'),
    ('WEDNESDAY', '09:00:00', '10:00:00'),
    ('WEDNESDAY', '10:00:00', '11:00:00'),
    ('WEDNESDAY', '11:00:00', '12:00:00'),
    ('WEDNESDAY', '13:00:00', '14:00:00'),
    ('WEDNESDAY', '14:00:00', '15:00:00'),
    ('WEDNESDAY', '15:00:00', '16:00:00'),

    ('THURSDAY', '08:00:00', '09:00:00'),
    ('THURSDAY', '09:00:00', '10:00:00'),
    ('THURSDAY', '10:00:00', '11:00:00'),
    ('THURSDAY', '11:00:00', '12:00:00'),
    ('THURSDAY', '13:00:00', '14:00:00'),
    ('THURSDAY', '14:00:00', '15:00:00'),
    ('THURSDAY', '15:00:00', '16:00:00'),
    ('THURSDAY', '16:00:00', '17:00:00'),

    ('FRIDAY', '08:00:00', '09:00:00'),
    ('FRIDAY', '09:00:00', '10:00:00'),
    ('FRIDAY', '10:00:00', '11:00:00'),
    ('FRIDAY', '11:00:00', '12:00:00'),
    ('FRIDAY', '13:00:00', '14:00:00'),
    ('FRIDAY', '14:00:00', '15:00:00'),
    ('FRIDAY', '15:00:00', '16:00:00'),
    ('FRIDAY', '16:00:00', '17:00:00'),

    ('SATURDAY', '08:00:00', '09:00:00'),
    ('SATURDAY', '09:00:00', '10:00:00'),
    ('SATURDAY', '10:00:00', '11:00:00'),
    ('SATURDAY', '11:00:00', '12:00:00'),
    ('SATURDAY', '13:00:00', '14:00:00'),
    ('SATURDAY', '14:00:00', '15:00:00'),
    ('SATURDAY', '15:00:00', '16:00:00');

INSERT INTO DOCTOR_DETAIL (doctordetail_information, doctordetail_experience, doctordetail_award_recognization)
VALUES 
    ('Chuyên gia Tim mạch', '10 năm kinh nghiệm trong lĩnh vực tim mạch', 'Giải thưởng Tim mạch xuất sắc 2020'),
    ('Chuyên gia Nhi khoa', '8 năm kinh nghiệm trong lĩnh vực nhi khoa', 'Giải thưởng Bác sĩ Nhi khoa 2019'),
    ('Chuyên gia Chỉnh hình', '12 năm kinh nghiệm trong phẫu thuật chỉnh hình', 'Giải thưởng Chỉnh hình 2021'),
    ('Chuyên gia Thần kinh', '9 năm kinh nghiệm trong lĩnh vực thần kinh', 'Giải thưởng Thần kinh học 2018'),
    ('Chuyên gia Da liễu', '7 năm kinh nghiệm trong da liễu', 'Giải thưởng Da liễu 2019'),
    ('Chuyên gia Tiêu hóa', '11 năm kinh nghiệm trong lĩnh vực tiêu hóa', 'Giải thưởng Tiêu hóa 2022'),
    ('Chuyên gia Ung bướu', '15 năm kinh nghiệm trong ung bướu', 'Giải thưởng Ung bướu 2020'),
    ('Chuyên gia Tâm lý', '9 năm kinh nghiệm trong sức khỏe tâm thần', 'Giải thưởng Tâm lý học 2021'),
    ('Chuyên gia Hô hấp', '13 năm kinh nghiệm trong sức khỏe hô hấp', 'Giải thưởng Hô hấp 2017'),
    ('Chuyên gia Thấp khớp', '10 năm kinh nghiệm trong lĩnh vực thấp khớp', 'Giải thưởng Thấp khớp 2021');

INSERT INTO USER (user_username, user_password, user_email, user_role)
VALUES 
    ('admin', 'admin', 'admin@example.com', 'ADMIN'),
    
    ('pa_anh', 'anh1234#', 'anh@example.com', 'PATIENT'),
    ('pa_bao', 'bao1234#', 'bao@example.com', 'PATIENT'),
    ('pa_cuong', 'cuong1234#', 'cuong@example.com', 'PATIENT'),
    ('pa_danh', 'danh1234#', 'danh@example.com', 'PATIENT'),
    ('pa_giang', 'giang1234#', 'giang@example.com', 'PATIENT'),
    ('pa_tan', 'tan1234#', 'tan@example.com', 'PATIENT'),
    ('pa_duc', 'duc1234#', 'duc@example.com', 'PATIENT'),
    ('pa_hue', 'hue1234#', 'hue@example.com', 'PATIENT'),
    ('pa_chinh', 'chinh1234#', 'chinh@example.com', 'PATIENT'),
    ('pa_nhung', 'nhung1234#', 'nhung@example.com', 'PATIENT'),
    
    ('em_quan', 'quan1234#', 'quan@example.com', 'EMPLOYEE'),
    ('em_trung', 'trung1234#', 'trung@example.com', 'EMPLOYEE'),
    ('em_quoc', 'quoc1234#', 'quoc@example.com', 'EMPLOYEE'),
    ('em_lac', 'lac1234#', 'lac@example.com', 'EMPLOYEE'),
    ('em_linh', 'linh1234#', 'linh@example.com', 'EMPLOYEE'),
    ('em_luong', 'luong1234#', 'luong@example.com', 'EMPLOYEE'),
    ('em_tham', 'thang1234#', 'tham@example.com', 'EMPLOYEE'),
    ('em_anh', 'anh1234#', 'viet@example.com', 'EMPLOYEE'),
    ('em_phuong', 'phuong1234#', 'phuong@example.com', 'EMPLOYEE'),
    ('em_tu', 'tu1234#', 'tu@example.com', 'EMPLOYEE'),
    
	('doc_khai', 'khai1234#', 'khai@example.com', 'DOCTOR'),
    ('doc_hoang', 'hoang1234#', 'hoang@example.com', 'DOCTOR'),
    ('doc_anh', 'anh1234#', 'anhdoc@example.com', 'DOCTOR'),
    ('doc_ly', 'ly1234#', 'ly@example.com', 'DOCTOR'),
    ('doc_phuc', 'phuc1234#', 'phuc@example.com', 'DOCTOR'),
    ('doc_quan', 'quan1234#', 'quandoc@example.com', 'DOCTOR'),
    ('doc_hieu', 'hieu1234#', 'hieu@example.com', 'DOCTOR'),
    ('doc_van', 'van1234#', 'van@example.com', 'DOCTOR'),
    ('doc_loc', 'loc1234#', 'loc@example.com', 'DOCTOR'),
    ('doc_tu', 'tu1234#', 'tudoc@example.com', 'DOCTOR');
    
INSERT INTO PATIENT (patient_userId, patient_name, patient_address, patient_phonenumber, patient_gender, patient_date_of_birth, patient_image)
VALUES 
    (1, 'Nguyễn Vân Anh', '123 Đường Hùng Vương, Quận Hải Châu, TP. Đà Nẵng', '+84912340001', b'0', '1991-02-15',
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242275/pa2_ruttml.jpg'), 
    (2, 'Nguyễn Văn Bảo', '456 Đường Trần Phú, Quận Hải Châu, TP. Đà Nẵng', '+84912340002', b'1', '1992-03-20',
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242275/pa1_wjygvu.jpg'),
    (3, 'Phạm Quốc Cường', '789 Đường Phạm Văn Đồng, Quận Sơn Trà, TP. Đà Nẵng', '+84912340003', b'1', '1993-04-25',
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242271/pa6_kacpe9.jpg'),
    (4, 'Trần Hoàng Danh', '101 Đường Nguyễn Văn Linh, Quận Hải Châu, TP. Đà Nẵng', '+84912340004', b'1', '1994-05-30',
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242271/pa7_kwuqww.jpg'),
    (5, 'Võ Minh Giang', '202 Đường Bạch Đằng, Quận Hải Châu, TP. Đà Nẵng', '+84912340005', b'0', '1995-06-10',
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242275/pa3_aao2ah.jpg'),
    (6, 'Đặng Văn Tân', '234 Đường Hoàng Diệu, Quận Hải Châu, TP. Đà Nẵng', '+84912340006', b'1', '1996-07-15',
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242268/pa8_r9rkoh.jpg'),
    (7, 'Bùi Đức Anh', '567 Đường Nguyễn Tất Thành, Quận Thanh Khê, TP. Đà Nẵng', '+84912340007', b'1', '1997-08-20',
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242268/pa9_tdn3vr.jpg'),
    (8, 'Lê Thị Huệ', '890 Đường Lê Duẩn, Quận Thanh Khê, TP. Đà Nẵng', '+84912340008', b'0', '1998-09-25',
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242272/pa4_fwpehi.jpg'),
    (9, 'Nguyễn Văn Chính', '321 Đường Điện Biên Phủ, Quận Thanh Khê, TP. Đà Nẵng', '+84912340009', b'1', '1999-10-30',
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa10_bcgrhx.jpg'),
    (10, 'Lê Hoàng Nhung', '654 Đường Võ Văn Kiệt, Quận Sơn Trà, TP. Đà Nẵng', '+84912340010', b'0', '2000-11-10',
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242272/pa5_wbufsg.jpg');
INSERT INTO EMPLOYEE (employee_userId, employee_name, employee_phonenumber, employee_gender, department_id, employee_status, employee_image)
VALUES
    (1, 'Nguyễn Văn Quân', '+84901234567', 1, 1, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa11_hfy4fy.jpg'),
    (2, 'Lê Minh Trung', '+84902345678', 0, 2, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242272/pa5_wbufsg.jpg'),
    (3, 'Trần Quốc Khánh', '+84903456789', 1, 3, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242260/pa17_q82v5g.jpg'),
    (4, 'Nguyễn Văn Lạc', '+84904567890', 0, 4, 'OFF', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242265/pa12_lc3xry.jpg'),
    (5, 'Phạm Thị Linh', '+84905678901', 0, 5, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242264/pa13_xrkes1.jpg'),
    (6, 'Hoàng Thị Lương', '+84906789012', 0, 6, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242263/pa15_ujr1bn.jpg'),
    (7, 'Nguyễn Thị Thắm', '+84907890123', 0, 7, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242263/pa16_wx2amq.jpg'),
    (8, 'Lê Thị Ánh', '+84908901234', 0, 8, 'OFF', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242259/pa18_chzhoo.jpg'),
    (9, 'Hồ Thị Phương', '+84909012345', 0, 9, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242259/pa19_ofppky.jpg'),
    (10, 'Trần Lê Tú', '+84900123456', 0, 10, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242259/pa20_k6cnbr.jpg');

INSERT INTO DOCTOR (doctor_userId, doctor_name, doctor_address, doctor_phonenumber, doctor_gender, doctor_date_of_birth, department_id, doctordetail_id, doctor_image, doctor_status)
VALUES 
    (1, 'Nguyễn Văn Khải', '123 Đường Lê Duẩn, Quận Hải Châu', '+84901234567', 1, '1980-05-10', 1, 1,
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241977/doc4_rvnhzx.jpg', 'ON'),
    (2, 'Trần Minh Hoàng', '56 Đường Nguyễn Văn Linh, Quận Thanh Khê', '+84902345678', 1, '1985-07-20', 2, 2,
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241977/doc6_drk8g8.jpg', 'ON'),
    (3, 'Phạm Thị Ánh', '89 Đường Phan Châu Trinh, Quận Sơn Trà', '+84903456789', 0, '1990-03-15', 3, 3,
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc2_dpk9gm.jpg', 'ON'),
    (4, 'Lê Thu Lý', '22 Đường Võ Nguyên Giáp, Quận Ngũ Hành Sơn', '+84904567890', 0, '1978-12-01', 4, 4,
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241977/doc5_zfcris.jpg', 'ON'),
    (5, 'Đỗ Thanh Phúc', '40 Đường Nguyễn Hữu Thọ, Quận Liên Chiểu', '+84905678901', 1, '1988-11-22', 5, 5,
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241977/doc6_drk8g8.jpg', 'ON'),
    (6, 'Bùi Văn Quân', '67 Đường Tôn Đức Thắng, Quận Hải Châu', '+84906789012', 1, '1991-04-10', 6, 6,
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc7_nndmxf.jpg', 'ON'),
    (7, 'Ngô Hải Hiếu', '35 Đường Hoàng Diệu, Quận Thanh Khê', '+84907890123', 1, '1984-08-30', 7, 7,
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc9_pozgu9.jpg', 'ON'),
    (8, 'Dương Thị Vân', '76 Đường Điện Biên Phủ, Quận Sơn Trà', '+84908901234', 0, '1973-06-17', 8, 8, 
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc8_rzalke.jpg', 'ON'),
    (9, 'Trịnh Văn Lộc', '52 Đường Trưng Nữ Vương, Quận Ngũ Hành Sơn', '+84909012345', 1, '1966-09-27', 9, 9,
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc10_nowqn6.jpg', 'ON'),
    (10, 'Đặng Văn Tư', '101 Đường Nguyễn Chí Thanh, Quận Hải Châu', '+84901123456', 0, '1979-02-13', 10, 10,
    'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc8_rzalke.jpg', 'ON');

INSERT INTO DOCTOR_SERVICE (doctor_id, service_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(3, 5),
(3, 6),
(4, 7),
(4, 8),
(5, 9),
(5, 10),
(6, 11),
(6, 12),
(7, 13),
(7, 14),
(8, 15),
(8, 16),
(9, 17),
(9, 18),
(10, 19),
(10, 20);

INSERT INTO DOCTOR_TIMEWORK (doctortimework_year, week_of_year, timework_id, doctor_id, doctortimework_status)
 VALUES
    (2024, 1, 1, 1, 'Available'),
    (2024, 1, 2, 2, 'Busy'),
    (2024, 2, 15, 3, 'Available'),
    (2024, 2, 17, 4, 'Available'),
    (2024, 3, 26, 5, 'Available'),
    (2024, 3, 36, 6, 'Busy'),
    (2024, 4, 10, 7, 'Available'),
    (2024, 4, 16, 8, 'Busy'),
    (2024, 5, 11, 9, 'Available'),
    (2024, 5, 12, 10, 'Available');
    
INSERT INTO APPOINTMENT (patient_id, doctortimework_id, employee_id, doctorservice_id, 
						appointment_created_at, appointment_status, appointment_note
) VALUES 
    (1, 1, 1, 1, '2024-10-16 08:00:00', 'Pending', 'Khám tổng quát'),
    (2, 2, 2, 2, '2024-10-16 09:00:00', 'Confirmed', 'Chữa răng'),
    (3, 3, 3, 3, '2024-10-17 10:00:00', 'Completed', 'Khám mắt'),
    (4, 4, 4, 4, '2024-11-01 11:00:00', 'Pending', 'Khám tim mạch'),
    (5, 5, 5, 5, '2024-11-01 12:00:00', 'Confirmed', 'Chữa răng'),
    (6, 6, 6, 6, '2024-11-03 13:00:00', 'Completed', 'Khám tai mũi họng'),
    (7, 7, 7, 7, '2024-11-09 14:00:00', 'Pending', 'Chữa xương khớp'),
    (8, 8, 8, 8, '2024-11-10 15:00:00', 'Confirmed', 'Khám sản'),
    (9, 9, 9, 9, '2024-11-11 16:00:00', 'Completed', 'Khám thần kinh'),
    (10, 10, 10, 10, '2024-11-11 17:00:00', 'Pending', 'Chữa răng');

