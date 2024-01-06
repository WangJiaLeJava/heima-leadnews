package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.model.common.dtos.ResponseResult;
import com.heima.user.model.common.enums.AppHttpCodeEnum;
import com.heima.user.model.user.dtos.LoginDto;
import com.heima.user.model.user.pojos.ApUser;
import com.heima.user.service.ApUserService;
import com.heima.user.utils.common.AppJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.Map;


@Service
@Transactional
@Slf4j
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {

    /**
     * app端登陆功能
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult login(LoginDto dto) {
        log.info("看看传入对象的数据{}",dto);
        if (StringUtils.isNotBlank(dto.getPhone())&&StringUtils.isNotBlank(dto.getPassword())){
            //1.是否判断为空
            ApUser dbUser = getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, dto.getPhone()));
            if (dbUser ==null){
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户信息不存在");
            }
            //1.2 比对密码
            String salt=dbUser.getSalt();
            log.info("看看盐{}",salt);
            String password=dto.getPassword();
            log.info("看看password值{}",password);
            String pswd= DigestUtils.md5DigestAsHex((password+salt).getBytes());
            log.info("看看盐{}",pswd);
            if (!pswd.equals(dbUser.getPassword())){
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }
            String token = AppJwtUtil.getToken(dbUser.getId().longValue());
            Map<String,Object>map=new HashMap<>();
            map.put("token",token);
            dbUser.setSalt("");
            dbUser.setPassword("");
            map.put("user",dbUser);
            return ResponseResult.okResult(map);
        }else {
            /**
             * 游客登陆
             */
            Map<String,Object>map=new HashMap<>();
            map.put("token",AppJwtUtil.getToken(0L));
        }
        return null;
    }
}
