package tech.hljzj.infrastructure.compatible.vo.user.userDetails;

import java.sql.Timestamp;

/**
 * 用户扩展信息
 */
public class UserExtend implements java.io.Serializable {
	private String userExtId;
	private String userId;
	private String userExtKey;
	private String userExtVal;
	private String userExtDesc;
	private String createUser;
	private Timestamp createTime;
	private String modifyUser;
	private Timestamp modifyTime;

	public String getUserExtId() {
		return userExtId;
	}

	public void setUserExtId(String userExtId) {
		this.userExtId = userExtId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserExtKey() {
		return userExtKey;
	}

	public void setUserExtKey(String userExtKey) {
		this.userExtKey = userExtKey;
	}

	public String getUserExtVal() {
		return userExtVal;
	}

	public void setUserExtVal(String userExtVal) {
		this.userExtVal = userExtVal;
	}

	public String getUserExtDesc() {
		return userExtDesc;
	}

	public void setUserExtDesc(String userExtDesc) {
		this.userExtDesc = userExtDesc;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
}