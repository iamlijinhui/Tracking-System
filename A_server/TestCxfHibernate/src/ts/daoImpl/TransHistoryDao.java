package ts.daoImpl;

import java.util.List;

import ts.daoBase.BaseDao;
import ts.model.TransHistory;
import ts.model.TransPackage;

public class TransHistoryDao extends BaseDao<TransHistory,Integer> {
	public TransHistoryDao(){
		super(TransHistory.class);
	}
	
	/**
     * 获得当前包裹的历史
     * @param transPackage 转运包裹
     * @return 最近的一次历史记录
     */
	public TransHistory geTransHistoryByPackage(TransPackage transPackage) {
		List<TransHistory> list = findLike("pkg", transPackage, "SN", false);
		return list.size() > 0 ? list.get(0) :null;
	}
}
