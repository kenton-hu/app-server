package cn.wildfirechat.app.admin.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.wildfirechat.app.admin.result.Result;
import cn.wildfirechat.app.admin.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: api拦截器
 * @author: Dragon
 * @date: 2024/8/22 22:26
 */
@Component
public class ApiInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(ApiInterceptor.class);

    @Autowired
    private UserUtils userUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOG.info("ApiInterceptor preHandle");
        if (!request.getContentType().contains("application/json")) {
            returnJson(response, JSONUtil.toJsonStr(new Result<>().error("content:error", "contentType应该为application/json;charset=utf-8", null)));
            return false;
        }
        String requestURI = request.getRequestURI();
        if (!requestURI.startsWith("/api/v1/admin/login") && "POST".equalsIgnoreCase(request.getMethod())) {
            String sessionId = request.getHeader("sessionId");
            if (StrUtil.isBlank(sessionId)) {
                returnJson(response, JSONUtil.toJsonStr(new Result<>().error("session:empty", "sessionId不能为空", null)));
                return false;
            } else {
                // 判断sessionId是否有效
                if (userUtils.getUserBySessionId(sessionId) == null) {
                    returnJson(response, JSONUtil.toJsonStr(new Result<>().error("session:invalid", "sessionId无效", sessionId)));
                    return false;
                }}

        }
        return true;
    }

    /*返回客户端数据*/
    private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
