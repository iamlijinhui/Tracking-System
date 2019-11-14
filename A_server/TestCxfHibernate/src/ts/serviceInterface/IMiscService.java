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
    /**************************customers���***************************************/
    /**
     *  �������ֻ�ȡcustomers�б� ok
     * @param name
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getCustomerListByName/{name}") 
	public List<CustomerInfo> getCustomerListByName(@PathParam("name")String name);
    
    /**
     *  �����ֻ���customers�б� ok 
     * @param TelCode
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getCustomerListByTelCode/{TelCode}") 
	public List<CustomerInfo> getCustomerListByTelCode(@PathParam("TelCode")String TelCode);
    
    /**
     *  ��ȡ����customer ok 
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/getAllCustomers")  
	public List<CustomerInfo> getAllCustomers();
    
    /**
     * ����id��ȡ ok
     * @param id
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getCustomerInfo/{id}") 
	public Response getCustomerInfo(@PathParam("id")String id);
    
    /**
     * ����idɾ��customer ok
     * @param id
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/deleteCustomerInfo/{id}") 
	public Response deleteCustomerInfo(@PathParam("id")int id);
    
    /**
     * ����customer��Ϣ ok
     * @param obj
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/saveCustomerInfo") 
	public Response saveCustomerInfo(CustomerInfo obj);
    
    /**
     * ���¿ͻ���Ϣ
     * @param obj
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/updateCustomerInfo") 
	public Response updateCustomerInfo(CustomerInfo obj);
    //===============================================================================================
	/**************************Transnodeת�˽ڵ������***************************************/
	/**
	 *  ����code��ȡָ��ת�˽ڵ� ok
	 * @param code
	 * @return
	 */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getNode/{NodeCode}") 
	public TransNode getNode(@PathParam("NodeCode")String code);
    
    /**
     *  ��������id�ͽڵ����ͻ�ȡĳ����region��ת�˽ڵ� ok 
     * @param regionCode
     * @param type
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getNodesList/{RegionCode}/{Type}") 
	public List<TransNode> getNodesList(@PathParam("RegionCode")String regionCode, @PathParam("Type")int type);
    
    //�������ֻ�ȡת�˽ڵ�
    
    
    /**
     * ��ȡ����ʡ�� ok 
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getProvinceList") 
	public List<CodeNamePair> getProvinceList();
    
    /**
     * ����ʡ��code��ȡ���� ok
     * @param prv
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getCityList/{prv}") 
	public List<CodeNamePair> getCityList(@PathParam("prv")String prv);
    
    /**
     * ���ݳ���code��ȡ�� ok 
     * @param city
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTownList/{city}") 
	public List<CodeNamePair> getTownList(@PathParam("city")String city);
    
    //�������ȡʡ
    
    
    
    //�������ȡ����
    
    
    /**
     *  ��ȡ�����ַ��� ok
     * @param id
     * @return
     */
    @GET
    @Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
    @Path("/getRegionString/{id}") 
	public String getRegionString(@PathParam("id")String id);
    
    /**
     * ����id��ȡ���� ok
     * @param id
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getRegion/{id}") 
	public Region getRegion(@PathParam("id")String id);
    
    //===============================================================================================
	
	
	
	/***************************************user���*******************************/  
	/**
	 * �����˺������¼ ok
	 * @param uid
	 * @param pwd
	 * @return
	 */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/doLogin/{uid}/{pwd}") 
	public Response doLogin(@PathParam("uid") int uid, @PathParam("pwd") String pwd);
    
    //���ݵ绰�����¼
    
    
    
    //ע��
    
    
    //�޸Ľ�ɫ
    
    
    //�޸�����
    
    
    
    /**
     * �ǳ�
     * @param uid
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/doLogOut/{uid}") 
	public void doLogOut(@PathParam("uid") int uid);

    /**
     * ��ȡ�����û�
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
     * ����id��ѯuser
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
     * ע��һ�����û�
     * @param obj
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/register") 
	public Response register(UserInfo obj);
    
    public void CreateWorkSession(int uid);
    /**
     * ˢ��
     */
	public void RefreshSessionList();
}
