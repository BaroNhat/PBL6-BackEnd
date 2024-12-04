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

INSERT INTO department(department_name, department_description)
VALUES 
    ('Răng-Hàm-Mặt', 'Chăm sóc và điều trị các vấn đề về răng miệng và hàm mặt'),
    ('Tim Mạch', 'Chuyên khám và điều trị các bệnh lý về tim mạch'),
    ('Dinh Dưỡng', 'Tư vấn và điều trị các vấn đề về dinh dưỡng'),
    ('Hô Hấp', 'Khám và điều trị các bệnh lý về đường hô hấp'),
    ('Tiêu Hóa', 'Chuyên điều trị các bệnh lý về tiêu hóa'),
    ('Thần Kinh', 'Chăm sóc và điều trị các bệnh về thần kinh'),
    ('Xương Khớp', 'Khám và điều trị các vấn đề về cơ xương khớp'),
    ('Sản Phụ Khoa', 'Chuyên về các dịch vụ sản khoa và phụ khoa'),
    ('Nhi Khoa', 'Khám và điều trị các bệnh cho trẻ em'),
    ('Da Liễu', 'Chăm sóc và điều trị các vấn đề về da liễu');
    
INSERT INTO service (service_name, department_id, service_description, service_price, service_image)
VALUES
('Khám tổng quát răng miệng', 1, 'Kiểm tra và tư vấn tình trạng răng miệng', 200000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731828237/keufscqc12vazk1qdi4s.jpg'),
('Nhổ răng khôn', 1, 'Nhổ răng khôn mọc lệch, mọc ngầm', 800000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731906234/v1wdiuxzodo6cojkcofc.png'),
('Khám tổng quát tim mạch', 2, 'Kiểm tra sức khỏe và chức năng tim mạch', 300000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731836224/jjnyn4z5jcridiapwkbk.jpg'),
('Điện tâm đồ', 2, 'Thực hiện đo điện tâm đồ để kiểm tra nhịp tim', 500000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731906232/wav4b11g4sxaib8qkpqi.jpg'),
('Tư vấn dinh dưỡng cá nhân', 3, 'Lập kế hoạch dinh dưỡng phù hợp với cá nhân', 200000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731836225/k9rr4sjzohxfdlyvy4jg.png'),
('Đánh giá tình trạng dinh dưỡng', 3, 'Kiểm tra tình trạng dinh dưỡng và đưa ra hướng điều trị', 300000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731906232/l0rxtn2pqcycmgvd4fft.jpg'),
('Khám tổng quát hô hấp', 4, 'Kiểm tra và tư vấn các bệnh lý đường hô hấp', 250000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731836225/hhweqgvbooluqnqkqwwj.png'),
('Xét nghiệm chức năng hô hấp', 4, 'Đánh giá chức năng hô hấp qua các xét nghiệm chuyên sâu', 700000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731906232/asaqqf68pw4xsvbxvliw.jpg'),
('Khám tiêu hóa tổng quát', 5, 'Kiểm tra và tư vấn các bệnh lý về tiêu hóa', 300000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731836224/narif8ewmtvlooew5kee.jpg'),
('Nội soi dạ dày', 5, 'Thực hiện nội soi để phát hiện bệnh lý dạ dày', 1500000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731906233/yo7v7vbyvs4riatn6hah.jpg'),
('Khám tổng quát thần kinh', 6, 'Kiểm tra và đánh giá các bệnh về thần kinh', 400000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731836224/ceer5db04i9aaaisoeq8.jpg'),
('Điện não đồ', 6, 'Đo điện não đồ để phát hiện bất thường', 800000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731906445/trozn2xuwleyck6gprak.png'),
('Khám xương khớp tổng quát', 7, 'Kiểm tra và tư vấn các bệnh lý cơ xương khớp', 350000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731836223/vbyxswr2y9eikcx2ffrv.jpg'),
('Chụp X-quang khớp', 7, 'Thực hiện chụp X-quang để phát hiện bệnh', 600000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731906322/ifgfslxcxwekasy4ngnr.jpg'),
('Khám phụ khoa định kỳ', 8, 'Kiểm tra và tư vấn các vấn đề về phụ khoa', 300000, 'shttps://res.cloudinary.com/dy8p5yjsd/image/upload/v1731906231/ln9ffr7lbloti53jmtsl.jpg'),
('Siêu âm thai', 8, 'Siêu âm thai và tư vấn cho thai phụ', 500000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731836225/amwkhfpjnnsqqx02epnf.jpg'),
('Khám tổng quát cho trẻ', 9, 'Kiểm tra sức khỏe tổng quát cho trẻ', 250000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731836223/fiadecryq5ofrokkefsd.jpg'),
('Tiêm chủng mở rộng', 9, 'Thực hiện tiêm các loại vắc-xin cần thiết cho trẻ', 300000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731906232/pixpylmze38giahajw3n.jpg'),
('Khám da tổng quát', 10, 'Kiểm tra và tư vấn các vấn đề về da', 200000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731836225/bgiu0zvtlwqyx2aozouv.jpg'),
('Điều trị mụn', 10, 'Chăm sóc và điều trị các loại mụn', 500000, 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731906231/fdejtcsb9kxa1itjeuok.jpg');

INSERT INTO timework (day_of_week, start_time, end_time)
VALUES 
    ('MONDAY', '08:00:00', '09:00:00'),
    ('MONDAY', '09:00:00', '10:00:00'),
    ('MONDAY', '10:00:00', '11:00:00'),
    ('MONDAY', '11:00:00', '12:00:00'),
    ('MONDAY', '13:00:00', '14:00:00'),
    ('MONDAY', '14:00:00', '15:00:00'),
    ('MONDAY', '15:00:00', '16:00:00'),
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
	('WEDNESDAY', '16:00:00', '17:00:00'),

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
    ('SATURDAY', '15:00:00', '16:00:00'),
    ('SATURDAY', '16:00:00', '17:00:00');

INSERT INTO doctor_detail (doctordetail_information, doctordetail_experience, doctordetail_award_recognization) 
VALUES
    ('Bác sĩ Răng-Hàm-Mặt hàng đầu', '12 năm kinh nghiệm trong điều trị nha khoa', 'Giải thưởng Bác sĩ Nha khoa xuất sắc 2021'),
    ('Chuyên gia chỉnh nha', '10 năm kinh nghiệm trong chỉnh nha thẩm mỹ', 'Giải thưởng Chỉnh nha quốc tế 2018'),
	('Bác sĩ tim mạch chuyên sâu', '15 năm kinh nghiệm trong phẫu thuật tim', 'Giải thưởng Quốc gia về Tim mạch 2020'),
    ('Bác sĩ hồi sức tim mạch', '9 năm kinh nghiệm trong cấp cứu và hồi sức tim mạch', 'Giải thưởng Y khoa 2017'),
    ('Chuyên gia nội tiết hàng đầu', '10 năm kinh nghiệm trong điều trị tiểu đường', 'Giải thưởng Nội tiết học 2020'),
    ('Bác sĩ nội tiết chuyên sâu', '12 năm kinh nghiệm trong điều trị rối loạn nội tiết', 'Giải thưởng Điều trị rối loạn nội tiết 2019'),
    ('Bác sĩ chuyên khoa hô hấp', '14 năm kinh nghiệm trong điều trị bệnh phổi mãn tính', 'Giải thưởng Quốc tế về Hô hấp 2018'),
    ('Chuyên gia phẫu thuật hô hấp', '11 năm kinh nghiệm trong phẫu thuật nội soi hô hấp', 'Giải thưởng Hô hấp xuất sắc 2021'),
    ('Bác sĩ chuyên khoa tiêu hóa', '13 năm kinh nghiệm trong điều trị viêm loét dạ dày', 'Giải thưởng Điều trị dạ dày xuất sắc 2019'),
    ('Chuyên gia phẫu thuật tiêu hóa', '10 năm kinh nghiệm trong phẫu thuật nội soi tiêu hóa', 'Giải thưởng Phẫu thuật Tiêu hóa 2020'),
    ('Bác sĩ thần kinh chuyên sâu', '10 năm kinh nghiệm điều trị đau đầu mãn tính', 'Giải thưởng Quốc tế về Thần kinh học 2021'),
    ('Chuyên gia thần kinh trẻ em', '9 năm kinh nghiệm trong điều trị thần kinh nhi khoa', 'Giải thưởng Bác sĩ thần kinh nhi xuất sắc 2018'),
    ('Bác sĩ xương khớp chuyên sâu', '11 năm kinh nghiệm trong điều trị viêm khớp', 'Giải thưởng Y học Xương khớp 2020'),
    ('Chuyên gia phục hồi chức năng', '13 năm kinh nghiệm trong vật lý trị liệu và phục hồi chức năng', 'Giải thưởng Phục hồi chức năng 2021'),
    ('Bác sĩ sản khoa chuyên sâu', '16 năm kinh nghiệm trong đỡ đẻ và chăm sóc thai sản', 'Giải thưởng Quốc gia về Sản khoa 2019'),
    ('Chuyên gia phụ khoa', '12 năm kinh nghiệm trong điều trị viêm nhiễm phụ khoa', 'Giải thưởng Quốc tế về Phụ khoa 2018'),
    ('Bác sĩ nhi khoa chuyên sâu', '9 năm kinh nghiệm điều trị các bệnh về đường hô hấp ở trẻ', 'Giải thưởng Bác sĩ Nhi khoa xuất sắc 2020'),
    ('Chuyên gia dinh dưỡng nhi khoa', '10 năm kinh nghiệm trong tư vấn dinh dưỡng trẻ em', 'Giải thưởng Quốc gia về Nhi khoa 2021'),
    ('Bác sĩ da liễu chuyên sâu', '8 năm kinh nghiệm trong điều trị nám và tàn nhang', 'Giải thưởng Quốc gia về Da liễu 2020'),
    ('Chuyên gia thẩm mỹ da', '10 năm kinh nghiệm trong trị liệu laser thẩm mỹ', 'Giải thưởng Quốc tế về Thẩm mỹ da 2019');

INSERT INTO user (user_username, user_password, user_email, user_role)
VALUES     
('pa_anh', '$2a$10$jnytaQEJOJsgyfdVHHmpf.qRSuScHxGgqs91XLwu720psGC.PNi8C', 'anh@gmail.com', 'PATIENT'),
('pa_bao', '$2a$10$SlWDXpG4L2wYHUUyA4RxROIfOoPOdl9iMxCegDqxKNN7i1iiWu/OC', 'bao@gmail.com', 'PATIENT'),
('pa_cuong', '$2a$10$4ky0d3fkOzoQPNOKtqyTOOiMumHf7FsRhvcyLeucq8XVv9NpY2oVe', 'cuong@gmail.com', 'PATIENT'),
('pa_danh', '$2a$10$tHA4W1z5YDsx58Kgbq/T7upgZIfI5gQ9qgV4Za9jg9cGDdYhTh2IW', 'danh@gmail.com', 'PATIENT'),
('pa_giang', '$2a$10$35VNN6N7AI7B42Rn3joaMOsR8/PL/.W.ItAx7Mr0LMkKOSJ8fBo..', 'giang@gmail.com', 'PATIENT'),
('pa_tan', '$2a$10$zDpsDj.fYpTgD/gt7QDN5.8j9FUVUQsRZnRUjSnFHdg34B0aP680C', 'tan@gmail.com', 'PATIENT'),
('pa_duc', '$2a$10$qq2FabHNKx3EFUXKQJMgpeafc6J5Co30uRNdt.6Ovxm/WerEorFPC', 'duc@gmail.com', 'PATIENT'),
('pa_hue', '$2a$10$w.ugi3OXD/.Tl9zrfKttluCKKpeX2DYs74WvkezIGIwSDJSQWtjwy', 'hue@gmail.com', 'PATIENT'),
('pa_chinh', '$2a$10$Qve36Z15CJr8j/BIzTrJWuNoXU8..ZiWtmzWiRbp0UCrVve14RZQq', 'chinh@gmail.com', 'PATIENT'),
('pa_nhung', '$2a$10$.GGH8GNvoJcUIQVYsy9fLe//.IgKT/aybjb.bKOgvRXhhh4sTX3.O', 'nhung@gmail.com', 'PATIENT'),

('em_quan', '$2a$10$c8kWoQk6IWXDw4qzDVdAP.2B1pgnE2KJsJtefzppVPbgkDKQNIEf.', 'quan@gmail.com', 'EMPLOYEE'),
('em_trung', '$2a$10$3vJ.IYu0JQllIwFrKfsGb.jRiKJzKiix5NBNI58jOW9D5rrYp4QFW', 'trung@gmail.com', 'EMPLOYEE'),
('em_khanh', '$2a$10$mWah7W2RcpC5yBc3hcWj0ODtUQbvkB6SjhSaIGKhdakY82GaP5.D2', 'quoc@gmail.com', 'EMPLOYEE'),
('em_lac', '$2a$10$4w2KavIMR./63LR5pKSMtukHSCB9xQdEXUJpp6btR0C8TXLnfm9vS', 'lac@gmail.com', 'EMPLOYEE'),
('em_linh', '$2a$10$OgcyfwBQjOVyXpBvQ.95g.wOVp3QefSh26MN6gQL03lV2INmjcmKS', 'linh@gmail.com', 'EMPLOYEE'),
('em_luong', '$2a$10$qXQciW13knMZ6F47pbWAs.i4wFwIlGCNiYX2vTrkfTaKWNrCYwJpC', 'luong@gmail.com', 'EMPLOYEE'),
('em_tham', '$2a$10$x0qaxF1Eg4kQkZPqoAqhJuGeZVVNmRfL95aAv/tUT1kQdlAZqR/GO', 'tham@gmail.com', 'EMPLOYEE'),
('em_anh', '$2a$10$vRMN3ynbsKzYcJbOPNJNPOoTYNLyG9MHTlD.8riEOc90ebN1tTP5S', 'viet@gmail.com', 'EMPLOYEE'),
('em_phuong', '$2a$10$uO1UKTUe6beP6dG/R/BElu4P7nNficGEsT1duKO9g4dZBt/oow9K2', 'phuong@gmail.com', 'EMPLOYEE'),
('em_tu', '$2a$10$8pZMovrJPAzNS0EQeBRrK.fUvC/bt.zyIh65DFFrvMmi70QzkFBu.', 'tu@gmail.com', 'EMPLOYEE'),

('doc_khai', '$2a$10$bqA6hY0Q5.36VaqMiohg6e55Q56LoM.HYCn.HickRlv6NJ9WEt08i', 'khai@gmail.com', 'DOCTOR'),
('doc_hoang', '$2a$10$kkPleyJGCNmny54j2DKWwuscZiLumjq.w.a9Oy6gf9zgPja/GmJDy', 'hoang@gmail.com', 'DOCTOR'),
('doc_anh', '$2a$10$cqYSTY.YsFimxjfs1/CIc.WdwLJukUJHO037sH5XtTBCIVXj7JzPu', 'anhdoc@gmail.com', 'DOCTOR'),
('doc_ly', '$2a$10$FMEIAN2Y0gJpaX8J07vPHOaDDWQu7iA6Ku2p2W6h1C3G0rM4p8WdK', 'ly@gmail.com', 'DOCTOR'),
('doc_phuc', '$2a$10$q7Y3c7PbZhqBysj4hMen0eFDGTK9LDxDhrP6CuXP3eMFsA5RUV3Iu', 'phuc@gmail.com', 'DOCTOR'),
('doc_quan', '$2a$10$0dqq6GKK/U1w9Tf9ENMd4e9kwWXcfj22clsdniwAWwctsQBeVBeYG', 'quandoc@gmail.com', 'DOCTOR'),
('doc_hieu', '$2a$10$4YB.AJXGEfTMEHUQHpjda.6eXiO6J.fm75HZ3ZSPtdokj6JgFngxi', 'hieu@gmail.com', 'DOCTOR'),
('doc_van', '$2a$10$rHQhztnfalTXme1.yGnWnOXaPm/j7nBeUjRno9zSEYRHwp6gE3iVe', 'van@gmail.com', 'DOCTOR'),
('doc_loc', '$2a$10$WQtiwbQYi7tCPD5HQ9QjaOTX3./JRyE885uj2xcP9Vbm.Kjq5hVDi', 'loc@gmail.com', 'DOCTOR'),
('doc_tu', '$2a$10$G7IXJ5Nn7lAxyglo5JOE7edjDegi.2QZ4AkTNkJkE7n0gw3WncU9i', 'tudoc@gmail.com', 'DOCTOR'),
('doc_trung', '$2a$10$jhusG9StbuVh4dnUep5lsukN0JBraWOs0fKy5qGfx4MgqoP47LMGS', 'trungdoc@gmail.com', 'DOCTOR'),
('doc_minh', '$2a$10$akhU8uCRIr7zRKfTM2jsW..y5SFYfLIF3AioOMMTZ3VJJA1Uu0n.e', 'minh@gmail.com', 'DOCTOR'),
('doc_hai', '$2a$10$rVrY6hGJuFj1t72zQYg0HuGFBiXfznjXWiCUFlxAhc783JfnJCcSe', 'hai@gmail.com', 'DOCTOR'),
('doc_dung', '$2a$10$lNGIaFMqCP3L5Ecl92acqucDHL0HvnLyp6hxAuAjhsCfnTAAFDNYa', 'dung@gmail.com', 'DOCTOR'),
('doc_hoa', '$2a$10$tinYYWlmS2eVV7e6XPN1QORY7pr4TfeuyAWpRiBZj1iNV8APwClUy', 'hoa@gmail.com', 'DOCTOR'),
('doc_binh', '$2a$10$i27IjcY5LvXFBsjwjWgPce6J8Ovn/rkKRIGPiZuubWJnCKou0ilme', 'binh@gmail.com', 'DOCTOR'),
('doc_hung', '$2a$10$0mBaQkSyb412qiLpL1eXYul3bV6oLIqWSo6N9YCatfwdXSJM.6Rjy', 'hung@gmail.com', 'DOCTOR'),
('doc_nhan', '$2a$10$owXQ28DBdrszUFv6vzS9H.V5s8P.zFi4SyiULq9uilAU22WCRRt0e', 'nhan@gmail.com', 'DOCTOR'),
('doc_tam', '$2a$10$He868WWSNwCnwuZ36rnlbexOQIyVojM2JoPs/.OFnq5ipFL4o6qjO', 'tam@gmail.com', 'DOCTOR'),
('doc_linh', '$2a$10$taANXCuX.EGkMbO8NiW6GOWLgBeHSG31kPbZXxyGAClJ.gpzvF1lm', 'linhdoc@gmail.com', 'DOCTOR');

INSERT INTO patient (patient_userId, patient_name, patient_address, patient_phonenumber, patient_gender, patient_date_of_birth, patient_image)
VALUES 
    (1, 'Nguyễn Vân Anh', '123 Đường Hùng Vương, Quận Hải Châu, TP. Đà Nẵng', '+84912340001', b'0', '1991-02-15', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242275/pa2_ruttml.jpg'), 
    (2, 'Nguyễn Văn Bảo', '456 Đường Trần Phú, Quận Hải Châu, TP. Đà Nẵng', '+84912340002', b'1', '1992-03-20', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242275/pa1_wjygvu.jpg'),
    (3, 'Phạm Quốc Cường', '789 Đường Phạm Văn Đồng, Quận Sơn Trà, TP. Đà Nẵng', '+84912340003', b'1', '1993-04-25', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242271/pa6_kacpe9.jpg'),
    (4, 'Trần Hoàng Danh', '101 Đường Nguyễn Văn Linh, Quận Hải Châu, TP. Đà Nẵng', '+84912340004', b'1', '1994-05-30', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242271/pa7_kwuqww.jpg'),
    (5, 'Võ Minh Giang', '202 Đường Bạch Đằng, Quận Hải Châu, TP. Đà Nẵng', '+84912340005', b'0', '1995-06-10', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242275/pa3_aao2ah.jpg'),
    (6, 'Đặng Văn Tân', '234 Đường Hoàng Diệu, Quận Hải Châu, TP. Đà Nẵng', '+84912340006', b'1', '1996-07-15', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242268/pa8_r9rkoh.jpg'),
    (7, 'Bùi Đức Anh', '567 Đường Nguyễn Tất Thành, Quận Thanh Khê, TP. Đà Nẵng', '+84912340007', b'1', '1997-08-20', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242268/pa9_tdn3vr.jpg'),
    (8, 'Lê Thị Huệ', '890 Đường Lê Duẩn, Quận Thanh Khê, TP. Đà Nẵng', '+84912340008', b'0', '1998-09-25', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242272/pa4_fwpehi.jpg'),
    (9, 'Nguyễn Văn Chính', '321 Đường Điện Biên Phủ, Quận Thanh Khê, TP. Đà Nẵng', '+84912340009', b'1', '1999-10-30', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242267/pa10_bcgrhx.jpg'),
    (10, 'Lê Hoàng Nhung', '654 Đường Võ Văn Kiệt, Quận Sơn Trà, TP. Đà Nẵng', '+84912340010', b'0', '2000-11-10', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242272/pa5_wbufsg.jpg');

INSERT INTO employee (employee_userId, employee_name, employee_phonenumber, employee_gender, department_id, employee_status, employee_image)
VALUES
    (11, 'Nguyễn Văn Quân', '+84901234567', 1, 1, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242272/pa5_wbufsg.jpg'),
    (12, 'Lê Minh Trung', '+84902345678', 0, 2, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242272/pa5_wbufsg.jpg'),
    (13, 'Trần Quốc Khánh', '+84903456789', 1, 3, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242260/pa17_q82v5g.jpg'),
    (14, 'Nguyễn Văn Lạc', '+84904567890', 0, 4, 'OFF', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242265/pa12_lc3xry.jpg'),
    (15, 'Phạm Thị Linh', '+84905678901', 0, 5, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242264/pa13_xrkes1.jpg'),
    (16, 'Hoàng Thị Lương', '+84906789012', 0, 6, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242263/pa15_ujr1bn.jpg'),
    (17, 'Nguyễn Thị Thắm', '+84907890123', 0, 7, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242263/pa16_wx2amq.jpg'),
    (18, 'Lê Thị Ánh', '+84908901234', 0, 8, 'OFF', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242259/pa18_chzhoo.jpg'),
    (19, 'Hồ Thị Phương', '+84909012345', 0, 9, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242259/pa19_ofppky.jpg'),
    (20, 'Trần Lê Tú', '+84900123456', 0, 10, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731242259/pa20_k6cnbr.jpg');

INSERT INTO doctor (doctor_userId, doctor_name, doctor_address, doctor_phonenumber, doctor_gender, doctor_date_of_birth, department_id, doctordetail_id, doctor_status, doctor_image, doctor_description)
VALUES 
    (21, 'Nguyễn Văn Khải', '123 Đường Lê Duẩn, Quận Hải Châu', '+84901234567', 1, '1980-05-10', 1, 1, 'ON',  'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc3_uwcvzl.jpg', 'Bác sĩ Răng-Hàm-Mặt hàng đầu'),
    (22, 'Trần Minh Hoàng', '56 Đường Nguyễn Văn Linh, Quận Thanh Khê', '+84902345678', 1, '1985-07-20', 1, 2, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc3_uwcvzl.jpg', 'Chuyên gia chỉnh nha'),
    (23, 'Phạm Thị Ánh', '89 Đường Phan Châu Trinh, Quận Sơn Trà', '+84903456789', 0, '1990-03-15', 2, 3, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc8_rzalke.jpg', 'Bác sĩ tim mạch chuyên sâu'),
    (24, 'Lê Thu Lý', '22 Đường Võ Nguyên Giáp, Quận Ngũ Hành Sơn', '+84904567890', 0, '1978-12-01', 2, 4, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc8_rzalke.jpg','Bác sĩ hồi sức tim mạch' ),
    (25, 'Đỗ Thanh Phúc', '40 Đường Nguyễn Hữu Thọ, Quận Liên Chiểu', '+84905678901', 1, '1988-11-22', 3, 5, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc3_uwcvzl.jpg', 'Chuyên gia nội tiết hàng đầu'),
    (26, 'Bùi Văn Quân', '67 Đường Tôn Đức Thắng, Quận Hải Châu', '+84906789012', 1, '1991-04-10', 3, 6,'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc3_uwcvzl.jpg', 'Bác sĩ nội tiết chuyên sâu'),
    (27, 'Ngô Hải Hiếu', '35 Đường Hoàng Diệu, Quận Thanh Khê', '+84907890123', 1, '1984-08-30', 4, 7, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc3_uwcvzl.jpg', 'Bác sĩ chuyên khoa hô hấp'),
    (28, 'Dương Thị Vân', '76 Đường Điện Biên Phủ, Quận Sơn Trà', '+84908901234', 0, '1973-06-17', 4, 8, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc8_rzalke.jpg', 'Chuyên gia phẫu thuật hô hấp'),
    (29, 'Trịnh Văn Lộc', '52 Đường Trưng Nữ Vương, Quận Ngũ Hành Sơn', '+84909012345', 1, '1966-09-27', 5, 9, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc3_uwcvzl.jpg', 'Bác sĩ chuyên khoa tiêu hóa'),
    (30, 'Đặng Văn Tư', '101 Đường Nguyễn Chí Thanh, Quận Hải Châu', '+84901123456', 0, '1979-02-13', 5, 10, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc8_rzalke.jpg', 'Chuyên gia phẫu thuật tiêu hóa'),
    (31, 'Nguyễn Thành Trung', '12 Đường Hùng Vương, Quận Thanh Khê', '+84901123451', 1, '1987-10-15', 6, 11, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc3_uwcvzl.jpg','Bác sĩ thần kinh chuyên sâu' ),
    (32, 'Trần Văn Minh', '89 Đường Phạm Văn Đồng, Quận Hải Châu', '+84901123452', 1, '1992-07-30', 6, 12, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc3_uwcvzl.jpg', 'Chuyên gia thần kinh trẻ em'),
    (33, 'Lê Hải Nam', '34 Đường Võ Thị Sáu, Quận Sơn Trà', '+84901123453', 1, '1985-04-25', 7, 13, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc3_uwcvzl.jpg', 'Bác sĩ xương khớp chuyên sâu'),
    (34, 'Phạm Đức Dũng', '23 Đường Trần Phú, Quận Ngũ Hành Sơn', '+84901123454', 1, '1980-06-10', 7, 14, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc3_uwcvzl.jpg', 'Chuyên gia phục hồi chức năng'),
    (35, 'Hoàng Thị Hoa', '78 Đường Lý Thái Tổ, Quận Liên Chiểu', '+84901123455', 0, '1983-09-12', 8, 15, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc8_rzalke.jpg', 'Bác sĩ sản khoa chuyên sâu'),
    (36, 'Đỗ Hữu Bình', '65 Đường Tôn Đức Thắng, Quận Hải Châu', '+84901123456', 1, '1990-12-15', 8, 16, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc3_uwcvzl.jpg', 'Chuyên gia phụ khoa'),
    (37, 'Bùi Thanh Hùng', '44 Đường Nguyễn Văn Linh, Quận Sơn Trà', '+84901123457', 1, '1975-11-20', 9, 17, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc3_uwcvzl.jpg','Bác sĩ nhi khoa chuyên sâu' ),
    (38, 'Ngô Thị Nhân', '12 Đường Phan Đình Phùng, Quận Thanh Khê', '+84901123458', 0, '1988-05-18', 9, 18, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc8_rzalke.jpg', 'Chuyên gia dinh dưỡng nhi khoa'),
    (39, 'Trịnh Văn Tâm', '45 Đường Hoàng Sa, Quận Ngũ Hành Sơn', '+84901123459', 1, '1995-03-27', 9, 19, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc3_uwcvzl.jpg', 'Bác sĩ da liễu chuyên sâu'),
    (40, 'Dương Thị Linh', '102 Đường Trưng Nữ Vương, Quận Hải Châu', '+84901123460', 0, '1982-01-30', 10, 20, 'ON', 'https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731241976/doc8_rzalke.jpg', 'Chuyên gia thẩm mỹ da' );

INSERT INTO doctor_service (doctor_id, service_id) VALUES
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
(10, 20),
(11, 1),
(11, 2),
(12, 3),
(12, 4),
(13, 5),
(13, 6),
(14, 7),
(14, 8 ),
(15, 9),
(15, 10),
(16, 11),
(16, 12),
(17, 13),
(17, 14),
(18, 15),
(18, 16),
(19, 17),
(19, 18),
(20, 19),
(20, 20);

INSERT INTO doctor_timework (doctortimework_year, week_of_year, timework_id, doctor_id, doctortimework_status)
 VALUES
    (2024, 1, 1, 1, 'Available'),
    (2024, 1, 2, 2, 'Busy'),
    (2024, 2, 3, 3, 'Available'),
    (2024, 2, 4, 4, 'Available'),
    (2024, 3, 5, 5, 'Available'),
    (2024, 3, 6, 6, 'Busy'),
    (2024, 4, 7, 7, 'Available'),
    (2024, 4, 8, 8, 'Busy'),
    (2024, 5, 9, 9, 'Available'),
    (2024, 5, 10, 10, 'Available'),
    (2024, 6, 11, 11, 'Available'),
    (2024, 6, 12, 12, 'Busy'),
    (2024, 7, 13, 13, 'Available'),
    (2024, 7, 14, 14, 'Available'),
    (2024, 8, 15, 15, 'Available'),
    (2024, 8, 16, 16, 'Busy'),
    (2024, 9, 17, 17, 'Available'),
    (2024, 9, 18, 18, 'Busy'),
    (2024, 10, 19, 19, 'Available'),
    (2024, 10, 20, 20, 'Available');
    
INSERT INTO appointment (patient_id, doctortimework_id, employee_id, doctorservice_id, 
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