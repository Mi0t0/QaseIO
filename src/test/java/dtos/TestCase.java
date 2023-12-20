package dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TestCase {
    String title;
    String status;
    String description;
    String suite;
    String severity;
    String priority;
    String type;
    String layer;
    boolean isFlaky;
    String behavior;
    String automationStatus;
    String preconditions;
    String postconditions;
    String tags;
    String attachments;
    String[][] steps;
}




