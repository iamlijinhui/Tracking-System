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
     * �����������øõص�ת�˽ڵ�
     * @param region_code ������
     * @return ת�˽ڵ�
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
