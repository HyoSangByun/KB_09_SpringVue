package org.scoula.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.scoula.security.account.domain.MemberVO;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberJoinDTO {
    private String username;
    private String password;
    private String email;

    // MultipartFile : Spring에서 HTTP 요청으로 전달된 파일을 쉽게 다루도록 해주는 객체
    private MultipartFile avatar;

    public MemberVO toVo() {
        return MemberVO.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
