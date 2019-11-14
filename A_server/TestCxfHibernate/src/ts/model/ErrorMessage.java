package ts.model;

import org.json.JSONObject;

import ts.model.ErrorMessage.CODE;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

/**
 * 预定义的各种信息
 */
@XmlRootElement(name="ErrorMessage")
public class ErrorMessage {
	private Integer msgCode;
	private String msgInfo;
	
	private static Map<Integer, String> msgMap = new HashMap<>();

	public Integer getMsgCode() {
		return msgCode;
	}
	
	@XmlElement
	public void setMsgCode(Integer msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsgInfo() {
		return msgInfo;
	}

	@XmlElement
	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}

	public static Map<Integer, String> getMsgMap() {
		return msgMap;
	}

	public static void setMsgMap(Map<Integer, String> msgMap) {
		ErrorMessage.msgMap = msgMap;
	}

	@Override
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", msgCode);
		jsonObject.put("message", msgInfo);
		return jsonObject.toString();
	}
	
	public ErrorMessage(Integer msgCode, String msgInfo) {
		this.msgCode = msgCode;
		this.msgInfo = msgInfo;
	}
	
	public ErrorMessage(Integer msgCode) {
		this.msgCode = msgCode;
		this.msgInfo = msgMap.get(msgCode);
	}
		
	public ErrorMessage() {
		
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorMessage)) return false;

        ErrorMessage message = (ErrorMessage) o;

        if (msgCode != message.msgCode) return false;
        return msgInfo != null ? msgInfo.equals(message.msgInfo) : message.msgInfo == null;
    }

    @Override
    public int hashCode() {
        int result = msgCode;
        result = 31 * result + (msgInfo != null ? msgInfo.hashCode() : 0);
        return result;
    }

    public static final class CODE{
        public static final int URL_NOT_FOUND = -2;
        public static final int UNKNOWN_ERROR = -1;
        public static final int SUCCESS = 0;

        // User相关
        public static final int LOGIN_FAILED = 100;
        public static final int CHANGE_PASSWD_FAILED = 101;
        public static final int NO_TOKEN = 102;
        public static final int TOKEN_ERROR = 103;

        // Customer相关
        public static final int CUSTOMER_DELETE_FAILED = 200;
    	

        //与ExpressSheet相关的错误 300 - 399
        public static final int EXPRESS_SHEET_NOT_EXISTED = 300;
        public static final int EXPRESS_SHEET_CANNOT_PACK = 301;
        public static final int EXPRESS_SHEET_HAS_EXISTED = 302;
        public static final int EXPRESS_SHEET_INCOMPLETE = 303;
        public static final int EXPRESS_SHEET_CANNOT_DISPATCH = 304;
        public static final int EXPRESS_SHEET_CANNOT_DELIVERY = 305;
        public static final int EXPRESS_SHEET_CANNOT_UNPACK = 306;
        public static final int EXPRESSSHEET_NOT_INPACKAGE = 307;

        //与CustomerInfo相关的信息 400 - 499
        public static final int CUSTOMER_INFO_INCOMPLETE = 400;


        //与 Transpackage相关的信息 500 - 599
        public static final int TRANS_PACKAGE_STATUE_ERROR = 500;
        public static final int TRANS_PACKAGE_IS_EMPTY = 501;
        public static final int TRANS_PACKAGE_NOT_EXISTED = 502;
        public static final int TRANS_PACKAGE_HAS_EXISTED = 503;
        public static final int NO_PACKING_PACKAGE = 504;
        public static final int HAS_PACKING_PACKAGE = 505;
        public static final int DELIVERY_NOT_FINSH = 506;
		public static final int EXPRESS_SHEET_CREATE_FAILED = 33333;
		public static final Integer USER_NOT_EXISTED = 11111;
    }

    static {
        msgMap.put(CODE.SUCCESS, "操作成功");
        msgMap.put(CODE.UNKNOWN_ERROR, "未知错误");
        msgMap.put(CODE.URL_NOT_FOUND, "URL错误");

        msgMap.put(CODE.NO_TOKEN, "没有Token");
        msgMap.put(CODE.TOKEN_ERROR, "Token错误");

        msgMap.put(CODE.LOGIN_FAILED, "登录失败，用户名或密码错误，请检查！");
        msgMap.put(CODE.CHANGE_PASSWD_FAILED, "密码修改失败，原始密码输入错误，请检查！");
        msgMap.put(CODE.CUSTOMER_DELETE_FAILED, "用户信息删除失败，系统中该可能存在该用户的快递信息等");
        msgMap.put(CODE.EXPRESS_SHEET_NOT_EXISTED, "快件不存在");
        msgMap.put(CODE.EXPRESS_SHEET_CANNOT_UNPACK, "当前快件不能被拆包");
        msgMap.put(CODE.EXPRESS_SHEET_CANNOT_PACK, "当前快件不能被打包");
        msgMap.put(CODE.EXPRESS_SHEET_HAS_EXISTED, "此快递ID已被使用，创建失败");
        msgMap.put(CODE.EXPRESS_SHEET_CREATE_FAILED, "快递单创建失败");
        msgMap.put(CODE.EXPRESS_SHEET_INCOMPLETE, "快件信息不完整");
        msgMap.put(CODE.EXPRESS_SHEET_CANNOT_DISPATCH, "只有处于分拣状态的快件才能派送");
        msgMap.put(CODE.EXPRESS_SHEET_CANNOT_DELIVERY, "只有处于派送状态的快件才能签收");
        msgMap.put(CODE.EXPRESSSHEET_NOT_INPACKAGE, "错误快件，不在该包裹中");

        msgMap.put(CODE.CUSTOMER_INFO_INCOMPLETE, "客户信息不完整");

        msgMap.put(CODE.TRANS_PACKAGE_STATUE_ERROR, "包裹状态错误");
        msgMap.put(CODE.TRANS_PACKAGE_IS_EMPTY, "包裹为空");
        msgMap.put(CODE.TRANS_PACKAGE_NOT_EXISTED, "包裹不存在");
        msgMap.put(CODE.TRANS_PACKAGE_HAS_EXISTED, "包裹已存在");
        msgMap.put(CODE.NO_PACKING_PACKAGE, "没有正在打包的包裹");
        msgMap.put(CODE.HAS_PACKING_PACKAGE, "您还有正在打包的包裹");
        msgMap.put(CODE.DELIVERY_NOT_FINSH, "快件未派送完成");
    }
}
