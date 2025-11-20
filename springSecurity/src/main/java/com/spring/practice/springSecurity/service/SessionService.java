package com.spring.practice.springSecurity.service;


import com.spring.practice.springSecurity.entity.SessionEntity;
import com.spring.practice.springSecurity.entity.UserEntity;
import com.spring.practice.springSecurity.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    private int sessionLimit = 2;

    public void generateNewSession(UserEntity user, String refreshToken){
        List<SessionEntity> userSessions = sessionRepository.findByUser(user);
        String userPlan;
        if(Objects.nonNull(user.getPlan())){
            userPlan = user.getPlan();
        }
        else{
            userPlan = "basic";
        }


        switch (userPlan){
            case "basic":
                sessionLimit = 3;
                break;
            case "silver":
                sessionLimit = 4;
                break;
            case "gold":
                sessionLimit = 5;
                break;
            default:
                sessionLimit = 2;
        }

        if(userSessions.size() == sessionLimit){
            userSessions.sort(Comparator.comparing(SessionEntity :: getLastUsedAt));
            SessionEntity lastRecentlyUsedSession = userSessions.get(0);
            sessionRepository.delete(lastRecentlyUsedSession);
        }
        SessionEntity sessionEntity = SessionEntity.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
        sessionRepository.save(sessionEntity);
    }

    public void validateSession(String refreshToken){
      SessionEntity sessionEntity =  sessionRepository.findByRefreshToken(refreshToken).orElseThrow(
              () -> new SessionAuthenticationException("Session not found")
      );
      sessionEntity.setLastUsedAt(LocalDateTime.now());
      sessionRepository.save(sessionEntity);
    }

    public String deleteSessionByRefreshToken(String refreshToken) {

        SessionEntity sessionEntity = sessionRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new SessionAuthenticationException("Session not found")
        );
        if(sessionEntity!=null) {
            sessionRepository.deleteById(sessionEntity.getId());
            return "Session deleted "+ sessionEntity;
        }
        return "no session found";

    }
}
