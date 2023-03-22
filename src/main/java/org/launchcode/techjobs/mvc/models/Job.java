package org.launchcode.techjobs.mvc.models;

import java.util.Objects;

public class Job {

    private int id;
    private static int nextId = 1;

    private String name;
    private Employer employer;
    private Location location;
    private PositionType positionType;
    private CoreCompetency coreCompetency;

    // Initialize a unique ID.
    public Job() {
        id = nextId;
        nextId++;
    }

    // Initialize the id and value fields.
    public Job(String aName, Employer anEmployer, Location aLocation, PositionType aPositionType, CoreCompetency aCoreCompetency) {
        this();
        name = aName;
        employer = anEmployer;
        location = aLocation;
        positionType = aPositionType;
        coreCompetency = aCoreCompetency;
    }

    // Custom toString method.
    // the toString method formats the job postings and handles empty fields
    @Override
    public String toString(){
        String output = "";
        if (name.equals("")){
            name = "Data not available";
        }
        if (employer.getValue().equals("") || employer.getValue() == null){
            employer.setValue("Data not available");
        }
        if (location.getValue().equals("") || location.getValue() == null){
            location.setValue("Data not available");
        }
        if (coreCompetency.getValue().equals("") || coreCompetency.getValue() == null){
            coreCompetency.setValue("Data not available");
        }
        if (positionType.getValue().equals("") || positionType.getValue() == null){
            positionType.setValue("Data not available");
        }

        output = String.format("\nID: %d\n" +
                "Name: %s\n" +
                "Employer: %s\n" +
                "Location: %s\n" +
                "Position Type: %s\n" +
                "Core Competency: %s\n", id, name, employer, location, positionType, coreCompetency);
        return output;
    }

    // Custom equals and hashCode methods. Two Job objects are "equal" when their id fields match.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // if job o already exists return true
        if (!(o instanceof Job)) return false; // if job o is not an instance of job class return false
        Job job = (Job) o; // cast o as a job
        return id == job.id; // return the job id for job o
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    // Getters and setters.

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    public CoreCompetency getCoreCompetency() {
        return coreCompetency;
    }

    public void setCoreCompetency(CoreCompetency coreCompetency) {
        this.coreCompetency = coreCompetency;
    }
}
