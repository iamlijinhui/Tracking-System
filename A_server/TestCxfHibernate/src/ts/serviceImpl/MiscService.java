package ts.serviceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.hibernate.criterion.Restrictions;
import ts.daoImpl.CustomerInfoDao;
import ts.daoImpl.RegionDao;
import ts.daoImpl.TransHistoryDao;
import ts.daoImpl.TransNodeDao;
import ts.daoImpl.TransPackageDao;
import ts.daoImpl.UserInfoDao;
import ts.daoImpl.UsersPackageDao;
import ts.model.CodeNamePair;
import ts.model.CustomerInfo;
import ts.model.ErrorMessage;
import ts.model.Region;
import ts.model.TransHistory;
import ts.model.TransNode;
import ts.model.TransPackage;
import ts.model.UserInfo;
import ts.model.UsersPackage;
import ts.serviceInterface.IMiscService;
import ts.util.JwtToken;


public class MiscService implements IMiscService{
	//TransNodeCatalog nodes;	//自己做的缓存和重定向先不要了,用Hibernate缓存对付一下，以后加上去
	//RegionCatalog regions;
	private TransNodeDao transNodeDao;
	private RegionDao regionDao;
	private CustomerInfoDao customerInfoDao;
	private UserInfoDao userInfoDao;
	private TransPackageDao transPackageDao;
	private UsersPackageDao usersPackageDao;
	private TransHistoryDao transHistoryDao;
	
	public TransNodeDao getTransNodeDao() {
		return transNodeDao;
	}

	public void setTransNodeDao(TransNodeDao dao) {
		this.transNodeDao = dao;
	}

	public RegionDao getRegionDao() {
		return regionDao;
	}

	public void setRegionDao(RegionDao dao) {
		this.regionDao = dao;
	}

	public CustomerInfoDao getCustomerInfoDao() {
		return customerInfoDao;
	}

	public void setCustomerInfoDao(CustomerInfoDao dao) {
		this.customerInfoDao = dao;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public TransPackageDao getTransPackageDao() {
		return transPackageDao;
	}

	public void setTransPackageDao(TransPackageDao transPackageDao) {
		this.transPackageDao = transPackageDao;
	}

	public UsersPackageDao getUsersPackageDao() {
		return usersPackageDao;
	}

	public void setUsersPackageDao(UsersPackageDao usersPackageDao) {
		this.usersPackageDao = usersPackageDao;
	}

	public TransHistoryDao getTransHistoryDao() {
		return transHistoryDao;
	}

	public void setTransHistoryDao(TransHistoryDao transHistoryDao) {
		this.transHistoryDao = transHistoryDao;
	}

	public MiscService(){
//		nodes = new TransNodeCatalog();
//		nodes.Load();
//		regions = new RegionCatalog();
//		regions.Load();
	}
	
	/**************************customers相关***************************************/
    // 根据名字获取customers列表
	@Override
	public List<CustomerInfo> getCustomerListByName(String name) {
//		List<CustomerInfo> listci = customerInfoDao.findByName(name);
//		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
//		for(CustomerInfo ci : listci){
//			CodeNamePair cn = new CodeNamePair(String.valueOf(ci.getID()),ci.getName());
//			listCN.add(cn);
//		}
//		return listCN;
		return customerInfoDao.findByName(name);
	}

	// 获取所有customer
	@Override
	public List<CustomerInfo> getAllCustomers() {
		return customerInfoDao.getAll();
	}
		
		// 根据手机号customers列表
	@Override
	public List<CustomerInfo> getCustomerListByTelCode(String TelCode) {
//		List<CustomerInfo> listci = customerInfoDao.findByTelCode(TelCode);
//		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
//		for(CustomerInfo ci : listci){
//			CodeNamePair cn = new CodeNamePair(String.valueOf(ci.getID()),ci.getName());
//			listCN.add(cn);
//		}
//		return listCN;
		return customerInfoDao.findByTelCode(TelCode);
	}

	//根据id获取
	@Override
	public Response getCustomerInfo(String id) {
		CustomerInfo cstm = customerInfoDao.get(Integer.parseInt(id));
//		try{
//			cstm.setRegionString(regionDao.getRegionNameByID(cstm.getRegionCode()));	//这部分功能放到DAO里去了
//		}catch(Exception e){}
		return Response.ok(cstm).header("EntityClass", "CustomerInfo").build(); 
	}
	
	//根据id删除customer
	@Override
	public Response deleteCustomerInfo(int id) {
		customerInfoDao.removeById(id);
		return Response.ok("Deleted").header("EntityClass", "D_CustomerInfo").build(); 
	}

	//保存customer信息
	@Override
	public Response saveCustomerInfo(CustomerInfo obj) {
		try{
			customerInfoDao.save(obj);			
			return Response.ok(obj).header("EntityClass", "R_CustomerInfo").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	//修改customer信息
		@Override
		public Response updateCustomerInfo(CustomerInfo obj) {
			try{
				customerInfoDao.update(obj);			
				return Response.ok(obj).header("EntityClass", "U_CustomerInfo").build(); 
			}
			catch(Exception e)
			{
				return Response.serverError().entity(e.getMessage()).build(); 
			}
		}
		
	/**************************Transnode转运节点和网点***************************************/
		
	/**
	 * 根据code获取指定转运节点
	 */
	@Override
	public TransNode getNode(String code) {
		// TODO Auto-generated method stub
		return transNodeDao.get(code);
	}

	// 根据区域id和节点类型获取某区域region的转运节点
	@Override
	public List<TransNode> getNodesList(String regionCode, int type) {
		return transNodeDao.findBy("ID", true, Restrictions.eq("regionCode", regionCode), Restrictions.eq("nodeType", type));
	}

	//根据名字获取转运节点
	
	//获取所有省份
	@Override
	public List<CodeNamePair> getProvinceList() {		
		List<Region> listrg = regionDao.getProvinceList();
		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
		for(Region rg : listrg){
			CodeNamePair cn = new CodeNamePair(rg.getORMID(),rg.getPrv());
			listCN.add(cn);
		}
		return listCN;
	}
	
	//根据省份code获取城市
	@Override
	public List<CodeNamePair> getCityList(String prv) {
		List<Region> listrg = regionDao.getCityList(prv);
		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
		for(Region rg : listrg){
			CodeNamePair cn = new CodeNamePair(rg.getORMID(),rg.getCty());
			listCN.add(cn);
		}
		return listCN;
	}

	//根据城市code获取镇
	@Override
	public List<CodeNamePair> getTownList(String city) {
		List<Region> listrg = regionDao.getTownList(city);
		List<CodeNamePair> listCN = new ArrayList<CodeNamePair>();
		for(Region rg : listrg){
			CodeNamePair cn = new CodeNamePair(rg.getORMID(),rg.getTwn());
			listCN.add(cn);
		}
		return listCN;
	}

	// 获取区域字符串
	@Override
	public String getRegionString(String code) {
		return regionDao.getRegionNameByID(code);
	}

	//根据id获取区域
	@Override
	public Region getRegion(String code) {
		return regionDao.getFullNameRegionByID(code);
	}


	/***************************************user相关*******************************/  
	// 使用id进行登录
		@Override
		public Response doLogin(int uid, String pwd) {
			UserInfo userInfo = userInfoDao.login(uid, pwd);
			if (userInfo != null && userInfo.getStatus() != -1) {
				userInfo.setUserToken(
						JwtToken.createJWT(userInfo.getName(), userInfo.getUID() + "", userInfo.getURull() + ""));
				return Response.ok(userInfo).header("EntityClass", "UserInfo").build();
			}
			return Response.ok("账号或密码错误，请重新输入！").header("EntityClass", "W_UserInfo").build();
//			System.out.println(ErrorMessage.CODE.LOGIN_FAILED);
//			ErrorMessage e = new ErrorMessage(ErrorMessage.CODE.LOGIN_FAILED);
//			System.out.println(ErrorMessage.CODE.LOGIN_FAILED);
//			return Response.ok(e).header("EntityClass", "ErrorMessage").build();
		}

	@Override
	public void doLogOut(int uid) {
		// TODO Auto-generated method stub
		
	}

	// 获取所有customer
	@Override
	public List<UserInfo> getAllUsers() {
		return userInfoDao.getAll();
	}
	
	@Override
	public List<UsersPackage> getUserPackages(int id) {
		List<UsersPackage> list = usersPackageDao.getUserPackages(id);
		for(int i = 0; i<list.size()-1;i++){ 
		    for(int j=list.size()-1; j>i; j--)  {       
		           if  (list.get(j).getPkg().getID().equals(list.get(i).getPkg().getID()))  {       
		              list.remove(j);       
		            }        
		        }        
		      }   
		return list;
	}
	
//	@Override	
//	public List<UsersPackage> getUserPackages(@PathParam("id")int id) {
//		List<UsersPackage> list = usersPackageDao.getUserPackages(id);
//		return list;
//		}
	
	@Override
	public Response getUserInfo(String id) {
		UserInfo userInfo = userInfoDao.get(Integer.parseInt(id));
		return Response.ok(userInfo).header("EntityClass", "UserInfo").build(); 
		}		
	
	@Override
	public Response fuck(int id) {
		return null;
		
	}
	
	@Override
	public Response saveUserInfo(UserInfo obj) {
		try{
			userInfoDao.save(obj);			
			return Response.ok(obj).header("EntityClass", "S_UserInfo").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	@Override
	public Response register(UserInfo userInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date tm = new Date();
		try {
			tm = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try{
			TransPackage transPackage1 = new TransPackage();
			transPackage1.setID("r" + new Date().getTime() + userInfo.getUID());
			transPackage1.setCreateTime(tm);
			transPackage1.setSourceNode(transNodeDao.get(userInfo.getDptID()).getRegionCode());
			transPackage1.setTargetNode(transNodeDao.get(userInfo.getDptID()).getRegionCode());
			transPackage1.setStatus(TransPackage.STATUS.STATUS_RECEIVED);
			transPackageDao.save2(transPackage1);
			userInfo.setReceivePackageID(transPackage1.getID());
			
			TransPackage transPackage2 = new TransPackage();
			transPackage2.setID("d" + new Date().getTime() + userInfo.getUID());
			transPackage2.setCreateTime(tm);
			transPackage2.setSourceNode(transNodeDao.get(userInfo.getDptID()).getRegionCode());
			transPackage2.setTargetNode(transNodeDao.get(userInfo.getDptID()).getRegionCode());
			transPackage2.setStatus(TransPackage.STATUS.STATUS_DELIVERIED);
			transPackageDao.save2(transPackage2);
			userInfo.setDelivePackageID(transPackage2.getID());
			TransPackage transPackage3 = new TransPackage();
			transPackage3.setID("t" + new Date().getTime() + userInfo.getUID());
			transPackage3.setCreateTime(tm);
			transPackage3.setSourceNode(transNodeDao.get(userInfo.getDptID()).getRegionCode());
			transPackage3.setTargetNode(transNodeDao.get(userInfo.getDptID()).getRegionCode());
			transPackage3.setStatus(TransPackage.STATUS.STATUS_TRANSPORT);
			transPackageDao.save2(transPackage3);
			userInfo.setTransPackageID(transPackage3.getID());
			userInfoDao.save(userInfo);
			userInfo = userInfoDao.getUIDByReceivePackageID(transPackage1.getID());
			userInfo = userInfoDao.getUIDByReceivePackageID(transPackage1.getID());
			//存储r
			//插入history表中
			TransHistory transHistory = new TransHistory();
			transHistory.setPkg(transPackage1);
			transHistory.setActTime(new Timestamp(System.currentTimeMillis()) );
			transHistory.setUIDFrom(userInfo.getUID());
			transHistory.setUIDTo(userInfo.getUID());
			transHistoryDao.save2(transHistory);
			//插入UsersPackage
			UsersPackage usersPackage = new UsersPackage();
			usersPackage.setPkg(transPackage1);
			usersPackage.setUserU(userInfo);
			usersPackageDao.save2(usersPackage);
			//存储d
			//插入history表中
			transHistory = new TransHistory();
			transHistory.setPkg(transPackage2);
			transHistory.setActTime(new Timestamp(System.currentTimeMillis()) );
			transHistory.setUIDFrom(userInfo.getUID());
			transHistory.setUIDTo(userInfo.getUID());
			transHistoryDao.save2(transHistory);
			//插入UsersPackage
			usersPackage = new UsersPackage();
			usersPackage.setPkg(transPackage2);
			usersPackage.setUserU(userInfo);
			usersPackageDao.save2(usersPackage);
			//存储t
			//插入history表中
			transHistory = new TransHistory();
			transHistory.setPkg(transPackage3);
			transHistory.setActTime(new Timestamp(System.currentTimeMillis()) );
			transHistory.setUIDFrom(userInfo.getUID());
			transHistory.setUIDTo(userInfo.getUID());
			transHistoryDao.save2(transHistory);
			//插入UsersPackage
			usersPackage = new UsersPackage();
			usersPackage.setPkg(transPackage3);
			usersPackage.setUserU(userInfo);
			usersPackageDao.save2(usersPackage);
			return Response.ok(userInfo).header("EntityClass", "R_UserInfo").build();
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	
	/***************************************懵*******************************/  
	@Override
	public void CreateWorkSession(int uid) {
		// TODO Auto-generated method stub		
	}
	
	@Override
	public void RefreshSessionList() {
		// TODO Auto-generated method stub
		
	}
}
