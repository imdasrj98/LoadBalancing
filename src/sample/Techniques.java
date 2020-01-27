package sample;

import java.util.List;
import java.util.Map;

public class Techniques {

    String techName;
    Map<String, Integer> cloudLetMapping;
    List<List<String>> cloudLogs;
    List<String> cloudHeaders;

    public Techniques(String techName, Map<String, Integer> cloudLetMapping) {
        this.techName = techName;
        this.cloudLetMapping = cloudLetMapping;
    }

    public List<String> getCloudHeaders() {
        return cloudHeaders;
    }

    public void setCloudHeaders(List<String> cloudHeaders) {
        this.cloudHeaders = cloudHeaders;
    }

    public List<List<String>> getCloudLogs() {
        return cloudLogs;
    }

    public void setCloudLogs(List<List<String>> cloudLogs) {
        this.cloudLogs = cloudLogs;
    }

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }

    public Map<String, Integer> getCloudLetMapping() {
        return cloudLetMapping;
    }

    public void setCloudLetMapping(Map<String, Integer> cloudLetMapping) {
        this.cloudLetMapping = cloudLetMapping;
    }
}
