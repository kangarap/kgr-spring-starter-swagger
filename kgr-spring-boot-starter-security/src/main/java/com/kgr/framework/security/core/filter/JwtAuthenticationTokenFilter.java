package com.kgr.framework.security.core.filter;

import cn.hutool.core.util.StrUtil;
import com.kgr.framework.common.pojo.CommonResult;
import com.kgr.framework.common.util.servlet.ServletUtils;
import com.kgr.framework.security.config.SecurityProperties;
import com.kgr.framework.security.core.LoginUser;
import com.kgr.framework.security.core.service.SecurityAuthFrameworkService;
import com.kgr.framework.security.core.util.SecurityFrameworkUtils;
import com.kgr.framework.web.core.handler.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 过滤器，验证 token 的有效性
 * 验证通过后，获得 {@link LoginUser} 信息，并加入到 Spring Security 上下文
 *
 * @author ruoyi
 */
@AllArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;

    private final SecurityAuthFrameworkService authService;

    private final GlobalExceptionHandler globalExceptionHandler;

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = SecurityFrameworkUtils.obtainAuthorization(request, securityProperties.getTokenHeader());
        if (StrUtil.isNotEmpty(token)) {
            try {
                // 验证 token 有效性
                LoginUser loginUser = authService.verifyTokenAndRefresh(token);
                // 模拟 Login 功能，方便日常开发调试
                if (loginUser == null) {
                    loginUser = this.mockLoginUser(token);
                }
                // 设置当前用户
                if (loginUser != null) {
                    SecurityFrameworkUtils.setLoginUser(loginUser, request);
                }
            } catch (Throwable ex) {
                CommonResult<?> result = globalExceptionHandler.allExceptionHandler(request, ex);
                ServletUtils.writeJSON(response, result);
                return;
            }
        }

        // 继续过滤链
        chain.doFilter(request, response);
    }

    /**
     * 模拟登陆用户，方便日常开发调试
     *
     * 注意，在线上环境下，一定要关闭该功能！！！
     *
     * @param token 模拟的 token，格式为 {@link SecurityProperties#getTokenSecret()} + 用户编号
     * @return 模拟的 LoginUser
     */
    private LoginUser mockLoginUser(String token) {
        if (!securityProperties.getMockEnable()) {
            return null;
        }
        // 必须以 mockSecret 开头
        if (!token.startsWith(securityProperties.getMockSecret())) {
            return null;
        }
        Long userId = Long.valueOf(token.substring(securityProperties.getMockSecret().length()));
        return authService.mockLogin(userId);
    }

}
