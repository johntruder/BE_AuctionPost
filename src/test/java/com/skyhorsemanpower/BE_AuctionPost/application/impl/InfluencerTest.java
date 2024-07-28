package com.skyhorsemanpower.BE_AuctionPost.application.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.skyhorsemanpower.BE_AuctionPost.GenerateRandom;
import com.skyhorsemanpower.BE_AuctionPost.application.InfluencerService;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerDetailResponseDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerSearchResponseDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerSummariesRequestDto;
import com.skyhorsemanpower.BE_AuctionPost.data.dto.InfluencerSummaryDto;
import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command.Influencer;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.command.InfluencerRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class InfluencerTest {

    private InfluencerService influencerService;
    private InfluencerRepository influencerRepository = Mockito.mock(InfluencerRepository.class);

    @BeforeEach
    public void setUp() {
        influencerService = new InfluencerServiceImpl(influencerRepository);
    }

    @Test
    @DisplayName("인플루언서 조회 테스트")
    public void findInfluencerTest() {
        // given
        String expectedUuid = "expectedUuid";
        String expectedName = "expectedName";
        String expectedPhoneNum = "expectedPhoneNum";
        String expectedProfileImage = "expectedProfileImage";
        String expectedDescription = "expectedDescription";

        Influencer expectedInfluencer = Influencer.builder()
            .uuid(expectedUuid)
            .name(expectedName)
            .phoneNum(expectedPhoneNum)
            .profileImage(expectedProfileImage)
            .description(expectedDescription)
            .build();

        Mockito.when(influencerRepository.findByUuid(expectedUuid))
            .thenReturn(Optional.of(expectedInfluencer));
        // when
        InfluencerDetailResponseDto influencerDetailResponseDto = influencerService.findInfluencer(
            "expectedUuid");
        // then
        assertEquals(expectedUuid, influencerDetailResponseDto.getInfluencerUuid());
    }

    @Test
    @DisplayName("인플루언서 검색 테스트")
    public void searchTest() {
        // given
        String expectedUuid = "expectedUuid";
        String expectedName = "expectedName";
        String expectedProfileImage = "expectedProfileImage";

        Influencer expectedInfluencer = Influencer.builder()
            .uuid(expectedUuid)
            .name(expectedName)
            .profileImage(expectedProfileImage)
            .build();

        Mockito.when(influencerRepository.findByNameContaining(expectedName))
            .thenReturn(List.of(expectedInfluencer));
        // when
        List<InfluencerSearchResponseDto> searchedInfluencers = influencerService.searchInfluencer(
            expectedName);
        // then
        assertFalse(searchedInfluencers.isEmpty(),
            "The list of searched influencers should not be empty");
        assertEquals(searchedInfluencers.get(0), searchedInfluencers.get(0));
    }

    @Test
    @DisplayName("인플루언서 uuid 리스트로 인플루언서 요약 정보들을 조회한다.")
    void getInfluencerSummariseTest() {
        List<String> influencerUuids = List.of("influencer1", "influencer2", "influencer3");

        InfluencerSummariesRequestDto influencerSummariesRequestDto = InfluencerSummariesRequestDto.builder()
            .influencerUuids(influencerUuids).build();

        List<Influencer> influencers = List.of(
            Influencer.builder()
                .name("장원영")
                .profileImage("https://jwy.png")
                .phoneNum("010-1234-5678")
                .birthDate(LocalDate.of(2004, 8, 31))
                .description("공주")
                .build(),
            Influencer.builder()
                .name("카리나")
                .profileImage("https://karina.png")
                .phoneNum("010-1234-5678")
                .birthDate(LocalDate.of(2000, 4, 11))
                .description("여신")
                .build(),
            Influencer.builder()
                .name("설윤")
                .profileImage("https://sy.png")
                .phoneNum("010-1234-5678")
                .birthDate(LocalDate.of(2004, 1, 26))
                .description("요정")
                .build()
        );

        Mockito.when(influencerRepository.findByUuidIn(influencerUuids)).thenReturn(influencers);

        List<InfluencerSummaryDto> influencerSummaryDtos = influencerService.getInfluencerSummaries(
            influencerSummariesRequestDto);

        assertThat(influencerSummaryDtos.size()).isEqualTo(influencerUuids.size());

        for (int i = 0; i < influencerSummaryDtos.size(); i++) {
            InfluencerSummaryDto influencerSummaryDto = influencerSummaryDtos.get(i);
            assertThat(influencerSummaryDto.getName()).isEqualTo(influencers.get(i).getName());
            assertThat(influencerSummaryDto.getProfileImage()).isEqualTo(
                influencers.get(i).getProfileImage());
            assertThat(influencerSummaryDto.getBirthDate()).isEqualTo(
                influencers.get(i).getBirthDate());
            assertThat(influencerSummaryDto.getDescription()).isEqualTo(
                influencers.get(i).getDescription());
        }
    }

    @Test
    @DisplayName("모든 인플루언서를 조회할때 1명 이상있으면 리스트에 담아 반환한다.")
    void getAllInfluencersAtLeastOneTest() {
        List<Influencer> influencers = List.of(
            Influencer.builder()
                .uuid(GenerateRandom.influencerUuid())
                .name("아이유")
                .profileImage("https://iu.png")
                .birthDate(LocalDate.of(1993, 5, 16))
                .description("국힙원탑")
                .build(),
            Influencer.builder()
                .uuid(GenerateRandom.influencerUuid())
                .name("장원영")
                .profileImage("https://jwy.png")
                .birthDate(LocalDate.of(2004, 8, 31))
                .description("공주님")
                .build()
        );

        Mockito.when(influencerRepository.findAll()).thenReturn(influencers);

        List<InfluencerDetailResponseDto> influencerDetailResponseDtos = influencerService.getAllInfluencers();

        assertThat(influencerDetailResponseDtos.size()).isEqualTo(influencers.size());
        for (int i = 0; i < influencerDetailResponseDtos.size(); i++) {
            assertThat(influencerDetailResponseDtos.get(i).getInfluencerUuid()).isEqualTo(influencers.get(i).getUuid());
            assertThat(influencerDetailResponseDtos.get(i).getName()).isEqualTo(influencers.get(i).getName());
            assertThat(influencerDetailResponseDtos.get(i).getProfileImage()).isEqualTo(
                influencers.get(i).getProfileImage());
            assertThat(influencerDetailResponseDtos.get(i).getBirth()).isEqualTo(influencers.get(i).getBirthDate());
            assertThat(influencerDetailResponseDtos.get(i).getDescription()).isEqualTo(influencers.get(i).getDescription());
        }
    }

    @Test
    @DisplayName("모든 인플루언서를 조회할때 등록된 인플루언서가 없으면 빈 리스트를 반환한다.")
    void getAllInfluencersEmptyListTest() {
        List<Influencer> influencers = List.of();

        Mockito.when(influencerRepository.findAll()).thenReturn(influencers);

        List<InfluencerDetailResponseDto> influencerDetailResponseDtos = influencerService.getAllInfluencers();

        assertTrue(influencerDetailResponseDtos.isEmpty());
    }
}
