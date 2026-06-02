package org.example.dao;

import org.example.db.DBUtility;
import org.example.entity.Course;
import org.example.entity.Enrollment;
import org.example.entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {

    public boolean isEmailExist(String email) {
        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement("SELECT COUNT(*) FROM student WHERE email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) flag = rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBUtility.closeConnection(rs, pstmt, con);
        }
        return flag;
    }

    public boolean isCourseTitleExist(String title) {
        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement("SELECT COUNT(*) FROM course WHERE title = ?");
            pstmt.setString(1, title);
            rs = pstmt.executeQuery();
            if (rs.next()) flag = rs.getInt(1) > 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
        finally {
            DBUtility.closeConnection(rs, pstmt, con);
        }
        return flag;
    }

    public boolean isStudentExist(int id) {
        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement("SELECT COUNT(*) FROM student WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) flag = rs.getInt(1) > 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
        finally {
            DBUtility.closeConnection(rs, pstmt, con);
        }
        return flag;
    }

    public boolean isCourseExist(int id) {
        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement("SELECT COUNT(*) FROM course WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) flag = rs.getInt(1) > 0;
        } catch (SQLException e) { throw new RuntimeException(e); }
        finally {
            DBUtility.closeConnection(rs, pstmt, con);
        }
        return flag;
    }

    public boolean isEnrollmentExist(int studentId, int courseId) {
        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement("SELECT COUNT(*) FROM enrollment WHERE student_id = ? AND course_id = ?");
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            rs = pstmt.executeQuery();
            if (rs.next()) flag = rs.getInt(1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBUtility.closeConnection(rs, pstmt, con);
        }
        return flag;
    }

    public boolean addStudent(Student student) {
        if (isEmailExist(student.getEmail())) {
            System.out.println("Lỗi: Địa chỉ Email [" + student.getEmail() + "] đã được đăng ký bởi sinh viên khác!");
            return false;
        }
        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("INSERT INTO student(name, email) VALUES (?, ?)");
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBUtility.closeConnection(null, pstmt, con);
        }
        return flag;
    }

    public boolean addCourse(Course course) {
        if (isCourseTitleExist(course.getTitle())) {
            System.out.println("Lỗi: Khóa học có tiêu đề [" + course.getTitle() + "] đã tồn tại!");
            return false;
        }
        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("INSERT INTO course(title, credits) VALUES (?, ?)");
            pstmt.setString(1, course.getTitle());
            pstmt.setInt(2, course.getCredits());
            pstmt.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBUtility.closeConnection(null, pstmt, con);
        }
        return flag;
    }

    public boolean enrollStudent(int studentId, int courseId) {
        if (!isStudentExist(studentId)) {
            System.out.println("Lỗi: Không tồn tại sinh viên mang mã ID = " + studentId);
            return false;
        }
        if (!isCourseExist(courseId)) {
            System.out.println("Lỗi: Không tồn tại khóa học mang mã ID = " + courseId);
            return false;
        }
        if (isEnrollmentExist(studentId, courseId)) {
            System.out.println("Lỗi: Sinh viên này đã ghi danh vào khóa học này từ trước!");
            return false;
        }

        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("INSERT INTO enrollment(student_id, course_id) VALUES (?, ?)");
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            throw new RuntimeException(e); }
        finally {
            DBUtility.closeConnection(null, pstmt, con); }
        return flag;
    }

    public boolean updateStudentGrade(int studentId, int courseId, double grade) {
        if (!isEnrollmentExist(studentId, courseId)) {
            System.out.println("Lỗi: Bản ghi ghi danh không tồn tại! Sinh viên chưa đăng ký khóa học này.");
            return false;
        }

        boolean flag = false;
        Connection con = DBUtility.openConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement("UPDATE enrollment SET grade = ? WHERE student_id = ? AND course_id = ?");
            pstmt.setDouble(1, grade);
            pstmt.setInt(2, studentId);
            pstmt.setInt(3, courseId);
            pstmt.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            DBUtility.closeConnection(null, pstmt, con);
        }
        return flag;
    }

    public List<Enrollment> listStudentsAndGrades() {
        List<Enrollment> list = new ArrayList<>();
        Connection con = DBUtility.openConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            String sql = "SELECT s.id AS stu_id, s.name AS stu_name, s.email AS stu_email, " +
                    "c.id AS cou_id, c.title AS cou_title, c.credits, " +
                    "e.grade " +
                    "FROM enrollment e " +
                    "INNER JOIN student s ON e.student_id = s.id " +
                    "INNER JOIN course c ON e.course_id = c.id " +
                    "ORDER BY s.id ASC";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("stu_id"),
                        rs.getString("stu_name"),
                        rs.getString("stu_email")
                );

                Course course = new Course(
                        rs.getInt("cou_id"),
                        rs.getString("cou_title"),
                        rs.getInt("credits")
                );

                Double gradeValue = rs.getDouble("grade");
                if (rs.wasNull()) {
                    gradeValue = null;
                }

                Enrollment enrollment = new Enrollment(student, course, gradeValue);
                list.add(enrollment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); }
        finally {
            DBUtility.closeConnection(rs, stmt, con);
        }
        return list;
    }
}