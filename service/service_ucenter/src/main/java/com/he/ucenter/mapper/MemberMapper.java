package com.he.ucenter.mapper;

import com.he.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表; InnoDB free: 8192 kB Mapper 接口
 * </p>
 *
 * @author hechunyu
 * @since 2022-03-01
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer selectRegist(String day);
}
