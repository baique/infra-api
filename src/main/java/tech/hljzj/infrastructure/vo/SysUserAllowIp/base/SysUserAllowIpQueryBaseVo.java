package tech.hljzj.infrastructure.vo.SysUserAllowIp.base;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.lang.*;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import tech.hljzj.infrastructure.domain.SysUserAllowIp;
import tech.hljzj.framework.pojo.form.PageDomain;

/**
 * 用户IP绑定信息 sys_user_allow_ip_ 
 * 交互实体 用于检索
 *
 * @author wa
 */
@Getter
@Setter
public class SysUserAllowIpQueryBaseVo<T extends SysUserAllowIp> extends PageDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    /** id_     */
    private String id,idNot,idLike,idPrefix,idSuffix;
    private List<String> idIn,idNotIn;

    /** 用户标识     */
    private String userId,userIdNot,userIdLike,userIdPrefix,userIdSuffix;
    private List<String> userIdIn,userIdNotIn;

    /** 绑定IP     */
    private String allowIp,allowIpNot,allowIpLike,allowIpPrefix,allowIpSuffix;
    private List<String> allowIpIn,allowIpNotIn;


    
    public Consumer<LambdaQueryWrapper<? extends T>> conditionId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getId()),T::getId, StrUtil.trim(this.getId()));
          builder.ne(StrUtil.isNotBlank(this.getIdNot()),T::getId, StrUtil.trim(this.getIdNot()));
          builder.in(null != this.getIdIn() && this.getIdIn().size() > 0,T::getId, this.getIdIn());
          builder.notIn(null != this.getIdNotIn() && this.getIdNotIn().size() > 0,T::getId, this.getIdNotIn());
          if (StrUtil.isNotBlank(this.getIdLike())) {
              builder.like(T::getId, StrUtil.trim(this.getIdLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getIdPrefix()),T::getId, StrUtil.trim(this.getIdPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getIdSuffix()),T::getId, StrUtil.trim(this.getIdSuffix()));
          }


        };
    }

    public Consumer<LambdaQueryWrapper<? extends T>> conditionUserId() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getUserId()),T::getUserId, StrUtil.trim(this.getUserId()));
          builder.ne(StrUtil.isNotBlank(this.getUserIdNot()),T::getUserId, StrUtil.trim(this.getUserIdNot()));
          builder.in(null != this.getUserIdIn() && this.getUserIdIn().size() > 0,T::getUserId, this.getUserIdIn());
          builder.notIn(null != this.getUserIdNotIn() && this.getUserIdNotIn().size() > 0,T::getUserId, this.getUserIdNotIn());
          if (StrUtil.isNotBlank(this.getUserIdLike())) {
              builder.like(T::getUserId, StrUtil.trim(this.getUserIdLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getUserIdPrefix()),T::getUserId, StrUtil.trim(this.getUserIdPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getUserIdSuffix()),T::getUserId, StrUtil.trim(this.getUserIdSuffix()));
          }


        };
    }

    public Consumer<LambdaQueryWrapper<? extends T>> conditionAllowIp() {
        return (builder)->{

          builder.eq(StrUtil.isNotBlank(this.getAllowIp()),T::getAllowIp, StrUtil.trim(this.getAllowIp()));
          builder.ne(StrUtil.isNotBlank(this.getAllowIpNot()),T::getAllowIp, StrUtil.trim(this.getAllowIpNot()));
          builder.in(null != this.getAllowIpIn() && this.getAllowIpIn().size() > 0,T::getAllowIp, this.getAllowIpIn());
          builder.notIn(null != this.getAllowIpNotIn() && this.getAllowIpNotIn().size() > 0,T::getAllowIp, this.getAllowIpNotIn());
          if (StrUtil.isNotBlank(this.getAllowIpLike())) {
              builder.like(T::getAllowIp, StrUtil.trim(this.getAllowIpLike()));
          } else {
              builder.likeRight(StrUtil.isNotBlank(this.getAllowIpPrefix()),T::getAllowIp, StrUtil.trim(this.getAllowIpPrefix()));
              builder.likeLeft(StrUtil.isNotBlank(this.getAllowIpSuffix()),T::getAllowIp, StrUtil.trim(this.getAllowIpSuffix()));
          }


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
        this.conditionUserId().accept(builder);
        this.conditionAllowIp().accept(builder);
        this.defaultSortBy().accept(builder);
        return builder;
    }

}