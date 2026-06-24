package com.pojo;
public class GetCourses {

    private String url;
    private String services;
    private String expertise;
    private Courses courses;  // this is nested json which has child json in Courses class, so return type of this json is Course, courses is object of Courses class
    private String linkedIn;
    private String instructor;
    public String getInstructor() {
        return instructor;
    }
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getServices() {
        return services;
    }
    public void setServices(String services) {
        this.services = services;
    }
    public String getExpertise() {
        return expertise;
    }
    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }
    public Courses getCourses() {
        return courses;
    }
    public void setCourses(Courses courses) {
        this.courses = courses;
    }
    public String getLinkedIn() {
        return linkedIn;
    }
    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }
    
}