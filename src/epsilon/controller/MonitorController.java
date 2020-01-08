package epsilon.controller;

import java.util.List;

import javax.inject.Inject;

import epsilon.model.Tenant;
import epsilon.service.TenantService;
import orion.annotation.Path;
import orion.view.View;
import orion.view.View.Type;

public class MonitorController extends BaseController {

	@Inject
	TenantService tenantService;

	@Path("/system/monitor/ping")
	public View ping() {
		List<Tenant> tenantList = tenantService.list();
		if (!tenantList.isEmpty()) {
			return new View(Type.JSON, "OK");
		}
		return new View(Type.JSON, "Error");
	}

}
