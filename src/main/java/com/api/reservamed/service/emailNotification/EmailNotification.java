package com.api.reservamed.service.emailNotification;

import com.api.reservamed.dtos.RequestAppointmentDTO;
import com.api.reservamed.service.DoctorsService;
import com.api.reservamed.service.PatientService;
import com.api.reservamed.service.validations.scheduling.ValidationAppointmentScheduling;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class EmailNotification {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorsService doctorsService;

    @Autowired
    private List<ValidationAppointmentScheduling> validations;

    private String remetente = "testparamensagens@gmail.com";
    private String assunto = "Clínica Conceição Lobo - Confirmação de cosulta ";


    //função criada para enviar um email de notificação para o cliente
    public String enviarEmailCliente(RequestAppointmentDTO appointmentData){

        var patient = patientService.getByCpf(appointmentData.patientCpf());
        var doctor = doctorsService.getById(appointmentData.doctorId());

        var nomePatient = patient.getName();
        var emailPatient = patient.getEmail();

        var nomeDoctor = doctor.getName();
        var doctorSpecialty = doctor.getSpecialty();

        validations.forEach(v -> v.validate(appointmentData));

        String[] primeitoNomeArray = nomePatient.split(" ");
        String primeitoNome = primeitoNomeArray[0];

        try{

            MimeMessage mensagem = javaMailSender.createMimeMessage();
            MimeMessageHelper help = new MimeMessageHelper(mensagem,true);
            help.setFrom(remetente);
            help.setSubject(assunto);
            help.setTo(emailPatient);

            String template = carregarTemplate();

            template = template.replace("#{nome}", primeitoNome);
            template = template.replace("#{nomeMedico}", nomeDoctor);
            template = template.replace("#{especialidade}", doctorSpecialty);
            template = template.replace("#{dia}", primeitoNome);
            template = template.replace("#{hora}", primeitoNome);
            help.setText(template,true);
            javaMailSender.send(mensagem);

            return "ok";

        }catch (Exception e){
            return "erro ao enviar";
        }

    }

    public String carregarTemplate() throws IOException {
        ClassPathResource resource = new ClassPathResource("email.html");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}
