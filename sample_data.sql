-- Bộ dữ liệu mẫu cho ứng dụng quản lý sản phẩm đấu giá
-- Database: product_management
-- 
-- Trạng thái sản phẩm trong hệ thống đấu giá:
-- - "Chờ duyệt": Sản phẩm vừa được đăng, chờ admin duyệt
-- - "Đang đấu giá": Sản phẩm đã được duyệt và đang trong quá trình đấu giá
-- - "Đã bán": Sản phẩm đã kết thúc đấu giá và có người thắng

-- Tạo database (nếu chưa có)
CREATE DATABASE IF NOT EXISTS product_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE product_management;

-- Xóa dữ liệu cũ (nếu có)
DELETE FROM product;
DELETE FROM product_type;

-- Reset AUTO_INCREMENT
ALTER TABLE product AUTO_INCREMENT = 1;
ALTER TABLE product_type AUTO_INCREMENT = 1;

-- Thêm dữ liệu cho bảng product_type
INSERT INTO product_type (name) VALUES 
('Điện tử'),
('Thời trang'),
('Gia dụng'),
('Thể thao'),
('Sách'),
('Đồ chơi'),
('Xe cộ');

-- Thêm dữ liệu cho bảng product (14 sản phẩm)
-- Tình trạng phù hợp với web đấu giá: Chờ duyệt, Đang đấu giá, Đã bán
INSERT INTO product (name, price, status, product_type_id) VALUES 
('iPhone 15 Pro Max 256GB', 25000000, 'Đang đấu giá', 1),
('Samsung Galaxy S24 Ultra', 22000000, 'Chờ duyệt', 1),
('MacBook Air M3 13 inch', 28000000, 'Đang đấu giá', 1),
('iPad Pro 11 inch M4', 20000000, 'Đã bán', 1),
('Áo khoác Nike Air Jordan', 2500000, 'Đang đấu giá', 2),
('Giày Adidas Ultraboost 22', 4200000, 'Chờ duyệt', 2),
('Túi xách Louis Vuitton', 35000000, 'Đã bán', 2),
('Nồi cơm điện Panasonic 1.8L', 1800000, 'Đang đấu giá', 3),
('Máy giặt Electrolux 9kg', 12000000, 'Chờ duyệt', 3),
('Bộ dao Nhật Bản Santoku', 3500000, 'Đang đấu giá', 3),
('Xe đạp thể thao Giant', 15000000, 'Đã bán', 4),
('Bộ tạ tay 20kg', 2200000, 'Đang đấu giá', 4),
('Combo sách kinh tế hay nhất', 850000, 'Chờ duyệt', 5),
('Mô hình Gundam Real Grade', 1200000, 'Đang đấu giá', 6);

-- Kiểm tra dữ liệu đã thêm
SELECT 
    p.id,
    p.name,
    FORMAT(p.price, 0) AS 'Giá (VND)',
    p.status,
    pt.name AS 'Loại sản phẩm'
FROM product p
JOIN product_type pt ON p.product_type_id = pt.id
ORDER BY p.id;

-- Thống kê theo loại sản phẩm
SELECT 
    pt.name AS 'Loại sản phẩm',
    COUNT(p.id) AS 'Số lượng sản phẩm',
    FORMAT(AVG(p.price), 0) AS 'Giá khởi điểm TB (VND)',
    FORMAT(MIN(p.price), 0) AS 'Giá thấp nhất (VND)',
    FORMAT(MAX(p.price), 0) AS 'Giá cao nhất (VND)'
FROM product_type pt
LEFT JOIN product p ON pt.id = p.product_type_id
GROUP BY pt.id, pt.name
ORDER BY COUNT(p.id) DESC;

-- Thống kê theo trạng thái đấu giá
SELECT 
    p.status AS 'Trạng thái',
    COUNT(*) AS 'Số lượng',
    FORMAT(AVG(p.price), 0) AS 'Giá khởi điểm TB (VND)',
    ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM product), 1) AS 'Tỷ lệ (%)'
FROM product p
GROUP BY p.status
ORDER BY COUNT(*) DESC;
