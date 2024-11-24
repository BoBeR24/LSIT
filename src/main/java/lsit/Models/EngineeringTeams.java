package lsit.Models;

import java.util.Map;  //I simplified the code  to only include variables

public class EngineeringTeams {
    public Map<String, String> mechanicalTasks;
    public Map<String, String> electricalTasks;
    public Map<String, String> softwareTasks;


    public EngineeringTeams() {}


    public EngineeringTeams(Map<String, String> mechanicalTasks,
                            Map<String, String> electricalTasks,
                            Map<String, String> softwareTasks) {
        this.mechanicalTasks = mechanicalTasks;
        this.electricalTasks = electricalTasks;
        this.softwareTasks = softwareTasks;
    }
}
