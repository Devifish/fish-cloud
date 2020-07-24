package cn.devifish.cloud.common.security.error;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.provider.error.OAuth2ExceptionRenderer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * OAuth2SecurityExceptionRenderer
 * OAuth2安全异常内容渲染器
 *
 * @author Devifish
 * @date 2020/7/24 10:55
 */
@Component
@RequiredArgsConstructor
public class OAuth2SecurityExceptionRenderer implements OAuth2ExceptionRenderer {

    private final MappingJackson2HttpMessageConverter messageConverter;

    @Override
    public void handleHttpEntityResponse(HttpEntity<?> httpEntity, ServletWebRequest webRequest) throws Exception {
        if (httpEntity == null) return;

        var inputMessage = createHttpInputMessage(webRequest);
        var outputMessage = createHttpOutputMessage(webRequest);
        if (httpEntity instanceof ResponseEntity && webRequest instanceof ServerHttpResponse) {
            var serverHttpResponse = ((ServerHttpResponse) outputMessage);
            var responseEntity = (ResponseEntity<?>) httpEntity;
            serverHttpResponse.setStatusCode(responseEntity.getStatusCode());
        }

        var entityHeaders = httpEntity.getHeaders();
        if (!entityHeaders.isEmpty()) {
            outputMessage.getHeaders().putAll(entityHeaders);
        }

        var body = httpEntity.getBody();
        if (body != null) {
            var acceptedMediaTypes = inputMessage.getHeaders().getAccept();
            var mediaType = CollectionUtils.isEmpty(acceptedMediaTypes)
                    ? MediaType.ALL
                    : acceptedMediaTypes.get(0);

            messageConverter.write(body, mediaType, outputMessage);
        } else {
            // flush headers
            outputMessage.getBody();
        }
    }

    private HttpInputMessage createHttpInputMessage(NativeWebRequest webRequest) throws Exception {
        var servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        return new ServletServerHttpRequest(Objects.requireNonNull(servletRequest));
    }

    private HttpOutputMessage createHttpOutputMessage(NativeWebRequest webRequest) throws Exception {
        var servletResponse = (HttpServletResponse) webRequest.getNativeResponse();
        return new ServletServerHttpResponse(Objects.requireNonNull(servletResponse));
    }
}
