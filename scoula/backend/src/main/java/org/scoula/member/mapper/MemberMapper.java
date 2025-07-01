package org.scoula.member.mapper;

import org.scoula.member.dto.ChangePasswordDTO;
import org.scoula.security.account.domain.AuthVO;
import org.scoula.security.account.domain.MemberVO;

/*
* MemberMapper
* 데이터베이스 접근 계층 DAO
* DB에 직접 SQL을 날리고, 그 결과를 객체로 매핑해주는 인터페이스 역할
* */
public interface MemberMapper {
    MemberVO get(String username);

    MemberVO findByUsername(String username); // id 중복 체크시 사용

    int insert(MemberVO member); // 회원 정보 추가

    int insertAuth(AuthVO auth); // 회원 권한 정보 추가

    int update(MemberVO member); // 회원 정보 업데이트

    int updatePassword(ChangePasswordDTO changePasswordDTO); // 비밀번호 변경
}
