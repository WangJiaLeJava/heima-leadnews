package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.user.model.common.dtos.ResponseResult;
import com.heima.user.model.user.dtos.LoginDto;
import com.heima.user.model.user.pojos.ApUser;
import com.mysql.fabric.Response;
import org.apache.zookeeper.Login;
import org.junit.validator.PublicClassValidator;

public interface ApUserService  extends IService<ApUser> {
    /**
     * app端登陆功能
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);
}
