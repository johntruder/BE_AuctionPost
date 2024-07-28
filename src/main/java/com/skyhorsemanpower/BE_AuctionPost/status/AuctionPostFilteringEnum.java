package com.skyhorsemanpower.BE_AuctionPost.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuctionPostFilteringEnum {
    // 모든 경매글 검색을 위해 선언한 필드
    ALL_AUCTION,
    // 경매 진행 전
    BEFORE_AUCTION,
    // 경매 진행 중
    AUCTION_IS_IN_PROGRESS,
    // 경매 정상 종료
    AUCTION_NORMAL_CLOSING,
    // 경매 참여자 없음
    AUCTION_NO_PARTICIPANTS,
    // 경매 비정상 종료
    AUCTION_ABNORMAL_CLOSING
}
