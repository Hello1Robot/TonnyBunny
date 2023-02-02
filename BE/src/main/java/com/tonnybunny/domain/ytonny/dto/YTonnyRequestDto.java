package com.tonnybunny.domain.ytonny.dto;


import com.tonnybunny.common.dto.LangCodeEnum;
import com.tonnybunny.common.dto.TonnySituCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


/**
 * clientSeq                : 고객 seq
 *
 * title                    : 제목
 * estimateDate             : 요청 날짜
 * estimateStartTime        : 요청 시간
 * estimateTime             : 예상 소요 시간
 * estimatePrice            : 의뢰 금액 (null)
 * content                  : 내용 (null)
 *
 * startLangCode            : 시작 언어 코드
 * endLangCode              : 종료 언어 코드
 * tonnySituCode            : 통역상황 카테고리 코드 (null)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class YTonnyRequestDto {

	private int size = 10; // limit
	private int page = 0; // offset

	private Long clientSeq;

	private String title;
	private String content;

	private LocalDate estimateDate;
	private LocalTime estimateStartTime;
	private LocalTime estimateTime;
	private Integer estimatePrice;

	private LangCodeEnum startLangCode;
	private LangCodeEnum endLangCode;
	private TonnySituCodeEnum tonnySituCode;

}