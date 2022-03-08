package com.he.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.he.exception.HeException;
import com.he.ucenter.entity.Member;
import com.he.ucenter.entity.vo.MemberVo;
import com.he.ucenter.mapper.MemberMapper;
import com.he.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.he.utils.JwtUtils;
import com.he.utils.MD5;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表; InnoDB free: 8192 kB 服务实现类
 * </p>
 *
 * @author hechunyu
 * @since 2022-03-01
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public String login(MemberVo memberVo) {

        String mobile = memberVo.getMobile();
        String password = memberVo.getPassword();
        //判断前台输入过来的是否为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new HeException(20001, "登录失败");
        }
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Member member = baseMapper.selectOne(wrapper);
        if (member == null) {
            throw new HeException(20001, "登录失败");
        }
        if (!MD5.encrypt(password).equals(member.getPassword())) {
            throw new HeException(20001, "登录失败");
        }
        //校验是否被禁用
        if (member.getIsDisabled()) {
            throw new HeException(20001, "登录失败");
        }
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    @Override
    public boolean register(MemberVo memberVo) {
        String nickname = memberVo.getNickname();
        String mobile = memberVo.getMobile();
        String password = memberVo.getPassword();
        String code = memberVo.getCode();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(code)) {
            throw new HeException(20001, "注册失败");
        }
        //如果手机号已经被注册注册失败
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new HeException(20001, "手机号已被注册 ,注册失败 ");
        }
        //查询redis中验证码 是否和输入的一样
        String s = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(s)) {
            throw new HeException(20001, "验证码错误，注册失败 ");
        }
        Member member = new Member();
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        baseMapper.insert(member);
        return true;
    }

    @Override
    public Member getOpenidMember(String openid) {
        QueryWrapper <Member> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        Member member = baseMapper.selectOne(queryWrapper);
        return member;
    }
    @Override
    public Integer selectRegist(String day) {

       Integer num = baseMapper.selectRegist(day);

        return num ;
    }


}
