package org.example.entity;

public class Enrollment {
    private Student student;
    private Course course;
    private Double grade;

    public Enrollment() {}
    public Enrollment(Student student, Course course, Double grade) {
        this.student = student;
        this.course = course;
        this.grade = grade;
    }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public Double getGrade() { return grade; }
    public void setGrade(Double grade) { this.grade = grade; }
}