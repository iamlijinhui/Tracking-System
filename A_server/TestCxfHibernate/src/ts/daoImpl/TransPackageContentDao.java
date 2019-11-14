package ts.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.ExpressSheet;
import ts.model.TransPackageContent;

public class TransPackageContentDao extends BaseDao<TransPackageContent,Integer> {
private ExpressSheetDao expressSheetDao;
	
	public ExpressSheetDao getExpressSheetDao() {
		return expressSheetDao;
	}

	public void setExpressSheetDao(ExpressSheetDao expressSheetDao) {
		this.expressSheetDao = expressSheetDao;
	}
	
	public TransPackageContentDao(){
		super(TransPackageContent.class);
	}
	
	public TransPackageContent get(String expressId, String packageId){
		List<TransPackageContent> list  = new ArrayList<TransPackageContent>();
		list = super.findBy("SN", true, 
				Restrictions.sqlRestriction("ExpressID = '"+ expressId + "' and PackageID = '" + packageId +"'"));
		if(list.size() == 0)
			return null;
		return list.get(0);
	}

	public int getSn(String expressId, String packageId){
		TransPackageContent cn = get(expressId,packageId);
		if(cn == null){
			return 0;
		}
		return get(expressId,packageId).getSN();
	}

	public void delete(String expressId, String packageId){
		List<TransPackageContent> list  = new ArrayList<TransPackageContent>();
		list = super.findBy("SN", true, 
				Restrictions.eq("ExpressID", expressId),
				Restrictions.eq("PackageID",packageId));
		for(TransPackageContent pc : list)
			super.remove(pc);
		return ;
	}
	
	/**
     * 获取这个快件待过的包裹
     * @param expressSheetID 快件id
     * @return 包裹id
     */
    public List<String> getPackageID(String expressSheetID){
    	System.out.println("hello");
    	List<String> list1 = new ArrayList<>();
    	try {
    		ExpressSheet express = expressSheetDao.get(expressSheetID);
        List<TransPackageContent> list = super.findBy("SN", true, 
				Restrictions.eq("express", express));
        for(TransPackageContent item:list){
            //过滤掉在转运节点的情况
            if (item.getPkg().getID().charAt(0) != 't') {
                list1.add(item.getPkg().getID());
            }
        }
    	}catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} 
   //     System.out.println("debugggg " + list1);
        return list1;
    }
}
