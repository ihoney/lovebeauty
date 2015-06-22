#订单交易成功，计算员工的均价
delimiter |
CREATE TRIGGER insert_emp_avgPrice AFTER UPDATE ON `order` FOR EACH ROW
BEGIN
	UPDATE employee SET avgPrice = (SELECT AVG(price) FROM `order` WHERE empId = new.empId and state = '交易成功') WHERE id = new.empId ;
END ;|
delimiter ;

#评价之后计算员工的评价得分
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

#添加手艺人时标记闲忙状态
delimiter |
CREATE TRIGGER addEmpBookState AFTER INSERT ON `employee` FOR EACH ROW
BEGIN
	INSERT INTO book_time (empId,today,tomorrow,afterTomorrow,bigDayAfterTomorrow) values (new.id,'111111111111','000000000000','000000000000','000000000000');
END ;|
delimiter ;