-- =====================================================================
-- Base de datos StudentDB (Java DB / Apache Derby)
-- Laboratorio 3 - Aplicacion empresarial con EJB-JPA
--
-- Ejecutar en NetBeans: Services > Databases > jdbc:derby://localhost:1527/StudentDB
--   clic derecho > Execute Command... > pegar este script > Run SQL
-- (Si no se ejecuta, EclipseLink crea las tablas solo al desplegar,
--  porque persistence.xml usa eclipselink.ddl-generation = create-tables)
-- =====================================================================

-- Tabla del tutorial (video): Student
CREATE TABLE STUDENT (
    STUDENTID   INTEGER      NOT NULL,
    FIRSTNAME   VARCHAR(50),
    LASTNAME    VARCHAR(50),
    YEARLEVEL   INTEGER,
    CONSTRAINT STUDENT_PK PRIMARY KEY (STUDENTID)
);

-- Extension: Curso
CREATE TABLE CURSO (
    CODIGO               VARCHAR(20)  NOT NULL,
    NOMBRE               VARCHAR(100) NOT NULL,
    CREDITOS             INTEGER      NOT NULL,
    SEMESTRE             INTEGER      NOT NULL,
    ESTUDIANTESADMITIDOS INTEGER      NOT NULL,
    CONSTRAINT CURSO_PK PRIMARY KEY (CODIGO)
);

-- Relacion varios estudiantes toman varios cursos (tabla intermedia)
CREATE TABLE STUDENT_CURSO (
    CODIGOCURSO VARCHAR(20) NOT NULL,
    STUDENTID   INTEGER     NOT NULL,
    CONSTRAINT STUDENT_CURSO_PK PRIMARY KEY (CODIGOCURSO, STUDENTID),
    CONSTRAINT STUDENT_CURSO_CURSO_FK FOREIGN KEY (CODIGOCURSO) REFERENCES CURSO (CODIGO),
    CONSTRAINT STUDENT_CURSO_STUDENT_FK FOREIGN KEY (STUDENTID) REFERENCES STUDENT (STUDENTID)
);

-- Tabla que usa EclipseLink para @GeneratedValue(strategy = GenerationType.AUTO)
CREATE TABLE SEQUENCE (
    SEQ_NAME  VARCHAR(50) NOT NULL,
    SEQ_COUNT DECIMAL(15),
    CONSTRAINT SEQUENCE_PK PRIMARY KEY (SEQ_NAME)
);
INSERT INTO SEQUENCE (SEQ_NAME, SEQ_COUNT) VALUES ('SEQ_GEN', 100);

-- Datos de ejemplo
INSERT INTO STUDENT VALUES (1, 'Joseph Bernabe', 'Bagnes', 4);
INSERT INTO STUDENT VALUES (2, 'Jose', 'Ibanez', 3);
INSERT INTO STUDENT VALUES (3, 'Maria', 'Lopez', 5);

INSERT INTO CURSO VALUES ('SIS101', 'Programacion I', 3, 1, 30);
INSERT INTO CURSO VALUES ('SIS305', 'Bases de Datos', 4, 3, 25);
INSERT INTO CURSO VALUES ('SIS501', 'Arquitectura de Software', 3, 5, 20);

INSERT INTO STUDENT_CURSO VALUES ('SIS101', 1);
INSERT INTO STUDENT_CURSO VALUES ('SIS305', 1);
INSERT INTO STUDENT_CURSO VALUES ('SIS305', 2);
INSERT INTO STUDENT_CURSO VALUES ('SIS501', 3);
INSERT INTO STUDENT_CURSO VALUES ('SIS305', 3);
