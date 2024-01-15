package dtos;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Project {

    @SerializedName("title")
    String projectName;

    @SerializedName("code")
    String projectCode;

    String description;

    @SerializedName("access_type")
    String projectAccessType;

    @SerializedName("access")
    String memberAccess;

    String group;

    Counts counts;

    static class Counts {
        int cases;
        int suites;
        int milestones;
        Run runs;
        Defect defects;
    }

    static class Run {
        int total;
        int active;
    }

    static class Defect {
        int total;
        int open;
    }
}
