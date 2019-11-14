package ts.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.TransHistory;
import ts.model.TransPackage;
import ts.model.UserInfo;


public class UserInfoDao extends BaseDao<UserInfo, Integer> {
	public UserInfoDao(){
		super(UserInfo.class);
	}
	private TransHistoryDao transHistoryDao;
	private TransPackageDao transPackageDao;
	private TransNodeDao transNodeDao;
	private RegionDao regionDao;
	
	public TransHistoryDao getTransHistoryDao() {
		return transHistoryDao;
	}

	public void setTransHistoryDao(TransHistoryDao transHistoryDao) {
		this.transHistoryDao = transHistoryDao;
	}

	public TransPackageDao getTransPackageDao() {
		return transPackageDao;
	}

	public void setTransPackageDao(TransPackageDao transPackageDao) {
		this.transPackageDao = transPackageDao;
	}

	public TransNodeDao getTransNodeDao() {
		return transNodeDao;
	}

	public void setTransNodeDao(TransNodeDao transNodeDao) {
		this.transNodeDao = transNodeDao;
	}

	public RegionDao getRegionDao() {
		return regionDao;
	}

	public void setRegionDao(RegionDao regionDao) {
		this.regionDao = regionDao;
	}

	// �û���¼
	public UserInfo login(int id, String passwd) {
		List<UserInfo> users = findBy("PWD", passwd, "UID", true);
			//List<UserInfo> users = findBy("UID", true, Restrictions.eq("PWD", passwd));
		for (UserInfo userInfo : users) {
			if (userInfo.getUID() == id) return userInfo;
		}
		return null;
	}
	
	// �������հ���ID��ѯ�û�
    public UserInfo getUIDByReceivePackageID(String packageID) {
        List<UserInfo> userInfos = findBy("receivePackageID", packageID, "UID", true);
        return userInfos.size()>0 ? userInfos.get(0) : null;
    }
 	
 	// ���ֻ��ŵ�½
 	public UserInfo loginByTel(String telCode, String passwd) {
 		List<UserInfo> users = findBy("telCode", true, Restrictions.eq("PWD", passwd));
 		for (UserInfo userInfo : users) {
 			if (userInfo.getTelCode().equals(telCode)) return userInfo;
 		}
 		return null;
 	}
 	
 	// ����ת�˰���
 	public List<TransPackage> getTransportTask(int id) {
 		List<TransHistory> list = transHistoryDao.findBy("SN", true, Restrictions.eq("UIDFrom", id), Restrictions.eq("UIDTo", 0));
 		List<TransPackage> list2 = new ArrayList<>();
 	//	System.out.println(list);
 		for (TransHistory transHistory : list) {
 			list2.add(transHistory.getPkg());
 		}
 	//	System.out.println(list2);
 		return list2;
 	}
 	

     // ����ת�˰���ID�����û�
     public UserInfo getUserByDelivePackageID(String packageID) {
         List<UserInfo> userInfos = findBy("delivePackageID", packageID, "UID", true);
         return userInfos.size()>0 ? userInfos.get(0) : null;
     }

     // ���ݰ���id�������һ�εľ�����
     public int getUIDByTransPackageID(String packageID) {
         TransPackage transPackage = transPackageDao.get(packageID);
         List<TransHistory> list = transHistoryDao.findBy("SN", false, Restrictions.eq("packeg", transPackage));
         if (list.size() == 0) {
             return -1;
         }
         return list.get(0).getUIDFrom();
     }
     
     // ��ȡ�û����ڽڵ�����
     public String getNodeName(int uid){
    	 String regionCode = transNodeDao.get(get(uid).getDptID()).getRegionCode();
    	 String regionName = regionDao.getRegionNameByID(regionCode);
    	 String nodeName = regionName + "����";
         return nodeName;
     }	
		
}
