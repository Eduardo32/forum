package com.pauloeduardocosta.forum.service;

import com.pauloeduardocosta.forum.model.Usuario;
import com.pauloeduardocosta.forum.service.exception.FalhaNoEnvioDeEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${forum.email.temporario.validade.validade.minutos}")
    private Long validadeSenhaTemporaris;

    public void enviarEmailVerificacao(Usuario usuario) {
        try {
            final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            MimeMessage email = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(email);
            String link = "<a href=\"" + baseUrl + "/api/usuarios/verificar-conta/" + usuario.getVerificacaoEmail().getUuid() + "\">aqui</a>";
            StringBuilder mensagem = new StringBuilder()
                    .append("<p>Olá,</p>")
                    .append("<p>Bem-vinda ao forum.</p>")
                    .append("<p>Para fazer a verificação da sua conta click " + link + ".</p>");

            helper.setSubject("Verificação de conta");
            helper.setTo(usuario.getNome() + "<" + usuario.getEmail() + ">");
            helper.setFrom("Forum <contato@forum.com>");
            helper.setText(mensagem.toString(), true);

            mailSender.send(email);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new FalhaNoEnvioDeEmailException("Por favor, tente novamente mais tarde.");
        }
    }

    public void enviarEmailNovaSenha(Usuario usuario, String senhaTemporaria) {
        try {
            final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            MimeMessage email = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(email);
            StringBuilder mensagem = new StringBuilder();
            String link = "<a href=\"" + baseUrl + "/api/usuarios/" + usuario.getId() + "/cancelar-senha-temp\">aqui</a>";
            mensagem.append("<p>Olá,</p>")
                    .append("<p>Sua nova senha é <b>" + senhaTemporaria + "</b>. Essa senha é temporário e ira expirar em " + validadeSenhaTemporaris + " minutos.</p>")
                    .append("<p>Por favor use essa senha para fazer o login e cadastrar uma nova senha.</p>")
                    .append("<p>Caso essa solicitação não tenha sido feita por você click " + link + ".</p>");

            helper.setSubject("Nova senha");
            helper.setTo(usuario.getNome() + "<" + usuario.getEmail() + ">");
            helper.setFrom("Forum <contato@forum.com>");
            helper.setText(mensagem.toString(), true);

            mailSender.send(email);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new FalhaNoEnvioDeEmailException("Por favor, tente novamente mais tarde.");
        }
    }
}
