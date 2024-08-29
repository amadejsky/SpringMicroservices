package org.amadejsky.courses.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AppFeignError implements ErrorDecoder{
    private final ErrorDecoder defaultErrorDecoder = new Default();


    @Override
    public Exception decode(String s, Response response) {
        if(HttpStatus.valueOf(response.status()).is4xxClientError()){
            throw new CourseException(CourseError.STUDENT_ACCOUNT_IS_INACTIVE);
        }
        return defaultErrorDecoder.decode(s,response);
    }
}
