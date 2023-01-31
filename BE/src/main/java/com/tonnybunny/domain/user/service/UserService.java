package com.tonnybunny.domain.user.service;


import com.tonnybunny.common.jwt.dto.TokenResponseDto;
import com.tonnybunny.common.jwt.entity.AuthEntity;
import com.tonnybunny.common.jwt.repository.AuthRepository;
import com.tonnybunny.common.jwt.service.JwtService;
import com.tonnybunny.domain.user.dto.*;
import com.tonnybunny.domain.user.entity.HistoryEntity;
import com.tonnybunny.domain.user.entity.UserEntity;
import com.tonnybunny.domain.user.repository.HistoryRepository;
import com.tonnybunny.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final HistoryRepository historyRepository;
	private final JwtService jwtService;
	private final AuthRepository authRepository;
	private final PasswordEncoder passwordEncoder;

	//	public UserEntity signup(UserRequestDto userRequestDto) {
	//		UserEntity user = userRequestDto.toEntity();
	//		/**
	//		 * repository 에서 회원가입 절차를 마치고 userEntity(또는 seq) 를 반환해준다.
	//		 * UserEntity savedUser = userRepository.signup(user);
	//		 * return savedUser;
	//		 */
	//		return user;
	//	}


	public Optional<UserEntity> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}


	@Transactional
	public TokenResponseDto signup(UserRequestDto userRequestDto) {
		UserEntity user =
			userRepository.save(
				UserEntity.builder()
					.password(passwordEncoder.encode(userRequestDto.getPassword()))
					.email(userRequestDto.getEmail())
					.phoneNumber(userRequestDto.getPhoneNumber())
					.nickName(userRequestDto.getNickname())
					.build());

		String accessToken = jwtService.generateJwtToken(user);
		String refreshToken = jwtService.saveRefreshToken(user);

		authRepository.save(
			AuthEntity.builder().user(user).refreshToken(refreshToken).build());

		return TokenResponseDto.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken)
			.build();
	}


	@Transactional
	public TokenResponseDto signin(UserRequestDto userRequestDto) throws Exception {
		UserEntity user =
			userRepository
				.findByEmail(userRequestDto.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		AuthEntity auth =
			authRepository
				.findByUserSeq(user.getSeq())
				.orElseThrow(() -> new IllegalArgumentException("Token 이 존재하지 않습니다."));
		if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
			throw new Exception("비밀번호가 일치하지 않습니다.");
		}
		String accessToken = "";
		String refreshToken = auth.getRefreshToken();

		if (jwtService.isValidRefreshToken(refreshToken)) {
			accessToken = jwtService.generateJwtToken(auth.getUser());
			return TokenResponseDto.builder()
				.ACCESS_TOKEN(accessToken)
				.REFRESH_TOKEN(auth.getRefreshToken())
				.build();
		} else {
			accessToken = jwtService.generateJwtToken(auth.getUser());
			refreshToken = jwtService.saveRefreshToken(user);
			auth.refreshUpdate(refreshToken);
		}

		return TokenResponseDto.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken)
			.build();
	}


	public List<UserEntity> findUsers() {
		return userRepository.findAll();
	}


	public Boolean checkNicknameDuplication(UserRequestDto userRequestDto) {
		UserEntity user = userRequestDto.toEntity();
		/**
		 * repository 에서 닉네임 중복확인 절차를 마치고 true/false 를 반환해준다.
		 * Boolean isDuplicate = userRepository.checkNicknameDuplication(user);
		 * return isDuplicate;
		 */
		return false;
	}


	public Boolean sendAuthCode(String phoneNumber) {
		/**
		 * 휴대폰으로 인증코드 발송 해야함
		 */
		return true;
	}


	public Boolean checkAuthCode(String authCode) {
		/**
		 * 인증코드 일치 여부 검증
		 */
		return true;
	}


	private Boolean checkAgreementStatus(Boolean bool) {
		/**
		 * 프론트에서는 모든 약관에 동의했는지에 대한 데이터를 넘겨주어야 한다
		 * bool (약관동의) 가 true 일때 true를 반환한다
		 */
		return bool;
	}


	private Boolean checkEmailValidation(String email) {
		/**
		 * 이메일 유효성 검사 로직
		 */
		return true;
	}


	private Boolean checkPasswordValidation(String password) {
		/**
		 * 비밀번호 유효성 확인 로직
		 * 불일치 시 비밀번호 양식에 맞지 않다고 출력
		 */
		return true;
	}


	private Boolean checkPasswordMatch(String password, String checkPassword) {
		/**
		 * 비밀번호 일치 확인 로직
		 * 불일치 시 비밀번호가 일치하지 않습니다 출력
		 */
		return true;
	}


	private String encryptPassword(String password) {
		/**
		 * 비밀번호 암호화 로직
		 */
		return "aisfnaiosnfoidnfioansefns";
	}


	public Boolean login(UserRequestDto userRequestDto) {
		UserEntity user = userRequestDto.toEntity();
		/**
		 * user.email로 user를 조회 => findUserByEmail
		 * 조회가 안될 시 익셉션 터트리기
		 * user.password를 encryptPassword로 복호화 후 비교하여 결과 반환
		 * 일치하지 않을 시 익셉션 터트리기
		 */
		return true;
	}


	public Boolean logout(UserRequestDto userRequestDto) {
		UserEntity user = userRequestDto.toEntity();
		/**
		 * 로그아웃 진행 코드
		 * 토큰 회수? 무슨 방식?
		 */
		return true;
	}


	public AccountResponseDto findAccouontInfo(AccountRequestDto accountRequestDto) {
		/**
		 * checkAuthCode() 을 통과했을 시
		 * 프론트에서 userDto의 type을 보내주어야 한다 => Email or Password
		 * 만약 Email 이면
		 * Email찾기 로직을 실행 => findUserByPhoneNumber 하여 유저의 이메일을 반환
		 *
		 * 만약 Passwrod 이면 Password찾기 로직을 실행
		 * findUserByPhoneNumber 하여 userEntity를 조회하고
		 * 조회된 user의 email이 userDto의 email과 같으면 true / false 반환? 재설정하기 위해?
		 */
		return new AccountResponseDto();
	}


	/**
	 * 회원 정보 조회
	 *
	 * @param userSeq : 조회할 userSeq 포함
	 * @return findUserBySeq로 조회된 searchedUser
	 */
	public UserEntity getUserInfo(Long userSeq) {
		// TODO : 로직

		return (UserEntity) new Object();
	}


	/**
	 * 회원 정보 수정
	 *
	 * @param userSeq        : 수정할 user의 seq
	 * @param userRequestDto : 수정할 데이터
	 * @return 수정 후 user의 seq
	 */
	public Long modifyUserInfo(Long userSeq, UserRequestDto userRequestDto) {
		// TODO : 로직
		UserEntity _new = userRequestDto.toEntity();
		/**
		 * UserEntity _old = boardRepository.findUserBySeq(userSeq);
		 */
		return _new.getSeq();
	}


	/**
	 * 회원 정보 삭제
	 *
	 * @param userSeq : 삭제할 user의 seq
	 * @return 유저 삭제 로직 성공 여부
	 */
	public Boolean deleteUserInfo(Long userSeq) {
		// TODO : 로직
		return true;
	}


	/**
	 * 즐겨찾기 추가
	 *
	 * @param userSeq   : 누군가를 추가하기를 원하는 userSeq
	 * @param followSeq : 추가될 누군가의 seq
	 * @return
	 */
	public Boolean createBookmark(Long userSeq, Long followSeq) {
		// TODO : 로직

		return true;
	}


	/**
	 * 즐겨찾기 삭제
	 *
	 * @param userSeq   : 누군가를 삭제하기를 원하는 userSeq
	 * @param followSeq : 삭제될 누군가의 seq
	 * @return
	 */
	public Boolean deleteBookmark(Long userSeq, Long followSeq) {
		// TODO : 로직

		return true;
	}


	/**
	 * 차단 유저 추가
	 *
	 * @param userSeq  : 누군가를 추가하기를 원하는 userSeq
	 * @param blockSeq : 추가될 누군가의 seq
	 * @return
	 */
	public Boolean createBlock(Long userSeq, Long blockSeq) {
		return true;
	}


	/**
	 * 차단 유저 삭제
	 *
	 * @param userSeq  : 누군가를 삭제하기를 원하는 userSeq
	 * @param blockSeq : 삭제될 누군가의 seq
	 * @return
	 */
	public Boolean deleteBlock(Long userSeq, Long blockSeq) {
		return true;
	}


	/**
	 * 유저 신고하기
	 *
	 * @param reportRequestDto
	 * @return
	 */
	public Boolean createReport(ReportRequestDto reportRequestDto) {
		return true;
	}

	// --------------------------------- 히스토리 ------------------------------------


	/**
	 * @param userSeq           : 로그인 유저 seq
	 * @param historyRequestDto : 목록 조회 필터링 조건
	 * @return 히스토리 목록 EntityList
	 */
	public List<HistoryEntity> getUserHistoryList(Long userSeq, HistoryRequestDto historyRequestDto) {
		/**
		 * 히스토리 목록 조회 로직
		 *
		 * 필터링 기준
		 * 1. 고객 기준
		 * 2. 헬퍼 기준
		 * 3. 통역/번역 기준
		 * 4. 언어 기준 (시작 언어, 번역 언어 통틀어서 조회)
		 */
		List<HistoryEntity> historyList;
		// 정렬 기준 동적 생성
		Sort.TypedSort<HistoryEntity> typedSort = Sort.sort(HistoryEntity.class);
		Sort sort;
		if (historyRequestDto.getOrderByCreatedAtAsc()) sort = typedSort.by(HistoryEntity::getCreatedAt).ascending();
		else sort = typedSort.by(HistoryEntity::getCreatedAt).descending();

		// 목록 조회
		if (historyRequestDto.getClientSeq() != null) { // 고객 기준 조회
			historyList = historyRepository.findByClient(historyRequestDto.getClientSeq(), sort);
		} else if (historyRequestDto.getHelperSeq() != null) { // 헬퍼 기준 조회
			historyList = historyRepository.findByHelper(historyRequestDto.getHelperSeq(), sort);
		} else if (historyRequestDto.getLangCode() != null) { // 업무에 사용된 언어 기준 조회
			String langCode = historyRequestDto.getLangCode();
			historyList = historyRepository.findByStartLangCodeOrEndLangCode(langCode, langCode, sort);
		} else if (historyRequestDto.getTaskCode() != null) { // 업무 기준 조회 (통역, 번역)
			historyList = historyRepository.findByTaskCode(historyRequestDto.getTaskCode(), sort);
		} else { // 사용자 기준 전체 조회 (고객 또는 헬퍼로 참여한 업무 전체 조회)
			historyList = historyRepository.findByClientOrHelper(userSeq, userSeq, sort);
		}
		return historyList;
	}


	/**
	 * @param userSeq    : 로그인 사용자 seq
	 * @param historySeq : 조회할 히스토리 seq
	 * @return
	 */
	public HistoryEntity getUserHistory(Long userSeq, Long historySeq) throws Exception {
		/**
		 * 히스토리 단일 조회
		 */
		Optional<HistoryEntity> history = historyRepository.findById(historySeq);
		return history.orElseThrow(() -> new Exception("히스토리 조회 결과가 존재하지 않습니다."));
	}

}
