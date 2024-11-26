package com.samjung.MentorMenteeMatching.dto;

import com.samjung.MentorMenteeMatching.entity.AnswerEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnswerDto {
    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
}
