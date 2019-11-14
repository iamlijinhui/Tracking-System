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

@Path("/Domain")	//业务操作
public interface IDomainService {
    //快件操作访问接口=======================================================================
	
	/******************************* 快件揽收相关 *******************************/
	/**
	 * 获取运单信息 ---- 揽收时校验运单id是否已经存在 ok
	 * @param id
	 * @return
	 */
	@GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressSheet/{id}") 
	public Response getExpressSheet(@PathParam("id")String id);

    /**
     *  新建运单 ---- 运单id和用户id ok  different
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
     *  保存快递单 ---- ok
     * @param obj
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/saveExpressSheet") 
	public Response saveExpressSheet(ExpressSheet obj);
    
    /**
     * 更新快件信息
     * @param obj
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/updateExpressSheet") 
	public Response updateExpressSheet(ExpressSheet obj);
    
    /******************************* 快递查询相关 *******************************/
    /**
     * 查询快件
     * @param property 属性名
     * @param restrictions 约束，eq/like
     * @param value 属性值
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressList/{Property}/{Restrictions}/{Value}") 
	public List<ExpressSheet> getExpressList(@PathParam("Property")String property, @PathParam("Restrictions")String restrictions, @PathParam("Value")String value);
 
    /**
     *  转运
     * @param packageId
     * @param target
     * @return
     * @throws Exception
     */
 	@GET
 	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
 	@Path("/transfer/{packageId}/{targetUid}")
 	Response transfer(@PathParam("packageId") String packageId, @PathParam("targetUid") int targetUid) throws Exception;
    // 获取转运任务
    
    // 获取揽收任务
    
    // 获取派送任务
    
    //path
 	 @GET
 	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 @Path("/getPath/{id}") 
	 List<ts.model.Path> getPath(@PathParam("id") String id) throws Exception;
    
    /************** 快递转运相关 *******************/
    
    /**
     *  查询新建包裹中的快递 ok
     * @param packageId
     * @return 状态为的快件列表
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getExpressListInPackage/PackageId/{PackageId}") 
	public List<ExpressSheet> getExpressListInPackage(@PathParam("PackageId")String packageId);

	/**
	 *  获取转运包裹列表 ok
	 * @param property  属性名	
	 * @param restrictions 约束 eq/like
	 * @param value 属性值
	 * @return 
	 */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTransPackageList/{Property}/{Restrictions}/{Value}") 
	public List<TransPackage> getTransPackageList(@PathParam("Property")String property, @PathParam("Restrictions")String restrictions, @PathParam("Value")String value);

    /**
     *  根据id获取转运包裹 ok
     * @param id
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/getTransPackage/{id}") 
	public Response getTransPackage(@PathParam("id")String id);

    // 根据id获取转运包裹content
    
    /**
     * 新建包裹 ok待更改  修改过
     * @param id
     * @param uid
     * @return
     */
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/newTransPackage/id/{id}/uid/{uid}") 
    public Response newTransPackage(@PathParam("id")String id, @PathParam("uid")int uid);

    /**
     * 保存包裹 ok
     * @param obj
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/saveTransPackage") 
	public Response saveTransPackage(TransPackage obj);
    
    //根据包裹id和目标地点保存包裹
    
	/**
	 *  拆包 
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
     * 拆包时包裹丢失
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
    
	// 查询该节点所有已拆包的expresssheet
    
	/**
	 *  打包
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
    
	// 开始打包
    
    /**
     * 结束打包
     * @param p
     * @return
     */
 	@GET
 	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
 	@Path("/finishPack/{uid}/{packageId}")
 	Response finishPack(@PathParam("uid") int uid, @PathParam("packageId") String packageId);
	// 分拣
 	
 	/**
	 * 快件坐标位置跟踪
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
	 * 包裹位置上传保存
	 * @param loc
	 * @return
	 * @throws Exception
	 */
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/uploadPosition")
    Response uploadPosition(Position pos) throws Exception;
	
	/**
	 * 员工保存自己的包裹位置
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
	
	/************************ 快件终点派送 *****************************/

    
	/**
	 *  分发
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
	 *  派送 ok different
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
     * 完成派送
     * @param packageId
     * @param uid
     * @return
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/finishDelivery/packageId/{packageId}/uid/{uid}") 
	public Response finishDelivery(@PathParam("packageId")String packageId, @PathParam("uid")int uid);    
    
	// 获取运单信息 ---- 揽收时校验运单id是否已经存在
    
    
	/**
	 *  完成揽收 ok different 修改
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
    
    //包裹操作访问接口=======================================================================
}
