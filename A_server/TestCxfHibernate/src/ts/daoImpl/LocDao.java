package ts.daoImpl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import ts.daoBase.BaseDao;
import ts.model.Location;
import ts.model.Region;

public class LocDao extends BaseDao<Location, String> {
	public LocDao() {
		super(Location.class);
	}

	public List<Location> getTrack(String pkgid) {
		return findBy("pkgid", pkgid, "date", false);
	}
}
