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
     * ��õ�ǰ��������ʷ
     * @param transPackage ת�˰���
     * @return �����һ����ʷ��¼
     */
	public TransHistory geTransHistoryByPackage(TransPackage transPackage) {
		List<TransHistory> list = findLike("pkg", transPackage, "SN", false);
		return list.size() > 0 ? list.get(0) :null;
	}
}
