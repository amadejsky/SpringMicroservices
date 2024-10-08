package org.amadejsky.courses.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.codec.language.bm.Rule;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
public class NotificationDto {
    private List<String> emails;
    private String courseCode;
    private String courseName;
    private String courseDescription;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime courseStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime courseEndDate;
}
