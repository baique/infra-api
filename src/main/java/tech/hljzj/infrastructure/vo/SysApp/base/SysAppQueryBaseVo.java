package tech.hljzj.infrastructure.vo.SysApp.base;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.framework.pojo.form.PageDomain;
import tech.hljzj.infrastructure.domain.SysApp;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

/**
 * 应用管理 sys_app_
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysAppQueryBaseVo<T extends SysApp> extends PageDomain implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * bigint
     */
    private String id, idNot, idLike, idPrefix, idSuffix;
    private List<String> idIn, idNotIn;

    /**
     * 应用标识
     */
    private String key, keyNot, keyLike, keyPrefix, keySuffix;
    private List<String> keyIn, keyNotIn;

    /**
     * 应用名称
     */
    private String name, nameNot, nameLike, namePrefix, nameSuffix;
    private List<String> nameIn, nameNotIn;

    /**
     * 主页地址
     */
    private String mainPagePath, mainPagePathNot, mainPagePathLike, mainPagePathPrefix, mainPagePathSuffix;
    private List<String> mainPagePathIn, mainPagePathNotIn;

    /**
     * 应用密钥
     */
    private String secret, secretNot, secretLike, secretPrefix, secretSuffix;
    private List<String> secretIn, secretNotIn;

    /**
     * 应用状态
     */
    private String status, statusNot, statusLike, statusPrefix, statusSuffix;
    private List<String> statusIn, statusNotIn;

    /**
     * 应用描述
     */
    private String desc, descNot, descLike, descPrefix, descSuffix;
    private List<String> descIn, descNotIn;

    /**
     * 排序编号
     */
    private Integer sort, sortNot, sortGt, sortGte, sortLt, sortLte;
    private List<Integer> sortIn, sortNotIn;


    public Consumer<LambdaQueryWrapper<? extends T>> conditionId() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getId()), SysApp::getId, StrUtil.trim(this.getId()));
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

    public Consumer<LambdaQueryWrapper<? extends T>> conditionKey() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getKey()), SysApp::getKey, StrUtil.trim(this.getKey()));
            builder.ne(StrUtil.isNotBlank(this.getKeyNot()), T::getKey, StrUtil.trim(this.getKeyNot()));
            builder.in(null != this.getKeyIn() && this.getKeyIn().size() > 0, T::getKey, this.getKeyIn());
            builder.notIn(null != this.getKeyNotIn() && this.getKeyNotIn().size() > 0, T::getKey, this.getKeyNotIn());
            if (StrUtil.isNotBlank(this.getKeyLike())) {
                builder.like(T::getKey, StrUtil.trim(this.getKeyLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getKeyPrefix()), T::getKey, StrUtil.trim(this.getKeyPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getKeySuffix()), T::getKey, StrUtil.trim(this.getKeySuffix()));
            }


        };
    }

    public Consumer<LambdaQueryWrapper<? extends T>> conditionName() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getName()), SysApp::getName, StrUtil.trim(this.getName()));
            builder.ne(StrUtil.isNotBlank(this.getNameNot()), T::getName, StrUtil.trim(this.getNameNot()));
            builder.in(null != this.getNameIn() && this.getNameIn().size() > 0, T::getName, this.getNameIn());
            builder.notIn(null != this.getNameNotIn() && this.getNameNotIn().size() > 0, T::getName, this.getNameNotIn());
            if (StrUtil.isNotBlank(this.getNameLike())) {
                builder.like(T::getName, StrUtil.trim(this.getNameLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getNamePrefix()), T::getName, StrUtil.trim(this.getNamePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getNameSuffix()), T::getName, StrUtil.trim(this.getNameSuffix()));
            }


        };
    }

    public Consumer<LambdaQueryWrapper<? extends T>> conditionMainPagePath() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getMainPagePath()), SysApp::getMainPagePath, StrUtil.trim(this.getMainPagePath()));
            builder.ne(StrUtil.isNotBlank(this.getMainPagePathNot()), T::getMainPagePath, StrUtil.trim(this.getMainPagePathNot()));
            builder.in(null != this.getMainPagePathIn() && this.getMainPagePathIn().size() > 0, T::getMainPagePath, this.getMainPagePathIn());
            builder.notIn(null != this.getMainPagePathNotIn() && this.getMainPagePathNotIn().size() > 0, T::getMainPagePath, this.getMainPagePathNotIn());
            if (StrUtil.isNotBlank(this.getMainPagePathLike())) {
                builder.like(T::getMainPagePath, StrUtil.trim(this.getMainPagePathLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getMainPagePathPrefix()), T::getMainPagePath, StrUtil.trim(this.getMainPagePathPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getMainPagePathSuffix()), T::getMainPagePath, StrUtil.trim(this.getMainPagePathSuffix()));
            }


        };
    }

    public Consumer<LambdaQueryWrapper<? extends T>> conditionSecret() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getSecret()), SysApp::getSecret, StrUtil.trim(this.getSecret()));
            builder.ne(StrUtil.isNotBlank(this.getSecretNot()), T::getSecret, StrUtil.trim(this.getSecretNot()));
            builder.in(null != this.getSecretIn() && this.getSecretIn().size() > 0, T::getSecret, this.getSecretIn());
            builder.notIn(null != this.getSecretNotIn() && this.getSecretNotIn().size() > 0, T::getSecret, this.getSecretNotIn());
            if (StrUtil.isNotBlank(this.getSecretLike())) {
                builder.like(T::getSecret, StrUtil.trim(this.getSecretLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getSecretPrefix()), T::getSecret, StrUtil.trim(this.getSecretPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getSecretSuffix()), T::getSecret, StrUtil.trim(this.getSecretSuffix()));
            }


        };
    }

    public Consumer<LambdaQueryWrapper<? extends T>> conditionStatus() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getStatus()), SysApp::getStatus, StrUtil.trim(this.getStatus()));
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

    public Consumer<LambdaQueryWrapper<? extends T>> conditionDesc() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getDesc()), SysApp::getDesc, StrUtil.trim(this.getDesc()));
            builder.ne(StrUtil.isNotBlank(this.getDescNot()), T::getDesc, StrUtil.trim(this.getDescNot()));
            builder.in(null != this.getDescIn() && this.getDescIn().size() > 0, T::getDesc, this.getDescIn());
            builder.notIn(null != this.getDescNotIn() && this.getDescNotIn().size() > 0, T::getDesc, this.getDescNotIn());
            if (StrUtil.isNotBlank(this.getDescLike())) {
                builder.like(T::getDesc, StrUtil.trim(this.getDescLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getDescPrefix()), T::getDesc, StrUtil.trim(this.getDescPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getDescSuffix()), T::getDesc, StrUtil.trim(this.getDescSuffix()));
            }


        };
    }

    public Consumer<LambdaQueryWrapper<? extends T>> conditionSort() {
        return (builder) -> {

            builder.eq(null != this.getSort(), SysApp::getSort, (this.getSort()));
            builder.ne(null != this.getSortNot(), T::getSort, (this.getSortNot()));
            builder.in(null != this.getSortIn() && this.getSortIn().size() > 0, T::getSort, this.getSortIn());
            builder.notIn(null != this.getSortNotIn() && this.getSortNotIn().size() > 0, T::getSort, this.getSortNotIn());
            builder.gt(this.getSortGt() != null, T::getSort, this.getSortGt());
            builder.ge(this.getSortGte() != null, T::getSort, this.getSortGte());
            builder.lt(this.getSortLt() != null, T::getSort, this.getSortLt());
            builder.le(this.getSortLte() != null, T::getSort, this.getSortLte());


        };
    }

    /**
     * 默认排序
     */
    public Consumer<LambdaQueryWrapper<? extends T>> defaultSortBy() {
        return (builder) -> {
            builder.orderByDesc(T::getId);
        };
    }

    /**
     * 构建查询条件
     */
    public <R extends T> LambdaQueryWrapper<R> buildQueryWrapper() {
        LambdaQueryWrapper<R> builder = Wrappers.lambdaQuery();
        this.conditionId().accept(builder);
        this.conditionKey().accept(builder);
        this.conditionName().accept(builder);
        this.conditionMainPagePath().accept(builder);
        this.conditionSecret().accept(builder);
        this.conditionStatus().accept(builder);
        this.conditionDesc().accept(builder);
        this.conditionSort().accept(builder);
        this.defaultSortBy().accept(builder);
        return builder;
    }

}