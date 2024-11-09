package nl.hu.inno.hulp.domain.strategy;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CombinedStrategy {
    @Bean
    public List<RegistrationValidator> getValidators() {
        return Arrays.asList(
                new CompletedCourseValidate(),
                new EnteredCourseValidate(),
                new MajorValidate(),
                new MaxEnteredCourseValidate()
        );
    }
}
