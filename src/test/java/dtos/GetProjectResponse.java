package dtos;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class GetProjectResponse {
    @SerializedName("status")
    boolean isPresent;
    Project result;
    String errorMessage;
}
