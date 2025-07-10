package tech.hljzj.infrastructure.compatible.vo.user.userDetails;

import tech.hljzj.infrastructure.domain.VSysUser;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class UserDetails implements Serializable {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 头像
     */
    private String userHeadportrait;
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 昵称
     */
    private String userNickname;
    /**
     * 姓氏
     */
    private String userSurname;
    /**
     * 中间名
     */
    private String userMiddlename;
    /**
     * 英文简拼
     */
    private String userEAName;
    /**
     * 全名
     */
    private String userFullname;
    /**
     * 部门
     */
    private String departmentName;
    /**
     * 部门ID
     */
    private String userDepartment;
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
    /**
     * 密码过期方式
     */
    // 下次登录验证邮箱
    private Integer userEmailValidateState;

    //  下次登录验证手机
    private Integer userMobileValidateState;

    //    密码过期方式
    private Integer userPasswordexpiration;


    /*密码过期时间*/
    private Timestamp userPasswordexpirationTime;

    //手机号码
    private String userPhone;

    //邮箱
    private String userEmail;
    /**
     * 国家
     */
    private String userAddressCountries;
    /**
     * 省份
     */
    private String userAddressCounty;
    /**
     * 城市
     */
    private String userAddressCity;
    /**
     * 曲线街道
     */
    private String userAddressProvince;
    private String userPosition;
    /**
     * 详细地址
     */
    private String userAddressDetailed;
    /**
     * 公司信息
     */
    private String userCorporation;
    /**
     * 所属部门类型
     */
    private String userDeptType;
    /**
     * 用户密级
     */
    private String userSecretType;
    /**
     * 是否为领导
     */
    private String userLeaderFlag;
    /**
     * 扩展字段
     */
    private List<UserExtend> extendList;


    public static UserDetails from(VSysUser userInfo) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(userInfo.getId());
        userDetails.setUserAccount(userInfo.getUsername());
        userDetails.setUserNickname(userInfo.getNickname());
        userDetails.setUserFullname(userInfo.getRealname());
        userDetails.setUserSurname(userInfo.getRealname());
        userDetails.setUserMiddlename(userInfo.getRealname());

        userDetails.setUserDepartment(userInfo.getDeptId());
        userDetails.setDepartmentName(userInfo.getDeptName());
        userDetails.setUserPhone(userInfo.getPhone());
        userDetails.setUserEmail(userInfo.getEmail());
        userDetails.setUserPosition(userInfo.getWorkPos());
        userDetails.setUserAddressCountries(userInfo.getHomeAddr());
        userDetails.setUserAddressCounty(userInfo.getHomeAddr());
        userDetails.setUserAddressCity(userInfo.getHomeAddr());
        userDetails.setUserAddressProvince(userInfo.getHomeAddr());
        userDetails.setUserCorporation(userInfo.getWorkUnit());
        userDetails.setUserDeptType(userInfo.getDeptType());
        //TODO 实现是否领导功能
        userDetails.setUserLeaderFlag("0");
        userDetails.setUserCreatetime(new Timestamp(
            userInfo.getCreateTime().getTime()
        ));


        return userDetails;
    }


    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }


    public String getUserId() {
        return userId;
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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserHeadportrait() {
        return userHeadportrait;
    }

    public void setUserHeadportrait(String userHeadportrait) {
        this.userHeadportrait = userHeadportrait;
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

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserMiddlename() {
        return userMiddlename;
    }

    public void setUserMiddlename(String userMiddlename) {
        this.userMiddlename = userMiddlename;
    }

    public String getUserEAName() {
        return userEAName;
    }

    public void setUserEAName(String userEAName) {
        this.userEAName = userEAName;
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

    public Integer getUserEmailValidateState() {
        return userEmailValidateState;
    }

    public void setUserEmailValidateState(Integer userEmailValidateState) {
        this.userEmailValidateState = userEmailValidateState;
    }

    public Integer getUserMobileValidateState() {
        return userMobileValidateState;
    }

    public void setUserMobileValidateState(Integer userMobileValidateState) {
        this.userMobileValidateState = userMobileValidateState;
    }

    public Integer getUserPasswordexpiration() {
        return userPasswordexpiration;
    }

    public void setUserPasswordexpiration(Integer userPasswordexpiration) {
        this.userPasswordexpiration = userPasswordexpiration;
    }

    public Timestamp getUserPasswordexpirationTime() {
        return userPasswordexpirationTime;
    }

    public void setUserPasswordexpirationTime(Timestamp userPasswordexpirationTime) {
        this.userPasswordexpirationTime = userPasswordexpirationTime;
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

    public String getUserAddressDetailed() {
        return userAddressDetailed;
    }

    public void setUserAddressDetailed(String userAddressDetailed) {
        this.userAddressDetailed = userAddressDetailed;
    }

    public List<UserExtend> getExtendList() {
        return extendList;
    }

    public void setExtendList(List<UserExtend> extendList) {
        this.extendList = extendList;
    }
}
