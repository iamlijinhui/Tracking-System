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
	//TransNodeCatalog nodes;	//�Լ����Ļ�����ض����Ȳ�Ҫ��,��Hibernate����Ը�һ�£��Ժ����ȥ
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
	
	/**************************customers���***************************************/
    // �������ֻ�ȡcustomers�б�
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

	// ��ȡ����customer
	@Override
	public List<CustomerInfo> getAllCustomers() {
		return customerInfoDao.getAll();
	}
		
		// �����ֻ���customers�б�
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

	//����id��ȡ
	@Override
	public Response getCustomerInfo(String id) {
		CustomerInfo cstm = customerInfoDao.get(Integer.parseInt(id));
//		try{
//			cstm.setRegionString(regionDao.getRegionNameByID(cstm.getRegionCode()));	//�ⲿ�ֹ��ܷŵ�DAO��ȥ��
//		}catch(Exception e){}
		return Response.ok(cstm).header("EntityClass", "CustomerInfo").build(); 
	}
	
	//����idɾ��customer
	@Override
	public Response deleteCustomerInfo(int id) {
		customerInfoDao.removeById(id);
		return Response.ok("Deleted").header("EntityClass", "D_CustomerInfo").build(); 
	}

	//����customer��Ϣ
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

	//�޸�customer��Ϣ
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
		
	/**************************Transnodeת�˽ڵ������***************************************/
		
	/**
	 * ����code��ȡָ��ת�˽ڵ�
	 */
	@Override
	public TransNode getNode(String code) {
		// TODO Auto-generated method stub
		return transNodeDao.get(code);
	}

	// ��������id�ͽڵ����ͻ�ȡĳ����region��ת�˽ڵ�
	@Override
	public List<TransNode> getNodesList(String regionCode, int type) {
		return transNodeDao.findBy("ID", true, Restrictions.eq("regionCode", regionCode), Restrictions.eq("nodeType", type));
	}

	//�������ֻ�ȡת�˽ڵ�
	
	//��ȡ����ʡ��
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
	
	//����ʡ��code��ȡ����
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

	//���ݳ���code��ȡ��
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

	// ��ȡ�����ַ���
	@Override
	public String getRegionString(String code) {
		return regionDao.getRegionNameByID(code);
	}

	//����id��ȡ����
	@Override
	public Region getRegion(String code) {
		return regionDao.getFullNameRegionByID(code);
	}


	/***************************************user���*******************************/  
	// ʹ��id���е�¼
		@Override
		public Response doLogin(int uid, String pwd) {
			UserInfo userInfo = userInfoDao.login(uid, pwd);
			if (userInfo != null && userInfo.getStatus() != -1) {
				userInfo.setUserToken(
						JwtToken.createJWT(userInfo.getName(), userInfo.getUID() + "", userInfo.getURull() + ""));
				return Response.ok(userInfo).header("EntityClass", "UserInfo").build();
			}
			return Response.ok("�˺Ż�����������������룡").header("EntityClass", "W_UserInfo").build();
//			System.out.println(ErrorMessage.CODE.LOGIN_FAILED);
//			ErrorMessage e = new ErrorMessage(ErrorMessage.CODE.LOGIN_FAILED);
//			System.out.println(ErrorMessage.CODE.LOGIN_FAILED);
//			return Response.ok(e).header("EntityClass", "ErrorMessage").build();
		}

	@Override
	public void doLogOut(int uid) {
		// TODO Auto-generated method stub
		
	}

	// ��ȡ����customer
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
			//�洢r
			//����history����
			TransHistory transHistory = new TransHistory();
			transHistory.setPkg(transPackage1);
			transHistory.setActTime(new Timestamp(System.currentTimeMillis()) );
			transHistory.setUIDFrom(userInfo.getUID());
			transHistory.setUIDTo(userInfo.getUID());
			transHistoryDao.save2(transHistory);
			//����UsersPackage
			UsersPackage usersPackage = new UsersPackage();
			usersPackage.setPkg(transPackage1);
			usersPackage.setUserU(userInfo);
			usersPackageDao.save2(usersPackage);
			//�洢d
			//����history����
			transHistory = new TransHistory();
			transHistory.setPkg(transPackage2);
			transHistory.setActTime(new Timestamp(System.currentTimeMillis()) );
			transHistory.setUIDFrom(userInfo.getUID());
			transHistory.setUIDTo(userInfo.getUID());
			transHistoryDao.save2(transHistory);
			//����UsersPackage
			usersPackage = new UsersPackage();
			usersPackage.setPkg(transPackage2);
			usersPackage.setUserU(userInfo);
			usersPackageDao.save2(usersPackage);
			//�洢t
			//����history����
			transHistory = new TransHistory();
			transHistory.setPkg(transPackage3);
			transHistory.setActTime(new Timestamp(System.currentTimeMillis()) );
			transHistory.setUIDFrom(userInfo.getUID());
			transHistory.setUIDTo(userInfo.getUID());
			transHistoryDao.save2(transHistory);
			//����UsersPackage
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
	
	
	/***************************************��*******************************/  
	@Override
	public void CreateWorkSession(int uid) {
		// TODO Auto-generated method stub		
	}
	
	@Override
	public void RefreshSessionList() {
		// TODO Auto-generated method stub
		
	}
}
