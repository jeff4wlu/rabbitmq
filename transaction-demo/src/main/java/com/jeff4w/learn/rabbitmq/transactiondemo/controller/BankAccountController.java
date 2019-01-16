package com.jeff4w.learn.rabbitmq.transactiondemo.controller;

import com.jeff4w.learn.rabbitmq.transactiondemo.Service.BankAccountService;
import com.jeff4w.learn.rabbitmq.transactiondemo.common.ResponseResult;
import com.jeff4w.learn.rabbitmq.transactiondemo.common.RestResultGenerator;
import com.jeff4w.learn.rabbitmq.transactiondemo.common.Utils;
import com.jeff4w.learn.rabbitmq.transactiondemo.domain.BankAccount;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2019-01-15 20:32
 */
@RestController
@RequestMapping("/api/bankaccounts")
@Api("账号信息api")
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;

    /**
     * 显示所有
     * url:"http://localhost/student/findall"
     *
     * @return
     */
    @ApiOperation(value = "查询全部账号信息",notes = "查询全部账号信息")
    @ApiResponses({
            @ApiResponse(code=400,message = "请求参数没有填好"),
            @ApiResponse(code=404,message="请求路径没有找到")
    })
    @GetMapping(value = "")
    public ResponseResult<List<BankAccount>> findAllStudent() throws Exception {
        List<BankAccount> all = bankAccountService.findAllBankAccount();
        return RestResultGenerator.genSuccessResult(all);
    }


    /**
     * 查找 restful 风格
     * url:"http://localhost/student/findone/1"
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询账号的信息",notes = "查询数据库中某个账号的信息")
    @ApiImplicitParam(name ="id",value = "账号id",paramType = "path",required = true,dataType = "Long")
    @ApiResponses({
            @ApiResponse(code=400,message = "请求参数没有填好"),
            @ApiResponse(code=404,message="请求路径没有找到")
    })
    @GetMapping(value = "/{id}")
    public ResponseResult<BankAccount> findStudent(@PathVariable Long id) throws Exception {
        BankAccount student = bankAccountService.findBankAccountById(id).get();
        return RestResultGenerator.genSuccessResult(student);
    }


    /**
     * 删除 restful 风格
     * url:"http://localhost/student/deleteone/4"
     * 注意无法通过浏览器的链接来模拟检验,可以通过 jquery的 $.ajax方法，并type="delete"
     *
     * @param id
     */
    @ApiOperation(value = "根据id删除账号的信息",notes = "删除数据库中某个账号的信息")
    @ApiImplicitParam(name ="id",value = "账号id",paramType = "path",required = true,dataType = "Long")
    @ApiResponses({
            @ApiResponse(code=400,message = "请求参数没有填好"),
            @ApiResponse(code=404,message="请求路径没有找到")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseResult deleteStudent(@PathVariable Long id) throws Exception {
        bankAccountService.delBankAccountById(id);
        return RestResultGenerator.genSuccessResult();
    }


    /**
     * 增加 restful 风格
     * url:"http://localhost/student/addone"
     * 通过<form>表单模拟验证
     *
     * @param student
     */
    @ApiOperation(value = "新增账号的信息",notes = "新增数据库中某个账号的全部信息")
    @ApiImplicitParam(name ="bankAccount",value = "账号实体对象",paramType = "body",required = true,dataType = "BankAccount")
    @ApiResponses({
            @ApiResponse(code=400,message = "请求参数没有填好"),
            @ApiResponse(code=404,message="请求路径没有找到")
    })
    @PostMapping(value = "")
    public ResponseResult<BankAccount> addBankAccount(@Valid @RequestBody BankAccount bankAccount) throws Exception {
        BankAccount save = bankAccountService.addBankAccount(bankAccount);
        return RestResultGenerator.genSuccessResult(save);
    }


    /**
     * 修改 restful 风格
     * url:"http://localhost/student/updateone"
     * 验证：可以通过 jquery的 $.ajax方法，并type="put",同时注意data形式——A=a&B=b&C=c
     *
     * @param student
     */
    @ApiOperation(value = "根据id更新账号的全部信息",notes = "更新数据库中某个账号的全部信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "账号id",dataType = "Long",paramType = "path",example = "1112"),
            @ApiImplicitParam(name = "newBankAccount",value = "账号实体对象",dataType = "BankAccount",paramType = "body",example = "1112")
    })
    @ApiResponses({
            @ApiResponse(code=400,message = "请求参数没有填好"),
            @ApiResponse(code=404,message="请求路径没有找到")
    })
    @PutMapping(value = "/{id}")
    public ResponseResult<BankAccount> updateStudentAll(@PathVariable Long id, @Valid @RequestBody BankAccount newBankAccount) throws Exception {
        BankAccount bankAccount = bankAccountService.findBankAccountById(id).get();
        // copy all new user props to user except id
        BeanUtils.copyProperties(newBankAccount, bankAccount, "id");
        bankAccount = bankAccountService.addBankAccount(bankAccount);

        return RestResultGenerator.genSuccessResult(bankAccount);
    }

    @ApiOperation(value = "根据id更新账号的部分信息",notes = "更新数据库中某个账号的部分信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "账号id",dataType = "Long",paramType = "path",example = "1112"),
            @ApiImplicitParam(name = "newBankAccount",value = "账号实体对象",dataType = "BankAccount",paramType = "body",example = "1112")
    })
    @ApiResponses({
            @ApiResponse(code=400,message = "请求参数没有填好"),
            @ApiResponse(code=404,message="请求路径没有找到")
    })
    @PatchMapping(value = "/{id}")
    public ResponseResult<BankAccount> update(@PathVariable Long id, @Valid @RequestBody BankAccount newBankAccount) throws Exception {
        BankAccount bankAccount = bankAccountService.findBankAccountById(id).get();
        // copy all new user props to user except null props
        BeanUtils.copyProperties(newBankAccount, bankAccount, Utils.getNullPropertyNames(newBankAccount));
        bankAccount = bankAccountService.addBankAccount(bankAccount);

        return RestResultGenerator.genSuccessResult(bankAccount);
    }
}
