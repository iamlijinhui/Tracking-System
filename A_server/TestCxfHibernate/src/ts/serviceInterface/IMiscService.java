package ts.serviceInterface;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ts.model.CodeNamePair;
import ts.model.CustomerInfo;
import ts.model.Region;
import ts.model.TransNode;
import ts.model.UserInfo;
import ts.model.UsersPackage;

@Path("/Misc")
public interface IMiscService {
	
	
    //===============================================================================================
    /**************************customers相关***************************************/
    /**
     *  根据名字获取customers列表 ok
     * @param name
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getCustomerListByName/{name}") 
	public List<CustomerInfo> getCustomerListByName(@PathParam("name")String name);
    
    /**
     *  根据手机号customers列表 ok 
     * @param TelCode
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getCustomerListByTelCode/{TelCode}") 
	public List<CustomerInfo> getCustomerListByTelCode(@PathParam("TelCode")String TelCode);
    
    /**
     *  获取所有customer ok 
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/getAllCustomers")  
	public List<CustomerInfo> getAllCustomers();
    
    /**
     * 根据id获取 ok
     * @param id
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getCustomerInfo/{id}") 
	public Response getCustomerInfo(@PathParam("id")String id);
    
    /**
     * 根据id删除customer ok
     * @param id
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/deleteCustomerInfo/{id}") 
	public Response deleteCustomerInfo(@PathParam("id")int id);
    
    /**
     * 保存customer信息 ok
     * @param obj
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/saveCustomerInfo") 
	public Response saveCustomerInfo(CustomerInfo obj);
    
    /**
     * 更新客户信息
     * @param obj
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/updateCustomerInfo") 
	public Response updateCustomerInfo(CustomerInfo obj);
    //===============================================================================================
	/**************************Transnode转运节点和网点***************************************/
	/**
	 *  根据code获取指定转运节点 ok
	 * @param code
	 * @return
	 */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getNode/{NodeCode}") 
	public TransNode getNode(@PathParam("NodeCode")String code);
    
    /**
     *  根据区域id和节点类型获取某区域region的转运节点 ok 
     * @param regionCode
     * @param type
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getNodesList/{RegionCode}/{Type}") 
	public List<TransNode> getNodesList(@PathParam("RegionCode")String regionCode, @PathParam("Type")int type);
    
    //根据名字获取转运节点
    
    
    /**
     * 获取所有省份 ok 
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getProvinceList") 
	public List<CodeNamePair> getProvinceList();
    
    /**
     * 根据省份code获取城市 ok
     * @param prv
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getCityList/{prv}") 
	public List<CodeNamePair> getCityList(@PathParam("prv")String prv);
    
    /**
     * 根据城市code获取镇 ok 
     * @param city
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTownList/{city}") 
	public List<CodeNamePair> getTownList(@PathParam("city")String city);
    
    //根据镇获取省
    
    
    
    //根据镇获取城市
    
    
    /**
     *  获取区域字符串 ok
     * @param id
     * @return
     */
    @GET
    @Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
    @Path("/getRegionString/{id}") 
	public String getRegionString(@PathParam("id")String id);
    
    /**
     * 根据id获取区域 ok
     * @param id
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getRegion/{id}") 
	public Region getRegion(@PathParam("id")String id);
    
    //===============================================================================================
	
	
	
	/***************************************user相关*******************************/  
	/**
	 * 根据账号密码登录 ok
	 * @param uid
	 * @param pwd
	 * @return
	 */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/doLogin/{uid}/{pwd}") 
	public Response doLogin(@PathParam("uid") int uid, @PathParam("pwd") String pwd);
    
    //根据电话号码登录
    
    
    
    //注册
    
    
    //修改角色
    
    
    //修改密码
    
    
    
    /**
     * 登出
     * @param uid
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/doLogOut/{uid}") 
	public void doLogOut(@PathParam("uid") int uid);

    /**
     * 获取所有用户
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/getAllUsers")  
	public List<UserInfo> getAllUsers();
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/getUserPackages/{id}")  
    public List<UsersPackage> getUserPackages(@PathParam("id")int id);
    
    /**
     * 根据id查询user
     * @param id
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getUserInfo/{id}") 
	public Response getUserInfo(@PathParam("id")String id);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/fuck/{id}") 
	public Response fuck(@PathParam("id")int id);
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/saveUserInfo") 
	public Response saveUserInfo(UserInfo obj);
    
    /**
     * 注册一个新用户
     * @param obj
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/register") 
	public Response register(UserInfo obj);
    
    public void CreateWorkSession(int uid);
    /**
     * 刷新
     */
	public void RefreshSessionList();
}
