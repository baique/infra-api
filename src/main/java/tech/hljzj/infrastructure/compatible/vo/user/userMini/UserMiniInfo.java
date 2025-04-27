package tech.hljzj.infrastructure.compatible.vo.user.userMini;

import tech.hljzj.infrastructure.domain.VSysUser;
import tech.hljzj.infrastructure.vo.VSysUser.VSysUserQueryVo;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserMiniInfo implements Serializable {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 昵称
     */
    private String userNickname;
    /**
     * 全名
     */
    private String userFullname;
    /**
     * 部门
     */
    private String departmentName;
    private String userDepartment;
    private String userPhone;
    private String userEmail;
    private String userPosition;
    private String userDesc;
    private String userFax;
    private String userOfficephone;
    private String userHomephone;
    private String userAddressCountries;
    private String userAddressCounty;
    private String userAddressDetailed;
    private String userAddressCity;
    private String userAddressProvince;
    private String userCorporation;
    private String userDeptType;
    private String userSecretType;
    private String userLeaderFlag;
    /**
     * 注册日期
     */
    private Timestamp userCreatetime;
    /**
     * 最后登录时间
     */
    private Timestamp userLastlogintime;
    /**
     * 状态
     */
    private String userState;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public String getUserFax() {
        return userFax;
    }

    public void setUserFax(String userFax) {
        this.userFax = userFax;
    }

    public String getUserOfficephone() {
        return userOfficephone;
    }

    public void setUserOfficephone(String userOfficephone) {
        this.userOfficephone = userOfficephone;
    }

    public String getUserHomephone() {
        return userHomephone;
    }

    public void setUserHomephone(String userHomephone) {
        this.userHomephone = userHomephone;
    }

    public String getUserAddressCountries() {
        return userAddressCountries;
    }

    public void setUserAddressCountries(String userAddressCountries) {
        this.userAddressCountries = userAddressCountries;
    }

    public String getUserAddressCounty() {
        return userAddressCounty;
    }

    public void setUserAddressCounty(String userAddressCounty) {
        this.userAddressCounty = userAddressCounty;
    }

    public String getUserAddressDetailed() {
        return userAddressDetailed;
    }

    public void setUserAddressDetailed(String userAddressDetailed) {
        this.userAddressDetailed = userAddressDetailed;
    }

    public String getUserAddressCity() {
        return userAddressCity;
    }

    public void setUserAddressCity(String userAddressCity) {
        this.userAddressCity = userAddressCity;
    }

    public String getUserAddressProvince() {
        return userAddressProvince;
    }

    public void setUserAddressProvince(String userAddressProvince) {
        this.userAddressProvince = userAddressProvince;
    }

    public String getUserCorporation() {
        return userCorporation;
    }

    public void setUserCorporation(String userCorporation) {
        this.userCorporation = userCorporation;
    }

    public String getUserDeptType() {
        return userDeptType;
    }

    public void setUserDeptType(String userDeptType) {
        this.userDeptType = userDeptType;
    }

    public String getUserSecretType() {
        return userSecretType;
    }

    public void setUserSecretType(String userSecretType) {
        this.userSecretType = userSecretType;
    }

    public String getUserLeaderFlag() {
        return userLeaderFlag;
    }

    public void setUserLeaderFlag(String userLeaderFlag) {
        this.userLeaderFlag = userLeaderFlag;
    }

    public Timestamp getUserCreatetime() {
        return userCreatetime;
    }

    public void setUserCreatetime(Timestamp userCreatetime) {
        this.userCreatetime = userCreatetime;
    }

    public Timestamp getUserLastlogintime() {
        return userLastlogintime;
    }

    public void setUserLastlogintime(Timestamp userLastlogintime) {
        this.userLastlogintime = userLastlogintime;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }


    public void fromVSysUser(VSysUser userInfo) {
        this.setUserId(userInfo.getId());
        this.setUserAccount(userInfo.getUsername());
        this.setUserNickname(userInfo.getNickname());
        this.setUserFullname(userInfo.getRealname());
        this.setUserDepartment(userInfo.getDeptId());
        this.setDepartmentName(userInfo.getDeptName());
        this.setUserPhone(userInfo.getPhone());
        this.setUserEmail(userInfo.getEmail());
        this.setUserPosition(userInfo.getHomeAddr());
        this.setUserHomephone(userInfo.getHomePhone());
        this.setUserAddressCountries(userInfo.getHomeAddr());
        this.setUserAddressCounty(userInfo.getHomeAddr());
        this.setUserAddressCity(userInfo.getHomeAddr());
        this.setUserAddressProvince(userInfo.getHomeAddr());
        this.setUserCorporation(userInfo.getWorkAddr());
        this.setUserDeptType(userInfo.getDeptType());
        // TODO 秘密类型
//        up.setUserSecretType(userInfo.getSecretType());
        // TODO 是否领导
        this.setUserLeaderFlag("0");
        this.setUserCreatetime(new Timestamp(
                userInfo.getCreateTime().getTime()
        ));
        //用户状态
        this.setUserState(userInfo.getStatus());
    }

    public static UserMiniInfo from(VSysUser userInfo) {
        UserMiniInfo v = new UserMiniInfo();
        v.fromVSysUser(userInfo);
        return v;
    }

    public VSysUserQueryVo toQuery() {
        VSysUserQueryVo query = new VSysUserQueryVo();
        query.setUsernameLike(this.getUserAccount());
        query.setRealnameLike(this.getUserFullname());
        query.setNicknameLike(this.getUserNickname());
        query.setEmailLike(this.getUserEmail());
        query.setPhoneLike(this.getUserPhone());
        query.setStatus(this.getUserState());
        return query;
    }
}
