package com.skyhorsemanpower.BE_AuctionPost.data.vo;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class CreateAuctionPostRequestVo {
    private String influencerUuid;
    private String influencerName;
    private String title;
    private String content;
    private int numberOfEventParticipants;
    private String localName;
    private String eventPlace;
    private LocalDateTime eventStartTime; // KST
    private LocalDateTime eventCloseTime; // KST
    private LocalDateTime auctionStartTime; // KST
    private BigDecimal startPrice;
    private BigDecimal incrementUnit;

    private String thumbnail;
    private List<String> images;
}
