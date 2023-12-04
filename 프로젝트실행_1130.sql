start transaction;

insert into USERtbl values ('a1234', 1234,  'aaa', '2000-01-01', '01011112222', '111223456788');
insert into USERtbl values ('a1235', 1235,  'aab', '2000-01-02', '01011112223', '111223456789');
insert into USERtbl values ('a1236', 1236,  'aac', '2000-01-03', '01011112224', '111223456781');
insert into USERtbl values ('a1237', 1237,  'aad', '2000-01-03', '01011112224', '111223456782');
insert into USERtbl values ('a1238', 1237,  'aad', '2008-12-31', '01011112224', '111223456783');

insert into placetbl values (222, '강남');
insert into placetbl values (223, '서초');

insert into vehicletbl(veh_id, veh_model, veh_defplace, veh_place) values (1000, 'j1234', 222, '강남'); 
insert into vehicletbl(veh_id, veh_model, veh_defplace, veh_place) values (5000, 'j1234', 223, '서초'); 
#delete from vehicletbl;

insert into reservetbl(user_id,veh_id, res_time) values('a1235',1000, '2022-12-02 18:00:00');
insert into reservetbl(user_id,veh_id, res_time) values('a1236',1000, '2022-12-02 16:00:00');

call res_ren_proc(1000); # 예약된 기기를 대여상태로 넘겨주는 트리거, input은 예약한 기기.
#delete from reservetbl;

insert into rentaltbl(user_id,veh_id,ren_splace) values ('a1234',1000,'강남');
insert into rentaltbl(user_id,veh_id,ren_splace) values ('a1234',5000,'서초');

insert into rentaltbl(user_id,veh_id,ren_splace) values ('a1235',5000,'서초');
#delete from rentaltbl;

insert into returntbl(rtn_num,rtn_eplace,rtn_dist) values (1,'은미아파트',70);
insert into returntbl(rtn_num,rtn_eplace,rtn_dist) values (2,'장미아파트',110);
#DELETE from returntbl;

-- 시나리오 실행 시 
savepoint a;
delete from usertbl where user_id = 'a1235';
delete from vehicletbl where veh_id = 5000;
select * from usertbl;
select * from vehicletbl;
rollback to savepoint a;

select * from usertbl;
select * from vehicletbl;

update vehicletbl set veh_id = 9000 where veh_id = 5000;
update vehicletbl set veh_id = 3000 where veh_id = 1000;
update usertbl set user_id = 'bb1234' where user_id = 'a1235';
update usertbl set user_name = '홍길동' where user_id = 'bb1234';
select * from vehicletbl;
select * from usertbl;
rollback to savepoint a;

select * from usertbl;
select * from placetbl;
select * from vehicletbl;
select * from reservetbl;
select * from rentaltbl;
select * from returntbl;
update usertbl set user_id = 'assss' where user_id = 'a1237';
call battery_proc('vehicletbl');
call userpassword_proc('a1234');
call vehdefplace_proc('vehicletbl');


drop view if exists history;
create view history as
	select A.user_id, A.veh_id,A.ren_num, A.ren_stime, A.ren_splace, B.rtn_num, B.rtn_etime, B.rtn_eplace, B.rtn_dist, B.rtn_price, C.res_num, C.res_time
	from rentaltbl A join returntbl B on A.ren_num = B.rtn_num left join reservetbl C on A.res_num = C.res_num;
    
select * from history;

select * from information_schema.COLUMNS where table_schema = 'project';
select * from information_schema.ROUTINES where routine_schema = 'project';
