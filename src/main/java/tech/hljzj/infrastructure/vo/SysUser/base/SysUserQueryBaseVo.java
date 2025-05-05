package tech.hljzj.infrastructure.vo.SysUser.base;

import cn.hutool.core.util.StrUtil;
import com.github.yulichang.toolkit.MPJWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.pojo.form.PageDomain;
import tech.hljzj.infrastructure.domain.SysUser;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * 用户管理 sys_user_
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysUserQueryBaseVo<T extends SysUser> extends PageDomain implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * id_
     */
    private String id, idNot, idLike, idPrefix, idSuffix;
    private List<String> idIn, idNotIn;

    /**
     * 所属部门
     */
    private String deptId, deptIdNot, deptIdLike, deptIdPrefix, deptIdSuffix;
    private List<String> deptIdIn, deptIdNotIn;

    /**
     * 部门内身份
     */
    private String deptIdentity, deptIdentityNot, deptIdentityLike, deptIdentityPrefix, deptIdentitySuffix;
    private List<String> deptIdentityIn, deptIdentityNotIn;

    /**
     * 账号
     */
    private String username, usernameNot, usernameLike, usernamePrefix, usernameSuffix;
    private List<String> usernameIn, usernameNotIn;

    /**
     * 密码策略
     */
    private String passwordPolicy, passwordPolicyNot, passwordPolicyLike, passwordPolicyPrefix, passwordPolicySuffix;
    private List<String> passwordPolicyIn, passwordPolicyNotIn;

    /**
     * 现有密码过期时间
     */
    private Date passwordExpired, passwordExpiredNot, passwordExpiredGt, passwordExpiredGte, passwordExpiredLt, passwordExpiredLte;
    private List<Date> passwordExpiredIn, passwordExpiredNotIn;

    /**
     * 昵称
     */
    private String nickname, nicknameNot, nicknameLike, nicknamePrefix, nicknameSuffix;
    private List<String> nicknameIn, nicknameNotIn;

    /**
     * 真实姓名
     */
    private String realname, realnameNot, realnameLike, realnamePrefix, realnameSuffix;
    private List<String> realnameIn, realnameNotIn;

    /**
     * 性别
     */
    private String sex, sexNot, sexLike, sexPrefix, sexSuffix;
    private List<String> sexIn, sexNotIn;

    /**
     * 证件类型
     */
    private String cardType, cardTypeNot, cardTypeLike, cardTypePrefix, cardTypeSuffix;
    private List<String> cardTypeIn, cardTypeNotIn;

    /**
     * 证件号码
     */
    private String cardNo, cardNoNot, cardNoLike, cardNoPrefix, cardNoSuffix;
    private List<String> cardNoIn, cardNoNotIn;

    /**
     * 手机号码
     */
    private String phone, phoneNot, phoneLike, phonePrefix, phoneSuffix;
    private List<String> phoneIn, phoneNotIn;

    /**
     * 邮箱
     */
    private String email, emailNot, emailLike, emailPrefix, emailSuffix;
    private List<String> emailIn, emailNotIn;

    /**
     * 工作地点
     */
    private String workAddr, workAddrNot, workAddrLike, workAddrPrefix, workAddrSuffix;
    private List<String> workAddrIn, workAddrNotIn;

    /**
     * 工作单位
     */
    private String workUnit, workUnitNot, workUnitLike, workUnitPrefix, workUnitSuffix;
    private List<String> workUnitIn, workUnitNotIn;

    /**
     * 工作职务
     */
    private String workPos, workPosNot, workPosLike, workPosPrefix, workPosSuffix;
    private List<String> workPosIn, workPosNotIn;

    /**
     * 工作职级
     */
    private String workRank, workRankNot, workRankLike, workRankPrefix, workRankSuffix;
    private List<String> workRankIn, workRankNotIn;

    /**
     * 家庭住址
     */
    private String homeAddr, homeAddrNot, homeAddrLike, homeAddrPrefix, homeAddrSuffix;
    private List<String> homeAddrIn, homeAddrNotIn;

    /**
     * 家庭电话
     */
    private String homePhone, homePhoneNot, homePhoneLike, homePhonePrefix, homePhoneSuffix;
    private List<String> homePhoneIn, homePhoneNotIn;

    /**
     * 用户来源
     */
    private String source, sourceNot, sourceLike, sourcePrefix, sourceSuffix;
    private List<String> sourceIn, sourceNotIn;

    /**
     * 排序编号
     */
    private Integer sort, sortNot, sortGt, sortGte, sortLt, sortLte;
    private List<Integer> sortIn, sortNotIn;

    /**
     * 用户状态
     */
    private String status, statusNot, statusLike, statusPrefix, statusSuffix;
    private List<String> statusIn, statusNotIn;

    /**
     * 账户是否锁定
     */
    private String accountLock, accountLockNot, accountLockLike, accountLockPrefix, accountLockSuffix;
    private List<String> accountLockIn, accountLockNotIn;

    /**
     * 上一次修改密码时间（这里配置了一段时间仍然可以使用旧密码来登录）
     */
    private Date lastChangePassword, lastChangePasswordNot, lastChangePasswordGt, lastChangePasswordGte, lastChangePasswordLt, lastChangePasswordLte;
    private List<Date> lastChangePasswordIn, lastChangePasswordNotIn;


    public Consumer<MPJLambdaWrapper<? extends T>> conditionId() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getId()), SysUser::getId, StrUtil.trim(this.getId()));
            builder.ne(StrUtil.isNotBlank(this.getIdNot()), T::getId, StrUtil.trim(this.getIdNot()));
            builder.in(null != this.getIdIn() && this.getIdIn().size() > 0, T::getId, this.getIdIn());
            builder.notIn(null != this.getIdNotIn() && this.getIdNotIn().size() > 0, T::getId, this.getIdNotIn());
            if (StrUtil.isNotBlank(this.getIdLike())) {
                builder.like(T::getId, StrUtil.trim(this.getIdLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getIdPrefix()), T::getId, StrUtil.trim(this.getIdPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getIdSuffix()), T::getId, StrUtil.trim(this.getIdSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionDeptId() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getDeptId()), SysUser::getDeptId, StrUtil.trim(this.getDeptId()));
            builder.ne(StrUtil.isNotBlank(this.getDeptIdNot()), T::getDeptId, StrUtil.trim(this.getDeptIdNot()));
            builder.in(null != this.getDeptIdIn() && this.getDeptIdIn().size() > 0, T::getDeptId, this.getDeptIdIn());
            builder.notIn(null != this.getDeptIdNotIn() && this.getDeptIdNotIn().size() > 0, T::getDeptId, this.getDeptIdNotIn());
            if (StrUtil.isNotBlank(this.getDeptIdLike())) {
                builder.like(T::getDeptId, StrUtil.trim(this.getDeptIdLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getDeptIdPrefix()), T::getDeptId, StrUtil.trim(this.getDeptIdPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getDeptIdSuffix()), T::getDeptId, StrUtil.trim(this.getDeptIdSuffix()));
            }


        };
    }


    public Consumer<MPJLambdaWrapper<? extends T>> conditionDeptIdentity() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getDeptIdentity()), SysUser::getDeptIdentity, StrUtil.trim(this.getDeptIdentity()));
            builder.ne(StrUtil.isNotBlank(this.getDeptIdentityNot()), T::getDeptIdentity, StrUtil.trim(this.getDeptIdentityNot()));
            builder.in(null != this.getDeptIdentityIn() && this.getDeptIdentityIn().size() > 0, T::getDeptIdentity, this.getDeptIdentityIn());
            builder.notIn(null != this.getDeptIdentityNotIn() && this.getDeptIdentityNotIn().size() > 0, T::getDeptIdentity, this.getDeptIdentityNotIn());
            if (StrUtil.isNotBlank(this.getDeptIdentityLike())) {
                builder.like(T::getDeptIdentity, StrUtil.trim(this.getDeptIdentityLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getDeptIdentityPrefix()), T::getDeptIdentity, StrUtil.trim(this.getDeptIdentityPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getDeptIdentitySuffix()), T::getDeptIdentity, StrUtil.trim(this.getDeptIdentitySuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionUsername() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getUsername()), SysUser::getUsername, StrUtil.trim(this.getUsername()));
            builder.ne(StrUtil.isNotBlank(this.getUsernameNot()), T::getUsername, StrUtil.trim(this.getUsernameNot()));
            builder.in(null != this.getUsernameIn() && this.getUsernameIn().size() > 0, T::getUsername, this.getUsernameIn());
            builder.notIn(null != this.getUsernameNotIn() && this.getUsernameNotIn().size() > 0, T::getUsername, this.getUsernameNotIn());
            if (StrUtil.isNotBlank(this.getUsernameLike())) {
                builder.like(T::getUsername, StrUtil.trim(this.getUsernameLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getUsernamePrefix()), T::getUsername, StrUtil.trim(this.getUsernamePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getUsernameSuffix()), T::getUsername, StrUtil.trim(this.getUsernameSuffix()));
            }


        };
    }


    public Consumer<MPJLambdaWrapper<? extends T>> conditionPasswordPolicy() {
        return (builder) -> {
            builder.eq(StrUtil.isNotBlank(this.getPasswordPolicy()), SysUser::getPasswordPolicy, StrUtil.trim(this.getPasswordPolicy()));
            builder.ne(StrUtil.isNotBlank(this.getPasswordPolicyNot()), T::getPasswordPolicy, StrUtil.trim(this.getPasswordPolicyNot()));
            builder.in(null != this.getPasswordPolicyIn() && this.getPasswordPolicyIn().size() > 0, T::getPasswordPolicy, this.getPasswordPolicyIn());
            builder.notIn(null != this.getPasswordPolicyNotIn() && this.getPasswordPolicyNotIn().size() > 0, T::getPasswordPolicy, this.getPasswordPolicyNotIn());
            if (StrUtil.isNotBlank(this.getPasswordPolicyLike())) {
                builder.like(T::getPasswordPolicy, StrUtil.trim(this.getPasswordPolicyLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getPasswordPolicyPrefix()), T::getPasswordPolicy, StrUtil.trim(this.getPasswordPolicyPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getPasswordPolicySuffix()), T::getPasswordPolicy, StrUtil.trim(this.getPasswordPolicySuffix()));
            }
        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionPasswordExpired() {
        return (builder) -> {

            builder.eq(null != this.getPasswordExpired(), SysUser::getPasswordExpired, (this.getPasswordExpired()));
            builder.ne(null != this.getPasswordExpiredNot(), T::getPasswordExpired, (this.getPasswordExpiredNot()));
            builder.in(null != this.getPasswordExpiredIn() && this.getPasswordExpiredIn().size() > 0, T::getPasswordExpired, this.getPasswordExpiredIn());
            builder.notIn(null != this.getPasswordExpiredNotIn() && this.getPasswordExpiredNotIn().size() > 0, T::getPasswordExpired, this.getPasswordExpiredNotIn());
            builder.gt(this.getPasswordExpiredGt() != null, T::getPasswordExpired, this.getPasswordExpiredGt());
            builder.ge(this.getPasswordExpiredGte() != null, T::getPasswordExpired, this.getPasswordExpiredGte());
            builder.lt(this.getPasswordExpiredLt() != null, T::getPasswordExpired, this.getPasswordExpiredLt());
            builder.le(this.getPasswordExpiredLte() != null, T::getPasswordExpired, this.getPasswordExpiredLte());


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionNickname() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getNickname()), SysUser::getNickname, StrUtil.trim(this.getNickname()));
            builder.ne(StrUtil.isNotBlank(this.getNicknameNot()), T::getNickname, StrUtil.trim(this.getNicknameNot()));
            builder.in(null != this.getNicknameIn() && this.getNicknameIn().size() > 0, T::getNickname, this.getNicknameIn());
            builder.notIn(null != this.getNicknameNotIn() && this.getNicknameNotIn().size() > 0, T::getNickname, this.getNicknameNotIn());
            if (StrUtil.isNotBlank(this.getNicknameLike())) {
                builder.like(T::getNickname, StrUtil.trim(this.getNicknameLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getNicknamePrefix()), T::getNickname, StrUtil.trim(this.getNicknamePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getNicknameSuffix()), T::getNickname, StrUtil.trim(this.getNicknameSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionRealname() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getRealname()), SysUser::getRealname, StrUtil.trim(this.getRealname()));
            builder.ne(StrUtil.isNotBlank(this.getRealnameNot()), T::getRealname, StrUtil.trim(this.getRealnameNot()));
            builder.in(null != this.getRealnameIn() && this.getRealnameIn().size() > 0, T::getRealname, this.getRealnameIn());
            builder.notIn(null != this.getRealnameNotIn() && this.getRealnameNotIn().size() > 0, T::getRealname, this.getRealnameNotIn());
            if (StrUtil.isNotBlank(this.getRealnameLike())) {
                builder.like(T::getRealname, StrUtil.trim(this.getRealnameLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getRealnamePrefix()), T::getRealname, StrUtil.trim(this.getRealnamePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getRealnameSuffix()), T::getRealname, StrUtil.trim(this.getRealnameSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionSex() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getSex()), SysUser::getSex, StrUtil.trim(this.getSex()));
            builder.ne(StrUtil.isNotBlank(this.getSexNot()), T::getSex, StrUtil.trim(this.getSexNot()));
            builder.in(null != this.getSexIn() && this.getSexIn().size() > 0, T::getSex, this.getSexIn());
            builder.notIn(null != this.getSexNotIn() && this.getSexNotIn().size() > 0, T::getSex, this.getSexNotIn());
            if (StrUtil.isNotBlank(this.getSexLike())) {
                builder.like(T::getSex, StrUtil.trim(this.getSexLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getSexPrefix()), T::getSex, StrUtil.trim(this.getSexPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getSexSuffix()), T::getSex, StrUtil.trim(this.getSexSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionCardType() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getCardType()), SysUser::getCardType, StrUtil.trim(this.getCardType()));
            builder.ne(StrUtil.isNotBlank(this.getCardTypeNot()), T::getCardType, StrUtil.trim(this.getCardTypeNot()));
            builder.in(null != this.getCardTypeIn() && this.getCardTypeIn().size() > 0, T::getCardType, this.getCardTypeIn());
            builder.notIn(null != this.getCardTypeNotIn() && this.getCardTypeNotIn().size() > 0, T::getCardType, this.getCardTypeNotIn());
            if (StrUtil.isNotBlank(this.getCardTypeLike())) {
                builder.like(T::getCardType, StrUtil.trim(this.getCardTypeLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getCardTypePrefix()), T::getCardType, StrUtil.trim(this.getCardTypePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getCardTypeSuffix()), T::getCardType, StrUtil.trim(this.getCardTypeSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionCardNo() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getCardNo()), SysUser::getCardNo, StrUtil.trim(this.getCardNo()));
            builder.ne(StrUtil.isNotBlank(this.getCardNoNot()), T::getCardNo, StrUtil.trim(this.getCardNoNot()));
            builder.in(null != this.getCardNoIn() && this.getCardNoIn().size() > 0, T::getCardNo, this.getCardNoIn());
            builder.notIn(null != this.getCardNoNotIn() && this.getCardNoNotIn().size() > 0, T::getCardNo, this.getCardNoNotIn());
            if (StrUtil.isNotBlank(this.getCardNoLike())) {
                builder.like(T::getCardNo, StrUtil.trim(this.getCardNoLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getCardNoPrefix()), T::getCardNo, StrUtil.trim(this.getCardNoPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getCardNoSuffix()), T::getCardNo, StrUtil.trim(this.getCardNoSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionPhone() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getPhone()), SysUser::getPhone, StrUtil.trim(this.getPhone()));
            builder.ne(StrUtil.isNotBlank(this.getPhoneNot()), T::getPhone, StrUtil.trim(this.getPhoneNot()));
            builder.in(null != this.getPhoneIn() && this.getPhoneIn().size() > 0, T::getPhone, this.getPhoneIn());
            builder.notIn(null != this.getPhoneNotIn() && this.getPhoneNotIn().size() > 0, T::getPhone, this.getPhoneNotIn());
            if (StrUtil.isNotBlank(this.getPhoneLike())) {
                builder.like(T::getPhone, StrUtil.trim(this.getPhoneLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getPhonePrefix()), T::getPhone, StrUtil.trim(this.getPhonePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getPhoneSuffix()), T::getPhone, StrUtil.trim(this.getPhoneSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionEmail() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getEmail()), SysUser::getEmail, StrUtil.trim(this.getEmail()));
            builder.ne(StrUtil.isNotBlank(this.getEmailNot()), T::getEmail, StrUtil.trim(this.getEmailNot()));
            builder.in(null != this.getEmailIn() && this.getEmailIn().size() > 0, T::getEmail, this.getEmailIn());
            builder.notIn(null != this.getEmailNotIn() && this.getEmailNotIn().size() > 0, T::getEmail, this.getEmailNotIn());
            if (StrUtil.isNotBlank(this.getEmailLike())) {
                builder.like(T::getEmail, StrUtil.trim(this.getEmailLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getEmailPrefix()), T::getEmail, StrUtil.trim(this.getEmailPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getEmailSuffix()), T::getEmail, StrUtil.trim(this.getEmailSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionWorkAddr() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getWorkAddr()), SysUser::getWorkAddr, StrUtil.trim(this.getWorkAddr()));
            builder.ne(StrUtil.isNotBlank(this.getWorkAddrNot()), T::getWorkAddr, StrUtil.trim(this.getWorkAddrNot()));
            builder.in(null != this.getWorkAddrIn() && this.getWorkAddrIn().size() > 0, T::getWorkAddr, this.getWorkAddrIn());
            builder.notIn(null != this.getWorkAddrNotIn() && this.getWorkAddrNotIn().size() > 0, T::getWorkAddr, this.getWorkAddrNotIn());
            if (StrUtil.isNotBlank(this.getWorkAddrLike())) {
                builder.like(T::getWorkAddr, StrUtil.trim(this.getWorkAddrLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getWorkAddrPrefix()), T::getWorkAddr, StrUtil.trim(this.getWorkAddrPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getWorkAddrSuffix()), T::getWorkAddr, StrUtil.trim(this.getWorkAddrSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionWorkUnit() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getWorkUnit()), SysUser::getWorkUnit, StrUtil.trim(this.getWorkUnit()));
            builder.ne(StrUtil.isNotBlank(this.getWorkUnitNot()), T::getWorkUnit, StrUtil.trim(this.getWorkUnitNot()));
            builder.in(null != this.getWorkUnitIn() && this.getWorkUnitIn().size() > 0, T::getWorkUnit, this.getWorkUnitIn());
            builder.notIn(null != this.getWorkUnitNotIn() && this.getWorkUnitNotIn().size() > 0, T::getWorkUnit, this.getWorkUnitNotIn());
            if (StrUtil.isNotBlank(this.getWorkUnitLike())) {
                builder.like(T::getWorkUnit, StrUtil.trim(this.getWorkUnitLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getWorkUnitPrefix()), T::getWorkUnit, StrUtil.trim(this.getWorkUnitPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getWorkUnitSuffix()), T::getWorkUnit, StrUtil.trim(this.getWorkUnitSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionWorkPos() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getWorkPos()), SysUser::getWorkPos, StrUtil.trim(this.getWorkPos()));
            builder.ne(StrUtil.isNotBlank(this.getWorkPosNot()), T::getWorkPos, StrUtil.trim(this.getWorkPosNot()));
            builder.in(null != this.getWorkPosIn() && this.getWorkPosIn().size() > 0, T::getWorkPos, this.getWorkPosIn());
            builder.notIn(null != this.getWorkPosNotIn() && this.getWorkPosNotIn().size() > 0, T::getWorkPos, this.getWorkPosNotIn());
            if (StrUtil.isNotBlank(this.getWorkPosLike())) {
                builder.like(T::getWorkPos, StrUtil.trim(this.getWorkPosLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getWorkPosPrefix()), T::getWorkPos, StrUtil.trim(this.getWorkPosPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getWorkPosSuffix()), T::getWorkPos, StrUtil.trim(this.getWorkPosSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionWorkRank() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getWorkRank()), SysUser::getWorkRank, StrUtil.trim(this.getWorkRank()));
            builder.ne(StrUtil.isNotBlank(this.getWorkRankNot()), T::getWorkRank, StrUtil.trim(this.getWorkRankNot()));
            builder.in(null != this.getWorkRankIn() && this.getWorkRankIn().size() > 0, T::getWorkRank, this.getWorkRankIn());
            builder.notIn(null != this.getWorkRankNotIn() && this.getWorkRankNotIn().size() > 0, T::getWorkRank, this.getWorkRankNotIn());
            if (StrUtil.isNotBlank(this.getWorkRankLike())) {
                builder.like(T::getWorkRank, StrUtil.trim(this.getWorkRankLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getWorkRankPrefix()), T::getWorkRank, StrUtil.trim(this.getWorkRankPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getWorkRankSuffix()), T::getWorkRank, StrUtil.trim(this.getWorkRankSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionHomeAddr() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getHomeAddr()), SysUser::getHomeAddr, StrUtil.trim(this.getHomeAddr()));
            builder.ne(StrUtil.isNotBlank(this.getHomeAddrNot()), T::getHomeAddr, StrUtil.trim(this.getHomeAddrNot()));
            builder.in(null != this.getHomeAddrIn() && this.getHomeAddrIn().size() > 0, T::getHomeAddr, this.getHomeAddrIn());
            builder.notIn(null != this.getHomeAddrNotIn() && this.getHomeAddrNotIn().size() > 0, T::getHomeAddr, this.getHomeAddrNotIn());
            if (StrUtil.isNotBlank(this.getHomeAddrLike())) {
                builder.like(T::getHomeAddr, StrUtil.trim(this.getHomeAddrLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getHomeAddrPrefix()), T::getHomeAddr, StrUtil.trim(this.getHomeAddrPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getHomeAddrSuffix()), T::getHomeAddr, StrUtil.trim(this.getHomeAddrSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionHomePhone() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getHomePhone()), SysUser::getHomePhone, StrUtil.trim(this.getHomePhone()));
            builder.ne(StrUtil.isNotBlank(this.getHomePhoneNot()), T::getHomePhone, StrUtil.trim(this.getHomePhoneNot()));
            builder.in(null != this.getHomePhoneIn() && this.getHomePhoneIn().size() > 0, T::getHomePhone, this.getHomePhoneIn());
            builder.notIn(null != this.getHomePhoneNotIn() && this.getHomePhoneNotIn().size() > 0, T::getHomePhone, this.getHomePhoneNotIn());
            if (StrUtil.isNotBlank(this.getHomePhoneLike())) {
                builder.like(T::getHomePhone, StrUtil.trim(this.getHomePhoneLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getHomePhonePrefix()), T::getHomePhone, StrUtil.trim(this.getHomePhonePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getHomePhoneSuffix()), T::getHomePhone, StrUtil.trim(this.getHomePhoneSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionSource() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getSource()), SysUser::getSource, StrUtil.trim(this.getSource()));
            builder.ne(StrUtil.isNotBlank(this.getSourceNot()), T::getSource, StrUtil.trim(this.getSourceNot()));
            builder.in(null != this.getSourceIn() && this.getSourceIn().size() > 0, T::getSource, this.getSourceIn());
            builder.notIn(null != this.getSourceNotIn() && this.getSourceNotIn().size() > 0, T::getSource, this.getSourceNotIn());
            if (StrUtil.isNotBlank(this.getSourceLike())) {
                builder.like(T::getSource, StrUtil.trim(this.getSourceLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getSourcePrefix()), T::getSource, StrUtil.trim(this.getSourcePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getSourceSuffix()), T::getSource, StrUtil.trim(this.getSourceSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionSort() {
        return (builder) -> {

            builder.eq(null != this.getSort(), SysUser::getSort, (this.getSort()));
            builder.ne(null != this.getSortNot(), T::getSort, (this.getSortNot()));
            builder.in(null != this.getSortIn() && this.getSortIn().size() > 0, T::getSort, this.getSortIn());
            builder.notIn(null != this.getSortNotIn() && this.getSortNotIn().size() > 0, T::getSort, this.getSortNotIn());
            builder.gt(this.getSortGt() != null, T::getSort, this.getSortGt());
            builder.ge(this.getSortGte() != null, T::getSort, this.getSortGte());
            builder.lt(this.getSortLt() != null, T::getSort, this.getSortLt());
            builder.le(this.getSortLte() != null, T::getSort, this.getSortLte());


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionStatus() {
        return (builder) -> {
            builder.eq(StrUtil.isNotBlank(this.getStatus()), SysUser::getStatus, StrUtil.trim(this.getStatus()));
            builder.ne(StrUtil.isNotBlank(this.getStatusNot()), T::getStatus, StrUtil.trim(this.getStatusNot()));
            builder.in(null != this.getStatusIn() && this.getStatusIn().size() > 0, T::getStatus, this.getStatusIn());
            builder.notIn(null != this.getStatusNotIn() && this.getStatusNotIn().size() > 0, T::getStatus, this.getStatusNotIn());
            if (StrUtil.isNotBlank(this.getStatusLike())) {
                builder.like(T::getStatus, StrUtil.trim(this.getStatusLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getStatusPrefix()), T::getStatus, StrUtil.trim(this.getStatusPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getStatusSuffix()), T::getStatus, StrUtil.trim(this.getStatusSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionAccountLock() {
        return (builder) -> {
            builder.eq(StrUtil.isNotBlank(this.getAccountLock()), T::getAccountLock, StrUtil.trim(this.getAccountLock()));
            builder.ne(StrUtil.isNotBlank(this.getAccountLockNot()), T::getAccountLock, StrUtil.trim(this.getAccountLockNot()));
            builder.in(null != this.getAccountLockIn() && this.getAccountLockIn().size() > 0, T::getAccountLock, this.getAccountLockIn());
            builder.notIn(null != this.getAccountLockNotIn() && this.getAccountLockNotIn().size() > 0, T::getAccountLock, this.getAccountLockNotIn());
            if (StrUtil.isNotBlank(this.getAccountLockLike())) {
                builder.like(T::getAccountLock, StrUtil.trim(this.getAccountLockLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getAccountLockPrefix()), T::getAccountLock, StrUtil.trim(this.getAccountLockPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getAccountLockSuffix()), T::getAccountLock, StrUtil.trim(this.getAccountLockSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionLastChangePassword() {
        return (builder) -> {

            builder.eq(null != this.getLastChangePassword(), SysUser::getLastChangePassword, (this.getLastChangePassword()));
            builder.ne(null != this.getLastChangePasswordNot(), T::getLastChangePassword, (this.getLastChangePasswordNot()));
            builder.in(null != this.getLastChangePasswordIn() && this.getLastChangePasswordIn().size() > 0, T::getLastChangePassword, this.getLastChangePasswordIn());
            builder.notIn(null != this.getLastChangePasswordNotIn() && this.getLastChangePasswordNotIn().size() > 0, T::getLastChangePassword, this.getLastChangePasswordNotIn());
            builder.gt(this.getLastChangePasswordGt() != null, T::getLastChangePassword, this.getLastChangePasswordGt());
            builder.ge(this.getLastChangePasswordGte() != null, T::getLastChangePassword, this.getLastChangePasswordGte());
            builder.lt(this.getLastChangePasswordLt() != null, T::getLastChangePassword, this.getLastChangePasswordLt());
            builder.le(this.getLastChangePasswordLte() != null, T::getLastChangePassword, this.getLastChangePasswordLte());


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> defaultSortBy() {
        return (builder) -> {
            builder.orderByAsc(T::getSort);
            builder.orderByDesc(T::getCreateTime);
            builder.orderByDesc(T::getId);
        };
    }


    /**
     * 构建查询条件
     */
    public <R extends T> MPJLambdaWrapper<R> buildQueryWrapper() {
        MPJLambdaWrapper<R> builder = MPJWrappers.lambdaJoin();
        this.conditionId().accept(builder);
        this.conditionDeptId().accept(builder);
        this.conditionDeptIdentity().accept(builder);
        this.conditionUsername().accept(builder);
        this.conditionPasswordPolicy().accept(builder);
        this.conditionPasswordExpired().accept(builder);
        this.conditionNickname().accept(builder);
        this.conditionRealname().accept(builder);
        this.conditionSex().accept(builder);
        this.conditionCardType().accept(builder);
        this.conditionCardNo().accept(builder);
        this.conditionPhone().accept(builder);
        this.conditionEmail().accept(builder);
        this.conditionWorkAddr().accept(builder);
        this.conditionWorkUnit().accept(builder);
        this.conditionWorkPos().accept(builder);
        this.conditionWorkRank().accept(builder);
        this.conditionHomeAddr().accept(builder);
        this.conditionHomePhone().accept(builder);
        this.conditionSource().accept(builder);
        this.conditionSort().accept(builder);
        this.conditionStatus().accept(builder);
        this.conditionAccountLock().accept(builder);
        this.conditionLastChangePassword().accept(builder);
        this.defaultSortBy().accept(builder);
        return builder;
    }


}