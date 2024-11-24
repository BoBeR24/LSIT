package lsit.Models;

import java.util.HashMap;
import java.util.Map;

public class EngineeringTeams {


    private Map<String, TaskStatus> mechanicalTasks;// to store tasks
    private Map<String, TaskStatus> electricalTasks;
    private Map<String, TaskStatus> softwareTasks;

    public enum TaskStatus {
        PENDING, IN_PROGRESS, COMPLETED
    }


    public EngineeringTeams() {
        this.mechanicalTasks = new HashMap<>();
        this.electricalTasks = new HashMap<>();
        this.softwareTasks = new HashMap<>();
    }


    public void assignMechanicalTask(String task) { // here we are assigning tasks to each engineer and the current status is pending
        this.mechanicalTasks.put(task, TaskStatus.PENDING);
    }

    public void assignElectricalTask(String task) {
        this.electricalTasks.put(task, TaskStatus.PENDING);
    }

    public void assignSoftwareTask(String task) {
        this.softwareTasks.put(task, TaskStatus.PENDING);
    }


    public void updateMechanicalTask(String task, TaskStatus status) { // then we are updating the status
                                                                        // for example making it pending to  in progress
        if (mechanicalTasks.containsKey(task)) {
            this.mechanicalTasks.put(task, status);
        }
    }

    public void updateElectricalTask(String task, TaskStatus status) {
        if (electricalTasks.containsKey(task)) {
            this.electricalTasks.put(task, status);
        }
    }

    public void updateSoftwareTask(String task, TaskStatus status) {
        if (softwareTasks.containsKey(task)) {
            this.softwareTasks.put(task, status);
        }
    }


    public Map<String, TaskStatus> getMechanicalTasks() {
        return mechanicalTasks;
    }

    public Map<String, TaskStatus> getElectricalTasks() {
        return electricalTasks;
    }

    public Map<String, TaskStatus> getSoftwareTasks() {
        return softwareTasks;
    }


    public String processRepairs() { // this is to generate a report on task progress  compiling the current statuses for all teams
        StringBuilder report = new StringBuilder();
        report.append("Engineering Teams Repair Status:\n");

        report.append("Mechanical engineer Team:\n");
        for (Map.Entry<String, TaskStatus> entry : mechanicalTasks.entrySet()) {
            report.append(" - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        report.append("Electrical engineer Team:\n");
        for (Map.Entry<String, TaskStatus> entry : electricalTasks.entrySet()) {
            report.append(" - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        report.append("Software Team:\n");
        for (Map.Entry<String, TaskStatus> entry : softwareTasks.entrySet()) {
            report.append(" - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return report.toString();
    }


    public boolean allTasksCompleted() { // returnin true if all the tasks are completed by all engineer tems
        return mechanicalTasks.values().stream().allMatch(status -> status == TaskStatus.COMPLETED) &&
                electricalTasks.values().stream().allMatch(status -> status == TaskStatus.COMPLETED) &&
                softwareTasks.values().stream().allMatch(status -> status == TaskStatus.COMPLETED);
    }
}
