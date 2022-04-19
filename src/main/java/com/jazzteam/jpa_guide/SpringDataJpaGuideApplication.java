package com.jazzteam.jpa_guide;

import com.jazzteam.jpa_guide.entity.Book;
import com.jazzteam.jpa_guide.entity.Student;
import com.jazzteam.jpa_guide.entity.StudentCard;
import com.jazzteam.jpa_guide.service.StudentCardService;
import com.jazzteam.jpa_guide.service.StudentService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@Slf4j
public class SpringDataJpaGuideApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaGuideApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentService studentService,
                                        StudentCardService studentCardService) {
        return args -> {
            //In this scenario: student - parent, student card - child
            studentAndStudentCardScenario(studentService, studentCardService);
            studentsAndBooksScenario(studentService);
        };
    }

    private static void generateRandomStudents(StudentService studentService) {
        for (int i = 0; i < 4; i++) {
            studentService.createStudent(getTestStudent());
        }
    }

    private static Student getTestStudent() {
        //Faker can generate random names, numbers and other random stuff
        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = String.format("%s.%s@jazzteam.edu", firstName, lastName);

        return new Student(
                firstName,
                lastName,
                email,
                faker.number().numberBetween(17, 55));
    }

    private static void studentAndStudentCardScenario(StudentService studentService,
                                                      StudentCardService studentCardService) {
        generateRandomStudents(studentService);
        List<Student> students = studentService.getAllStudents();
        log.info("Students: " + students);

        StudentCard studentCard = studentCardService.createStudentCard(new StudentCard("123456789"));

        Student student = students.get(0);

        /*
        This code will set card in student, but in StudentCard entity student field will not be updated:
            student.setStudentCard(studentCard);
         */

        student.setStudentCardAndUpdateCard(studentCard);

        Student updatedStudent = studentService.updateStudent(student);
        log.info("Student with card: " + updatedStudent);

        //When we delete student or a student card, both side will be deleted
        studentCardService.deleteStudentCard(studentCard.getId());
    }

    private static void studentsAndBooksScenario(StudentService studentService) {
        Student student = getTestStudent();

        student.addBook(
                new Book("JazzTeam super", LocalDateTime.now().minusDays(5))
        );

        student.addBook(
                new Book("JazzTeam super 1", LocalDateTime.now().minusDays(5))
        );

        Student createdStudent = studentService.createStudent(student);

        /*
            By default, books is lazy-loading.
            There are several good practices of loading them directly:
                1. Use JOIN fetch in @query annotation.
                2. Use @NamedEntityGraph and @EntityGraph annotations.

            In this tutorial is used the second variant.
         */
        List<Book> books = studentService.getStudentByIdAndFetchBooks(createdStudent.getId()).getBooks();
        log.info("Books:" + books);
    }
}
