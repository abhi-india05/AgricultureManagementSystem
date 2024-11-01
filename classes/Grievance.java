package classes;

public class Grievance {
    private String username;
    private String issue;
    private String response;

    public Grievance(String username, String issue) {
        this.username = username;
        this.issue = issue;
        this.response = null; 
    }

    public String getUsername() {
        return username;
    }

    public String getIssue() {
        return issue;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}