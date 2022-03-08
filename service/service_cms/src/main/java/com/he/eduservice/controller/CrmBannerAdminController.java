package com.he.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.he.eduservice.entity.CrmBanner;
import com.he.eduservice.service.impl.CrmBannerServiceImpl;
import com.he.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 首页banner表; InnoDB free: 8192 kB 前端控制器
 * </p>
 *
 * @author hechunyu
 * @since 2022-02-28
 */
@RestController
@RequestMapping("/cmsservice/admincrmbanner")
@CrossOrigin
public class CrmBannerAdminController {
    @Resource
    private CrmBannerServiceImpl service;

    //分页查询
    @GetMapping("selectPage/{current}/{limit}")
    public R selectPage(@PathVariable long current, @PathVariable long limit) {
        Page<CrmBanner> page = new Page<>(current, limit);
        service.page(page, null);
        return R.ok().data("items", page.getRecords()).data("total", page.getTotal());
    }

    //修改
    @PostMapping("update")
    public R updateCms(@RequestBody CrmBanner crmBanner) {
        ;
        service.updateById(crmBanner);
        return R.ok();
    }
    //删除
    @DeleteMapping("delete/{id}")
    public R deleteCms(@PathVariable String id ){
        service.removeById(id);
        return R.ok();
    }
    //通过id查询
    @GetMapping("selectById/{id}")
    public R selectById (@PathVariable String id ){
        CrmBanner crmBanner = service.getById(id);
        return R.ok().data("item",crmBanner);
    }
    //新增
    @PostMapping("addBanner")
    public R insertBanner(@RequestBody  CrmBanner crmBanner){
        service.save(crmBanner);
        return R.ok();
    }

}

