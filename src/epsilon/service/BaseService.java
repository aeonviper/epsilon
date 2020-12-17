package epsilon.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import common.BeanUtility;
import epsilon.core.Utility;
import epsilon.model.BaseModel;
import omega.annotation.Transactional;
import omega.service.GenericService;

public class BaseService extends GenericService {

	@Transactional
	public <T> int insert(String tableName, String sequenceName, T entity, String... array) {
		String sql = "insert into " + tableName + " (id," + Utility.join(array, ",") + ") values ((select next value for " + sequenceName + ")," + Utility.repeat("?", array.length, ",") + ")";
		List parameterList = new ArrayList<>();
		try {
			for (String entry : array) {
				parameterList.add(BeanUtility.instance().getPropertyUtils().getProperty(entity, entry));
			}
			return write(sql, parameterList.toArray());
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Transactional
	public Long sequence(String name) {
		// return select(Long.class, "select nextval('" + name + "')");
		return select(Long.class, "select next value for " + name + "");
	}

	public static void toDecorate(BaseModel entity) {
		if (entity == null) {
			return;
		}
		if (entity.getMap() != null) {
			entity.setMapData(Utility.gson.toJson(entity.getMap()));
		}
	}

	public static void fromDecorate(BaseModel entity) {
		if (entity == null) {
			return;
		}
		if (Utility.isNotBlank(entity.getMapData())) {
			entity.setMap(Utility.gson.fromJson(entity.getMapData(), Utility.typeMapOfStringObject));
		}
	}

	public <T extends BaseModel> List<T> apply(Function<T, T> function, List<T> list) {
		if (list != null) {
			for (T entry : list) {
				function.apply(entry);
			}
		}
		return list;
	}

	public <T extends BaseModel> List<T> sanitise(List<T> list) {
		if (list != null) {
			for (BaseModel entry : list) {
				sanitise(entry);
			}
		}
		return list;
	}

	public <T extends BaseModel> T sanitise(T entity) {
		if (entity != null) {
			entity.setCreatorId(null);
			entity.setCreator(null);
			entity.setCreated(null);
			entity.setEditorId(null);
			entity.setEditor(null);
			entity.setEdited(null);
			entity.setMapData(null);
		}
		return entity;
	}

}
