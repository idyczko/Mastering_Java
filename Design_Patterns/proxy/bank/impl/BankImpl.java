package proxy.bank.impl;

import proxy.bank.Bank;

public final class BankImpl implements Bank {
  private Integer account;

  public BankImpl(Integer account) {
    this.account = account;
  }

  public Integer getAccount() {
    return this.account;
  }

  public void setAccount(Integer account) {
    this.account = account;
  }
}