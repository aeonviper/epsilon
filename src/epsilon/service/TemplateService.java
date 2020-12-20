package epsilon.service;

import java.util.List;

import epsilon.model.Template;
import omega.annotation.Transactional;

public class TemplateService extends BaseService {

	@Transactional
	public List<Template> list() {
		return list(Template.class, "select * from template order by id");
	}

}
