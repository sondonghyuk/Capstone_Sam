package com.samjung.MentorMenteeMatching.service;

import com.samjung.MentorMenteeMatching.dto.UsersDto;
import com.samjung.MentorMenteeMatching.entity.UserType;
import com.samjung.MentorMenteeMatching.entity.UsersEntity;
import com.samjung.MentorMenteeMatching.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersEntity create(String username, String email, String password, String phoneNumber, String department, String studentId, String userType) {
        UsersEntity user = new UsersEntity();
        user.setUsername(username);
        user.setEmail(email);

        // 비밀번호 암호화 (기존에 주입받은 passwordEncoder 사용)
        user.setPassword(passwordEncoder.encode(password));

        user.setPhoneNumber(phoneNumber);
        user.setDepartment(department);
        user.setStudentId(studentId);

        // userType을 UserType enum으로 변환 (예외 처리 추가)
        try {
            user.setUserType(UserType.valueOf(userType.toUpperCase()));  // userType은 "MENTOR" 또는 "MENTEE"로 전달됨
        } catch (IllegalArgumentException e) {
            log.error("Invalid userType: {}", userType);
            // 적절한 예외 처리나 오류 메시지 반환
            throw new IllegalArgumentException("Invalid userType provided: " + userType);
        }
        // 사용자 저장
        this.usersRepository.save(user);
        return user;
    }

    //사용자명으로 조회
    public UsersEntity getUser(String username) {
        Optional<UsersEntity> user = this.usersRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new IllegalArgumentException("user not found");
        }
    }

    public UsersDto findById(Long id) {
        UsersEntity user = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("사용자 정보 없음"));
        return new UsersDto(user); // UsersDto로 변환하여 반환
    }

    public void updateUser(Long id, UsersDto usersDto) {
        UsersEntity user = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("사용자 정보 없음"));

        // 사용자가 제공한 정보로 갱신
        user.setUsername(usersDto.getUsername());
        user.setEmail(usersDto.getEmail());
        if (usersDto.getPassword() != null && !usersDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(usersDto.getPassword()));  // 비밀번호 변경 시 암호화
        }
        user.setPhoneNumber(usersDto.getPhoneNumber());
        user.setDepartment(usersDto.getDepartment());
        user.setStudentId(usersDto.getStudentId());

        usersRepository.save(user);  // 변경 사항 저장
    }
}
//    public void save(UsersDto usersDto) {
//        // 1. dto -> entity 변환
//        // 2. repository의 save 메서드 호출(조건. entity객체를 넘겨줘야 함)
//        UsersEntity usersEntity = UsersEntity.toUsersEntity(usersDto);
//        log.info("Saving entity: {}", usersEntity);
//        usersRepository.save(usersEntity);
//        log.info("Entity saved successfully");
//    }
//
//    public UsersDto login(UsersDto usersDto) {
//        //1.회원이 입력한 이메일로 DB에서 조회
//        //2.DB에서 조횐한 비밀번호화 사용자가 입력한 비밀번호가 일치하는지 판단
//        Optional<UsersEntity> byUsersEmail = usersRepository.findByEmail(usersDto.getEmail());
//        if(byUsersEmail.isPresent()){
//            //조회 결과가 있음
//            UsersEntity usersEntity = byUsersEmail.get();
//            if(usersEntity.getPassword().equals(usersDto.getPassword())){
//                //비밀번호 일치
//                //entity->dto
//                UsersDto usersdto = UsersDto.toUsersDto(usersEntity);
//                return usersdto;
//            }else{
//                //비밀번호 불일치
//                return null;
//            }
//        }else{
//            //조회 결과가 없음
//            return null;
//        }
//    }
//
//    public List<UsersDto> findAll() {
//        List<UsersEntity> usersEntityList = usersRepository.findAll();
//        List<UsersDto> usersDtoList = new ArrayList<>();
//        for(UsersEntity usersEntity : usersEntityList){
//            usersDtoList.add(UsersDto.toUsersDto(usersEntity));
//        }
//        return usersDtoList;
//    }
//
//
//
//    public UsersDto updateForm(String myEmail) {
//        Optional<UsersEntity> userByEmail = usersRepository.findByEmail(myEmail);
//        if(userByEmail.isPresent()){
//            return UsersDto.toUsersDto(userByEmail.get());
//        }else{
//            return null;
//        }
//    }
//
//    public void update(UsersDto usersDto) {
//        usersRepository.save(UsersEntity.toUpdateUsersEntity(usersDto));
//    }
//
//    public void deleteById(Long id) {
//        usersRepository.deleteById(id);
//    }

