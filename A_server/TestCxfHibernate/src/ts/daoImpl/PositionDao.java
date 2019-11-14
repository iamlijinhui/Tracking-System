package ts.daoImpl;

import ts.daoBase.BaseDao;
import ts.model.Position;
import ts.model.TransNode;

public class PositionDao  extends BaseDao<Position, String> {
	public PositionDao() {
		super(Position.class);
	}
}
