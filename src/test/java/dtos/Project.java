package dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @JsonProperty("title")
    String projectName;

    @JsonProperty("code")
    String projectCode;

    String description;

    @JsonProperty("access_type")
    String projectAccessType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("access")
    String memberAccess;

    String group;

    @JsonIgnore
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