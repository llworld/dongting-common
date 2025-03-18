package online.dongting.utils;

import online.dongting.constant.Const;
import online.dongting.enums.SystemErrorType;
import online.dongting.exceptions.BusinessException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: ll
 * @since: 2023/10/8 HOUR:17 MINUTE:41
 * @description:
 */
public class SecurityUtil {

    private static HttpServletRequest httpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static Long getCurrentUserId() {
        HttpServletRequest request = httpServletRequest();
        String userId = request.getHeader(Const.CURRENT_USER_ID_HEADER);
        userId = "1873991761097957377";
        BusinessException.isNull(userId, SystemErrorType.USER_ERROR);
        return Long.valueOf(userId);
    }


}
