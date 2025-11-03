Arkanoid OOP Game
 Giới thiệu
Arkanoid OOP Game là một trò chơi phá gạch được phát triển bằng ngôn ngữ lập trình Java kết hợp với thư viện LibGDX. Dự án được xây dựng theo tư duy lập trình hướng đối tượng (OOP), giúp tổ chức mã nguồn rõ ràng, dễ mở rộng và bảo trì.
Người chơi điều khiển một thanh chắn (bar) để giữ quả bóng không rơi khỏi màn hình, đồng thời phá vỡ toàn bộ các khối gạch (block) để hoàn thành màn chơi. Trò chơi bao gồm nhiều màn với độ khó khác nhau, cùng các tính năng như tạm dừng, chọn màn, điều chỉnh âm lượng và hiển thị điểm số.
 Tính năng nổi bật
•	 Đa dạng block:
o	Block thường (phá vỡ 1 lần)
o	Block sắt (không thể vỡ)
o	Block di chuyển 
•	 Điều khiển mượt mà:
o	Hỗ trợ bàn phím để điều khiển thanh chắn
•	Tạm dừng và tiếp tục:
o	Có thể tạm dừng game và quay lại bất kỳ lúc nào
•	 Cài đặt âm thanh:
o	Tuỳ chỉnh âm lượng và loại nhạc nền
•	 Tuỳ chỉnh số mạng:
o	Chọn số mạng ban đầu (1, 3 hoặc 5) trong phần cài đặt
•	Hiện thị rank người chơi:
o	Lưu lại tên và điểm số để hiện thị rank

•	 Điểm số và điểm cao nhất:
o	Hiển thị điểm hiện tại và lưu trữ tên và điểm cao nhất
•	Chọn màn chơi:
•	Giao diện chọn level trực quan với hình ảnh đại diện từng màn
🗂️ Cấu trúc thư mục
OOP_GR1_Project/
├── core/
│   ├── anhtuannguyen.oop.Menu/       # Giao diện menu, setting, pause, result
│   ├── anhtuannguyen.oop.Object/     # Các đối tượng: Ball, Bar, Block
│   ├── anhtuannguyen.oop.Level/      # Các màn chơi: Level1 → Level12
│   └── anhtuannguyen.oop.main/       # Điểm khởi chạy game
├── assets/                           # Hình ảnh, âm thanh, font
├── desktop/                          # Cấu hình chạy trên desktop
└── README.md                         # Mô tả dự án
Hướng dẫn chơi
•	Di chuyển thanh chắn: Dùng A/D hoặc phím mũi tên trái/phải để điều khiển.
•	Mục tiêu: Giữ bóng không rơi và phá hết các block trên màn hình.
•	Thắng: Khi tất cả block bị phá hủy.
•	Thua: Khi hết bóng và không còn mạng sống.
Cài đặt & chạy game
Yêu cầu hệ thống
•	Java 11 trở lên
•	LibGDX 1.11+
•	IDE: IntelliJ IDEA, VS Code hoặc Eclipse
•	Hệ điều hành: Windows / macOS / Linux
Cách chạy game
1.	Clone dự án:
git clone https://github.com/anhtuan-2006/OOP_GR1_Project
2.	Mở bằng IDE:
o	Import project như một Gradle project (nếu có cấu hình sẵn)
o	Hoặc cấu hình LibGDX thủ công nếu không dùng Gradle
Đối tượng:
•	Người yêu thích game cổ điển nhưng muốn trải nghiệm hiện đại
•	Sinh viên học OOP muốn thấy lý thuyết được áp dụng vào thực tế
•	Người chơi muốn thử thách phản xạ, chiến thuật và kiên nhẫn

Nhóm phát triển
+ Nguyễn Anh Tuấn
+ Trần Đình Thông
+ Ngô Hoàng Anh
+ Đặng Minh Phúc

Video giới thiệu
https://l.facebook.com/l.php?u=https%3A%2F%2Fyoutu.be%2Fas2zHnwUawc%3Ffbclid%3DIwZXh0bgNhZW0CMTAAYnJpZBExOWE0V1NVQmd6NG1xOXpEdAEe9Yn2ALaNG4YmoGDKm3nXoMo7oIMj8qGWWBx8pLF2_QKV6jaJZVhYXqIGZh4_aem_IZXPNXMkmAvbJmYoFFO1XA&h=AT1Q1aIsEJrJFOQdwHbfq7xjnJUIV_xV5DnhrA8cD2EEe5mVxnK0zN-icJd279jdBB40mvdd2WD0K77tCkcSVf3VNm65yHAh2nAbCcJkATD1BSC1T50j1w7q7FTn7B12FTRYGQ

 Ghi chú
•	Tất cả hình ảnh và âm thanh được sử dụng trong game là do nhóm tự thiết kế hoặc lấy từ nguồn miễn phí.
•	Game được phát triển như một đồ án môn học Lập trình Hướng Đối Tượng.

