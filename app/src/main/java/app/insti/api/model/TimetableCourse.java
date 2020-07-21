package app.insti.api.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class TimetableCourse {
    @SerializedName("id")
    private int id;

    @SerializedName("course_dept")
    private String course_dept;

    @SerializedName("course_id")
    private String course_id;


    public TimetableCourse(String course_id, int id) {
        this.course_id = course_id;
        this.id = id;
    }

    public void setCourse_dept(String course_dept) {
        this.course_dept = course_dept;
    }

    public String getCourse_dept() {
        return course_dept;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public void setId(int id) { this.id = id; }

    public int getId() { return id; }

    @NonNull
    public String toString(){
        return course_dept+course_id;
    }

    public boolean isEmpty(){
        return (course_id.trim()).length() == 0;
    }
}
