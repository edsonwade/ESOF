package ufp.esof.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ufp.esof.project.models.*;
import ufp.esof.project.repositories.*;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
@Transactional
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ExplainerRepo explainerRepo;
    private CourseRepo courseRepo;
    private AppointmentRepo appointmentRepo;
    private StudentRepo studentRepo;
    private CollegeRepo collegeRepo;
    private DegreeRepo degreeRepo;
    private AvailabilityRepo availabilityRepo;

    @Autowired
    public Bootstrap(ExplainerRepo explainerRepo, CourseRepo courseRepo, AvailabilityRepo availabilityRepo, DegreeRepo degreeRepo, CollegeRepo collegeRepo, AppointmentRepo appointmentRepo, StudentRepo studentRepo) {
        this.explainerRepo = explainerRepo;
        this.courseRepo = courseRepo;
        this.explainerRepo = explainerRepo;
        this.studentRepo = studentRepo;
        this.appointmentRepo = appointmentRepo;
        this.availabilityRepo = availabilityRepo;
        this.collegeRepo = collegeRepo;
        this.degreeRepo = degreeRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.debug("Startup");

        Degree degree = new Degree("Degree1");

        this.degreeRepo.save(degree);

        ArrayList<College> colleges = new ArrayList<>();
        colleges.add(new College("Universidade Fernando Pessoa"));
        colleges.add(new College("Universidade Católica do Porto"));
        colleges.add(new College("Faculdade de Engenharia da Universidade do Porto"));

        this.collegeRepo.saveAll(colleges);

        ArrayList<Course> courses = new ArrayList<>();
        courses.add(new Course("Engenharia de Software"));
        courses.add(new Course("Bases de dados"));

        this.courseRepo.saveAll(courses);

        Language language1 = Language.Portuguese;
        Language language2 = Language.French;
        Language language3 = Language.Italian;
        Language language4 = Language.Spanish;

        Explainer explainer1 = new Explainer("Vanilson", language1);
        Explainer explainer2 = new Explainer("João", language2);
        Explainer explainer3 = new Explainer("Filipe", language3);
        Explainer explainer4 = new Explainer("Alexandre", language4);

        Set<Explainer> explainers = new HashSet<>();
        explainers.add(explainer1);
        explainers.add(explainer2);
        explainers.add(explainer3);
        explainers.add(explainer4);

        explainerRepo.saveAll(explainers);

        LocalTime start1 = LocalTime.of(10, 00);
        LocalTime end1 = LocalTime.of(11, 30);
        LocalTime start2 = LocalTime.of(9, 30);
        LocalTime end2 = LocalTime.of(10, 00);

        DayOfWeek dayOfWeek0 = DayOfWeek.MONDAY;
        DayOfWeek dayOfWeek1 = DayOfWeek.FRIDAY;

        LocalDateTime start = LocalDateTime.of(20, Month.DECEMBER, 2, 15, 30);
        LocalDateTime end = LocalDateTime.of(20, Month.DECEMBER, 2, 16, 0);
        LocalDateTime starts = LocalDateTime.of(2020, Month.JANUARY, 16, 9, 0);
        LocalDateTime ends = LocalDateTime.of(2020, Month.JANUARY, 16, 9, 30);

        Set<Availability> availabilities1 = new HashSet<>();
        Availability availability1 = new Availability(dayOfWeek0, start1, end1);
        availability1.setExplainer(explainer1);
        availabilities1.add(availability1);

        Availability availability2 = new Availability(dayOfWeek1, start2, end2);
        availability2.setExplainer(explainer2);
        availabilities1.add(availability2);

        explainer1.setAvailabilities(availabilities1);

        this.availabilityRepo.saveAll(availabilities1);

        Set<Appointment> appointments1 = new HashSet<>();
        Appointment appointment1 = new Appointment(start, end);
        appointment1.setExplainer(explainer1);
        appointments1.add(appointment1);
        Set<Appointment> appointments2 = new HashSet<>();
        Appointment appointment2 = new Appointment(starts, ends);
        appointment2.setExplainer(explainer2);
        appointments2.add(appointment2);

        this.appointmentRepo.saveAll(appointments1);
        this.appointmentRepo.saveAll(appointments2);
    }
}
