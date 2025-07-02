package org.scoula.security.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.servlet.ServletContext;

/**
 * Spring Security 필터 체인을 등록하는 초기화 클래스
 * 웹 애플리케이션 시작 시 자동으로 Security 필터들을 등록
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
    // 별도 구현 불필요 - 상위 클래스에서 자동 처리

    /*Multipart Resolver를 Security Filter보다 앞에 설정해야 함.

         WebConfig.java에 설정한 문자 인코딩 필터 제거
        ⁃ Filter[] getServletFilters() 메서드 제거*/

    // 문자셋 필터
    private CharacterEncodingFilter encodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return encodingFilter;
    }
    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        insertFilters(servletContext, encodingFilter(), new MultipartFilter());
    }
}
