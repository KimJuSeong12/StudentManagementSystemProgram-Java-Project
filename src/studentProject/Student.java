package studentProject;

import java.util.Objects;

public class Student implements Comparable<Student> {
	public static final int COUNT = 3;

	private int id;
	private String name;
	private int age;
	private int kor;
	private int eng;
	private int math;
	private int total;
	private double avg;
	private String grade;

	public Student() {

	}

	public Student(String name) {
		this(name, 0, 0, 0, 0);
	}

	public Student(String name, int age) {
		this(name, age, 0, 0, 0);
	}

	public Student(String name, int age, int kor, int eng, int math) {
		this(0, name, age, kor, eng, math, 0, 0.0, null);
	}

	public Student(int id, String name, int age, int kor, int eng, int math, int total, double avg, String grade) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		this.total = total;
		this.avg = avg;
		this.grade = grade;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getKor() {
		return this.kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return this.eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getMath() {
		return this.math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	public int getTotal() {
		return this.total;
	}

	public void calTotal() {
		this.total = kor + eng + math;
	}

	public double getAvg() {
		String data = String.format("%.2f", this.avg);
		return Double.parseDouble(data);
	}

	public void calAvg() {
		this.avg = total / (double) Student.COUNT;
	}

	public String getGrade() {
		return this.grade;
	}

	public void calGrade() {
		switch ((int) avg / 10) {
		case 10:
		case 9:
			this.grade = "A";
			break;
		case 8:
			this.grade = "B";
			break;
		case 7:
			this.grade = "C";
			break;
		case 6:
			this.grade = "D";
			break;
		default:
			this.grade = "F";
			break;

		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.age);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Student) {
			Student student = (Student) obj;
			return (this.age == student.age) && (this.name.equals(student.name));
		}
		return false;
	}

	@Override
	public String toString() {
		return id + "\t" + name + "\t" + age + "\t" + kor + "\t" + eng + "\t" + math + "\t" + total + "\t"
				+ String.format("%.2f", avg) + "\t" + grade;
	}

	@Override
	public int compareTo(Student o) {
		if ((this.total - o.total) == 0) {
			return 0;
		} else if ((this.total - o.total) > 0) {
			return 1;
		} else {
			return -1;
		}
	}
}