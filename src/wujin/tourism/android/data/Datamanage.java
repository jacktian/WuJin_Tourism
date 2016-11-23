package wujin.tourism.android.data;

import java.util.List;
import net.tsz.afinal.FinalDb;
import android.content.Context;

public class Datamanage {
	FinalDb db;

	public Datamanage(Context context) {
		db = FinalDb.create(context, "wjlyfb.db");
	}

	public <T> List<T> findAll(Class<T> clazz) {
		return db.findAll(clazz);
	}

	public <T> List<T> findAllByWhere(Class<T> clazz, String strWhere) {
		return db.findAllByWhere(clazz, strWhere);
	}

	public <T> T findById(Object id, Class<T> clazz) {
		return db.findById(id, clazz);
	}

	public void deleteAll(Class<?> clazz) {
		db.deleteAll(clazz);
	}

	public void deleteByWhere(Class<?> clazz, String strWhere) {
		db.deleteByWhere(clazz, strWhere);
	}

	public void deleteById(Class<?> clazz, Object id) {
		db.deleteById(clazz, id);
	}

	public void save(Object entity) {
		db.save(entity);
	}

	public void dropDb() {
		// db.deleteDb();
	}
}
