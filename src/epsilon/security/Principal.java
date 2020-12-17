package epsilon.security;

import java.util.HashMap;
import java.util.Map;

import epsilon.model.PersonRole;

public class Principal {

	private Long id;
	private String name;
	private String email;
	private Long tenantId;
	private PersonRole role;
	private String token;
	private Map<String, Object> map = new HashMap<>();

	public static final Principal System = new Principal(null, null, null);

	public Principal() {
	}

	public Principal(Long id, String email, String name) {
		this.id = id;
		this.email = email;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public PersonRole getRole() {
		return role;
	}

}
