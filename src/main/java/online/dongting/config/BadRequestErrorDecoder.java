package online.dongting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import online.dongting.exceptions.HystrixBadResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * 返回4xx的状态码，解析成{@link HystrixBadRequestException}. 这些异常不应该走断路器。》
 */
public class BadRequestErrorDecoder extends ErrorDecoder.Default {

  @Override
  public Exception decode(String methodKey, Response response) {
    if (HttpStatus.BAD_REQUEST.value() <= response.status()
        && response.status() < HttpStatus.INTERNAL_SERVER_ERROR.value()) {
      String message = String.format("status %s reading %s", response.status(), methodKey);
      try {
        if (response.body() != null) {
          String body = Util.toString(response.body().asReader());
          ObjectMapper objectMapper = new ObjectMapper();
          message = objectMapper
              .writeValueAsString(new HystrixBadResponse(message, body, response.status()));
        }
      } catch (IOException var4) {
        var4.printStackTrace();
      }
      throw new HystrixBadRequestException(message);
    } else {
      return super.decode(methodKey, response);
    }

  }
}
