package com.samjung.MentorMenteeMatching.dto;

import com.samjung.MentorMenteeMatching.entity.UsersEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.usertype.UserType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsersDto {
    private Long userId; // 사용자 ID

    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String username; // 사용자 이름

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email; // 이메일 주소

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password; // 비밀번호

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String confirmPassword; // 비밀번호 확인

    @NotEmpty(message = "전화번호는 필수항목입니다.")
    private String phoneNumber; // 전화번호

    @NotEmpty(message = "학과은 필수항목입니다.")
    private String department; // 학과

    @NotEmpty(message = "학번은 필수항목입니다.")
    private String studentId; // 학번

    private String userType; // 사용자 유형 (MENTOR 또는 MENTEE)



    public UsersDto(UsersEntity usersEntity) {
        UsersDto usersDto = new UsersDto();
        usersDto.setUserId(usersEntity.getUserId());
        usersDto.setUsername(usersEntity.getUsername());
        usersDto.setEmail(usersEntity.getEmail());
        usersDto.setPassword(usersEntity.getPassword());
        usersDto.setConfirmPassword(usersEntity.getPassword()); // 비밀번호 확인용
        usersDto.setPhoneNumber(usersEntity.getPhoneNumber());
        usersDto.setDepartment(usersEntity.getDepartment());
        usersDto.setStudentId(usersEntity.getStudentId());
        usersDto.setUserType(String.valueOf(usersEntity.getUserType())); //Enum을 문자열로 변환
    }
}


