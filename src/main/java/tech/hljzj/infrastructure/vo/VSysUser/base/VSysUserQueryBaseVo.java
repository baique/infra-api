package tech.hljzj.infrastructure.vo.VSysUser.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.Getter;
import lombok.Setter;
import tech.hljzj.infrastructure.domain.SysDept;
import tech.hljzj.infrastructure.domain.SysUserExtAttr;
import tech.hljzj.infrastructure.domain.VSysUser;
import tech.hljzj.infrastructure.service.SysDeptService;
import tech.hljzj.infrastructure.vo.SysUser.SysUserQueryVo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@Getter
@Setter
public class VSysUserQueryBaseVo<T extends VSysUser> extends SysUserQueryVo<T> {
    /**
     * 组织标识
     */
    private String deptKey, deptKeyNot, deptKeyLike, deptKeyPrefix, deptKeySuffix;
    private List<String> deptKeyIn, deptKeyNotIn;

    /**
     * 组织名称
     */
    private String deptName, deptNameNot, deptNameLike, deptNamePrefix, deptNameSuffix;
    private List<String> deptNameIn, deptNameNotIn;

    /**
     * 组织别名
     */
    private String deptAlias, deptAliasNot, deptAliasLike, deptAliasPrefix, deptAliasSuffix;
    private List<String> deptAliasIn, deptAliasNotIn;

    /**
     * 组织简称
     */
    private String deptShortName, deptShortNameNot, deptShortNameLike, deptShortNamePrefix, deptShortNameSuffix;
    private List<String> deptShortNameIn, deptShortNameNotIn;

    /**
     * 组织类型
     */
    private String deptType, deptTypeNot, deptTypeLike, deptTypePrefix, deptTypeSuffix;
    private List<String> deptTypeIn, deptTypeNotIn;

    /**
     * 统一社会信用代码
     */
    private String deptUscNo, deptUscNoNot, deptUscNoLike, deptUscNoPrefix, deptUscNoSuffix;
    private List<String> deptUscNoIn, deptUscNoNotIn;

    /**
     * 所在地点编码
     */
    private String deptAddrNo, deptAddrNoNot, deptAddrNoLike, deptAddrNoPrefix, deptAddrNoSuffix;
    private List<String> deptAddrNoIn, deptAddrNoNotIn;
    /**
     * 是否同时拉取扩展属性
     */
    private boolean fetchExtAttr;
    /**
     * 查询扩展属性
     */
    private Map<String, Object> attrQuery;


    public Consumer<MPJLambdaWrapper<? extends T>> conditionDeptKey() {
        return (builder) -> {
            builder.eq(StrUtil.isNotBlank(this.getDeptKey()), VSysUser::getDeptKey, StrUtil.trim(this.getDeptKey()));
            builder.ne(StrUtil.isNotBlank(this.getDeptKeyNot()), T::getDeptKey, StrUtil.trim(this.getDeptKeyNot()));
            builder.in(null != this.getDeptKeyIn() && this.getDeptKeyIn().size() > 0, T::getDeptKey, this.getDeptKeyIn());
            builder.notIn(null != this.getDeptKeyNotIn() && this.getDeptKeyNotIn().size() > 0, T::getDeptKey, this.getDeptKeyNotIn());
            if (StrUtil.isNotBlank(this.getDeptKeyLike())) {
                builder.like(T::getDeptKey, StrUtil.trim(this.getDeptKeyLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getDeptKeyPrefix()), T::getDeptKey, StrUtil.trim(this.getDeptKeyPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getDeptKeySuffix()), T::getDeptKey, StrUtil.trim(this.getDeptKeySuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionDeptName() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getDeptName()), VSysUser::getDeptName, StrUtil.trim(this.getDeptName()));
            builder.ne(StrUtil.isNotBlank(this.getDeptNameNot()), T::getDeptName, StrUtil.trim(this.getDeptNameNot()));
            builder.in(null != this.getDeptNameIn() && this.getDeptNameIn().size() > 0, T::getDeptName, this.getDeptNameIn());
            builder.notIn(null != this.getDeptNameNotIn() && this.getDeptNameNotIn().size() > 0, T::getDeptName, this.getDeptNameNotIn());
            if (StrUtil.isNotBlank(this.getDeptNameLike())) {
                builder.like(T::getDeptName, StrUtil.trim(this.getDeptNameLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getDeptNamePrefix()), T::getDeptName, StrUtil.trim(this.getDeptNamePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getDeptNameSuffix()), T::getDeptName, StrUtil.trim(this.getDeptNameSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionDeptAlias() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getDeptAlias()), VSysUser::getDeptAlias, StrUtil.trim(this.getDeptAlias()));
            builder.ne(StrUtil.isNotBlank(this.getDeptAliasNot()), T::getDeptAlias, StrUtil.trim(this.getDeptAliasNot()));
            builder.in(null != this.getDeptAliasIn() && this.getDeptAliasIn().size() > 0, T::getDeptAlias, this.getDeptAliasIn());
            builder.notIn(null != this.getDeptAliasNotIn() && this.getDeptAliasNotIn().size() > 0, T::getDeptAlias, this.getDeptAliasNotIn());
            if (StrUtil.isNotBlank(this.getDeptAliasLike())) {
                builder.like(T::getDeptAlias, StrUtil.trim(this.getDeptAliasLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getDeptAliasPrefix()), T::getDeptAlias, StrUtil.trim(this.getDeptAliasPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getDeptAliasSuffix()), T::getDeptAlias, StrUtil.trim(this.getDeptAliasSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionDeptShortName() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getDeptShortName()), VSysUser::getDeptShortName, StrUtil.trim(this.getDeptShortName()));
            builder.ne(StrUtil.isNotBlank(this.getDeptShortNameNot()), T::getDeptShortName, StrUtil.trim(this.getDeptShortNameNot()));
            builder.in(null != this.getDeptShortNameIn() && this.getDeptShortNameIn().size() > 0, T::getDeptShortName, this.getDeptShortNameIn());
            builder.notIn(null != this.getDeptShortNameNotIn() && this.getDeptShortNameNotIn().size() > 0, T::getDeptShortName, this.getDeptShortNameNotIn());
            if (StrUtil.isNotBlank(this.getDeptShortNameLike())) {
                builder.like(T::getDeptShortName, StrUtil.trim(this.getDeptShortNameLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getDeptShortNamePrefix()), T::getDeptShortName, StrUtil.trim(this.getDeptShortNamePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getDeptShortNameSuffix()), T::getDeptShortName, StrUtil.trim(this.getDeptShortNameSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionDeptType() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getDeptType()), VSysUser::getDeptType, StrUtil.trim(this.getDeptType()));
            builder.ne(StrUtil.isNotBlank(this.getDeptTypeNot()), T::getDeptType, StrUtil.trim(this.getDeptTypeNot()));
            builder.in(null != this.getDeptTypeIn() && this.getDeptTypeIn().size() > 0, T::getDeptType, this.getDeptTypeIn());
            builder.notIn(null != this.getDeptTypeNotIn() && this.getDeptTypeNotIn().size() > 0, T::getDeptType, this.getDeptTypeNotIn());
            if (StrUtil.isNotBlank(this.getDeptTypeLike())) {
                builder.like(T::getDeptType, StrUtil.trim(this.getDeptTypeLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getDeptTypePrefix()), T::getDeptType, StrUtil.trim(this.getDeptTypePrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getDeptTypeSuffix()), T::getDeptType, StrUtil.trim(this.getDeptTypeSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionDeptUscNo() {
        return (builder) -> {

            builder.eq(StrUtil.isNotBlank(this.getDeptUscNo()), VSysUser::getDeptUscNo, StrUtil.trim(this.getDeptUscNo()));
            builder.ne(StrUtil.isNotBlank(this.getDeptUscNoNot()), T::getDeptUscNo, StrUtil.trim(this.getDeptUscNoNot()));
            builder.in(null != this.getDeptUscNoIn() && this.getDeptUscNoIn().size() > 0, T::getDeptUscNo, this.getDeptUscNoIn());
            builder.notIn(null != this.getDeptUscNoNotIn() && this.getDeptUscNoNotIn().size() > 0, T::getDeptUscNo, this.getDeptUscNoNotIn());
            if (StrUtil.isNotBlank(this.getDeptUscNoLike())) {
                builder.like(T::getDeptUscNo, StrUtil.trim(this.getDeptUscNoLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getDeptUscNoPrefix()), T::getDeptUscNo, StrUtil.trim(this.getDeptUscNoPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getDeptUscNoSuffix()), T::getDeptUscNo, StrUtil.trim(this.getDeptUscNoSuffix()));
            }


        };
    }

    public Consumer<MPJLambdaWrapper<? extends T>> conditionDeptAddrNo() {
        return (builder) -> {
            builder.eq(StrUtil.isNotBlank(this.getDeptAddrNo()), VSysUser::getDeptAddrNo, StrUtil.trim(this.getDeptAddrNo()));
            builder.ne(StrUtil.isNotBlank(this.getDeptAddrNoNot()), T::getDeptAddrNo, StrUtil.trim(this.getDeptAddrNoNot()));
            builder.in(null != this.getDeptAddrNoIn() && this.getDeptAddrNoIn().size() > 0, T::getDeptAddrNo, this.getDeptAddrNoIn());
            builder.notIn(null != this.getDeptAddrNoNotIn() && this.getDeptAddrNoNotIn().size() > 0, T::getDeptAddrNo, this.getDeptAddrNoNotIn());
            if (StrUtil.isNotBlank(this.getDeptAddrNoLike())) {
                builder.like(T::getDeptAddrNo, StrUtil.trim(this.getDeptAddrNoLike()));
            } else {
                builder.likeRight(StrUtil.isNotBlank(this.getDeptAddrNoPrefix()), T::getDeptAddrNo, StrUtil.trim(this.getDeptAddrNoPrefix()));
                builder.likeLeft(StrUtil.isNotBlank(this.getDeptAddrNoSuffix()), T::getDeptAddrNo, StrUtil.trim(this.getDeptAddrNoSuffix()));
            }
        };
    }

    protected Consumer<MPJLambdaWrapper<? extends T>> conditionExtAttr() {
        return (query) -> {
            if (this.fetchExtAttr || CollUtil.isNotEmpty(this.attrQuery)) {
                query.leftJoin(SysUserExtAttr.class, "ext", SysUserExtAttr::getId, VSysUser::getId)
                        .selectAs(SysUserExtAttr::getAttribution, VSysUser::getAttribution)
                ;
                if (this.attrQuery != null) {
                    this.attrQuery.forEach((k, v) -> {
                        String field;
                        if (StrUtil.endWithIgnoreCase(k, "Like")) {
                            field = StrUtil.removeSuffix(k, "Like");
                            query.apply(formatExt(field) + " like concat('%',{0},'%')", v);
                        } else if (StrUtil.endWithIgnoreCase(k, "Prefix")) {
                            field = StrUtil.removeSuffix(k, "Prefix");
                            query.apply(formatExt(field) + " like concat({0},'%')", v);
                        } else if (StrUtil.endWithIgnoreCase(k, "Suffix")) {
                            field = StrUtil.removeSuffix(k, "Suffix");
                            query.apply(formatExt(field) + " like concat('%',{0})", v);
                        } else if (StrUtil.endWithIgnoreCase(k, "Gt")) {
                            field = StrUtil.removeSuffix(k, "Gt");
                            query.apply(formatExt(field) + " > {0}", v);
                        } else if (StrUtil.endWithIgnoreCase(k, "Lt")) {
                            field = StrUtil.removeSuffix(k, "Lt");
                            query.apply(formatExt(field) + " < {0}", v);
                        } else if (StrUtil.endWithIgnoreCase(k, "Not")) {
                            field = StrUtil.removeSuffix(k, "Not");
                            query.apply(formatExt(field) + " <> {0}", v);
                        } else {
                            field = k;
                            query.apply(formatExt(field) + " = {0}", v);
                        }
                    });
                }
            }
        };

    }

    private String formatExt(String field) {
        return StrUtil.format("JSON_EXTRACT(ext.attribution_,'$.{}')", field);
    }


    @Override
    public Consumer<MPJLambdaWrapper<? extends T>> conditionBelongDeptId() {
        //这里构建条件方式要重写
        return query -> {
            String belongDeptId = StrUtil.blankToDefault(super.getBelongDeptId(), "0");
            if (Objects.nonNull(belongDeptId) && !Objects.equals("0", belongDeptId)) {
                SysDept queryDept = SpringUtil.getBean(SysDeptService.class).getById(belongDeptId);
                if (Objects.isNull(queryDept)) {
                    // 因为用户不存在，所以如果以当前的所属部门身份去进行检索，就是查不出来的
                    query.eq(VSysUser::getId, -1);
                } else {
                    query.likeRight(VSysUser::getNodePath, queryDept.getNodePath());
                }
            }
        };
    }

    @Override
    public <R extends T> MPJLambdaWrapper<R> buildQueryWrapper() {
        MPJLambdaWrapper<R> builder = super.buildQueryWrapper();
        builder.selectAll(VSysUser.class);
        this.conditionDeptKey().accept(builder);
        this.conditionDeptName().accept(builder);
        this.conditionDeptAlias().accept(builder);
        this.conditionDeptShortName().accept(builder);
        this.conditionDeptType().accept(builder);
        this.conditionDeptUscNo().accept(builder);
        this.conditionDeptAddrNo().accept(builder);
        this.conditionExtAttr().accept(builder);
        return builder;
    }
}
