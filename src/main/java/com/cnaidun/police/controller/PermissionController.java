package com.cnaidun.police.controller;

import com.cnaidun.police.config.tip.ErrorTip;
import com.cnaidun.police.config.tip.SuccessTip;
import com.cnaidun.police.config.tip.Tip;
import com.cnaidun.police.dto.AddPermissionRequestDTO;
import com.cnaidun.police.dto.PermissionResourceType;
import com.cnaidun.police.interceptor.ratelimit.RateLimit;
import com.cnaidun.police.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author dongyin
 */
@Api(description = "后台权限菜单相关api")
@Slf4j
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    PermissionService permissionService;

    @ApiOperation("查询权限菜单按钮")
    @PostMapping("/findAllPermissions")
    public Tip findAllPermissions(){
        return new SuccessTip(permissionService.findAllPermissions());
    }

    @ApiOperation("查询所有被选中的权限按钮id")
    @PostMapping("/findSelectedPermissionId")
    public Tip findSelectedPermissionId(@RequestParam Integer roleId){
        return new SuccessTip(permissionService.findSelectedPermissionId(roleId));
    }

    /**
     * @Author gzw
     * @Description 根据菜单名分页获取菜单分页
     * @Date 2019/7/2 16:21
     * @Param [pageNo 页数, pageSize 页大小, menuName 菜单名,允许后模糊查询]
     * @return com.cnaidun.police.config.tip.Tip
     **/
    @ApiOperation("根据菜单名分页获取菜单列表")
    @PostMapping("/findPermissionByMenuName")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "页数",required = true,dataType = "Integer" ),
            @ApiImplicitParam(name = "pageSize",value = "页大小",required = true,dataType = "Integer" ),
            @ApiImplicitParam(name = "menuName",value = "菜单名称(支持后模糊查询)",dataType = "String" )
    })
    public Tip findPermissionByMenuName(@RequestParam Integer pageNo,@RequestParam Integer pageSize,String menuName){
        return new SuccessTip(permissionService.findPermissionByMenuName(pageNo, pageSize, menuName));
    }

    /**
     * @Author gzw
     * @Description 添加或修改菜单
     * @Date 2019/7/2 17:53
     * @Param [addPermissionRequestDTO, bindingResult]
     * @return com.cnaidun.police.config.tip.Tip
     **/
    @ApiOperation("添加或修改菜单")
    @PostMapping("/addOrUpdatePermission")
    @RateLimit(lockName = "addOrUpdatePermission" ,keys = "addPermissionRequestDTO.name")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "菜单id，修改时传参",dataType = "Integer"),
            @ApiImplicitParam(name = "name",value = "菜单名",required = true,dataType = "String"),
            @ApiImplicitParam(name = "resourceType",value = "资源类型，[menu|button]",required = true,dataType = "String"),
            @ApiImplicitParam(name = "url",value = "资源路径",dataType = "String"),
            @ApiImplicitParam(name = "permission",value = "权限字段，ex  see:all",required = true,dataType = "String"),
            @ApiImplicitParam(name = "parentId",value = "父编号(无父编号，传0)",required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "buttonType",value = "按钮类型，ex:danger,primary",dataType = "String"),
            @ApiImplicitParam(name = "icon",value = "一级菜单icon",dataType = "String")
    })
    public Tip addOrUpdatePermission(@Valid @ApiIgnore AddPermissionRequestDTO addPermissionRequestDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ErrorTip(bindingResult.getFieldError().getDefaultMessage());
        }
        //判断resourceType字段，只能为button或menu
        if(!addPermissionRequestDTO.getResourceType().equals(PermissionResourceType.BUTTON)&&!addPermissionRequestDTO.getResourceType().equals(PermissionResourceType.MENU)){
            return new ErrorTip("资源类型非法");
        }
        boolean effectLine = permissionService.addOrUpdatePermission(addPermissionRequestDTO);
        if(effectLine){
            return new SuccessTip();
        }else{
            return new ErrorTip("操作失败");
        }
    }


    /**
     * @Author gzw
     * @Description 删除菜单
     * @Date 2019/7/2 17:53
     * @Param [addPermissionRequestDTO, bindingResult]
     * @return com.cnaidun.police.config.tip.Tip
     **/
    @ApiOperation("删除菜单")
    @PostMapping("/deletePermission")
    @ApiImplicitParam(name = "id",value = "菜单id",required = true,dataType = "Integer")
    public Tip deletePermission(@RequestParam Integer id){
        boolean result = permissionService.deletePermission(id);
        if(result){
            return new SuccessTip();
        }else{
            return new ErrorTip("删除失败");
        }
    }
}
