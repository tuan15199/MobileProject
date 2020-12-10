package mobile.project.models;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
	ROLE_ADMIN, ROLE_CLIENT;

	public String getAuthority() {
		return name();
	}

}
