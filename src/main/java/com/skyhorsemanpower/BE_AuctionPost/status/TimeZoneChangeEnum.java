package com.skyhorsemanpower.BE_AuctionPost.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimeZoneChangeEnum {
    KOREA(9);
    private int timeDiff;

    TimeZoneChangeEnum(int timeDiff) {
        this.timeDiff = timeDiff;
    }
}
