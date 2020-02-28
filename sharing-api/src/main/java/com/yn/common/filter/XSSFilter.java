package com.yn.common.filter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class XSSFilter implements Filter {
    private static Logger log = LoggerFactory.getLogger(XSSFilter.class);

    public void destroy() { }

    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestStr = getRequestString(request);
//        log.info("requestStr： ======================== " + requestStr);
//        log.info("完整的地址是====" + request.getRequestURL().toString());
//        log.info("提交的方式是========" + request.getMethod());

        if ("Y".equals(guolv2(requestStr)) || "Y".equals(guolv2(request.getRequestURL().toString()))) {
            log.info("======访问地址发现非法字符，已拦截======其非法地址为："+guolv2(request.getRequestURL().toString()));
            response.setStatus(403);
            return;
        }
        // 主机ip和端口 或 域名和端口
//        String myhosts = request.getHeader("host");
//        if (StringUtils.equals(myhosts, "xx.xx.xxx.xxx:xxxx")
//                || StringUtils.equals(myhosts, "xx.xx.xxx.xxx:xxxx")
//                || StringUtils.equals(myhosts, "xx.xx.xxx.xxx:xxxx")
//                || StringUtils.equals(myhosts, "xx.xx.xxx.xxx")
//                || StringUtils.equals(myhosts, "xx.xx.xxx.xxx")
//                || StringUtils.equals(myhosts, "xx.xx.xxx.xxx")) {
            // 过滤请求特殊字符，扫描跨站式漏洞
            Map parameters = request.getParameterMap();
            if (parameters != null && parameters.size() > 0) {
                for (Object o : parameters.keySet()) {
                    String key = (String) o;
                    String[] values = (String[]) parameters.get(key);
                    for (int i = 0; i < values.length; i++) {
                        values[i] = guolv(values[i]);
                    }
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
//        } else {
//            log.info("======访问host非法，已拦截======其非法host为:" + myhosts);
//            response.setStatus(403);  //或者response.setStatus(403);
//        }

    }

    public void init(FilterConfig filterConfig) { }

    /**
     * @Author yn
     * @Desc 过滤参数中特殊字符
     * @Date 14:53 2019/10/28 0028
     */
    public static String guolv(String a) {
        a = a.replaceAll("%22", "");
        a = a.replaceAll("%27", "");
        a = a.replaceAll("%3E", "");
        a = a.replaceAll("%3e", "");
        a = a.replaceAll("%3C", "");
        a = a.replaceAll("%3c", "");
        a = a.replaceAll("<", "");
        a = a.replaceAll(">", "");
        a = a.replaceAll("\"", "");
        a = a.replaceAll("'", "");
        a = a.replaceAll("\\+", "");
        a = a.replaceAll("\\(", "");
        a = a.replaceAll("\\)", "");
        a = a.replaceAll(" and ", "");
        a = a.replaceAll(" or ", "");
        a = a.replaceAll(" 1=1 ", "");
        return a;
    }

    private String getRequestString(HttpServletRequest req) {
        String requestPath = req.getServletPath();
        String queryString = req.getQueryString();
        if (queryString != null)
            return requestPath + "?" + queryString;
        else
            return requestPath;
    }
    /**
     * @Author yn
     * @Desc 判断请求中特殊字符
     * @Date 14:52 2019/10/28 0028
     */
    public String guolv2(String a) {
        if (StringUtils.isNotEmpty(a)) {
            if (a.contains("%22") || a.contains("%3E") || a.contains("%3e")
                    || a.contains("%3C") || a.contains("%3c")
                    || a.contains("<") || a.contains(">") || a.contains("\"")
                    || a.contains("'") || a.contains("+") || /* a.contains("%27") ||*/
                    a.contains(" and ") || a.contains(" or ")
                    || a.contains("1=1") || a.contains("(") || a.contains(")")) {
                return "Y";
            }
        }
        return a;
    }

}
