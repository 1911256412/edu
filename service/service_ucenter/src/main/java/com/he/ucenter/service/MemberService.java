package com.he.ucenter.service;

import com.he.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.he.ucenter.entity.vo.MemberVo;

/**
 * <p>
 * 会员表; InnoDB free: 8192 kB 服务类
 * </p>
 *
 * @author hechunyu
 * @since 2022-03-01
 */
public interface MemberService extends IService<Member> {

    String login(MemberVo memberVo);


    boolean register(MemberVo memberVo);

    Member  getOpenidMember(String openid);

    Integer selectRegist(String day);
}
