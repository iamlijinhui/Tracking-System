package ts.daoImpl;

import java.util.List;

import ts.daoBase.BaseDao;
import ts.model.CustomerInfo;

public class CustomerInfoDao extends BaseDao<CustomerInfo, Integer>{
	private RegionDao regionDao;
	public RegionDao getRegionDao() {
		return regionDao;
	}

	public void setRegionDao(RegionDao dao) {
		this.regionDao = dao;
	}
	
	public CustomerInfoDao(){
		super(CustomerInfo.class);
	}
	
	public CustomerInfo get(int id) {
		CustomerInfo ci = super.get(id);
		ci.setRegionString(regionDao.getRegionNameByID(ci.getRegionCode()));	//获取区域的名字字符串
		return ci;
	}

	@Override
	public List<CustomerInfo> getAll() {
		List<CustomerInfo> list = super.getAll();
		for (CustomerInfo customerInfo : list) {
			if (customerInfo.getRegionCode() != null)
			customerInfo.setRegionString(regionDao.getRegionNameByID(customerInfo.getRegionCode()));
			else 
			customerInfo.setRegionString("用户未填写");
		}
		return list;
	}
	
	public List<CustomerInfo> findByName(String name) {
		return findLike("name", name+"%", "telCode", true);
	}

	public List<CustomerInfo> findByTelCode(String telCode) {
		return findBy("telCode", telCode, "telCode", true);
	}
}
