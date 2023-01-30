import java.util.*;

public class ClassRoom {
    static public void main(String[] args) {
        Teacher teacher = new Teacher("Ashok", 30);
        Subject subject = new Subject("Rahul", teacher, "8th class");
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        subjects.add(subject);
        ClassAsset newClassRoom = new ClassAsset(100, 50, 50, "8th class", subjects);

        System.out.println("Basic class is setup");
    }
}

class ClassAsset {
    private String className;
    private int totalSeats;
    private int totalGirls;
    private int totalBoys;
    private ArrayList<Subject> subjects;

    public ClassAsset(int totalSeats, int totalGirls, int totalBoys, String className, ArrayList<Subject> subjects) {
        this.className = className;
        this.totalBoys = totalBoys;
        this.totalGirls = totalGirls;
        this.subjects = subjects;
        this.totalSeats = totalSeats;
    }

    public int getTotalSeats() {
        return this.totalSeats;
    }

    public int getTotalBoys() {
        return this.totalBoys;
    }

    public int getTotalGirls() {
        return this.totalGirls;
    }

    public String getClassName() {
        return this.className;
    }

    public ArrayList<Subject> getSubjects() {
        return this.subjects;
    }

    public int setTotalSeats(int val) {
        this.totalSeats = val;
        return this.totalSeats;
    }

    public int setTotalBoys(int val) {
        this.totalBoys = val;
        return this.totalBoys;
    }

    public int setTotalGirls(int val) {
        this.totalGirls = val;
        return this.totalGirls;
    }

    public String setClassName(String className) {
        this.className = className;
        return this.className;
    }

    public ArrayList<Subject> setSubject(Subject subject) {
        this.subjects.add(subject);
        return this.subjects;
    }

}

class Teacher {
    private String name;
    private int age;

    Teacher(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class Subject {

    private String name;
    private Teacher teacher;
    private String gradeName;

    public Subject(String name, Teacher teacher, String gradeName) {
        this.name = name;
        this.teacher = teacher;
        this.gradeName = gradeName;
    }
}
