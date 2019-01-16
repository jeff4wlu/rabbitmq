package com.jeff4w.learn.rabbitmq.transactiondemo.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Lu Weijian
 * @description 备注
 * @email lwj@kapark.cn
 * @date 2019-01-15 20:07
 */
@Entity
@Table(name = "t_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date createDate;
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal moneyTransfer;
    private String remark;

    public Long getId() {
        return id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigDecimal getMoneyTransfer() {
        return moneyTransfer;
    }

    public void setMoneyTransfer(BigDecimal moneyTransfer) {
        this.moneyTransfer = moneyTransfer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
