public class Feedback {
    long id;
    String  courseName;
    String comment;
    int labNr;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLabNr() {
        return labNr;
    }

    public void setLabNr(int labNr) {
        this.labNr = labNr;
    }
}


