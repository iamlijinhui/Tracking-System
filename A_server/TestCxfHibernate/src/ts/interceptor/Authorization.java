package ts.interceptor;


import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.XMLMessage;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import ts.model.ErrorMessage;
import ts.util.JwtToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**

 */
public class Authorization extends AbstractPhaseInterceptor<Message> {

    public Authorization() {
        super(Phase.PRE_INVOKE);//调用之前
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        HttpServletRequest request = (HttpServletRequest) message.get("HTTP.REQUEST");
        HttpServletResponse response = (HttpServletResponse) message.get("HTTP.RESPONSE");
       
        String uri = (String) message.get(Message.REQUEST_URI);
        System.out.println(uri);
        if (!uri.matches("^/TestCxfHibernate/REST/Misc/doLogin/\\S*$") && !uri.matches("^/TestCxfHibernate/REST/Domain/getPath/\\S*$")
        		&& !uri.matches("^/TestCxfHibernate/REST/Domain/getPosition/\\S*$")&& !uri.matches("^/TestCxfHibernate/REST/Domain/getExpressSheet/\\S*$")) { //不是登录
            String token = request.getHeader("token");
            try {
                if (token == null || token.length()== 0) { //没有token
                    PrintWriter out = response.getWriter();
                    out.write(new ErrorMessage(ErrorMessage.CODE.NO_TOKEN).toString());
                    System.out.print(new ErrorMessage(ErrorMessage.CODE.NO_TOKEN).toString());
                    message.getInterceptorChain().doInterceptStartingAfter(message, "org.apache.cxf.jaxrs.interceptor.JAXRSOutInterceptor");
                    out.flush();
                } else {
                    try{
                        JwtToken.parseJWT(token);
                        //token有效
                    } catch (Exception e) {
                        //token无效
                        PrintWriter out = response.getWriter();
                        out.write(new ErrorMessage(ErrorMessage.CODE.TOKEN_ERROR).toString());
                        out.flush();
                        message.getInterceptorChain().doInterceptStartingAfter(message, "org.apache.cxf.jaxrs.interceptor.JAXRSOutInterceptor");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //HTTP.RESPONSE
        //org.apache.cxf.request.uri
    }

}
