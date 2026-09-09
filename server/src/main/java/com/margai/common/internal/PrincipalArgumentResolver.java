package com.margai.common.internal;

import com.margai.common.api.AuthException;
import com.margai.common.api.Principal;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * A {@link Principal} parameter on a controller method is the caller the security layer published
 * under {@link Principal#REQUEST_ATTRIBUTE} (TECH_PLAN §1.5 steps 3 and 6, §9.3) — so controllers
 * in every module read the caller without a dependency on Spring Security or on {@code auth}. On an
 * authenticated route the attribute is always there; should it ever be missing, the answer is the
 * {@code AUTH_REQUIRED} envelope, never a 500 (D10).
 */
final class PrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Principal.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest request, WebDataBinderFactory binderFactory) {
        Object published = request.getAttribute(Principal.REQUEST_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        if (published instanceof Principal principal) {
            return principal;
        }
        throw AuthException.required();
    }
}
