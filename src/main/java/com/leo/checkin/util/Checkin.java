package com.leo.checkin.util;

import com.leo.checkin.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 自动打卡工具
 * @Author: leo
 * @Date: 2021/9/12 0:07
 */
@Slf4j
public class Checkin {
    /**
     * 登录并获取session
     * @param username
     * @param password
     * @return Map<String, String> session
     */
    public static Map<String, String> login(String username, String password) throws IOException {
        String loginUrl = "http://login.cuit.edu.cn/Login/xLogin/Login.asp";
        String tyLoginUrl = "http://jszx-jxpt.cuit.edu.cn/Jxgl/Login/tyLogin.asp";
        Map<String, String> cookies = new HashMap<>();

        Map<String,String> headers = new HashMap<>();
        headers.put("Referer", loginUrl);

        Map<String,String> data = new HashMap<>();
        data.put("txtId", username);
        data.put("txtMM", password);
        data.put("codeKey", "480980");
        data.put("Login", "Check");

        Response response1 = Jsoup.connect(loginUrl).headers(headers).data(data).method(Method.POST).execute();
        cookies.putAll(response1.cookies());

        Response response2 = Jsoup.connect(tyLoginUrl).cookies(cookies).method(Method.GET).execute();
        cookies.putAll(response2.cookies());

        final Pattern pattern = Pattern.compile("URL=(.*?)\">");
        Matcher matcher = pattern.matcher(response2.body());
        matcher.find();
        String directUrl = matcher.group(1);
        Response response3 = Jsoup.connect(directUrl).cookies(cookies).method(Method.GET).execute();
        cookies.putAll(response3.cookies());

        // 验证是否登录成功
        if (!cookieVarify(cookies, username)) {
            throw new AppException("用户名或密码错误");
        }

        log.info("cookies:{}",cookies.toString());
        return cookies;
    }

    /**
     * 表单提交
     * @return
     */
    public static boolean submit(Map<String, String> session, Map<String, String> params) throws IOException {
        String getIdUrl = "http://jszx-jxpt.cuit.edu.cn/Jxgl/Xs/netks/sj.asp?UTp=Xs&jkdk=Y";
        Response res = Jsoup.connect(getIdUrl).cookies(session).method(Method.GET).execute();
        final Pattern pattern = Pattern.compile("&Id=(.*?) target=_self>");
        Matcher matcher = pattern.matcher(res.body());
        matcher.find();
        String ObjId = matcher.group(1);

        if (params.isEmpty()) {
            throw new AppException("缺少参数");
        }

        String submitUrl = "http://jszx-jxpt.cuit.edu.cn/Jxgl/Xs/netks/editSjRs.asp";
        Map<String, String> data = new HashMap<>();
        data.put("RsNum", "4");
        data.put("Id", params.get("sutNum"));
        data.put("Tx", "33_1");
        data.put("canTj", "1");
        data.put("isNeedAns", "0");
        data.put("UTp", "Xs");
        data.put("ObjId", ObjId);
        data.put("th_1", "21650");
        data.put("wtOR_1", "1\\|/\\|/\\|/\\|/1\\|/\\|/\\|/\\|/\\|/");
        data.put("sF21650_1","1");
        data.put("sF21650_2","");
        data.put("sF21650_3","");
        data.put("sF21650_4","");
        data.put("sF21650_5","1");
        data.put("sF21650_6","1");
        data.put("sF21650_7","1");
        data.put("sF21650_8","1");
        data.put("sF21650_9","1");
        data.put("sF21650_10","");
        data.put("th_2", "21912");
        data.put("wtOR_2", null);
        data.put("sF21912_1", params.get("destination"));
        data.put("sF21912_2", params.get("reason"));
        data.put("sF21912_3", params.get("startDay"));
        data.put("sF21912_4", params.get("startTime"));
        data.put("sF21912_5", params.get("endDay"));
        data.put("sF21912_6", params.get("endTime"));
        data.put("sF21912_N", "6");
        data.put("th_3","21648");
        data.put("wtOR_3","N\\|/\\|/N\\|/\\|/N\\|/");
        data.put("sF21648_1","N");
        data.put("sF21648_2","");
        data.put("sF21648_3","N");
        data.put("sF21648_4","");
        data.put("sF21648_5","N");
        data.put("sF21648_6","");
        data.put("sF21648_N","6");
        data.put("th_4","21649");
        data.put("wtOR_4","\\|/\\|/\\|/");
        data.put("sF21649_1","");
        data.put("sF21649_2","");
        data.put("sF21649_3","");
        data.put("sF21649_4","");
        data.put("sF21649_N","4");
        data.put("zw1","");
        data.put("cxStYt","A");
        data.put("zw2","");
        data.put("B2","%CC%E1%BD%BB%B4%F2%BF%A8");

        Response response = Jsoup.connect(submitUrl).data(data).method(Method.POST).execute();
        if (!Pattern.matches("alert(.*?);", response.body())) {
            return false;
        }
        return true;
    }

    /**
     * cookie校验
     * @param cookie
     * @return
     */
    public static boolean cookieVarify(Map<String, String> cookie, String username) throws IOException {
        final String url = "http://jszx-jxpt.cuit.edu.cn/Jxgl/Xs/netks/sj.asp";

        Response response = Jsoup.connect(url).cookies(cookie).method(Method.GET).execute();
        if (!response.body().contains(username)) {
            return false;
        }
        return true;
    }
}
