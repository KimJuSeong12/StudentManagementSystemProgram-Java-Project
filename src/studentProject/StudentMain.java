package studentProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentMain {
	public static final int INPUT = 1, PRINT = 2, ANALYZE = 3, SEARCH = 4, UPDATE = 5, SORT = 6, DELETE = 7, EXIT = 8;
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		boolean run = true;
		int no = 0;
		DBConnection dbConnection = new DBConnection();
		while (run) {
			System.out.println("=======================================================================");
			System.out.println("| 1.정보입력 | 2.정보출력 | 3.분석 | 4.검색 | 5.수정 | 6.순위 | 7.삭제 | 8.종료 |");
			System.out.println("=======================================================================");
			System.out.print(">>");
			no = Integer.parseInt(sc.nextLine());
			switch (no) {
			case INPUT:
				Student student = inputStudent();
				int returnValue = dbConnection.insert(student);
				if (returnValue == 1) {
					System.out.println("삽입성공");
				} else {
					System.out.println("삽입실패");
				}
				break;
			case PRINT:
				ArrayList<Student> list2 = dbConnection.select();
				if (list2 == null) {
					System.out.println("선택실패");
				} else {
					printStudent(list2);
				}
				break;
			case ANALYZE:
				ArrayList<Student> list3 = dbConnection.analyzeSelect();
				if (list3 == null) {
					System.out.println("선택실패");
				} else {
					analyzeStudent(list3);
				}
				break;
			case SEARCH:
				String dataName = searchStudent();
				ArrayList<Student> list4 = dbConnection.nameSearchSelect(dataName);
				if (list4.size() >= 1) {
					printStudent(list4);
				} else {
					System.out.println("학생이름 검색 오류");
				}
				break;
			case UPDATE:
				int updateReturnValue = 0;
				int id = inputId();
				Student stu = dbConnection.selectId(id);
				if (stu == null) {
					System.out.println("수정 오류 발생");
				} else {
					Student updateStudent = updateStudent(stu);
					updateReturnValue = dbConnection.update(updateStudent);
				}
				if (updateReturnValue == 1) {
					System.out.println("update 성공");
				} else {
					System.out.println("update 실패");
				}
				break;
			case SORT:
				ArrayList<Student> list5 = dbConnection.selectSort();
				if (list5 == null) {
					System.out.println("정렬 실패");
				} else {
					printScoreSort(list5);
				}
				break;
			case DELETE:
				int deleteId = inputId();
				int deleteReturnValue = dbConnection.delete(deleteId);
				if (deleteReturnValue == 1) {
					System.out.println("삭제 성공");
				} else {
					System.out.println("삭제 실패");
				}
				break;
			case EXIT:
				run = false;
				break;
			}
		}
		System.out.println("종료");
	}

	private static int inputId() {
		boolean run = true;
		int id = 0;
		while (run) {
			try {
				System.out.print("id 입력(숫자): ");
				id = Integer.parseInt(sc.nextLine());
				if (id > 0 && id < Integer.MAX_VALUE) {
					run = false;
				}
			} catch (NumberFormatException e) {
				System.out.println("id 입력 오류");
			}

		}
		return id;
	}

	private static void printScoreSort(ArrayList<Student> list) {
		Collections.sort(list, Collections.reverseOrder());
		System.out.println("순위" + "\t" + "id" + "\t" + "이름" + "\t" + "나이" + "\t" + "국어" + "\t" + "영어" + "\t" + "수학"
				+ "\t" + "총점" + "\t" + "평균" + "\t" + "등급");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i + 1 + "등\t" + list.get(i).toString());
		}
	}

	private static Student updateStudent(Student student) {
		int kor = inputScoreSubject(student.getName(), "국어", student.getKor());
		student.setKor(kor);
		int eng = inputScoreSubject(student.getName(), "영어", student.getEng());
		student.setEng(eng);
		int math = inputScoreSubject(student.getName(), "수학", student.getMath());
		student.setMath(math);
		student.calTotal();
		student.calAvg();
		student.calGrade();
		System.out.println("id" + "\t" + "이름" + "\t" + "나이" + "\t" + "국어" + "\t" + "영어" + "\t" + "수학" + "\t" + "총점"
				+ "\t" + "평균" + "\t" + "등급");
		System.out.println(student);
		return student;
	}

	private static int inputScoreSubject(String name, String subject, int score) {
		boolean run = true;
		int data = 0;
		while (run) {
			System.out.print(name + " " + subject + ": " + score + " >> ");
			try {
				data = Integer.parseInt(sc.nextLine());
				Pattern pattern = Pattern.compile("^[0-9]{1,3}$");
				Matcher matcher = pattern.matcher(String.valueOf(score));
				if (matcher.find() && data < 101 && data >= 0) {
					run = false;
				} else {
					System.out.println("오류 재입력");
				}
			} catch (Exception e) {
				System.out.println("오류 재입력");
				data = 0;
			}
		}
		return data;
	}

	private static String searchStudent() {
		String name = null;
		name = nameMatching();
		return name;
	}

	private static String nameMatching() {
		String name = null;
		System.out.print("이름을 입력하세요. : ");
		while (true) {
			try {
				name = sc.nextLine();
				Pattern pattern = Pattern.compile("^[가-힣]{2,4}$");
				Matcher matcher = pattern.matcher(name);
				if (matcher.find()) {
					break;
				} else {
					System.out.println("이름을 잘못입력하셨습니다.");
				}
			} catch (Exception e) {
				System.out.println("입력에서 오류가 발생했습니다.");
				break;
			}
		}
		return name;
	}

	private static void analyzeStudent(ArrayList<Student> list) {
		System.out.println("id" + "\t" + "이름" + "\t" + "나이" + "\t" + "총점" + "\t" + "평균" + "\t" + "등급");
		for (Student data : list) {
			System.out.println(data.getId() + "\t" + data.getName() + "\t" + data.getAge() + "\t" + data.getTotal()
					+ "\t" + String.format("%.2f", data.getAvg()) + "\t" + data.getGrade());
		}
	}

	private static void printStudent(ArrayList<Student> list) {
		System.out.println("id" + "\t" + "이름" + "\t" + "나이" + "\t" + "국어" + "\t" + "영어" + "\t" + "수학" + "\t" + "총점"
				+ "\t" + "평균" + "\t" + "등급");
		for (Student data : list) {
			System.out.println(data);
		}
	}

	private static Student inputStudent() {
		String name = nameMatching();
		int age = inputAge();
		int kor = inputScore("국어");
		int eng = inputScore("영어");
		int math = inputScore("수학");
		Student student = new Student(name, age, kor, eng, math);
		student.calTotal();
		student.calAvg();
		student.calGrade();
		return student;
	}

	private static int inputScore(String subject) {
		int score = 0;
		while (true) {
			try {
				System.out.print(subject + "점수 입력 : ");
				score = Integer.parseInt(sc.nextLine());
				Pattern pattern = Pattern.compile("^[0-9]{1,3}$");
				Matcher matcher = pattern.matcher(String.valueOf(score));
				if (matcher.find() && score >= 0 && score < 101) {
					break;
				} else {
					System.out.println("오류 재입력");
				}
			} catch (NumberFormatException e) {
				System.out.println("입력 오류");
				break;
			}
		}
		return score;
	}

	private static int inputAge() {
		int age = 0;
		while (true) {
			try {
				System.out.print("나이를 입력하세요. : ");
				age = Integer.parseInt(sc.nextLine());
				Pattern pattern = Pattern.compile("^[0-9]{1,3}$");
				Matcher matcher = pattern.matcher(String.valueOf(age));
				if (matcher.find() && age > 0 && age < 125) {
					break;
				} else {
					System.out.println("오류 재입력");
				}
			} catch (NumberFormatException e) {
				System.out.println("나이 입력중 오류 발생");
				break;
			}
		}
		return age;
	}

}