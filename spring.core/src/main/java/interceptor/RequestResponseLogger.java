package interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestResponseLogger  implements HandlerInterceptor {

    static Logger log = LoggerFactory.getLogger(RequestResponseLogger.class);

    public static final String TRANSACTION_ID = "transactionId";

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        String transactionId = MDC.get(TRANSACTION_ID);
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString() != null ? "?" + request.getQueryString() : "";

        log.info("Transaction ID: {}, HTTP Method: {}, Endpoint: {}{}", transactionId, method, uri, queryString);
        return true;
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NotNull Object handler,
                                Exception ex) {
        String transactionId = MDC.get(TRANSACTION_ID);
        int status = response.getStatus();
        String message = (ex != null) ? ex.getMessage() : "Response OK";
        log.info("Transaction ID: {}, Response Status: {}, Message: {}", transactionId, status, message);
    }
}
