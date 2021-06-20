package com.cool.crm.controller;

import com.cool.crm.service.UserRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("user_role")
public class UserRoleController {

    @Resource
    private UserRoleService userRoleService;
}
