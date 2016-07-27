package com.yafei.wx.mgr;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;

public class WxMpServlet extends HttpServlet {

    protected WxMpInMemoryConfigStorage config;
    protected WxMpService wxMpService;
    protected WxMpMessageRouter wxMpMessageRouter;

    @Override
    public void init() throws ServletException {
        super.init();

        config = new WxMpInMemoryConfigStorage();
        config.setAppId("..."); // ����΢�Ź��ںŵ�appid
        config.setSecret("..."); // ����΢�Ź��ںŵ�app corpSecret
        config.setToken("..."); // ����΢�Ź��ںŵ�token
        config.setAesKey("..."); // ����΢�Ź��ںŵ�EncodingAESKey

        wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(config);

        WxMpMessageHandler handler = new WxMpMessageHandler() {
            @Override
            public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                    WxMpService wxMpService, WxSessionManager sessionManager) {
                WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content("���Լ�����Ϣ")
                        .fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
                return m;
            }

        };

        wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
        wxMpMessageRouter.rule().async(false).content("����") // ��������Ϊ������������Ϣ
                .handler(handler).end();

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String signature = request.getParameter("signature");
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");

        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            // ��Ϣǩ������ȷ��˵�����ǹ���ƽ̨����������Ϣ
            response.getWriter().println("�Ƿ�����");
            return;
        }

        String echostr = request.getParameter("echostr");
        if (StringUtils.isNotBlank(echostr)) {
            // ˵����һ������������֤�����󣬻���echostr
            response.getWriter().println(echostr);
            return;
        }

        String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type")) ? "raw" : request
                .getParameter("encrypt_type");

        if ("raw".equals(encryptType)) {
            // ���Ĵ������Ϣ
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
            WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
            response.getWriter().write(outMessage.toXml());
            return;
        }

        if ("aes".equals(encryptType)) {
            // ��aes���ܵ���Ϣ
            String msgSignature = request.getParameter("msg_signature");
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), config, timestamp,
                    nonce, msgSignature);
            WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
            response.getWriter().write(outMessage.toEncryptedXml(config));
            return;
        }

        response.getWriter().println("����ʶ��ļ�������");
        return;
    }

}
