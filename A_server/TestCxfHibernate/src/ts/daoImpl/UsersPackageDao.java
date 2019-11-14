package ts.daoImpl;

import java.util.List;

import ts.daoBase.BaseDao;
import ts.model.UserInfo;
import ts.model.UsersPackage;

public class UsersPackageDao extends BaseDao<UsersPackage,Integer> {
	
	private UserInfoDao userInfoDao;
	public UsersPackageDao(){
		super(UsersPackage.class);
	}
	
	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public List<UsersPackage> getUserPackages(int id) {
		UserInfo userInfo = userInfoDao.get(id);
		return findBy("userU", userInfo, "SN", true);
	}
}
