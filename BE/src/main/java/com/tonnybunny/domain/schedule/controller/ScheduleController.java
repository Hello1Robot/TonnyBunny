package com.tonnybunny.domain.schedule.controller;

import com.tonnybunny.common.dto.ResultDto;
import com.tonnybunny.domain.schedule.dto.ScheduleRequestDto;
import com.tonnybunny.domain.schedule.dto.ScheduleResponseDto;
import com.tonnybunny.domain.schedule.entity.ScheduleEntity;
import com.tonnybunny.domain.schedule.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/schedules")
@RequiredArgsConstructor
@Api(tags = "일정 관리 API")
public class ScheduleController {

	private final ScheduleService scheduleService;

	@GetMapping
	@ApiOperation(value="특정 날짜의 일정 목록을 조회합니다.", notes="")
	public ResponseEntity<List<ScheduleResponseDto>> getScheduleList(@RequestBody ScheduleRequestDto scheduleRequestDto){
		List<ScheduleEntity> scheduleList = scheduleService.getScheduleList(scheduleRequestDto);
		List<ScheduleResponseDto> scheduleResponseDtoList = ScheduleResponseDto.fromEntityList(scheduleList);
		return ResponseEntity.status(HttpStatus.OK).body(scheduleResponseDtoList);
	}

	@GetMapping("/{scheduleSeq}")
	@ApiOperation(value="특정 일정을 상세 조회합니다.", notes="")
	public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable("scheduleSeq") int scheduleSeq){
		ScheduleEntity schedule = scheduleService.getSchedule(scheduleSeq);
		ScheduleResponseDto scheduleResponseDto = ScheduleResponseDto.fromEntity(schedule);
		return ResponseEntity.status(HttpStatus.OK).body(scheduleResponseDto);
	}

	@PostMapping
	@ApiOperation(value="일정을 생성합니다.", notes="")
	public ResponseEntity<ResultDto<Long>> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto){
		Long createdScheduleSeq = scheduleService.createSchedule(scheduleRequestDto);
		return ResponseEntity.status(HttpStatus.OK).body(ResultDto.of( createdScheduleSeq ));
	}

	@PutMapping("/{scheduleSeq}")
	@ApiOperation(value="일정을 수정합니다.", notes="")
	public ResponseEntity<ResultDto<Long>> modifySchedule(@PathVariable("scheduleSeq") int scheduleSeq, @RequestBody ScheduleRequestDto scheduleRequestDto){
		Long updatedScheduleSeq = scheduleService.modifySchedule(scheduleSeq, scheduleRequestDto);
		return ResponseEntity.status(HttpStatus.OK).body(ResultDto.of(updatedScheduleSeq));
	}

	@DeleteMapping("/{scheduleSeq}")
	@ApiOperation(value="일정을 삭제합니다.", notes="")
	public ResponseEntity<ResultDto<Boolean>> deleteSchedule(@PathVariable("scheduleSeq") int scheduleSeq){
		Boolean result = scheduleService.deleteSchedule(scheduleSeq);
		return ResponseEntity.status(HttpStatus.OK).body(ResultDto.of(result));
	}
}
