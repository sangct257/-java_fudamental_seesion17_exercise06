package org.example;

import org.example.dao.StudentManager;
import org.example.entity.Course;
import org.example.entity.Enrollment;
import org.example.entity.Student;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final StudentManager manager = new StudentManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n======= 🎓 HỆ THỐNG QUẢN LÝ SINH VIÊN & KHÓA HỌC =======");
            System.out.println("1. Thêm sinh viên mới");
            System.out.println("2. Thêm khóa học mới");
            System.out.println("3. Ghi danh sinh viên vào khóa học");
            System.out.println("4. Cập nhật điểm cho sinh viên");
            System.out.println("5. Hiển thị danh sách sinh viên & điểm số");
            System.out.println("6. Thoát");
            System.out.print("Vui lòng chọn chức năng (1-6): ");

            int choice = inputInt();

            switch (choice) {
                case 1: handleAddStudent(); break;
                case 2: handleAddCourse(); break;
                case 3: handleEnrollStudent(); break;
                case 4: handleUpdateGrade(); break;
                case 5: handleListAll(); break;
                case 6:
                    System.out.println("Đã thoát chương trình quản lý. Tạm biệt!");
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn sai! Vui lòng chọn lại số từ 1 đến 6.");
            }
        }
    }


    private static int inputInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Lỗi: Dữ liệu phải là số nguyên! Mời nhập lại: ");
            }
        }
    }

    private static double inputGrade() {
        while (true) {
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                if (val < 0 || val > 10) {
                    System.out.print("Lỗi: Thang điểm hợp lệ phải từ 0.0 đến 10.0! Mời nhập lại: ");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Lỗi: Định dạng điểm số chưa đúng! Mời nhập lại: ");
            }
        }
    }

    private static String inputString(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Lỗi: Không được bỏ trống trường thông tin này!");
            } else {
                return input;
            }
        }
    }


    private static void handleAddStudent() {
        System.out.println("\n--- 📝 THÊM SINH VIÊN MỚI ---");
        String name = inputString("Nhập họ tên sinh viên: ");
        String email = inputString("Nhập email sinh viên: ");

        Student student = new Student(name, email);
        if (manager.addStudent(student)) {
            System.out.println("Thành công: Đã lưu thông tin sinh viên mới!");
        }
    }

    private static void handleAddCourse() {
        System.out.println("\n--- THÊM KHÓA HỌC MỚI ---");
        String title = inputString("Nhập tên/tiêu đề khóa học: ");
        System.out.print("Nhập số tín chỉ (Credits): ");
        int credits = inputInt();
        if (credits <= 0) {
            System.out.println("Lỗi: Số tín chỉ phải lớn hơn 0!");
            return;
        }

        Course course = new Course(title, credits);
        if (manager.addCourse(course)) {
            System.out.println("Thành công: Đã khởi tạo môn học mới!");
        }
    }

    private static void handleEnrollStudent() {
        System.out.println("\n--- GHI DANH SINH VIÊN VÀO KHÓA HỌC ---");
        System.out.print("Nhập mã ID sinh viên: ");
        int studentId = inputInt();
        System.out.print("Nhập mã ID khóa học: ");
        int courseId = inputInt();

        if (manager.enrollStudent(studentId, courseId)) {
            System.out.println("Thành công: Sinh viên đã đăng ký môn học thành công (Hiện chưa có điểm)!");
        }
    }

    private static void handleUpdateGrade() {
        System.out.println("\n--- CẬP NHẬT ĐIỂM SỐ KHÓA HỌC ---");
        System.out.print("Nhập mã ID sinh viên: ");
        int studentId = inputInt();
        System.out.print("Nhập mã ID khóa học: ");
        int courseId = inputInt();
        System.out.print("Nhập số điểm cần nhập (0.0 - 10.0): ");
        double grade = inputGrade();

        if (manager.updateStudentGrade(studentId, courseId, grade)) {
            System.out.println("Thành công: Đã vào điểm cho sinh viên!");
        }
    }

    private static void handleListAll() {
        System.out.println("\n--- BẢNG ĐIỂM GHI DANH ĐÀO TẠO ---");
        List<Enrollment> list = manager.listStudentsAndGrades();

        if (list.isEmpty()) {
            System.out.println("Hệ thống trống. Chưa có dữ liệu sinh viên đăng ký môn học.");
            return;
        }

        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-8s | %-25s | %-30s | %-10s | %-12s\n",
                "MÃ SV", "TÊN SINH VIÊN", "KHÓA HỌC ĐĂNG KÝ", "TÍN CHỈ", "ĐIỂM SỐ");
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        for (Enrollment e : list) {
            String gradeDisplay = (e.getGrade() == null) ? "Chưa có điểm" : String.format("%.2f", e.getGrade());

            System.out.printf("%-8d | %-25s | %-30s | %-10d | %-12s\n",
                    e.getStudent().getId(),
                    e.getStudent().getName(),
                    e.getCourse().getTitle(),
                    e.getCourse().getCredits(),
                    gradeDisplay
            );
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------");
    }
}