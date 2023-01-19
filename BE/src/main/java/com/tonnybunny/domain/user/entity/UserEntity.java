package com.tonnybunny.domain.user.entity;


import com.tonnybunny.common.CommonEntity;
import com.tonnybunny.domain.ytonny.entity.YTonnyNotiEntity;
import com.tonnybunny.domain.ytonny.entity.YTonnyNotiHelperEntity;
import com.tonnybunny.domain.ytonny.entity.YTonnyResultEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends CommonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_seq")
	private Long seq;

	private String email;
	private String password;
	private String phoneNumber;
	private String nickName;

	private Long point;
	private int reportCount;

	private String profileImagePath;

	private String userCode;

	// 연결
	@OneToMany(mappedBy = "user")
	private List<PossibleLanguageEntity> possibleLanguageList = new ArrayList<>(); // 사용언어

	@OneToMany(mappedBy = "user")
	private List<CertificateEntity> certificateList = new ArrayList<>(); // 자격증

	@OneToMany(mappedBy = "user")
	private List<BlockEntity> blockUserList = new ArrayList<>(); // 차단한 유저

	@OneToMany(mappedBy = "following")
	private List<FollowEntity> followingList = new ArrayList<>(); // 팔로잉 목록

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	private HelperInfoEntity helperInfo;                            // 헬퍼 정보

	// 예약통역
	@OneToMany(mappedBy = "client")
	private List<YTonnyNotiEntity> yTonnyNotiList = new ArrayList<>(); // 예약통역공고리스트

	@OneToMany(mappedBy = "helper")
	private List<YTonnyNotiHelperEntity> yTonnyHelperList = new ArrayList<>(); // 예약통역공고신청자리스트

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "client")
	private YTonnyResultEntity yTonnyResultClient; // 예약통역 결과 고객

	//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "helper")
	//	private YTonnyResultEntity yTonnyResultHelper; // 예약통역 결과 헬퍼

}
