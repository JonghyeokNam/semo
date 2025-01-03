//package com.semoi.semo.chat.config;
//
//import com.semoi.semo.jwt.service.TokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.stereotype.Component;
//
//import java.security.Principal;
//
//@Component
//@RequiredArgsConstructor
//public class StompHandler implements ChannelInterceptor {
//
//    private final TokenProvider tokenProvider;
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//
//        // CONNECT 커맨드가 들어오면 토큰 유효성 체크
//        if (accessor.getCommand() == StompCommand.CONNECT) {
//            validateToken(accessor);
//        }
//
//        return message;
//    }
//
//    private void validateToken(StompHeaderAccessor accessor) {
//        // 클라이언트에서 보낸 토큰은 일반적으로 NativeHeader 중 "Authorization"에 담긴다고 가정
//        String authHeader = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            // 실제 상황에 맞게 예외 처리
//            // 예: throw new AccountException(StatusCode.FILTER_ACCESS_DENIED);
//            throw new RuntimeException("No valid Authorization header found");
//        }
//
//        // "Bearer " 문자열 제거
//        String token = authHeader.substring("Bearer ".length());
//
//        // TokenProvider에서 유효성 검사
//        if (!tokenProvider.validToken(token)) {
//            // 예: throw new AccountException(StatusCode.FILTER_ACCESS_DENIED);
//            throw new RuntimeException("Invalid or expired JWT token");
//        }
//
//         (선택) 인증 성공 시, Authentication 객체를 생성해 Stomp 세션에 저장할 수도 있음
//         예: Principal(또는 Authentication) 등록
//         accessor.setUser(tokenProvider.getAuthentication(token));
//         이후 @MessageMapping 메서드에서 Principal principal로 사용 가능
//    }
//}
