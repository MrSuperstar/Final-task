DROP DATABASE IF EXISTS hospital;
CREATE DATABASE hospital;

USE hospital;
-- ----------------------------
-- Table structure for genders
-- ----------------------------
DROP TABLE IF EXISTS `genders`;
CREATE TABLE `genders`  (
  `GenderId` int(11) NOT NULL AUTO_INCREMENT,
  `GenderName` varchar(50) NOT NULL,
  PRIMARY KEY (`GenderId`)
);


-- ----------------------------
-- Table structure for medicaments
-- ----------------------------
DROP TABLE IF EXISTS `medicaments`;
CREATE TABLE `medicaments`  (
  `MedicamentId` int(11) NOT NULL AUTO_INCREMENT,
  `MedicamentName` varchar(50),
  PRIMARY KEY (`MedicamentId`)
);


-- ----------------------------
-- Table structure for operations
-- ----------------------------
DROP TABLE IF EXISTS `operations`;
CREATE TABLE `operations`  (
  `OperationId` int(11) NOT NULL AUTO_INCREMENT,
  `OperationName` varchar(50) NOT NULL,
  PRIMARY KEY (`OperationId`)
);


-- ----------------------------
-- Table structure for diagnosis
-- ----------------------------
DROP TABLE IF EXISTS `diagnosis`;
CREATE TABLE `diagnosis`  (
  `DiagnoseId` int(11) NOT NULL AUTO_INCREMENT,
  `DiagnoseName` varchar(50),
  PRIMARY KEY (`DiagnoseId`)
);

-- ----------------------------
-- Table structure for procedures
-- ----------------------------
DROP TABLE IF EXISTS `procedures`;
CREATE TABLE `procedures`  (
  `ProcedureId` int(11) NOT NULL AUTO_INCREMENT,
  `ProcedureName` varchar(50) NOT NULL,
  PRIMARY KEY (`ProcedureId`)
);


-- ----------------------------
-- Table structure for positions
-- ----------------------------
DROP TABLE IF EXISTS `positions`;
CREATE TABLE `positions`  (
  `PositionId` int(11) NOT NULL AUTO_INCREMENT,
  `PositionName` varchar(50) NOT NULL,
  PRIMARY KEY (`PositionId`)
);


-- ----------------------------
-- Table structure for employees
-- ----------------------------
DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees`  (
  `EmployeeId` int(11) NOT NULL AUTO_INCREMENT,
  `EmployeeName` varchar(20) NOT NULL,
  `GenderId` int(11) NOT NULL,
  `PositionId` int(11) NOT NULL,
  `EmployeeStatus` tinyint(1) NOT NULL,
  `Login` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  PRIMARY KEY (`EmployeeId`),
  FOREIGN KEY (`GenderId`) REFERENCES `genders` (`GenderId`),
  FOREIGN KEY (`PositionId`) REFERENCES `positions` (`PositionId`)
);


-- ----------------------------
-- Table structure for patients
-- ----------------------------
DROP TABLE IF EXISTS `patients`;
CREATE TABLE `patients`  (
  `PatientId` int(11) NOT NULL AUTO_INCREMENT,
  `PatientName` varchar(20) NOT NULL,
  `GenderId` int(11) NOT NULL,
  `OperationId` int(11) NULL DEFAULT NULL,
  `DiagnoseId` int(11) NULL DEFAULT NULL,
  `ProcedureId` int(11) NULL DEFAULT NULL,
  `MedicamentId` int(11) NULL DEFAULT NULL,
  `PatientStatus` tinyint(1) NOT NULL,
  `Login` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  PRIMARY KEY (`PatientId`),
  FOREIGN KEY (`GenderId`) REFERENCES `genders` (`GenderId`),
  FOREIGN KEY (`OperationId`) REFERENCES `operations` (`OperationId`),
  FOREIGN KEY (`DiagnoseId`) REFERENCES `diagnosis` (`DiagnoseId`),
  FOREIGN KEY (`ProcedureId`) REFERENCES `procedures` (`ProcedureId`),
  FOREIGN KEY (`MedicamentId`) REFERENCES `medicaments` (`MedicamentId`)
);

-- ----------------------------
-- Records of genders
-- ----------------------------
INSERT INTO `genders` VALUES (1, 'Male');
INSERT INTO `genders` VALUES (2, 'Female');

-- ----------------------------
-- Records of positions
-- ----------------------------
INSERT INTO `positions` VALUES (1, 'Nurse');
INSERT INTO `positions` VALUES (2, 'Doctor');

-- ----------------------------
-- Records of medicaments
-- ----------------------------
INSERT INTO `medicaments` VALUES (1, 'M1');
INSERT INTO `medicaments` VALUES (2, 'M2');
INSERT INTO `medicaments` VALUES (3, 'M3');
INSERT INTO `medicaments` VALUES (4, 'M4');
INSERT INTO `medicaments` VALUES (5, 'M5');
INSERT INTO `medicaments` VALUES (6, 'M6');
INSERT INTO `medicaments` VALUES (7, 'M7');
INSERT INTO `medicaments` VALUES (8, 'M8');

-- ----------------------------
-- Records of diagnosis
-- ----------------------------
INSERT INTO `diagnosis` VALUES (1, 'D1');
INSERT INTO `diagnosis` VALUES (2, 'D2');
INSERT INTO `diagnosis` VALUES (3, 'D3');
INSERT INTO `diagnosis` VALUES (4, 'D4');
INSERT INTO `diagnosis` VALUES (5, 'D5');
INSERT INTO `diagnosis` VALUES (6, 'D6');
INSERT INTO `diagnosis` VALUES (7, 'D7');
INSERT INTO `diagnosis` VALUES (8, 'D8');
INSERT INTO `diagnosis` VALUES (9, 'D9');

-- ----------------------------
-- Records of operations
-- ----------------------------
INSERT INTO `operations` VALUES (1, 'O1');
INSERT INTO `operations` VALUES (2, 'O2');
INSERT INTO `operations` VALUES (3, 'O3');
INSERT INTO `operations` VALUES (4, 'O4');
INSERT INTO `operations` VALUES (5, 'O5');
INSERT INTO `operations` VALUES (6, 'O6');
INSERT INTO `operations` VALUES (7, 'O7');
INSERT INTO `operations` VALUES (8, 'O8');
INSERT INTO `operations` VALUES (9, 'O9');

-- ----------------------------
-- Records of procedures
-- ----------------------------
INSERT INTO `procedures` VALUES (1, 'P1');
INSERT INTO `procedures` VALUES (2, 'P2');
INSERT INTO `procedures` VALUES (3, 'P3');
INSERT INTO `procedures` VALUES (4, 'P4');
INSERT INTO `procedures` VALUES (5, 'P5');
INSERT INTO `procedures` VALUES (6, 'P6');
INSERT INTO `procedures` VALUES (7, 'P7');
INSERT INTO `procedures` VALUES (8, 'P8');


-- ----------------------------
-- Records of patients
-- ----------------------------
INSERT INTO `patients` VALUES (1, 'Patient', 1, 1, NULL, NULL, NULL, 1, 'login', 'password');
INSERT INTO `patients` VALUES (2, 'Patient', 1, NULL, 2, NULL, NULL, 1, 'login1', 'password');
INSERT INTO `patients` VALUES (3, 'Patient', 1, NULL, NULL, 3, NULL, 1, 'login2', 'password');
INSERT INTO `patients` VALUES (4, 'Patient', 1, NULL, NULL, NULL, 4, 1, 'login3', 'password');


-- ----------------------------
-- Records of employees
-- ----------------------------
INSERT INTO `employees` VALUES (1, 'Employee', 1, 1, 1, 'login4', 'password');
INSERT INTO `employees` VALUES (2, 'Employee', 2, 2, 1, 'login5', 'password');



-- ----------------------------
-- View structure for getemployees
-- ----------------------------
DROP VIEW IF EXISTS `getemployees`;
CREATE  VIEW `getemployees` AS select 
`employees`.`EmployeeId` AS `EmployeeId`,
`employees`.`EmployeeName` AS `EmployeeName`,
`genders`.`GenderName` AS `GenderName`,
`positions`.`PositionName` AS `PositionName`,
`employees`.`EmployeeStatus` AS `EmployeeStatus` 
from ((`employees` join `genders` on(`employees`.`GenderId` = `genders`.`GenderId`))

join `positions` on(`employees`.`PositionId` = `positions`.`PositionId`));

-- ----------------------------
-- View structure for getpatients
-- ----------------------------
DROP VIEW IF EXISTS `getpatients`;
CREATE VIEW `getpatients` AS select 
`patients`.`PatientId` AS `PatientId`,
`patients`.`PatientName` AS `PatientName`,
`genders`.`GenderName` AS `GenderName`,
`patients`.`DiagnoseId` AS `DiagnoseId`,
`patients`.`MedicamentId` AS `MedicamentId`,
`patients`.`OperationId` AS `OperationId`,
`patients`.`ProcedureId` AS `ProcedureId`,
`patients`.`PatientStatus` AS `PatientStatus` 
from ((((
(`patients` join `genders` on(`patients`.`GenderId` = `genders`.`GenderId`))
left join `diagnosis` on(`patients`.`DiagnoseId` = `diagnosis`.`DiagnoseId`)) 
left join `medicaments` on(`patients`.`MedicamentId` = `medicaments`.`MedicamentId`)) 
left join `operations` on(`patients`.`OperationId` = `operations`.`OperationId`)) 
left join `procedures` on(`patients`.`ProcedureId` = `procedures`.`ProcedureId`)) 
where (`patients`.`PatientStatus` = 1);


-- ----------------------------
-- Procedure structure for updateEmployee
-- ----------------------------
DROP PROCEDURE IF EXISTS `updateEmployee`;
delimiter ;;
CREATE PROCEDURE `updateEmployee`(id INTEGER, positionId INTEGER)
BEGIN
	 UPDATE employees SET PositionId=positionId WHERE EmployeeId=id;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for updatePatient
-- ----------------------------
DROP PROCEDURE IF EXISTS `updatePatient`;
delimiter ;;
CREATE PROCEDURE `updatePatient`(id INTEGER, operationId INTEGER, diagnoseId INTEGER, medicamentId INTEGER, procedureId INTEGER)
BEGIN
	 UPDATE patients SET OperationId=operationId, DiagnoseId=diagnoseId,
	 MedicamentId=medicamentId, ProcedureId=procedureId WHERE PatientId=id;
END
;;
delimiter ;


-- ----------------------------
-- Procedure structure for getEmployeeById
-- ----------------------------
DROP PROCEDURE IF EXISTS `getEmployeeById`;
delimiter ;;
CREATE PROCEDURE `getEmployeeById`(id INTEGER)
BEGIN
	SELECT employees.EmployeeId, employees.EmployeeName, genders.GenderName, positions.PositionName, employees.EmployeeStatus
	
	FROM employees JOIN genders ON employees.GenderId = genders.GenderId
	JOIN positions ON employees.PositionId = positions.PositionId
	
	WHERE EmployeeId = id;
END
;;
delimiter ;


-- ----------------------------
-- Procedure structure for getPatientById
-- ----------------------------
DROP PROCEDURE IF EXISTS `getPatientById`;
delimiter ;;
CREATE PROCEDURE `getPatientById`(id INTEGER)
BEGIN
	SELECT patients.PatientId, patients.PatientName, genders.GenderName, patients.DiagnoseId, 
	patients.MedicamentId, patients.OperationId, patients.ProcedureId, patients.PatientStatus
	
	FROM patients JOIN genders ON patients.GenderId = genders.GenderId
    LEFT JOIN operations ON patients.OperationId = operations.OperationId	
	LEFT JOIN diagnosis ON patients.DiagnoseId = diagnosis.DiagnoseId
	LEFT JOIN procedures ON patients.ProcedureId = procedures.ProcedureId
	LEFT JOIN medicaments ON patients.MedicamentId = medicaments.MedicamentId
	
	WHERE PatientId = id;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for getMedicamentById
-- ----------------------------
DROP PROCEDURE IF EXISTS `getMedicamentById`;
delimiter ;;
CREATE PROCEDURE `getMedicamentById`(id INTEGER)
BEGIN
	 SELECT * FROM medicaments WHERE MedicamentId = id;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for getOperationById
-- ----------------------------
DROP PROCEDURE IF EXISTS `getOperationById`;
delimiter ;;
CREATE PROCEDURE `getOperationById`(id INTEGER)
BEGIN
	 SELECT * FROM operations WHERE OperationId = id;
END
;;
delimiter ;
-- ----------------------------
-- Procedure structure for getProcedureById
-- ----------------------------
DROP PROCEDURE IF EXISTS `getProcedureById`;
delimiter ;;
CREATE PROCEDURE `getProcedureById`(id INTEGER)
BEGIN
	 SELECT * FROM procedures WHERE ProcedureId = id;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for getDiagnoseById
-- ----------------------------
DROP PROCEDURE IF EXISTS `getDiagnoseById`;
delimiter ;;
CREATE PROCEDURE `getDiagnoseById`(id INTEGER)
BEGIN
	 SELECT * FROM diagnosis WHERE DiagnoseId = id;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for releasedPatient
-- ----------------------------
DROP PROCEDURE IF EXISTS `releasedPatient`;
delimiter ;;
CREATE PROCEDURE `releasedPatient`(id INTEGER)
BEGIN
	 UPDATE patients SET patients.PatientStatus = 0 WHERE PatientId=id;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for resignationEmployee
-- ----------------------------
DROP PROCEDURE IF EXISTS `resignationEmployee`;
delimiter ;;
CREATE PROCEDURE `resignationEmployee`(id INTEGER)
BEGIN
	 UPDATE employees SET employees.EmployeeStatus = 0 WHERE EmployeeId=id;
END
;;
delimiter ;



-- ----------------------------
-- Procedure structure for loginEmployee
-- ----------------------------
DROP PROCEDURE IF EXISTS `loginEmployee`;
delimiter ;;
CREATE PROCEDURE `loginEmployee`(login varchar(20), password varchar(20))
BEGIN
	 SELECT `employees`.`EmployeeId` FROM `employees` WHERE `employees`.`Login` = login AND `employees`.`Password` = password;
END
;;
delimiter ;


-- ----------------------------
-- Procedure structure for loginEmployee
-- ----------------------------
DROP PROCEDURE IF EXISTS `loginPatient`;
delimiter ;;
CREATE PROCEDURE `loginPatient`(login varchar(20), password varchar(20))
BEGIN
	 SELECT `patients`.`PatientId` FROM `patients` WHERE `patients`.`Login` = login AND `patients`.`Password` = password;
END
;;
delimiter ;










