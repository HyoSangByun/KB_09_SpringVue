package org.scoula.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.member.dto.ChangePasswordDTO;
import org.scoula.member.dto.MemberDTO;
import org.scoula.member.dto.MemberJoinDTO;
import org.scoula.member.dto.MemberUpdateDTO;
import org.scoula.member.exception.PasswordMissmatchException;
import org.scoula.member.mapper.MemberMapper;
import org.scoula.security.account.domain.AuthVO;
import org.scoula.security.account.domain.MemberVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    // 생성자 DI
    final PasswordEncoder passwordEncoder; //SecurityConfig @Bean
    final MemberMapper mapper; // Mybatis

    @Override
    public boolean checkDuplicate(String username) {
        MemberVO member = mapper.findByUsername(username);
        return member != null;
    }

    @Override
    public MemberDTO get(String username) {
        MemberVO member = Optional.ofNullable(mapper.get(username))
                .orElseThrow(NoSuchElementException::new);

        return MemberDTO.of(member);
    }

    private void saveAvatar(MultipartFile avatar, String username) {
        //아바타 업로드
        if(avatar != null && !avatar.isEmpty()) {
            File dest = new File("c:/upload/avatar" , username + ".png");
            try {
                avatar.transferTo(dest);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Transactional
    @Override
    public MemberDTO join(MemberJoinDTO dto) {
        MemberVO member = dto.toVo();

        member.setPassword(passwordEncoder.encode(member.getPassword())); // 비밀번호 암호화
        mapper.insert(member);

        AuthVO auth = new AuthVO();
        auth.setUsername(member.getUsername());
        auth.setAuth("ROLE_MEMBER");
        mapper.insertAuth(auth);

        saveAvatar(dto.getAvatar(), member.getUsername());

        return get(member.getUsername());
    }

    @Override
    public MemberDTO update(MemberUpdateDTO member) {
        MemberVO vo = mapper.get(member.getUsername());
        if(!passwordEncoder.matches(member.getPassword(),vo.getPassword())) { // 비밀번호 일치 확인
            throw new PasswordMissmatchException();
        }
        mapper.update(member.toVO());
        saveAvatar(member.getAvatar(), member.getUsername());
        return get(member.getUsername());
    }

    // 비밀번호 변경 서비스
    @Override
    public void changePassword(ChangePasswordDTO changePassword) {
        // 1. 사용자 정보 조회
        MemberVO member = mapper.get(changePassword.getUsername());
        // 2. 기존 비밀번호 검증
        if(!passwordEncoder.matches(changePassword.getOldPassword(), member.getPassword())) {
            throw new PasswordMissmatchException();
        }
        // 3. 새 비밀번호 암호화
        changePassword.setNewPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        // 4. 데이터베이스 업데이트
        mapper.updatePassword(changePassword);
    }
}
