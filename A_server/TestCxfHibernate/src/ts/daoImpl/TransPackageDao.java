package ts.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.ExpressSheet;
import ts.model.Path;
import ts.model.TransHistory;
import ts.model.TransPackage;
import ts.model.UserInfo;

public class TransPackageDao extends BaseDao<TransPackage,String> {
	public TransHistoryDao transHistoryDao;
    public UserInfoDao userInfoDao;
    private RegionDao regionDao;

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

    public void setUserInfoDao(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    public TransHistoryDao getTransHistoryDao() {
        return transHistoryDao;
    }

    public void setTransHistoryDao(TransHistoryDao transHistoryDao) {
        this.transHistoryDao = transHistoryDao;
    }

	public RegionDao getRegionDao() {
		return regionDao;
	}

	public void setRegionDao(RegionDao regionDao) {
		this.regionDao = regionDao;
	}

	public TransPackageDao(){
		super(TransPackage.class);
	}
	
	public List<Path> getPackagePath(String packageId) {
        List<Path> res = new ArrayList<>();
        TransPackage transPackage = get(packageId);
        List<TransHistory> transHistories = transHistoryDao.findBy("SN", true, Restrictions.eq("pkg", transPackage));
   //     System.out.println(transHistories);
        if (transHistories.size() == 0) {
            //异常
            return res;
        }
        Path path = new Path();
   //     System.out.println(transPackage.getCreateTime());
        path.setStart(transHistories.get(0).getActTime().toString().substring(0, 19));
   //     System.out.println(transHistories.get(0).getActTime());
        path.setEnd(transHistories.get(0).getActTime().toString().substring(0, 19));
   //     System.out.println(transHistories.get(0).getUIDFrom());
        int uid = transHistories.get(0).getUIDTo();
        UserInfo userInfo = userInfoDao.get(uid);
        String userInfo1 = userInfo.getUID() +"号 " + userInfo.getName() + " 电话:" + userInfo.getTelCode();
        path.setUserInfo(userInfo1);
//        path.setUid(transHistories.get(0).getUIDTo());
   //     System.out.println(userInfoDao.getNodeName(path.getUid()));
        path.setNodeName(userInfoDao.getNodeName(uid));
        path.setStatus(ExpressSheet.STATUS.STATUS_SORTING);
        res.add(path);
   //     System.out.println("dddddddddddddd" + res);

        for (int j = 1; j < transHistories.size(); j++) {
            path = new Path();
            uid = transHistories.get(j).getUIDFrom();
            userInfo = userInfoDao.get(uid);
            userInfo1 = userInfo.getUID() +"号 " + userInfo.getName() + " 电话:" + userInfo.getTelCode();
            path.setUserInfo(userInfo1);
//            path.setUid(transHistories.get(j).getUIDFrom());
            path.setStart(transHistories.get(j).getActTime().toString().substring(0, 19));
            path.setEnd(transHistories.get(j).getActTime().toString().substring(0, 19));
            path.setNodeName(userInfoDao.getNodeName(uid));
            path.setStatus(ExpressSheet.STATUS.STATUS_TRANSPORT);
            String nextNodeName = regionDao.getRegionNameByID(transPackage.getTargetNode());
            nextNodeName = nextNodeName + "网点";
            path.setNextNodeName(nextNodeName);
            res.add(path);
        }
    //    System.out.println("deeeeeeeeeeeeeeee" + res);
        return res;
    }
}
