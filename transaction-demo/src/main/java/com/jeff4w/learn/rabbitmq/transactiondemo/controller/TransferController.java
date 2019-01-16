package com.jeff4w.learn.rabbitmq.transactiondemo.controller;

import com.jeff4w.learn.rabbitmq.transactiondemo.Service.BankAccountService;
import com.jeff4w.learn.rabbitmq.transactiondemo.Service.TransactionService;
import com.jeff4w.learn.rabbitmq.transactiondemo.common.ResponseResult;
import com.jeff4w.learn.rabbitmq.transactiondemo.common.RestResultGenerator;
import com.jeff4w.learn.rabbitmq.transactiondemo.common.Utils;
import com.jeff4w.learn.rabbitmq.transactiondemo.domain.BankAccount;
import com.jeff4w.learn.rabbitmq.transactiondemo.domain.Transaction;
import com.jeff4w.learn.rabbitmq.transactiondemo.dto.TransferDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2019-01-16 14:50
 */
@RestController
@RequestMapping("/api/transfer")
@Api("转账服务api")
public class TransferController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TransactionService transactionService;

    @ApiOperation(value = "转账服务",notes = "转账服务")
    @ApiResponses({
            @ApiResponse(code=400,message = "请求参数没有填好"),
            @ApiResponse(code=404,message="请求路径没有找到")
    })
    @PostMapping(value = "")
    public ResponseResult transfer(@RequestBody TransferDto dto) throws Exception {
        Boolean bOk = bankAccountService.transfer(dto.getFromId(), dto.getToId(), dto.getMoney());
        if(bOk){
            return RestResultGenerator.genSuccessResult();
        }

        return RestResultGenerator.genErrorResult("存款不足以转账");

    }
}
