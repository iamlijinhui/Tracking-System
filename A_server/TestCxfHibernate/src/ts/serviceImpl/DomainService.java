package ts.serviceImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.hibernate.criterion.Restrictions;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion.User;

import ts.daoImpl.CustomerInfoDao;
import ts.daoImpl.ExpressSheetDao;
import ts.daoImpl.LocDao;
import ts.daoImpl.PositionDao;
import ts.daoImpl.TransHistoryDao;
import ts.daoImpl.TransNodeDao;
import ts.daoImpl.TransPackageContentDao;
import ts.daoImpl.TransPackageDao;
import ts.daoImpl.UserInfoDao;
import ts.daoImpl.UsersPackageDao;
import ts.model.CustomerInfo;
import ts.model.ErrorMessage;
import ts.model.ExpressSheet;
import ts.model.Location;
import ts.model.MissingExpressSheet;
import ts.model.Path;
import ts.model.Position;
import ts.model.TransHistory;
import ts.model.TransPackage;
import ts.model.TransPackageContent;
import ts.model.UserInfo;
import ts.model.UsersPackage;
import ts.model.ErrorMessage.CODE;
import ts.serviceInterface.IDomainService;

public class DomainService implements IDomainService {
	
	private ExpressSheetDao expressSheetDao;
	private TransPackageDao transPackageDao;
	private TransHistoryDao transHistoryDao;
	private TransPackageContentDao transPackageContentDao;
	private UserInfoDao userInfoDao;
	private TransNodeDao transNodeDao;
	private UsersPackageDao usersPackageDao;
	private LocDao locDao;
	private PositionDao positionDao;
	private CustomerInfoDao customerInfoDao;
	
	public ExpressSheetDao getExpressSheetDao() {
		return expressSheetDao;
	}

	public void setExpressSheetDao(ExpressSheetDao dao) {
		this.expressSheetDao = dao;
	}

	public TransPackageDao getTransPackageDao() {
		return transPackageDao;
	}

	public void setTransPackageDao(TransPackageDao dao) {
		this.transPackageDao = dao;
	}

	public TransHistoryDao getTransHistoryDao() {
		return transHistoryDao;
	}

	public void setTransHistoryDao(TransHistoryDao dao) {
		this.transHistoryDao = dao;
	}

	public TransPackageContentDao getTransPackageContentDao() {
		return transPackageContentDao;
	}

	public void setTransPackageContentDao(TransPackageContentDao dao) {
		this.transPackageContentDao = dao;
	}

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao dao) {
		this.userInfoDao = dao;
	}

	public TransNodeDao getTransNodeDao() {
		return transNodeDao;
	}

	public void setTransNodeDao(TransNodeDao transNodeDao) {
		this.transNodeDao = transNodeDao;
	}

	public UsersPackageDao getUsersPackageDao() {
		return usersPackageDao;
	}

	public void setUsersPackageDao(UsersPackageDao usersPackageDao) {
		this.usersPackageDao = usersPackageDao;
	}

	public LocDao getLocDao() {
		return locDao;
	}

	public void setLocDao(LocDao locDao) {
		this.locDao = locDao;
	}

	public PositionDao getPositionDao() {
		return positionDao;
	}

	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}

	public CustomerInfoDao getCustomerInfoDao() {
		return customerInfoDao;
	}

	public void setCustomerInfoDao(CustomerInfoDao customerInfoDao) {
		this.customerInfoDao = customerInfoDao;
	}

	public Date getCurrentDate() {
		//����һ�����������ʱ��,��Ȼ,SQLʱ���JAVAʱ���ʽ��һ��
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date tm = new Date();
		try {
			tm= sdf.parse(sdf.format(new Date()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return tm;
	}
	
	/******************************* ������� *******************************/
	
//	@Override
//	public List<ExpressSheet> getExpressList(String property,
//			String restrictions, String value) {
//		Criterion cr1;
//		Criterion cr2 = Restrictions.eq("Status", 0);
//
//		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
//		switch(restrictions.toLowerCase()){
//		case "eq":
//			cr1 = Restrictions.eq(property, value);
//			break;
//		case "like":
//			cr1 = Restrictions.like(property, value);
//			break;
//		default:
//			cr1 = Restrictions.like(property, value);
//			break;
//		}
//		list = expressSheetDao.findBy("ID", true,cr1,cr2);		
//		return list;
//	}



	@Override
	public Response getExpressSheet(String id) {
		ExpressSheet es = expressSheetDao.get(id);
		return Response.ok(es).header("EntityClass", "ExpressSheet").build(); 
	}

	// �½��˵�
	@Override
	public Response newExpressSheet(String id, int uid) {
		ExpressSheet es = null;
		try{
			es = expressSheetDao.get(id);
		} catch (Exception e1) {}

		if(es != null){
//			if(es.getStatus() != 0)
//				return Response.ok(es).header("EntityClass", "L_ExpressSheet").build(); //�Ѿ�����,�Ҳ��ܸ���
//			else
				return Response.ok("����˵���Ϣ�Ѿ�����!\n�޷�����!").header("EntityClass", "E_ExpressSheet").build(); //�Ѿ�����
		}
		try{
			String pkgId = userInfoDao.get(uid).getReceivePackageID();
			ExpressSheet nes = new ExpressSheet();
			nes.setID(id);
			nes.setType(0);
			nes.setAccepter(String.valueOf(uid));
			System.out.println(getCurrentDate());
			nes.setAccepteTime(getCurrentDate());
			System.out.println(nes.getAccepteTime());
			nes.setStatus(ExpressSheet.STATUS.STATUS_CREATED);
//			TransPackageContent pkg_add = new TransPackageContent();
//			pkg_add.setPkg(transPackageDao.get(pkgId));
//			pkg_add.setExpress(nes);
//			nes.getTransPackageContent().add(pkg_add);
			expressSheetDao.save(nes);
			System.out.println(expressSheetDao.get(id).getAccepteTime());
			//�ŵ��ռ�������
			MoveExpressIntoPackage(nes.getID(),pkgId);
			return Response.ok(nes).header("EntityClass", "ExpressSheet").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}

	/**
	 * �����˵�
	 */
	@Override
	public Response saveExpressSheet(ExpressSheet obj) {
		try{
			//ExpressSheet nes = expressSheetDao.get(obj.getID());
//			if(obj.getStatus() != ExpressSheet.STATUS.STATUS_CREATED){
//				return Response.ok("����˵��Ѹ���!�޷��������!").header("EntityClass", "E_ExpressSheet").build(); 
//			}
			ExpressSheet expressSheet = new ExpressSheet();
			expressSheet = expressSheetDao.get(obj.getID());
			expressSheet.setAcc1(obj.getAcc1());
			expressSheet.setAcc2(obj.getAcc2());
			expressSheet.setAccepter(obj.getAccepter());
			expressSheet.setDeliver(obj.getDeliver());
			expressSheet.setDeliveTime(obj.getDeliveTime());
			expressSheet.setInsuFee(obj.getInsuFee());
			expressSheet.setPackageFee(obj.getPackageFee());
			expressSheet.setRecever(obj.getRecever());
			expressSheet.setSender(obj.getSender());
			expressSheet.setStatus(obj.getStatus());
			expressSheet.setTranFee(obj.getTranFee());
			expressSheet.setType(obj.getType());
			expressSheet.setWeight(obj.getWeight());
			expressSheetDao.save(expressSheet);			
			return Response.ok(expressSheet).header("EntityClass", "R_ExpressSheet").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	//���¿����Ϣ
	@Override
	public Response updateExpressSheet(ExpressSheet obj) {
		try{
			//ExpressSheet nes = expressSheetDao.get(obj.getID());
			expressSheetDao.update(obj);			
			return Response.ok(obj).header("EntityClass", "R_ExpressSheet").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	/******************************* ��ݲ�ѯ��� *******************************/

	// ��ѯ���
		@Override
		public List<ExpressSheet> getExpressList(String property,
				String restrictions, String value) {
			List<ExpressSheet> list = new ArrayList<ExpressSheet>();
			switch(restrictions.toLowerCase()){
			case "eq":
				list = expressSheetDao.findBy(property, value, "ID", true);
				break;
			case "like":
				list = expressSheetDao.findLike(property, value+"%", "ID", true);
				break;
			}
			return list;
		}
		
		/**
		 * ��������
		 */
		@Override
		public Response transfer(String packageId, int targetUid) throws Exception {
			UserInfo userInfo = userInfoDao.get(targetUid);
			if (userInfo == null) {
				return Response.ok(new ErrorMessage(CODE.USER_NOT_EXISTED)).header("EntityClass", "Message").build();
			}
			TransPackage transPackage = transPackageDao.get(packageId);
			if (transPackage == null) {
				throw new Exception();
			}
			if (transPackage.getStatus() != TransPackage.STATUS.STATUS_TRANSPORT) {
				// ֻ��ת��״̬�İ�����������
				return Response.ok(new ErrorMessage(CODE.TRANS_PACKAGE_STATUE_ERROR)).header("EntityClass", "Message").build();
			}
			try {
	//			if (userInfo.getURull() == UserInfo.URULL.URULL_STAFF) {
	//				List<ExpressSheet> list = expressSheetDao.getListInPackage(packageId);
	//				list.forEach(expressSheet -> {
	//					expressSheet.setStatus(ExpressSheet.STATUS.STATUS_TRANSPORT);
	//					expressSheetDao.update(expressSheet);
	//				});
	//				userInfo.setDelivePackageID(packageId);
	//				userInfoDao.update(userInfo);
	//			} else {
					userInfo.setTransPackageID(packageId);
					userInfoDao.update(userInfo);
	//			}
				//����������������µ�transHistory
				
				TransHistory transHistory = transHistoryDao.findBy("SN", false, Restrictions.eq("pkg", transPackage)).get(0);
				transHistory.setActTime(new Timestamp(System.currentTimeMillis()));
				transHistory.setUIDTo(targetUid);
				transHistoryDao.update(transHistory);
				System.out.println("here #2");
//				//Ϊ�����������һ��transHistory
//				TransHistory transHistory1 = new TransHistory();
//				transHistory1.setPkg(transHistory.getPkg());
//				transHistory1.setActTime(new Timestamp(System.currentTimeMillis()));
//				transHistory1.setUIDFrom(targetUid);
//				transHistory1.setUIDTo(targetUid);
//				transHistoryDao.save2(transHistory1);
				//����usersPackage
				UsersPackage usersPackage = new UsersPackage();
				usersPackage.setPkg(transPackage);
				usersPackage.setUserU(userInfo);
				usersPackageDao.save(usersPackage);
			}catch(Exception e) {
				System.out.print(e.getMessage());
			}
			return Response.ok(new ErrorMessage(CODE.SUCCESS)).header("EntityClass", "S_Message").build();
		}
		
		@Override
		public Response uploadPosition(Position pos) throws Exception {
			if(pos.getX()!=0 || pos.getY()!=0) {
				positionDao.save(pos);
			}
			
			return Response.ok("hello").build();
		}
		
		@Override
		public Response track(String expressId, int uid) throws Exception {
			List<String> list = transPackageContentDao.getPackageID(expressId);
			List<Location> res = new ArrayList<>();
			for (String string : list) {
				List<Location> c = locDao.getTrack(string);
				res.addAll(c);
			}
			return Response.ok(res).header("Collection", "List").build();
		}
		
		@Override
		public Response savePosition(int uid, float x, float y) throws Exception {
			Position position = new Position();
			UserInfo userInfo = userInfoDao.get(uid);
			System.out.println(userInfo);
			String packageId = userInfo.getDelivePackageID();
			if (packageId != null) {
				position.setPackageId(packageId);
				position.setX(x);
				position.setY(y);
				positionDao.save(position);
			}
			
			position = new Position();
			packageId = userInfo.getReceivePackageID();
			if (packageId != null) {
				position.setPackageId(packageId);
				position.setX(x);
				position.setY(y);
				positionDao.save(position);
			}
			
			position = new Position();
			packageId = userInfo.getTransPackageID();
			if (packageId != null) {
				position.setPackageId(packageId);
				position.setX(x);
					position.setY(y);
				positionDao.save(position);
			}
			
			return Response.ok(new ErrorMessage(CODE.SUCCESS)).header("EntityClass", "ErrorMessage").build();
		}
		
		@Override
		public List<Position> getPosition(String expressId) throws Exception {
			List<String> list = transPackageContentDao.getPackageID(expressId);
			System.out.println("test #1");
			List<Position> ans = new ArrayList<>();
			for (String string : list) {
				System.out.println(string);
				List<Position> list2 = positionDao.findLike("packageId", string, "posCode", true);
				ans.addAll(list2);
			}
			System.out.println("test #2");
			Collections.sort(ans, new Comparator<Position>() {   
	            @Override  
	            public int compare(Position o1, Position o2) {  
	            	int tmp1 = o1.getPosCode();
	            	int tmp2 = o2.getPosCode();
	            	if (tmp1 > tmp2) {
	            		return 1;
	            	} else if (tmp1 == tmp2) {
	            		return 0;
	            	} else return -1;
	            } 
	        });  
			return ans;
		}
		
		@Override
	    public List<Path> getPath(String id) throws Exception {
			//��ȡλ��
			List<String> list11 = transPackageContentDao.getPackageID(id);
			System.out.println("test #1");
			List<Position> ans = new ArrayList<>();
			for (String string : list11) {
				System.out.println(string);
				List<Position> list2 = positionDao.findLike("packageId", string, "posCode", true);
				ans.addAll(list2);
			}
			System.out.println("test #2");
			Collections.sort(ans, new Comparator<Position>() {   
	            @Override  
	            public int compare(Position o1, Position o2) {  
	            	int tmp1 = o1.getPosCode();
	            	int tmp2 = o2.getPosCode();
	            	if (tmp1 > tmp2) {
	            		return 1;
	            	} else if (tmp1 == tmp2) {
	            		return 0;
	            	} else return -1;
	            } 
	        });
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String tm = new String();
	        //gson���ܽ����������ʱ��
			tm= sdf.format(new Date());
	        ExpressSheet expressSheet = expressSheetDao.get(id);
	        CustomerInfo receiver = customerInfoDao.get(expressSheet.getRecever().getID());
	        System.out.println("Express" + expressSheet);
	        List<String> list = transPackageContentDao.getPackageID(id);
	        System.out.println(list);
	        Path path=new Path();
	        List<Path> list1 = new ArrayList<>();
	        if (list.size() == 0) {
	            //������˵�id
	            return list1;
	        }
	        if (expressSheet.getStatus() == 1 && list.size() == 1) {
	            //�ո�����
	            path.setStart(expressSheet.getAccepteTime().toString().substring(0, 19));
	            path.setEnd(tm);
	            int uid = Integer.parseInt(expressSheet.getAccepter());
	            UserInfo userInfo = userInfoDao.get(uid);
	            String userInfo1 = userInfo.getUID() +" " + userInfo.getName() + " �绰:" + userInfo.getTelCode();
	            path.setUserInfo(userInfo1);
//	            path.setUid(userInfoDao.getUIDByReceivePackageID(list.get(0)).getUID());
	            path.setNodeName(userInfoDao.getNodeName(uid));
	            path.setStatus(ExpressSheet.STATUS.STATUS_PICKUP);
	            path.setReceiver(receiver);
	            path.setPositons(ans);
	            list1.add(path);
	            return list1;
	        }
	        //���Ա�����հ������ܽ��н���
	        path.setStart(expressSheet.getAccepteTime().toString().substring(0, 19));
	        path.setEnd(transHistoryDao.geTransHistoryByPackage(transPackageDao.get(list.get(0))).getActTime().toString().substring(0, 19));
	        path.setStart(expressSheet.getAccepteTime().toString().substring(0, 19));
            path.setEnd(tm);
            int uid = Integer.parseInt(expressSheet.getAccepter());
            UserInfo userInfo = userInfoDao.get(uid);
            String userInfo1 = userInfo.getUID() +"�� " + userInfo.getName() + " �绰:" + userInfo.getTelCode();
            path.setUserInfo(userInfo1);
//	        path.setUid(transHistoryDao.geTransHistoryByPackage(transPackageDao.get(list.get(0))).getUIDFrom());
	        path.setNodeName(userInfoDao.getNodeName(uid));
	        path.setStatus(ExpressSheet.STATUS.STATUS_PICKUP);
	        path.setReceiver(receiver);
	        path.setPositons(ans);
	        list1.add(path);
	        System.out.println(list1);
	        for(int i = 1;i<list.size()-1;i++){
	            if (list.get(i).charAt(0) == 't') {
	                //ת�˽ڵ㣬����İ���
	                continue;
	            }
	            list1.addAll(transPackageDao.getPackagePath(list.get(i)));
	        }
	        System.out.println(list1);
	        if (expressSheet.getStatus() == ExpressSheet.STATUS.STATUS_DELIVERIED) {//��ǩ��
	            //�ּ�
	        	path = new Path();
	        	Date start = new Date((transHistoryDao.geTransHistoryByPackage(transPackageDao.get(list.get(list.size()-2))).getActTime().getTime()+20000));
	            path.setStart(sdf.format(start));
	            path.setEnd(expressSheet.getDeliveTime().toString().substring(0, 19));
	            uid = transHistoryDao.geTransHistoryByPackage(transPackageDao.get(list.get(list.size()-1))).getUIDFrom();
	            userInfo = userInfoDao.get(uid);
	            userInfo1 = userInfo.getUID() +"�� " + userInfo.getName() + " �绰:" + userInfo.getTelCode();
	            path.setUserInfo(userInfo1);
//	            path.setUid(transHistoryDao.geTransHistoryByPackage(transPackageDao.get(list.get(list.size()-1))).getUIDFrom());
	            path.setNodeName(userInfoDao.getNodeName(uid));
	            path.setStatus(ExpressSheet.STATUS.STATUS_SORTING);
	            list1.add(path);
	            //����
	            path = new Path();
	            start = new Date(start.getTime()+25000);
	            path.setStart(sdf.format(start));
	            path.setEnd(expressSheet.getDeliveTime().toString().substring(0, 19));
	            uid = transHistoryDao.geTransHistoryByPackage(transPackageDao.get(list.get(list.size()-1))).getUIDFrom();
	            userInfo = userInfoDao.get(uid);
	            userInfo1 = userInfo.getUID() +"�� " + userInfo.getName() + " �绰:" + userInfo.getTelCode();
	            path.setUserInfo(userInfo1);
//	            path.setUid(transHistoryDao.geTransHistoryByPackage(transPackageDao.get(list.get(list.size()-1))).getUIDFrom());
	            path.setNodeName(userInfoDao.getNodeName(uid));
	            path.setStatus(ExpressSheet.STATUS.STATUS_DISPATCH);
	            list1.add(path);
	          //��ǩ��
	            path = new Path();
	            path.setStart(transHistoryDao.geTransHistoryByPackage(transPackageDao.get(list.get(list.size()-2))).getActTime().toString()
	            		.substring(0, 19));
	            path.setEnd(expressSheet.getDeliveTime().toString().substring(0, 19));
	            uid = transHistoryDao.geTransHistoryByPackage(transPackageDao.get(list.get(list.size()-1))).getUIDFrom();
	            userInfo = userInfoDao.get(uid);
	            userInfo1 = userInfo.getUID() +"�� " + userInfo.getName() + " �绰:" + userInfo.getTelCode();
	            path.setUserInfo(userInfo1);
//	            path.setUid(transHistoryDao.geTransHistoryByPackage(transPackageDao.get(list.get(list.size()-1))).getUIDFrom());
	            path.setNodeName(userInfoDao.getNodeName(uid));
	            path.setStatus(ExpressSheet.STATUS.STATUS_DELIVERIED);
	            list1.add(path);
	        } else {
	            if (expressSheet.getStatus() == ExpressSheet.STATUS.STATUS_TRANSPORT) {
	                //ת����
	                list1.addAll(transPackageDao.getPackagePath(list.get(list.size()-1)));
//	                list1.get(list1.size()-1).setStatus(ExpressSheet.STATUS.STATUS_TRANSPORT);
	                list1.get(list1.size()-1).setEnd(tm);
	                //��ת�˵�Ŀ�ĵص����path
//	                TransPackage transPackage = transPackageDao.get(list.get(list.size()-1));
//	                List<TransHistory> transHistories = transHistoryDao.findBy("SN", true, Restrictions.eq("pkg", transPackage));
//	                Path path1 = new Path();
//		            path1.setStart(transHistoryDao.geTransHistoryByPackage(transPackageDao.get(list.get(list.size()-1))).getActTime());
//		            path1.setEnd(tm);
//		            path1.setUid(transHistories.get(0).getUIDFrom());
//		            path1.setNodeName(transNodeDao.findByRegionCode(transPackage.getTargetNode()).get(0).getNodeName());
//		            list1.add(path1);
	            } else if(expressSheet.getStatus() == ExpressSheet.STATUS.STATUS_SORTING) {
	            	//�ּ���
	            	if(list.size() > 1) {
	            		list1.addAll(transPackageDao.getPackagePath(list.get(list.size()-1)));
	            	}
		            path = new Path();
		            path.setStart(transHistoryDao.geTransHistoryByPackage(transPackageDao.get(list.get(list.size()-1))).getActTime().toString()
		            		.substring(0, 19));
		            path.setEnd(tm);
		            uid = transHistoryDao.geTransHistoryByPackage(transPackageDao.get(list.get(list.size()-1))).getUIDTo();
		            userInfo = userInfoDao.get(uid);
		            userInfo1 = userInfo.getUID() +"�� " + userInfo.getName() + " �绰:" + userInfo.getTelCode();
		            path.setUserInfo(userInfo1);
//		            path.setUid(transHistoryDao.geTransHistoryByPackage(transPackageDao.get(list.get(list.size()-1))).getUIDFrom());
		            path.setNodeName(userInfoDao.getNodeName(uid));
		            path.setStatus(ExpressSheet.STATUS.STATUS_SORTING);
		            list1.add(path);
	            }else{
	                //������
	                //���͹����в��ܽ���
	            	path = new Path();
	            	Date start = new Date((transHistoryDao.geTransHistoryByPackage(transPackageDao.get(list.get(list.size()-2))).getActTime().getTime()+20000));
		            path.setStart(sdf.format(start));
	                path.setEnd(tm);
	                TransPackage transPackage = transPackageDao.get(list.get(list.size()-1));
	                uid = userInfoDao.getUserByDelivePackageID(transPackage.getID()).getUID();
		            userInfo = userInfoDao.get(uid);
		            userInfo1 = userInfo.getUID() +"�� " + userInfo.getName() + " �绰:" + userInfo.getTelCode();
		            path.setUserInfo(userInfo1);
//	                path.setUid(userInfoDao.getUIDByDelivePackageID(transPackage.getID()).getUID());
	                path.setNodeName(userInfoDao.getNodeName(uid));
	                path.setStatus(ExpressSheet.STATUS.STATUS_SORTING);
		            list1.add(path);
		            //����
	                path = new Path();
	                path.setStart(transHistoryDao.geTransHistoryByPackage(transPackageDao.get(list.get(list.size()-2))).getActTime().toString()
	                		.substring(0, 19));
	                path.setEnd(tm);
	                TransPackage transPackage1 = transPackageDao.get(list.get(list.size()-1));
	                uid = userInfoDao.getUserByDelivePackageID(transPackage1.getID()).getUID();
		            userInfo = userInfoDao.get(uid);
		            userInfo1 = userInfo.getUID() +"�� " + userInfo.getName() + " �绰:" + userInfo.getTelCode();
		            path.setUserInfo(userInfo1);
//	                path.setUid(userInfoDao.getUIDByDelivePackageID(transPackage.getID()).getUID());
	                path.setNodeName(userInfoDao.getNodeName(uid));
	                path.setStatus(ExpressSheet.STATUS.STATUS_DISPATCH);
		            list1.add(path);
	            }	         
	        }
	        return list1;
	    }
		
		/************** ���ת����� *******************/
		
		private TransPackage get(String string) {
			// TODO Auto-generated method stub
			return null;
		}

		/**
	     *  ��ѯ�����еĿ�� ok
	     * @param packageId
	     * @return ����б�
	     */
		@Override
		public List<ExpressSheet> getExpressListInPackage(String packageId){
			List<ExpressSheet> list = new ArrayList<ExpressSheet>();
			list = expressSheetDao.getListInPackage(packageId);
			return list;		
		}
		
		// ��ȡת�˰����б�
		@Override
		public List<TransPackage> getTransPackageList(String property,
				String restrictions, String value) {
			List<TransPackage> list = new ArrayList<TransPackage>();
			switch(restrictions.toLowerCase()){
			case "eq":
				list = transPackageDao.findBy(property, value, "ID", true);
				break;
			case "like":
				list = transPackageDao.findLike(property, value+"%", "ID", true);
				break;
			}
			return list;
		}
		
		// ����id��ȡת�˰���
		@Override
		public Response getTransPackage(String id) {
			TransPackage es = transPackageDao.get(id);
			return Response.ok(es).header("EntityClass", "TransPackage").build(); 
		}
		
		@Override
		public Response newTransPackage(String id, int uid) {
			if (transPackageDao.get(id) != null) {
				return Response.serverError().entity("�˰���ID�ѱ�ʹ��").build();
			}
//			try {
				TransPackage npk = new TransPackage();
				npk.setID(id);
				npk.setStatus(TransPackage.STATUS.STATUS_CREATE);
				npk.setCreateTime(getCurrentDate());
				
				UserInfo userInfo = userInfoDao.get(uid);
				String dpt = userInfo.getDptID();
				npk.setSourceNode(transNodeDao.get(dpt).getRegionCode());   //����user�Ĳ��Ž����ð�����Դ�ص�
				transPackageDao.save2(npk);
				return Response.ok(new ErrorMessage(CODE.SUCCESS)).header("EntityClass", "Message").build();
//			} catch (Exception e) {
//				return Response.serverError().entity(e.getMessage()).build();
//			}
//			try{
//				TransPackage npk = new TransPackage();
//				npk.setID(id);
//				//npk.setStatus(value);
//				npk.setCreateTime(new Date());
//				transPackageDao.save(npk);
//				return Response.ok(npk).header("EntityClass", "TransPackage").build(); 
//			}
//			catch(Exception e)
//			{
//				return Response.serverError().entity(e.getMessage()).build(); 
//			}
		}

		@Override
		public Response saveTransPackage(TransPackage obj) {
			try{
				transPackageDao.save(obj);			
				return Response.ok(obj).header("EntityClass", "R_TransPackage").build(); 
			}
			catch(Exception e)
			{
				return Response.serverError().entity(e.getMessage()).build(); 
			}
		}
				
		@Override
		public Response unpack(String packageId, String expressSheetId, int uid) throws Exception {
			
			TransHistory transHistory;
			TransPackage transPackage = transPackageDao.get(packageId);
			List<ExpressSheet> expressList = expressSheetDao.getListInPackage(packageId);
			int flag = 0;
			for(ExpressSheet express : expressList) {
				if(express.getID().equals(expressSheetId)) {
					flag = 1; break;
				}
			}
			if (transPackage == null) {
				// ����������
//				return Response.ok("����������").header("EntityClass", "N_TransPackage").build();
				return Response.ok(new ErrorMessage(CODE.TRANS_PACKAGE_NOT_EXISTED)).header("EntityClass", "Message").build();
			}
			// ���״̬
			if (transPackage.getStatus() != TransPackage.STATUS.STATUS_TRANSPORT
					&& transPackage.getStatus() != TransPackage.STATUS.STATUS_RECEIVED) {
				return Response.ok(new ErrorMessage(CODE.TRANS_PACKAGE_STATUE_ERROR, ""))
						.header("EntityClass", "Message").build();
			}
			//������������Ŀ��
			if(flag == 0) {
				return Response.ok(new ErrorMessage(CODE.EXPRESSSHEET_NOT_INPACKAGE)).header("EntityClass", "Message").build();
			}
			System.out.println("debugggg  " + packageId);
			List<ExpressSheet> list = expressSheetDao.getListInPackage(packageId);
			
			List<ExpressSheet> listt = new ArrayList<>();
			
			for (ExpressSheet expressSheet1 : list) {
				if(expressSheet1.getStatus() == 1 || expressSheet1.getStatus() == 3) {
					listt.add(expressSheet1);
				}
//				List<String> list2 = transPackageContentDao.getPackageID(expressSheet1.getID());  //��ȡ��������İ���id
//				boolean flag = true;
//				for (String string : list2) {
//					if (transPackageDao.get(string).getCreateTime().after(transPackageDao.get(packageId).getCreateTime())) {
//						flag = false;
//						break;
//					}
//				}
//				if (flag) {
//					listt.add(expressSheet1);
//				}
			} 

			if (list == null) {
				return Response.ok(new ErrorMessage(CODE.TRANS_PACKAGE_NOT_EXISTED)).header("EntityClass", "Message").build();
			}
			if (list.size() == 0) {
				return Response.ok(new ErrorMessage(CODE.TRANS_PACKAGE_IS_EMPTY)).header("EntityClass", "Message").build();
			}

			UserInfo userInfo = userInfoDao.get(uid);
			if (userInfo == null) {
				throw new Exception();
			}
			List<UserInfo> list1 = userInfoDao.findBy("UID", false, Restrictions.eq("dptID", userInfo.getDptID()),
					Restrictions.eq("URull", UserInfo.URULL.URULL_MANAGER));  //��ȡ������Ϣ
			System.out.println("debug   " + list1);
			if (list1.size() != 1) {
				System.out.println("saassdas");
				throw new Exception();
			}
			System.out.println("debug #0");
			
			
//			TransPackageContent transPackageContent = new TransPackageContent();
//			transPackageContent.setPkg(transPackageDao.get(list1.get(0).getTransPackageID()));
			
			
			
			if (packageId.charAt(0) == 'r') { //����ǿ��Ա���յİ���(�������)
				
				ExpressSheet expressSheet = expressSheetDao.get(expressSheetId);
				//�жϿ��״̬�Ƿ���ȷ
				if(expressSheet.getStatus() != 1 && expressSheet.getStatus() != 3) {
					return Response.ok(new ErrorMessage(CODE.EXPRESS_SHEET_CANNOT_UNPACK)).header("EntityClass", "Message").build();
				}
				//���¿����Ϣ
				expressSheet.setStatus(ExpressSheet.STATUS.STATUS_SORTING);
				expressSheetDao.update(expressSheet);
				MoveExpressBetweenPackage(expressSheetId, packageId, list1.get(0).getTransPackageID());  //�������ԭ���İ������뾭�������
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				Date tm = new Date();
				try {
					
					tm = sdf.parse(sdf.format(new Date()));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			
				if (listt.size() == 1) {  //���һ���������Ÿ�����Ϣ
					//����user��Ϣ
					UserInfo userInfo1 = userInfoDao.getUIDByReceivePackageID(packageId);
					//Ϊ���Ա����һ���µİ���
					TransPackage transPackage1 = new TransPackage();
					transPackage1.setID("r" + (new Date()).getTime());
					transPackage1.setCreateTime(tm);
					transPackage1.setSourceNode(userInfo1.getDptID());
					transPackage1.setStatus(TransPackage.STATUS.STATUS_RECEIVED);
					transPackageDao.save2(transPackage1);
	
					//����history����
					transHistory = new TransHistory();
					transHistory.setPkg(transPackage1);
					transHistory.setActTime(new Timestamp(System.currentTimeMillis()) );
					transHistory.setUIDFrom(uid);
					transHistory.setUIDTo(uid);
					transHistoryDao.save2(transHistory);
					//����UsersPackage
					UsersPackage usersPackage = new UsersPackage();
					usersPackage.setPkg(transPackage1);
					usersPackage.setUserU(userInfo1);
					usersPackageDao.save(usersPackage);
					//����user��Ϣ
					userInfo1.setReceivePackageID(transPackage1.getID());
					userInfoDao.update(userInfo1);
				}
				

//				transPackageContent.setExpress(expressSheet);
//				transPackageContentDao.save(transPackageContent);
				System.out.println("debug  #1");
			} else {
				ExpressSheet expressSheet = expressSheetDao.get(expressSheetId);
				if(expressSheet.getStatus() != 1 && expressSheet.getStatus() != 3) {
					return Response.ok(new ErrorMessage(CODE.EXPRESS_SHEET_CANNOT_UNPACK)).header("EntityClass", "Message").build();
				}
				//���¿����Ϣ
				expressSheet.setStatus(ExpressSheet.STATUS.STATUS_SORTING);
				expressSheetDao.update(expressSheet);
				MoveExpressBetweenPackage(expressSheetId, packageId, list1.get(0).getTransPackageID());  //�������ԭ���İ������뾭�������
//				transPackageContent.setExpress(expressSheet);
//				transPackageContentDao.save(transPackageContent);
				System.out.println("debug #2");			
			}
			if (listt.size() == 1) {
				try {
					//����UsersPackage
					UsersPackage usersPackage = new UsersPackage();
					usersPackage.setPkg(transPackage);
					usersPackage.setUserU(userInfo);
					usersPackageDao.save(usersPackage);
					//�޸İ���״̬
					transPackage.setStatus(TransPackage.STATUS.STATUS_HISTORY);
					transPackageDao.update(transPackage);
					transHistory = transHistoryDao
							.findBy("SN", false, Restrictions.eq("pkg", transPackage), Restrictions.isNotNull("actTime"))
							.get(0);
					transHistory.setActTime(new Timestamp(System.currentTimeMillis()));
					int uid2 = transHistory.getUIDFrom();
					transHistory.setUIDTo(uid);
					transHistoryDao.update(transHistory);
					//�½�һ��history
//					transHistory = new TransHistory();
//					transHistory.setPkg(transPackage);
//					transHistory.setActTime(new Timestamp(System.currentTimeMillis()) );
//					transHistory.setUIDFrom(uid2);
//					transHistory.setUIDTo(uid);
//					transHistoryDao.save2(transHistory);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception();
				}
			}	
						
			System.out.println("yes");
			return Response.ok(new ErrorMessage(CODE.SUCCESS)).header("EntityClass", "Message").build();
		}
		
		@Override
		public Response expressSheetMiss(String packageId, String expressSheetId) {
			ExpressSheet expressSheet = new ExpressSheet();
			expressSheet = expressSheetDao.get(expressSheetId);
			expressSheet.setStatus(ExpressSheet.STATUS.STATUS_MISSING);
			MoveExpressFromPackage(expressSheetId, packageId);
			expressSheetDao.update(expressSheet);
			return Response.ok(new ErrorMessage(CODE.SUCCESS)).header("EntityClass", "Message").build();
		}
		
		@Override
		public List<MissingExpressSheet> getMissExpressSheet() {
			List<ExpressSheet> list = new ArrayList<ExpressSheet>();
			list = expressSheetDao.getMissExpressSheet();
			List<MissingExpressSheet> list1 = new ArrayList<MissingExpressSheet>();
			for(ExpressSheet expressSheet : list) {
				MissingExpressSheet missingExpressSheet = new MissingExpressSheet();
				List<String> packageList = transPackageContentDao.getPackageID(expressSheet.getID());
				String packageId = packageList.get(packageList.size()-1);
				TransHistory transHistory = new TransHistory();
				TransPackage transPackage = transPackageDao.get(packageId);
				transHistory = transHistoryDao.findBy("pkg", transPackage, "SN", false).get(0);
				int id = transHistory.getUIDFrom();
				UserInfo userInfo = new UserInfo();
				userInfo = userInfoDao.get(id);
				missingExpressSheet.setExpressSheet(expressSheet);
				missingExpressSheet.setUserInfo(userInfo);
				list1.add(missingExpressSheet);	
			}
			System.out.println("success");
			return list1;			
		}
		
		@Override
		public Response pack(int uid, String packageId, String expressSheetId) throws Exception {
			UserInfo userInfo = userInfoDao.get(uid);
			if (userInfo == null) {
				return Response.ok(new ErrorMessage(CODE.USER_NOT_EXISTED)).header("EntityClass", "Message").build();
			}

			// �����Ƿ�Ϸ�
			TransPackage transPackage = transPackageDao.get(packageId);
			if (transPackage == null) {
				return Response.ok(new ErrorMessage(CODE.TRANS_PACKAGE_NOT_EXISTED)).header("EntityClass", "Message").build();
			}
			if (transPackage.getStatus() != TransPackage.STATUS.STATUS_CREATE
					&& transPackage.getStatus() != TransPackage.STATUS.STATUS_PACKING) {
				return Response.ok(new ErrorMessage(CODE.TRANS_PACKAGE_STATUE_ERROR, ""))
						.header("EntityClass", "PACK_Finished").build();
			}
			// ����Ƿ�Ϸ�
			ExpressSheet expressSheet = expressSheetDao.get(expressSheetId);
			// ���İ�����Ϣ
			if (transPackage.getStatus() == TransPackage.STATUS.STATUS_CREATE) {
				transPackage.setStatus(TransPackage.STATUS.STATUS_PACKING);
				transPackageDao.save(transPackage);
			}
			
			List<UserInfo> list1 = userInfoDao.findBy("UID", false, Restrictions.eq("dptID", userInfo.getDptID()),
					Restrictions.eq("URull", UserInfo.URULL.URULL_MANAGER));  //��ȡ������Ϣ
			System.out.println("debug   " + list1);
			if (list1.size() != 1) {
				System.out.println("saassdas");
				throw new Exception();
			}
			MoveExpressBetweenPackage(expressSheetId, list1.get(0).getTransPackageID(), packageId);
			if (packageId.charAt(0) == 'd') {
				//��������Ͱ��������¿����Ϣ ���������
				expressSheet.setStatus(ExpressSheet.STATUS.STATUS_DISPATCH);
				expressSheetDao.update(expressSheet);
			}
			if (expressSheet.getStatus() == ExpressSheet.STATUS.STATUS_SORTING) {
				// �ּ� -- �� ת��
				expressSheet.setStatus(ExpressSheet.STATUS.STATUS_TRANSPORT);
				expressSheetDao.update(expressSheet);
			}
			return Response.ok(new ErrorMessage(CODE.SUCCESS)).header("EntityClass", "Message").build();			
		}
		
		
		public Response finishPack(int uid, String packageId) {
			UserInfo userInfo = userInfoDao.get(uid);
			if (userInfo == null) {
				return Response.ok(new ErrorMessage(CODE.USER_NOT_EXISTED)).header("EntityClass", "Message").build();
			}
			TransPackage transPackage = transPackageDao.get(packageId);
			if (transPackage == null) {
				// ����������
				return Response.ok(new ErrorMessage(CODE.TRANS_PACKAGE_NOT_EXISTED)).header("EntityClass", "Message").build();
			}
			// ���״̬
			if (transPackage.getStatus() != TransPackage.STATUS.STATUS_PACKING) {
				return Response.ok(new ErrorMessage(CODE.TRANS_PACKAGE_STATUE_ERROR, "����״̬����"))
						.header("EntityClass", "Message").build();
			}
			//�½�transhistory��������û��Ͱ������������ʷ
			TransHistory transHistory = new TransHistory();
			transHistory.setPkg(transPackage);
			transHistory.setActTime(new Timestamp(System.currentTimeMillis()));
			transHistory.setUIDFrom(uid);
			transHistory.setUIDTo(uid);
			transHistoryDao.save2(transHistory);
			TransHistory transHistory1 = new TransHistory();
			transHistory1.setPkg(transPackage);
			transHistory1.setActTime(new Timestamp(System.currentTimeMillis()));
			transHistory1.setUIDFrom(uid);
			transHistory1.setUIDTo(uid);
			transHistoryDao.save2(transHistory1);
			//�½�usersPackage�����븺������������û�
			UsersPackage usersPackage = new UsersPackage();  
			usersPackage.setUserU(userInfo);
			usersPackage.setPkg(transPackage);
			usersPackageDao.save2(usersPackage);
			transPackage.setStatus(TransPackage.STATUS.STATUS_TRANSPORT);
			transPackageDao.save(transPackage);
			return Response.ok(new ErrorMessage(CODE.SUCCESS)).header("EntityClass", "Message").build();		
		}
		/************************ ����յ����� *****************************/
		
		/**
		 *  �ַ�
		 */
		@Override
		public Response DispatchExpressSheet(String id, int uid) {
			ExpressSheet expressSheet = expressSheetDao.get(id);
	        if (expressSheet == null) {
	            return Response.ok(new ErrorMessage(CODE.EXPRESS_SHEET_NOT_EXISTED)).header("EntityClass", "Message").build();
	        }
	        if (expressSheet.getStatus() != ExpressSheet.STATUS.STATUS_SORTING) {
	            //ֻ�зּ�״̬��������
	            return Response.ok(new ErrorMessage(ts.model.ErrorMessage.CODE.EXPRESS_SHEET_CANNOT_DISPATCH)).header("EntityClass", "Message").build();
	        }
	        // ����״̬Ϊ������
	        expressSheet.setStatus(ExpressSheet.STATUS.STATUS_DISPATCH);
	        expressSheet.setDeliver(String.valueOf(uid));
	        UserInfo userInfo  = userInfoDao.get(uid);
	        List<UserInfo> list1 = userInfoDao.findBy("UID", false, Restrictions.eq("dptID", userInfo.getDptID()),
					Restrictions.eq("URull", UserInfo.URULL.URULL_MANAGER));  //��ȡ������Ϣ
			System.out.println("debug   " + list1);
			if (list1.size() != 1) {
				System.out.println("saassdas");
			}
			MoveExpressBetweenPackage(id, list1.get(0).getTransPackageID(), userInfo.getDelivePackageID());
//	        TransPackageContent transPackageContent = new TransPackageContent();
//	        transPackageContent.setPkg(transPackageDao.get(userInfo.getDelivePackageID()));
//	        transPackageContent.setExpress(expressSheetDao.get(id));
//	        transPackageContentDao.save(transPackageContent);
	        expressSheetDao.update(expressSheet);
	        return Response.ok(expressSheet).header("EntityClass", "ExpressSheet").build();
		}
		
	@Override
	public Response ReceiveExpressSheetId(String id, int uid) {
		try {
			// ��ȡ�˵��ĳ־û�����
			ExpressSheet nes = expressSheetDao.get(id);
			if (nes == null || nes.getStatus() != ExpressSheet.STATUS.STATUS_CREATED) {
				return Response.ok("����ʧ��!").header("EntityClass", "E_ExpressSheet").build();
			}
			
			// �˵���Ϣ������
//			if (!nes.complete()) {
//				return Response.ok("��Ϣ������").build();
//			}
			
			// ����Ա��Ϣ
			UserInfo userInfo = userInfoDao.get(uid);
			if (userInfo == null) {
				return Response.ok("����ʧ�ܣ�").header("EntityClass", "ExceptionMessage").build();
			}
//			TransPackage transPackage = transPackageDao.get(userInfo.getReceivePackageID());
//			transPackage.setStatus(TransPackage.STATUS.STATUS_RECEIVED);
//			transPackageDao.save(transPackage);
			nes.setAccepter(String.valueOf(uid));
			nes.setAccepteTime(getCurrentDate());
			nes.setStatus(ExpressSheet.STATUS.STATUS_PICKUP);
			expressSheetDao.save(nes);
			
			return Response.ok(nes).header("EntityClass", "ExpressSheet").build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
//		try{
//			ExpressSheet nes = expressSheetDao.get(id);
//			if(nes.getStatus() != ExpressSheet.STATUS.STATUS_CREATED){
//				return Response.ok("����˵�״̬����!�޷��ռ�!").header("EntityClass", "E_ExpressSheet").build(); 
//			}
//			nes.setAccepter(String.valueOf(uid));
//			nes.setAccepteTime(getCurrentDate());
//			nes.setStatus(ExpressSheet.STATUS.STATUS_TRANSPORT);
//			expressSheetDao.save(nes);
//			return Response.ok(nes).header("EntityClass", "ExpressSheet").build(); 
//		}
//		catch(Exception e)
//		{
//			return Response.serverError().entity(e.getMessage()).build(); 
//		}
	}

	@Override
	public Response DeliveryExpressSheetId(String id, int uid) {
		try{
			String pkgId = userInfoDao.get(uid).getDelivePackageID();
			ExpressSheet nes = expressSheetDao.get(id);
			if (nes == null || nes.getStatus() != ExpressSheet.STATUS.STATUS_DISPATCH) {
				return Response.ok("ֻ������״̬����ǩ��").header("EntityClass", "E_ExpressSheet").build();
			}
//			if(transPackageContentDao.getSn(id, pkgId) == 0){
//				//��ʱ��һ������ʽ,��·�˰����Ĵ��ݹ���,�Լ��Ļ�������һ��
//				MoveExpressBetweenPackage(id, userInfoDao.get(uid).getReceivePackageID(),pkgId);
//				return Response.ok("����˵�״̬����!\n�����Ϣû�������ɼ�������!").header("EntityClass", "E_ExpressSheet").build(); 
//			}
			nes.setDeliver(String.valueOf(uid));
			nes.setDeliveTime(getCurrentDate());
			nes.setStatus(ExpressSheet.STATUS.STATUS_DELIVERIED);
			expressSheetDao.save(nes);
			//���ɼ�������ɾ��
			MoveExpressFromPackage(nes.getID(),pkgId);
			//���û����ʷ��¼,���Ѹ����ռ��ͽ����ļ�¼
			return Response.ok(nes).header("EntityClass", "ExpressSheet").build(); 
		}
		catch(Exception e)
		{
			return Response.serverError().entity(e.getMessage()).build(); 
		}
	}
	
	public Response finishDelivery(String packageId, int uid) {
		List<ExpressSheet> list = new ArrayList<ExpressSheet>();
		list = expressSheetDao.getListInPackage(packageId);
		if(list.size()!=0) {
			return Response.ok(new ErrorMessage(CODE.DELIVERY_NOT_FINSH)).header("EntityClass", "Message").build();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date tm = new Date();
		try {
			
			tm = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		//����user��Ϣ
		UserInfo userInfo = userInfoDao.get(uid);
		//Ϊ���Ա����һ���µİ���
		TransPackage transPackage = new TransPackage();
		transPackage.setID("d" + (new Date()).getTime());
		transPackage.setCreateTime(tm);
		transPackage.setSourceNode(userInfo.getDptID());
		transPackage.setStatus(TransPackage.STATUS.STATUS_DELIVERIED);
		transPackageDao.save2(transPackage);

		//����history����
		TransHistory transHistory = new TransHistory();
		transHistory = new TransHistory();
		transHistory.setPkg(transPackage);
		transHistory.setActTime(new Timestamp(System.currentTimeMillis()) );
		transHistory.setUIDFrom(uid);
		transHistory.setUIDTo(uid);
		transHistoryDao.save2(transHistory);
		//����UsersPackage
		UsersPackage usersPackage = new UsersPackage();
		usersPackage.setPkg(transPackage);
		usersPackage.setUserU(userInfo);
		usersPackageDao.save(usersPackage);
		//����user��Ϣ
		userInfo.setDelivePackageID(transPackage.getID());
		userInfoDao.update(userInfo);
		return Response.ok(new ErrorMessage(CODE.SUCCESS)).header("EntityClass", "Message").build();
	}
	
	@Override	
	public Response getUserPackages(@PathParam("id")int id) {
		List<UsersPackage> list = usersPackageDao.getUserPackages(id);
		return Response.ok(list).header("EntityClass", "UsersPackage").build(); 
	}
	
	/**
	 * ���˵��ŵ�������ȥ
	 * @param id
	 * @param targetPkgId
	 * @return
	 */
	public boolean MoveExpressIntoPackage(String id, String targetPkgId) {
		TransPackage targetPkg = transPackageDao.get(targetPkgId);
//		if((targetPkg.getStatus() > 0) && (targetPkg.getStatus() < 3)){	//������״̬��㶨��,�򿪵İ������߻������ܲ���==================================================================
//			return false;
//		}

		TransPackageContent pkg_add = new TransPackageContent();
		pkg_add.setPkg(targetPkg);
		pkg_add.setExpress(expressSheetDao.get(id));
		pkg_add.setStatus(TransPackageContent.STATUS.STATUS_ACTIVE);
		transPackageContentDao.save(pkg_add); 
		return true;
	}

	public boolean MoveExpressFromPackage(String id, String sourcePkgId) {
		TransPackage sourcePkg = transPackageDao.get(sourcePkgId);
//		if((sourcePkg.getStatus() > 0) && (sourcePkg.getStatus() < 3)){
//			return false;
//		}

		TransPackageContent pkg_add = transPackageContentDao.get(id, sourcePkgId);
		pkg_add.setStatus(TransPackageContent.STATUS.STATUS_OUTOF_PACKAGE);
		transPackageContentDao.save(pkg_add); 
		return true;
	}

	public boolean MoveExpressBetweenPackage(String id, String sourcePkgId, String targetPkgId) {
		//��Ҫ�����������
		MoveExpressFromPackage(id,sourcePkgId);
		MoveExpressIntoPackage(id,targetPkgId);
		return true;
	}
}
