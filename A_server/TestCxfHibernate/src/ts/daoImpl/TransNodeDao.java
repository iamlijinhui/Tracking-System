package ts.daoImpl;

import java.util.List;

import org.springframework.util.Assert;

import ts.daoBase.BaseDao;
import ts.model.TransNode;

public class TransNodeDao extends BaseDao<TransNode, String>{
	public TransNodeDao(){
		super(TransNode.class);
	}

	/**
     * 根据区域码获得该地的转运节点
     * @param region_code 区域码
     * @return 转运节点
     */
	public List<TransNode> findByRegionCode(String region_code) {
        Assert.hasText(region_code);
        return findBy("regionCode", region_code, "nodeName", true);
	}
	
//	public String getNodeName(String id) {
//		TransNode transNode = this.findByRegionCode(id).get(0);
//		return transNode.getNodeName();
//	}

}
