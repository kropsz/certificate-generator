package com.github.kropsz.certificate_generator.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.kropsz.certificate_generator.model.Course;
import com.github.kropsz.certificate_generator.model.User;
import com.github.kropsz.certificate_generator.registration.UserCourses;
import com.github.kropsz.certificate_generator.repository.CourseRepository;
import com.github.kropsz.certificate_generator.repository.UserCoursesRepository;
import com.github.kropsz.certificate_generator.repository.UserRepository;
import com.github.kropsz.certificate_generator.service.email.EmailService;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCoursesService {

        private final UserCoursesRepository userCoursesRepository;
        private final CourseRepository courseRepository;
        private final UserRepository userRepository;
        private final EmailService emailService;

        public UserCourses registerUserToCourse(String userId, String courseId) {

                userCoursesRepository.findByUserIdAndCourseId(userId, courseId).ifPresent(userCourse -> {
                        throw new IllegalArgumentException("UserCourse already exists");
                });

                var user = userRepository.findById(userId).orElseThrow(
                                () -> new IllegalArgumentException("User not found"));
                var course = courseRepository.findById(courseId).orElseThrow(
                                () -> new IllegalArgumentException("Course not found"));
                user.addCourse(courseId);
                userRepository.save(user);
                var userCourse = buildUserCourse(user, course);

                return userCoursesRepository.save(userCourse);
        }

        public UserCourses completeCourse(String userId, String courseId) {
                var userCourse = userCoursesRepository.findByUserIdAndCourseId(userId, courseId).orElseThrow(
                                () -> new IllegalArgumentException("UserCourse not found"));
                if (userCourse.isCompleted()) {
                        throw new IllegalArgumentException("Course already completed");

                }
                userRepository.findById(userId).ifPresent(user -> {
                        user.removeCourse(courseId);
                        userRepository.save(user);
                });
                userCourse.setCompleted(true);
                userCourse.setEndDate(LocalDate.now());
                return userCoursesRepository.save(userCourse);

        }

        public UserCourses buildUserCourse(User user, Course course) {
                return UserCourses.builder()
                                .userId(user.getId())
                                .courseId(course.getId())
                                .startDate(LocalDate.now())
                                .endDate(null)
                                .completed(false)
                                .certificateId(null)
                                .build();
        }

        public void generateCertificate(String userId, String courseId) {
                var user = userRepository.findById(userId).orElseThrow(
                                () -> new IllegalArgumentException("User not found"));

                var course = courseRepository.findById(courseId).orElseThrow(
                                () -> new IllegalArgumentException("Course not found"));

                var userCourse = userCoursesRepository.findByUserIdAndCourseId(userId, courseId).orElseThrow(
                                () -> new IllegalArgumentException("UserCourse not found"));
                if (!userCourse.isCompleted()) {
                        throw new IllegalArgumentException("UserCourse not completed");

                }
                if (userCourse.getCertificateId() != null) {
                        throw new IllegalArgumentException("Certificate already generated");
                }

                try {
                        String outputDir = "certificates";
                        String fileName = "certificado-" + user.getId() + ".pdf";
                        String outputPath = outputDir + File.separator + fileName;

                        File directory = new File(outputDir);
                        if (!directory.exists()) {
                                directory.mkdirs();
                        }

                        String pdfPath = "certificadoBase.pdf";
                        PdfDocument pdfDoc = new PdfDocument(new PdfReader(pdfPath), new PdfWriter(outputPath));

                        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);

                        PdfFormField nameField = form.getField("Text4");
                        if (nameField != null) {
                                nameField.setValue(user.getName());
                                nameField.setFontSize(14);
                        }

                        PdfFormField courseField = form.getField("Text5");
                        if (courseField != null) {
                                courseField.setValue(course.getName());
                                courseField.setFontSize(14);
                        }

                        PdfFormField durationField = form.getField("Text6");
                        if (durationField != null) {
                                durationField.setValue(String.valueOf(course.getDuration()));
                                durationField.setFontSize(14);
                        }

                        PdfFormField endDate = form.getField("Text7");
                        if (durationField != null) {
                                endDate.setValue(String.valueOf(userCourse.getEndDate()));
                                endDate.setFontSize(14);
                        }

                        pdfDoc.close();
                        UUID certificateId = UUID.randomUUID();
                        userCourse.setCertificateId(certificateId.toString());
                        sendEmail(user, course, new File(outputPath));

                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        public void sendEmail(User user, Course course, File certificate) {
                StringBuilder text = new StringBuilder();
                text = text.append("Olá ").append(user.getName()).append(",\n\n")
                                .append("Parabéns por concluir o curso ").append(course.getName()).append(".\n\n")
                                .append("Segue em anexo o certificado de conclusão.\n\n")
                                .append("Atenciosamente,\n")
                                .append("Equipe de suporte");

                try {
                        emailService.sendEmail(user.getEmail(), "Certificado de conclusão",
                                        text.toString(), certificate);
                } catch (Exception e) {
                        e.printStackTrace();
                }

        }
}
