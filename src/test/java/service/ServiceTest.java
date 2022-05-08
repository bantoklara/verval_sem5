package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ServiceTest {

    private static Service service;

    @BeforeEach
    public void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository studentXMLRepository = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository homeworkXMLRepository = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository gradeXMLRepository = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new Service(studentXMLRepository, homeworkXMLRepository, gradeXMLRepository);

        service.deleteStudent("maria0908");
        service.deleteHomework("#142");

        service.saveStudent("maria0908", "Kis Maria", 521);
        service.saveHomework("#142", "web", 12, 6);
    }

    @Test
    public void saveStudentShouldReturn1IfGivenStudentIdAlreadyExists() {
        int ret = service.saveStudent("maria0908", "Anna", 521);
        assertEquals(1, ret);
    }

    @Test
    public void saveStudentShouldReturn1IfGivenGroupIsInvalid() {
        int ret = service.saveStudent("10", "Gergo", 3);
        assertTrue(ret == 1);
    }

    @Test
    public void saveStudentShouldReturn1IfGivenGroupIsNotANumber() {
        assertThrows(NumberFormatException.class, () -> service.saveStudent("klari", "Klari", Integer.parseInt("alma")));
    }

    @Test
    public void updateStudentShouldReturn1WhenInputIsValid() {
        int ret = service.updateStudent("maria0908", "Kovacs Maria", 521);

        assertEquals(1, ret);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-5, 4, 10, 11})
    public void addGradeForStudentShouldReturn0IfGradeIsBetween0And10(double grade) {
        int ret = service.saveGrade("maria0908", "#142", grade, 7, "...");
        if (grade > 0 && grade <= 10)
            assertEquals(0, ret);
        else assertEquals(1, ret);
    }
}