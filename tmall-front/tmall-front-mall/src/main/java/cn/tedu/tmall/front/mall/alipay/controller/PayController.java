package cn.tedu.tmall.front.mall.alipay.controller;

import cn.tedu.tmall.front.mall.alipay.pojo.param.PayParam;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "9. 模拟支付")
@RestController
@RequestMapping("/alipay")
public class PayController {

    /**
     * APP ID，请在支付宝沙箱应用（https://open.alipay.com/develop/sandbox/app）中查找正确的值
     */
    private static final String APP_ID = "9021000128612617";
    /**
     * 应用私钥，请在支付宝沙箱应用（https://open.alipay.com/develop/sandbox/app）中查找正确的值
     */
    private static final String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCjfaEjQraeBccYQwTujHDXvdg/xeytEkGZVgnRB9TBEv5w6iXBELgmQFLDCpBsQdOJ+2o992NJfJIy+aGFc2xM1VvQpqPzjSFw+WeY0f5DM46xsOW2nfszAtFxcC9VqU4XhT6+1MEd5/dF8W1M+OAgR33DZ2IMJxi5Wj99J0hHx49ZjL1d3+6At6ZstV71FfYdO2iPjg34f+t6oP76eiiCM5YUrTSGpImYc0tOGj7HTgWfNnlQJG7vdh6Uzn7z3b3liuLZgaRacobaOuzJHNpyaRZHS/nMPHyRnHaET55YRCryUtCLb2LMpG9FoxJX1bBmj6pEHLqZ5CowGQrjl9HtAgMBAAECggEBAJLaogd+QXMoVXIhoSh11la0HK8MJlA/+dS2SBMPKbhUS2E5sbf+QKt7U9wGfTwA1Pc4cnI+6Vx1HQ3U1ZV93GYXsGV3igWXW9aS8M+AsRfEFATglTa4KY/klCWFU79qWM8fzQC1MtKeLqsQ9dESLKBSF1GSZ6vq4nZ77lOJGY/+7bhCD23AieH7VhSB3LRhHcrKwCyZickjtbaEdBg51Fkv3UFtFIOHZgBsvTT8rBdEWVLcY47rtMtW+C3LSyQt2UGmt787c4KfDppKr19bJVT+F16oOj/JsdJHvOf+pE5qUPNV8YvS7/9jZTSeuGNmoW7LvmkKyiCnMuyHxvyTT4ECgYEA0zw5Ws1X8CMw8ue1nr5pJYhFM3XYHgjOMEXz0z0V4eB6McjfeTsf26IvGtXN7rAU8xm6Gec9MA7G1jAE8TZXcbd4Pk35jazT2+k7qo8ODFKrIBprWI5BSbbV3I0e2Ruk4MOlEzo/FC8xFH7oXHlYzTkjKI8FhpYCuUTN+h2aYBUCgYEAxiM0xX1qF+zABFqkj8+0/5DWOQjUirQ7bxODL4vcNf04XRJWmm74KEQUOYH6llBhaI9OdQjaJgZLG6IonMoG9gaEXTwuBgW2qtTG7UvXgQMT7OaNRREV26MNpUGJvmnWYcwfz6TE0zknkAoMN+LpD/aFRY11v2/y9aSbMDRQyHkCgYEAmWLg7wuvMIQNVkVlP8lahtOGFJEdBuoz9x/Epv5zdlrPwHjxtZF2V8FUXTMl343jGIf0+DxO1SfVwNCLtjBUnRGx8eQjM3ptOQIKvpY0iEQ7WQt2KY7a39ExSH79CIAnYMMQin7AMUL3a7+fsQ64qgU0f44jYBBsF+Zbf6ShZeUCgYBuUSHLp3VPltCeW/1t2L604GZ14PW9uHWy9ZFAAsX8BV3BbDltyMVAXg9IqZbk5CIn/ko3nVMggraEzzd/fHh9uMkvxBb34WyAFvIfIu/888K/Tupu/wFFhbZOQXw42WK0lttLWDkCRbtCVmXRI/SRn7c6L3XUvbbvj9Ob9jTYOQKBgGDn0JqoTmtl3A4oXehpQJ32pI+d8vouHi/fnPMD0HKgT3Xc6xAYvjAU4p43yrS4OM8026VjM5GNgXXJTg5KmTi3eNLQSPZPBGDxXkyuV/iivDBsyiBOC8TtRnNr4o3TrDTl1pRXu1JqIRxVn42G62ADO+E0TzzpI3PKH2GfRatE";
    /**
     * 支付宝公钥，请在支付宝沙箱应用（https://open.alipay.com/develop/sandbox/app）中查找正确的值
     */
    private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj82xW5dGoXMnJmfdQvE7NAZzyuFXgwFamBTEdRfYCZkDl2ggmRVZt3CkVlLEoO9wTk4qtN/oqquEe5QaVbnxvJqzUEB5pPAl3rZqH+1c+Ca5BP6WEqMcZvlKaTGqv3THbtED2RaN/HNyZ8q6svMQkCuzQQL9Q6Sg3tjrs05Exbp8xAKXuv6XnI/0qyAesw8h25LOM/m/ez+xbWiVNRn7//iJ8IZPse72czBzyTfqebsu1Iy7PRGIqmjjo3NPMsBBTB13P/85XAHMZ1LnwEdzhw3Tlj6lS+x9zibEeUjLv+Kr+7+GMIXxiQIMx5QoibJJcA1unFNsno7QAkcEQ63EtQIDAQAB";
    /**
     * 通知网址，用于处理支付宝完成支付处理后，你的应用中进行下一步处理的请求地址，就是穿透的forwarding地址
     */
    private static final String NOTIFY_URL = "http://vevmej.natappfree.cc/alipay/notify";
    /**
     * 返回网址，用于支付宝完成支付处理后，重定向到某个位置
     */
    private static final String RETURN_URL = "http://vevmej.natappfree.cc/alipay/finish";
    /**
     * 【固定】支付宝网关
     */
    private static final String ALIPAY_GATEWAY = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    /**
     * 【固定】加密算法
     */
    private static final String ENCRYPTION_ALGORITHM = "RSA2";
    /**
     * 【固定】字符编码
     */
    private static final String CHARSET = "UTF-8";
    /**
     * 【固定】数据格式
     */
    private static final String DATA_FORMAT = "json";

    // 此方法用于接收客户端发起的支付请求，并响应支付表单页面到客户端
    // 网页客户端应该通过window.open直接向此处发起请求，则响应的支付表单会直接显示在window.open打开的窗口中
    @ApiOperation("支付宝沙箱支付")
    @ApiOperationSupport(order = 100)
    @GetMapping("/pay")
    public String pay(PayParam payParam, @ApiIgnore HttpServletResponse response) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(
                ALIPAY_GATEWAY, APP_ID, PRIVATE_KEY, DATA_FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, ENCRYPTION_ALGORITHM);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl(NOTIFY_URL);
        //同步跳转地址，仅支持http/https
        request.setReturnUrl(RETURN_URL);
        /******必传参数******/
        JSONObject bizContent = new JSONObject();
        //商户订单号，商家自定义，保持唯一性;实际是按着out_trade_no这个名字把参数发给支付宝的，所以类中原来定义的参数名是什么不重要
        bizContent.put("out_trade_no", payParam.getOutTradeNo());
        //支付金额，最小值0.01元
        bizContent.put("total_amount", payParam.getTotalAmount());
        //订单标题，不可使用特殊符号
        bizContent.put("subject", payParam.getSubject());
        //电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

        /******可选参数，过期时间******/
        //bizContent.put("time_expire", "2022-08-01 22:00:00");

        //// 商品明细信息，按需传入
        //JSONArray goodsDetail = new JSONArray();
        //JSONObject goods1 = new JSONObject();
        //goods1.put("goods_id", "goodsNo1");
        //goods1.put("goods_name", "子商品1");
        //goods1.put("quantity", 1);
        //goods1.put("price", 0.01);
        //goodsDetail.add(goods1);
        //bizContent.put("goods_detail", goodsDetail);

        //// 扩展信息，按需传入
        //JSONObject extendParams = new JSONObject();
        //extendParams.put("sys_service_provider_id", "2088511833207846");
        //bizContent.put("extend_params", extendParams);

        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse alipayTradePagePayResponse = alipayClient.pageExecute(request);
        if (alipayTradePagePayResponse.isSuccess()) {
            System.out.println("调用成功，将在浏览器中打开支付宝【沙箱】支付页");
            //直接获取页面的源代码并返回-- alipayTradePagePayResponse.getBody()；响应的就是跳转的支付界面
            String responseBody = alipayTradePagePayResponse.getBody();
            System.out.println(responseBody);
            return responseBody;
        } else {
            return "交易失败，调用支付宝远程接口失败，请返回订单页查看订单状态，或重新支付！";
        }
    }

    // 接收支付宝发送的通知（支付结果）
    // 注意：必须是POST请求
    @ApiIgnore
    @PostMapping("/notify")
    public String payNotify(@ApiIgnore HttpServletRequest request) throws Throwable {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            params.put(name, request.getParameter(name));
        }
        String sign = params.get("sign");
        String content = AlipaySignature.getSignCheckContentV1(params);
        boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, ALIPAY_PUBLIC_KEY, CHARSET); // 验证签名
        // 支付宝验签
        if (checkSignature) {
            // 验签通过
            System.out.println("交易名称: " + params.get("subject"));
            System.out.println("交易状态: " + params.get("trade_status"));
            System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
            System.out.println("商户订单号: " + params.get("out_trade_no"));
            System.out.println("交易金额: " + params.get("total_amount"));
            System.out.println("买家支付宝ID: " + params.get("buyer_id"));
            System.out.println("买家付款时间: " + params.get("gmt_payment"));
            System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
            // 你应该在此处补充支付成功后续的代码，例如修改订单状态等
            // 完成
            return "支付完成，商家将尽快发货";
        } else {
            return "支付失败，请返回订单页查看订单状态，或重新支付！";
        }
    }

    // 接收处理支付宝的跳转（支付完成后自动重定向到此处）
    @ApiIgnore
    @GetMapping("/finish")
    public String finish() {
        return "支付完成，请返回订单页查看订单状态！";
    }

}
