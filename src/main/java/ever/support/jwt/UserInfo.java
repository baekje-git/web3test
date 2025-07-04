package ever.support.jwt;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String userId;
    private String name;    
    private String email;
    private String phone;
    private String deviceId;
    private String cretrId;
    String cretDt;    
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getCretrId() {
		return cretrId;
	}
	public void setCretrId(String cretrId) {
		this.cretrId = cretrId;
	}
	public String getCretDt() {
		return cretDt;
	}
	public void setCretDt(String cretDt) {
		this.cretDt = cretDt;
	}
	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", name=" + name + ", email=" + email + ", phone=" + phone + ", deviceId="
				+ deviceId + ", cretrId=" + cretrId + ", cretDt=" + cretDt + "]";
	}
    
    
}
