/**
 * This file is part of mycollab-services.
 *
 * mycollab-services is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-services is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-services.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.module.user.domain;

import com.esofthead.mycollab.core.arguments.NotBindable;
import com.esofthead.mycollab.core.utils.StringUtils;
import com.esofthead.mycollab.security.PermissionMap;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
public class SimpleUser extends User {

	private static final long serialVersionUID = 1L;

	private Integer roleid;
	private String roleName;

	@NotBindable
	private PermissionMap permissionMaps;

	private Boolean isAccountOwner;
	private String subdomain;
	private Integer accountId;
	private String registerstatus;

	public String getDisplayName() {
		String result = getFirstname() + " " + getLastname();
		if (result.trim().equals("")) {
			String displayName = getUsername();
			return StringUtils.extractNameFromEmail(displayName);
		}
		return result;
	}

	public PermissionMap getPermissionMaps() {
		return permissionMaps;
	}

	public void setPermissionMaps(PermissionMap permissionMaps) {
		this.permissionMaps = permissionMaps;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public Boolean getIsAccountOwner() {
		return isAccountOwner;
	}

	public void setIsAccountOwner(Boolean isAccountOwner) {
		this.isAccountOwner = isAccountOwner;
	}

	public String getSubdomain() {
		return subdomain;
	}

	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getRegisterstatus() {
		return registerstatus;
	}

	public void setRegisterstatus(String registerstatus) {
		this.registerstatus = registerstatus;
	}

	public boolean equals(Object arg0) {
		if (arg0 instanceof SimpleUser) {
			if (getUsername() != null
					&& ((SimpleUser) arg0).getUsername() != null) {
				return getUsername().equals(((SimpleUser) arg0).getUsername());
			} else {
				return getEmail().equals(((SimpleUser) arg0).getEmail());
			}
		}
		return false;
	}
}
