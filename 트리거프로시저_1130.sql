use project;

-- USER 테이블
-- 회원비밀번호 초기화 프로시저
DROP PROCEDURE IF EXISTS userpassword_proc;
DELIMITER $$
CREATE PROCEDURE userpassword_proc(
IN userid INT
)
BEGIN
	update usertbl set user_password = '1111' where user_id = userid;
    
end $$
DELIMITER ;

-- 면허증 형식 함수(fuction)
drop function if exists mask;
DELIMITER //
CREATE FUNCTION mask (unformatted_value BIGINT, format_string CHAR(32))
RETURNS CHAR(32) DETERMINISTIC
BEGIN
# Declare variables
DECLARE input_len TINYINT;
DECLARE output_len TINYINT;
DECLARE temp_char CHAR;

# Initialize variables
SET input_len = LENGTH(unformatted_value);
SET output_len = LENGTH(format_string);

# Construct formated string
WHILE ( output_len > 0 ) DO

SET temp_char = SUBSTR(format_string, output_len, 1);
IF ( temp_char = '#' ) THEN
IF ( input_len > 0 ) THEN
SET format_string = INSERT(format_string, output_len, 1, SUBSTR(unformatted_value, input_len, 1));
SET input_len = input_len - 1;
ELSE
SET format_string = INSERT(format_string, output_len, 1, '0');
END IF;
END IF;

SET output_len = output_len - 1;
END WHILE;

RETURN format_string;
END //

DELIMITER ;

-- 정확한 면허증 번호를 입력하게 하는 트리거 (조건 : 12자리 길이 & 자동 형식 스토어드 함수 사용.) << 추가로 문자열 입력 못하게 해야함.
drop trigger if exists userlicenseformat_trg;
delimiter //
create trigger userlicenseformat_trg
	before insert
    on USERTBL
    for each row
begin
	if length(new.user_license) <> 12 then
		signal sqlstate '45000'
			set message_text = '올바른 번호가 아닙니다. 정확한 면허증 번호를 입력하세요.';
	else
		select mask(new.user_license, '##-##-######-##') into @license;
		set new.user_license = @license;
	end if;
end //
delimiter ;

-- 회원가입 시 면허증이 없는 회원의 회원가입을 반려하도록 하는 트리거 
drop trigger if exists userlicense_trg;
delimiter //
create trigger userlicense_trg
	before insert
    on USERTBL
    for each row
    follows userlicenseformat_trg
begin
	if new.user_license is null then
		signal sqlstate '45000'
			set message_text = '면허증이 필요합니다.';

	end if;
end //
delimiter ;



-- 회원 가입 시 나이가 13세 이상이 되어야 하는 트리거 생성
drop trigger if exists userbirth_trg;
delimiter //
create trigger userbirth_trg
	after insert
    on USERTBL
    for each row
begin
	if new.user_birth > '2009-01-01' then
		signal sqlstate '45000'
			set message_text = '회원가입하기에는 너무 어립니다.';
	end if;
end //
delimiter ;

-- 비밀번호 조합 생각해보기(시간 부족해서 못하겠음). 전화번호랑 면허증에 문자열 들어가지 못하게하기(이거 하고 싶은데 못하겠음)


-- 기기 테이블 트리거
-- 기기 예약하면 기기 예약중으로 바꿔주는 트리거 + 예약시간이 현재시간 이전이면 오류 발생하는 트리거.
drop trigger if exists reservevehicle_trg;
delimiter //
create trigger reservevehicle_trg
	after insert
    on RESERVETBL
    for each row
begin
	if new.res_time < current_timestamp() then
		signal sqlstate '45000'
			set message_text = '예약시간이 현재시간보다 이릅니다. 예약시간을 다시 설정하세요.';
		set @msg1 = '예약시간이 현재시간보다 이릅니다. 예약시간을 다시 설정하세요.';
	else
    	update vehicletbl set veh_reserve = 'o' where veh_id = new.veh_id;
	end if;
end //
delimiter ;

-- 기기를 대여하면 기기 사용중으로 바꿔주는 트리거 생성.
drop trigger if exists usingvehicle_trg;
delimiter //
create trigger usingvehicle_trg
	after insert
    on RENTALTBL
    for each row
begin
	update vehicletbl set veh_reserve = 'x', veh_using = 'o' where veh_id = new.veh_id;
end //
delimiter ;

-- 기기 반납이 이루어지면 거리에 비례해서 기기의 배터리가 변경하는 트리거 계획.
drop trigger if exists vehbatt_trg;
delimiter //
create trigger vehbatt_trg
	after insert
    on RETURNTBL
    for each row
begin
	select veh_id into @vehicle from rentaltbl join returntbl on ren_num = rtn_num ORDER BY ren_num DESC limit 1; 
    select veh_batt into @battery from vehicletbl where veh_id = @vehicle;
	if new.rtn_dist is not null and @battery > new.rtn_dist then
		update vehicletbl set veh_batt = veh_batt - new.rtn_dist where veh_id = @vehicle;
	else
		update vehicletbl set veh_batt = 0 where veh_id = @vehicle;
	end if;
end //
delimiter ;

-- 기기 반납 시 기기 사용료 입력.(시간비례 분당 200원)
drop trigger if exists pricechk_trg;
delimiter //
create trigger pricechk_trg
	before insert
    on RETURNTBL
    for each row
begin
	select ren_stime into @rentime from rentaltbl where ren_num = new.rtn_num;
    set new.rtn_price = 200 * timestampdiff(minute, @rentime, new.rtn_etime);
end //
delimiter ;

-- 기기를 반납할 때 반납 장소를 기기 현재위치로 변경해주는 트리거 생성. + 기기 예약중 x + 기기 사용중 x로 변경.
drop trigger if exists chgvehplace_trg;
delimiter //
create trigger chgvehplace_trg
	after insert
    on RETURNTBL
    for each row
begin
	select veh_id into @vehicle from rentaltbl join returntbl on ren_num = rtn_num ORDER BY ren_num DESC limit 1;
	if new.rtn_eplace is not null then
		update vehicletbl set veh_place = new.rtn_eplace where veh_id = @vehicle;
        select veh_place into @place from vehicletbl where veh_id = @vehicle;
        if @place not in (select place_name from placetbl) then
			update vehicletbl set veh_state = '방치' where veh_id = @vehicle;
		else
			update vehicletbl set veh_state = '정상' where veh_id = @vehicle;
		end if;
	end if;
	update vehicletbl set veh_reserve = 'x', veh_using = 'x' where veh_id = @vehicle;

end //
delimiter ;

-- 기기를 대여할 때 장소에 없는 기기를 빌리려할 때 오류 생성. + 기기 사용중이 x여야하고 정상상태여야 함.
drop trigger if exists chkvehicle_trg;
delimiter //
create trigger chkvehicle_trg
	before insert
    on rentaltbl
    for each row
begin
	select veh_place, veh_batt, veh_reserve, veh_using into @place, @battery, @reserve, @usevehicle from vehicletbl where veh_id = new.veh_id;
	select user_id into @res_user from reservetbl where veh_id = new.veh_id order by res_time desc limit 1;

	if new.ren_splace <> @place then
		signal sqlstate '45000'
			set message_text = '해당 장소에 기기가 존재하지 않습니다.';
	elseif @battery < 10 then
		signal sqlstate '45000'
			set message_text = '기기가 방전되어 대여할 수 없습니다.';
	elseif new.user_id <> @res_user and @reserve <> 'x' then
		signal sqlstate '45000'
			set message_text = '기기가 예약상태이므로 대여할 수 없습니다.';
	elseif @usevehicle <> 'x' then
		signal sqlstate '45000'
			set message_text = '기기가 사용중이므로 대여할 수 없습니다.';
	end if;
end //
delimiter ;

-- 기기를 예약할 때 장소에 없는 기기를 빌리려할 때 오류 생성. + 기기 사용중이 x여야하고 정상상태여야 함.
drop trigger if exists chkvehicle_trg2;
delimiter //
create trigger chkvehicle_trg2
	before insert
    on reservetbl
    for each row
begin
	select veh_place, veh_batt, veh_reserve, veh_using into @place, @battery, @reserve, @usevehicle from vehicletbl where veh_id = new.veh_id;
	select user_id into @res_user from reservetbl where veh_id = new.veh_id order by res_time desc limit 1;
    if @battery < 10 then
		signal sqlstate '45000'
			set message_text = '기기가 방전되어 예약할 수 없습니다.';
	elseif new.user_id <> @res_user and @reserve <> 'x' then
		signal sqlstate '45000'
			set message_text = '기기가 예약상태이므로 예약할 수 없습니다.';
	elseif @usevehicle <> 'x' then
		signal sqlstate '45000'
			set message_text = '기기가 사용중이므로 예약할 수 없습니다.';
	end if;
end //
delimiter ;


-- 예약된 기기를 대여하시겠습니까? => 예약정보를 대여정보로 넣어주는 프로시저
DROP PROCEDURE IF EXISTS RES_REN_proc;
DELIMITER $$
CREATE PROCEDURE RES_REN_proc(
IN vehicle varchar(10)  
)
BEGIN
	declare pres_num INT;
    declare puser_id INT;
    declare pveh_id INT;
    declare pres_time DATETIME;
    declare pplace varchar(10);
	select RES_NUM, USER_ID, VEH_ID, RES_TIME into pres_num, puser_id, pveh_id, pres_time from reservetbl where veh_id = vehicle order by res_num desc limit 1;
    select veh_place into pplace from vehicletbl where veh_id = pveh_id;
		
	If (pres_time > current_timestamp()) then
		insert into rentaltbl values (null, puser_id, pveh_id, current_timestamp(), pplace, pres_num);
	ELSE
		insert into rentaltbl values (null, puser_id, pveh_id, pres_time, pplace, pres_num);
	END IF;
end $$
DELIMITER ;

-- 방전된 기기 충전해주는 프로시저
DROP PROCEDURE IF EXISTS battery_proc;
DELIMITER $$
CREATE PROCEDURE battery_proc(
IN tblName varchar(20) 
)
BEGIN
	set @query = concat('update ', tblName, ' set veh_batt = 100 where veh_batt < 10');
	prepare myquery from @query;
	execute myquery;
	
	deallocate prepare myquery;
end $$
DELIMITER ;

-- 방치된 기기 원래 장소로 되돌려주는 프로시저
DROP PROCEDURE IF EXISTS vehdefplace_proc;
DELIMITER $$
CREATE PROCEDURE vehdefplace_proc(
IN tblName varchar(20) 
)
BEGIN
	set @query = concat('update ', tblname, " join placetbl on veh_defplace = place_id set veh_place = place_name, veh_state='정상' where veh_state = '방치'");
	prepare myquery from @query;
	execute myquery;
	deallocate prepare myquery;
        
end $$
DELIMITER ;


-- 방치 회원 명단 조회
