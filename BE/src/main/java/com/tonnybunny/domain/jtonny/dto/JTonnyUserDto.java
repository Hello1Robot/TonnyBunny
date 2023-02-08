package com.tonnybunny.domain.jtonny.dto;


import com.tonnybunny.domain.user.entity.UserEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class JTonnyUserDto {

	private Long seq;
	//	private String userCode;
	private String nickName;
	private JTonnyHelperInfoDto helperInfo;


	public JTonnyUserDto(UserEntity user) {
		this.seq = user.getSeq();
		//		this.userCode = user.getUserCode();
		this.nickName = user.getNickName();
		//		if (this.userCode == UserCodeEnum.클라이언트.getUserCode()) {
		//			this.helperInfo = new HelperInfoResponseDto();
		//		} else {
		this.helperInfo = JTonnyHelperInfoDto.fromEntity(user.getHelperInfo());
		//		}

	}

}
