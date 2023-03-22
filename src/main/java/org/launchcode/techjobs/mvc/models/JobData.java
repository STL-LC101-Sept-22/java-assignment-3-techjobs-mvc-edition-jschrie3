package org.launchcode.techjobs.mvc.models;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.launchcode.techjobs.mvc.NameSorter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LaunchCode
 */
public class JobData {

    // FIELDS
    // read in data file
    private static final String DATA_FILE = "job_data.csv";
    private static boolean isDataLoaded = false;

    // declare fields for jobs
    private static ArrayList<Job> allJobs;
    private static ArrayList<Employer> allEmployers = new ArrayList<>();
    private static ArrayList<Location> allLocations = new ArrayList<>();
    private static ArrayList<PositionType> allPositionTypes = new ArrayList<>();
    private static ArrayList<CoreCompetency> allCoreCompetency = new ArrayList<>();

    // METHODS TO SEARCH FOR RESULTS
    /**
     * Fetch list of all job objects from loaded data,
     * without duplicates, then return a copy.
     */

    // METHODS - FIND ALL JOBS
    // load data into arraylist as Job objects names allJobs
    public static ArrayList<Job> findAll() {

        // load data, if not already loaded
        loadData();

        // Bonus mission; normal version returns allJobs
        return new ArrayList<>(allJobs);
    }

    // METHODS - SEARCH BY FIELD AND SEARCH TERM
    /**
     * Returns the results of searching the Jobs data by field and search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column Job field that should be searched.
     * @param value Value of the field to search for.
     * @return List of all jobs matching the criteria.
     */
    public static ArrayList<Job> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        // declare jobs arraylist to hold search results
        ArrayList<Job> jobs = new ArrayList<>();

        // where value is the field to search for
        // if value = all, call findAll
        if (value.toLowerCase().equals("all")){
            return findAll();
        }

        // where column is the Job field to be searched
        // where value is the field to search for
        // if column = all, pass value into findByValue method to search jobs arrayList for value
        if (column.equals("all")){
            jobs = findByValue(value);
            return jobs;
        }

        // for each job in allJobs arraylist
        for (Job job : allJobs) {

            // initialize string aValue that calls getFieldValue method
            // job (a Job object) and column (a String for fieldName) are passed in
            String aValue = getFieldValue(job, column);

            // adds all the jobs meeting search criteria to jobs arraylist
            if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                jobs.add(job);
            }
        }

        // returns the jobs in jobs arraylist that meet the search criteria
        return jobs;
    }

    // METHODS TO RETURN RESULTS
    // METHODS - RETURNS SEARCH RESULTS FOR SEARCH BY FIELD AND SEARCH TERM (ABOVE)
    public static String getFieldValue(Job job, String fieldName){

        // initialize a string to hold the job data that meets search criteria
        String theValue;

        // uses fieldName to determine which fields from job object to return
        if (fieldName.equals("name")){
            theValue = job.getName();
        } else if (fieldName.equals("employer")){
            theValue = job.getEmployer().toString();
        } else if (fieldName.equals("location")){
            theValue = job.getLocation().toString();
        } else if (fieldName.equals("positionType")){
            theValue = job.getPositionType().toString();
        } else {
            theValue = job.getCoreCompetency().toString();
        }

        // return job fields matching search criteria
        return theValue;
    }

    // METHODS - RETURNS SEARCH RESULTS FOR SEARCH BY FIELD (ABOVE)
    /**
     * Search all Job fields for the given term.
     *
     * @param value The search term to look for.
     * @return      List of all jobs with at least one field containing the value.
     */
    public static ArrayList<Job> findByValue(String value) {

        // load data, if not already loaded
        loadData();

        // declare jobs arraylist to hold search results
        ArrayList<Job> jobs = new ArrayList<>();

        // where value is the search term
        // for each job in allJobs arraylist
        for (Job job : allJobs) {

            // if the search term (value) matches a field in allJobs, add that job to the results (jobs arraylist)
            if (job.getName().toLowerCase().contains(value.toLowerCase())) {
                jobs.add(job);
            } else if (job.getEmployer().toString().toLowerCase().contains(value.toLowerCase())) {
                jobs.add(job);
            } else if (job.getLocation().toString().toLowerCase().contains(value.toLowerCase())) {
                jobs.add(job);
            } else if (job.getPositionType().toString().toLowerCase().contains(value.toLowerCase())) {
                jobs.add(job);
            } else if (job.getCoreCompetency().toString().toLowerCase().contains(value.toLowerCase())) {
                jobs.add(job);
            }

        }

        // returns list of all jobs with at least one field containing the value.
        return jobs;
    }

    // METHODS - USED IN THE LOAD DATA METHOD (BELOW)
    private static Object findExistingObject(ArrayList list, String value){
        for (Object item : list){
            if (item.toString().toLowerCase().equals(value.toLowerCase())){
                return item;
            }
        }
        return null;
    }


    // METHODS - LOAD DATA
    /**
     * Read in data from a CSV file and store it in an ArrayList of Job objects.
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Resource resource = new ClassPathResource(DATA_FILE);
            InputStream is = resource.getInputStream();
            Reader reader = new InputStreamReader(is);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {

                String aName = record.get(0);
                String anEmployer = record.get(1);
                String aLocation = record.get(2);
                String aPosition = record.get(3);
                String aSkill = record.get(4);

                Employer newEmployer = (Employer) findExistingObject(allEmployers, anEmployer);
                Location newLocation = (Location) findExistingObject(allLocations, aLocation);
                PositionType newPosition = (PositionType) findExistingObject(allPositionTypes, aPosition);
                CoreCompetency newSkill = (CoreCompetency) findExistingObject(allCoreCompetency, aSkill);

                if (newEmployer == null){
                    newEmployer = new Employer(anEmployer);
                    allEmployers.add(newEmployer);
                }

                if (newLocation == null){
                    newLocation = new Location(aLocation);
                    allLocations.add(newLocation);
                }

                if (newSkill == null){
                    newSkill = new CoreCompetency(aSkill);
                    allCoreCompetency.add(newSkill);
                }

                if (newPosition == null){
                    newPosition = new PositionType(aPosition);
                    allPositionTypes.add(newPosition);
                }

                Job newJob = new Job(aName, newEmployer, newLocation, newPosition, newSkill);

                allJobs.add(newJob);
            }
            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

    // GETTERS AND SETTERS
    public static ArrayList<Employer> getAllEmployers() {
        loadData();
        allEmployers.sort(new NameSorter());
        return allEmployers;
    }

    public static ArrayList<Location> getAllLocations() {
        loadData();
        allLocations.sort(new NameSorter());
        return allLocations;
    }

    public static ArrayList<PositionType> getAllPositionTypes() {
        loadData();
        allPositionTypes.sort(new NameSorter());
        return allPositionTypes;
    }

    public static ArrayList<CoreCompetency> getAllCoreCompetency() {
        loadData();
        allCoreCompetency.sort(new NameSorter());
        return allCoreCompetency;
    }

}

