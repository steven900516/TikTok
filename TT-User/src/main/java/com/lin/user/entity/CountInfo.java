package com.lin.user.entity;


import lombok.Data;

@Data
public class CountInfo {
    private int loveCountTotal;

    private int friendCountTotal;

    private int followCountTotal;

    private int FansCountTotal;


    public CountInfo(int loveCountTotal, int friendCountTotal, int followCountTotal, int fansCountTotal) {
        this.loveCountTotal = loveCountTotal;
        this.friendCountTotal = friendCountTotal;
        this.followCountTotal = followCountTotal;
        FansCountTotal = fansCountTotal;
    }

    public CountInfo() {
    }
}
