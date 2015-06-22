#�������׳ɹ�������Ա���ľ���
delimiter |
CREATE TRIGGER insert_emp_avgPrice AFTER UPDATE ON `order` FOR EACH ROW
BEGIN
	UPDATE employee SET avgPrice = (SELECT AVG(price) FROM `order` WHERE empId = new.empId and state = '���׳ɹ�') WHERE id = new.empId ;
END ;|
delimiter ;

#����֮�����Ա�������۵÷�
delimiter |
CREATE TRIGGER insert_emp_score AFTER INSERT ON emp_comment FOR EACH ROW
BEGIN
	UPDATE employee
SET majorScore = (
	SELECT
		AVG(majorScore)
	FROM
		emp_comment
	WHERE
		empId = new.empId
),
 comScore = (
	SELECT
		AVG(comScore)
	FROM
		emp_comment
	WHERE
		empId = new.empId
),
 punctualScore = (
	SELECT
		AVG(punctualScore)
	FROM
		emp_comment
	WHERE
		empId = new.empId
)

WHERE
	id = new.empId ;
END ;|
delimiter ;

#���������ʱ�����æ״̬
delimiter |
CREATE TRIGGER addEmpBookState AFTER INSERT ON `employee` FOR EACH ROW
BEGIN
	INSERT INTO book_time (empId,today,tomorrow,afterTomorrow,bigDayAfterTomorrow) values (new.id,'111111111111','000000000000','000000000000','000000000000');
END ;|
delimiter ;