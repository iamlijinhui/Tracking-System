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

import ts.model.ExpressSheet;
import ts.model.Location;
import ts.model.MissingExpressSheet;
import ts.model.Position;
import ts.model.TransPackage;

@Path("/Domain")	//ҵ�����
public interface IDomainService {
    //����������ʽӿ�=======================================================================
	
	/******************************* ���������� *******************************/
	/**
	 * ��ȡ�˵���Ϣ ---- ����ʱУ���˵�id�Ƿ��Ѿ����� ok
	 * @param id
	 * @return
	 */
	@GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressSheet/{id}") 
	public Response getExpressSheet(@PathParam("id")String id);

    /**
     *  �½��˵� ---- �˵�id���û�id ok  different
     * @param id
     * @param uid
     * @return
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/newExpressSheet/id/{id}/uid/{uid}") 
	public Response newExpressSheet(@PathParam("id")String id, @PathParam("uid")int uid);
    
    /**
     *  �����ݵ� ---- ok
     * @param obj
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/saveExpressSheet") 
	public Response saveExpressSheet(ExpressSheet obj);
    
    /**
     * ���¿����Ϣ
     * @param obj
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/updateExpressSheet") 
	public Response updateExpressSheet(ExpressSheet obj);
    
    /******************************* ��ݲ�ѯ��� *******************************/
    /**
     * ��ѯ���
     * @param property ������
     * @param restrictions Լ����eq/like
     * @param value ����ֵ
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressList/{Property}/{Restrictions}/{Value}") 
	public List<ExpressSheet> getExpressList(@PathParam("Property")String property, @PathParam("Restrictions")String restrictions, @PathParam("Value")String value);
 
    /**
     *  ת��
     * @param packageId
     * @param target
     * @return
     * @throws Exception
     */
 	@GET
 	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
 	@Path("/transfer/{packageId}/{targetUid}")
 	Response transfer(@PathParam("packageId") String packageId, @PathParam("targetUid") int targetUid) throws Exception;
    // ��ȡת������
    
    // ��ȡ��������
    
    // ��ȡ��������
    
    //path
 	 @GET
 	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 @Path("/getPath/{id}") 
	 List<ts.model.Path> getPath(@PathParam("id") String id) throws Exception;
    
    /************** ���ת����� *******************/
    
    /**
     *  ��ѯ�½������еĿ�� ok
     * @param packageId
     * @return ״̬Ϊ�Ŀ���б�
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressListInPackage/PackageId/{PackageId}") 
	public List<ExpressSheet> getExpressListInPackage(@PathParam("PackageId")String packageId);

	/**
	 *  ��ȡת�˰����б� ok
	 * @param property  ������	
	 * @param restrictions Լ�� eq/like
	 * @param value ����ֵ
	 * @return 
	 */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTransPackageList/{Property}/{Restrictions}/{Value}") 
	public List<TransPackage> getTransPackageList(@PathParam("Property")String property, @PathParam("Restrictions")String restrictions, @PathParam("Value")String value);

    /**
     *  ����id��ȡת�˰��� ok
     * @param id
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTransPackage/{id}") 
	public Response getTransPackage(@PathParam("id")String id);

    // ����id��ȡת�˰���content
    
    /**
     * �½����� ok������  �޸Ĺ�
     * @param id
     * @param uid
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/newTransPackage/id/{id}/uid/{uid}") 
    public Response newTransPackage(@PathParam("id")String id, @PathParam("uid")int uid);

    /**
     * ������� ok
     * @param obj
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/saveTransPackage") 
	public Response saveTransPackage(TransPackage obj);
    
    //���ݰ���id��Ŀ��ص㱣�����
    
	/**
	 *  ��� 
	 * @param packageId
	 * @param expressSheetId
	 * @param uid
	 * @return
	 * @throws Exception
	 */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/unpack/packageId/{packageId}/expressId/{expressSheetId}/uId/{uid}")
	public Response unpack(@PathParam("packageId") String packageId, @PathParam("expressSheetId") String expressSheetId, @PathParam("uid") int uid) throws Exception;
	
    /**
     * ���ʱ������ʧ
     * @param uid
     * @param expressSheetId
     * @return
     */
    @GET
 	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
 	@Path("/expressSheetMiss/{packageId}/{expressSheetId}")
    public Response expressSheetMiss(@PathParam("packageId") String packageId, @PathParam("expressSheetId") String expressSheetId);
    
    @GET
 	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
 	@Path("/getMissExpressSheet")
    public List<MissingExpressSheet> getMissExpressSheet();
    
	// ��ѯ�ýڵ������Ѳ����expresssheet
    
	/**
	 *  ���
	 * @param uid
	 * @param packageId
	 * @param expressSheetId
	 * @return
	 * @throws Exception
	 */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/pack/{uid}/{packageId}/{expressSheetId}")
	Response pack(@PathParam("uid") int uid, @PathParam("packageId") String packageId, @PathParam("expressSheetId") String expressSheetId) throws Exception;
    
	// ��ʼ���
    
    /**
     * �������
     * @param p
     * @return
     */
 	@GET
 	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
 	@Path("/finishPack/{uid}/{packageId}")
 	Response finishPack(@PathParam("uid") int uid, @PathParam("packageId") String packageId);
	// �ּ�
 	
 	/**
	 * �������λ�ø���
	 * @param expressId
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
    @Path("/track/{expressId}/{uid}")
    Response track(@PathParam("expressId") String expressId, @PathParam("uid") int uid) throws Exception;
	
 	/**
	 * ����λ���ϴ�����
	 * @param loc
	 * @return
	 * @throws Exception
	 */
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/uploadPosition")
    Response uploadPosition(Position pos) throws Exception;
	
	/**
	 * Ա�������Լ��İ���λ��
	 * @param uid
	 * @param x
	 * @param y
	 * @return
	 * @throws Exception
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/savePosition/uid/{uid}/position.getX/{x}/position.getY/{y}")
    Response savePosition(@PathParam("uid") int uid, @PathParam("x") float x, @PathParam("y") float y) throws Exception;
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getPosition/{expressId}")
    List<Position> getPosition(@PathParam("expressId") String expressId) throws Exception;
	
	/************************ ����յ����� *****************************/

    
	/**
	 *  �ַ�
	 * @param id
	 * @param uid
	 * @return
	 */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/dispatchExpressSheetId/id/{id}/uid/{uid}") 
	public Response DispatchExpressSheet(@PathParam("id")String id, @PathParam("uid")int uid);
    
	/**
	 *  ���� ok different
	 * @param id
	 * @param uid
	 * @return
	 */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/deliveryExpressSheetId/id/{id}/uid/{uid}") 
	public Response DeliveryExpressSheetId(@PathParam("id")String id, @PathParam("uid")int uid);
    
    /**
     * �������
     * @param packageId
     * @param uid
     * @return
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/finishDelivery/packageId/{packageId}/uid/{uid}") 
	public Response finishDelivery(@PathParam("packageId")String packageId, @PathParam("uid")int uid);    
    
	// ��ȡ�˵���Ϣ ---- ����ʱУ���˵�id�Ƿ��Ѿ�����
    
    
	/**
	 *  ������� ok different �޸�
	 * @param id
	 * @param uid
	 * @return
	 */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/receiveExpressSheetId/id/{id}/uid/{uid}") 
	public Response ReceiveExpressSheetId(@PathParam("id")String id, @PathParam("uid")int uid);
    
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getUserPackages/{id}") 
	public Response getUserPackages(@PathParam("id")int id);
    
    //�����������ʽӿ�=======================================================================
}
