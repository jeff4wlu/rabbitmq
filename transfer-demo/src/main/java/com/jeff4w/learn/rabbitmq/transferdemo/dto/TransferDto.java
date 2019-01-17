package com.jeff4w.learn.rabbitmq.transferdemo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2019-01-16 15:00
 */
@Data
@ApiModel("转账服务输入类")
public class TransferDto {
    @ApiModelProperty("车场id")
    public Long fromId ;

    @ApiModelProperty("")
    public Long toId ;

    @ApiModelProperty("")
    public BigDecimal money ;
}
