package com.tonnybunny.domain.alert.dto;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


@Data
@RequiredArgsConstructor
public class AlertSettingsDto {

	@Autowired
	private ModelMapper modelMapper;

	private Long alertSettingsSeq;

	@NonNull
	private Long userSeq;

	private Boolean isAll;
	private Boolean isTonnyBunny;
	private Boolean isCommunity;
	private Boolean isChat;

}
