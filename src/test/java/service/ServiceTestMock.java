package service;

import domain.Student;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class ServiceTestMock {
    private Service service;

    private StudentXMLRepository studentXMLRepository;
    private HomeworkXMLRepository homeworkXMLRepository;
    private GradeXMLRepository gradeXMLRepository;

    @Before
    public void setup() {
        System.out.println("HERE");
        studentXMLRepository = mock(StudentXMLRepository.class);
        homeworkXMLRepository = mock(HomeworkXMLRepository.class);
        gradeXMLRepository = mock(GradeXMLRepository.class);

        service = new Service(studentXMLRepository, homeworkXMLRepository, gradeXMLRepository);

        service.deleteStudent("maria0908");
        service.deleteHomework("#142");

        service.saveStudent("maria0908", "Kis Maria", 521);
        service.saveHomework("#142", "web", 12, 6);
    }

    @Test
    public void saveStudent() {
        Student student = new Student("maria0908", "Kist Maria", 521);
        Mockito.doReturn(null).when(studentXMLRepository).save(student);
        int ret = service.saveStudent(student.getID(), student.getName(), student.getGroup());
        assertEquals(1, ret);
    }
}
