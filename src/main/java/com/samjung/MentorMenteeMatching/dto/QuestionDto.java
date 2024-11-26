package com.samjung.MentorMenteeMatching.dto;

import com.samjung.MentorMenteeMatching.entity.QuestionEntity;
//import com.samjung.MentorMenteeMatching.entity.UsersEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionDto {
    @NotEmpty(message = "제목은 필수항목입니다.")
    @Size(max=200)
    private String title;

    @NotEmpty(message = "제목은 필수항목입니다.")
    private String content;

}