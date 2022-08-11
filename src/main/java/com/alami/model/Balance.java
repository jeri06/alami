package com.alami.model;

public class Balance {
  private String id;
  private int averageBalance;
  private long noThread;
  private int balance;
  private int freeTransfer;
  public int getFreeTransfer() {
    return freeTransfer;
  }
  public void setFreeTransfer(int freeTransfer) {
    this.freeTransfer = freeTransfer;
  }
  public int getBalance() {
    return balance;
  }
  public void setBalance(int balance) {
    this.balance = balance;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public int getAverageBalance() {
    return averageBalance;
  }
  public void setAverageBalance(int averageBalance) {
    this.averageBalance = averageBalance;
  }
  public long getNoThread() {
    return noThread;
  }
  public void setNoThread(long noThread) {
    this.noThread = noThread;
  }
}
