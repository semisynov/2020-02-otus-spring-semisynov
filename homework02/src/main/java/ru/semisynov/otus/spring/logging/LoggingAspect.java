package ru.semisynov.otus.spring.logging;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Log4j2
public class LoggingAspect {

    @Around("execution(* ru.semisynov.otus.spring.dao.QuestionDaoImpl.questionList())")
    public final Object logQuestionsCount(ProceedingJoinPoint joinPoint) throws Throwable {
        StringBuilder logInfo = new StringBuilder();
        logInfo.append("Класс:");
        logInfo.append(joinPoint.getTarget().getClass().getName());
        logInfo.append(". ");
        Object result = joinPoint.proceed();
        if (result instanceof List) {
            List resultList = (List) result;
            logInfo.append("Метод:" + joinPoint.getSignature().getName() + ". ");
            logInfo.append("Загружено вопросов:" + resultList.size() + ".");
        }
        log.debug(logInfo.toString());
        return result;
    }

    @Before("target(ru.semisynov.otus.spring.services.CsvFileReader)")
    public final void logObjectToString(JoinPoint joinPoint) {
        log.debug(joinPoint.getTarget().toString());
    }

    @After("target(ru.semisynov.otus.spring.services.Quiz)")
    public final void logQuizToString(JoinPoint joinPoint) {
        log.debug(joinPoint.getTarget().toString());
    }
}
