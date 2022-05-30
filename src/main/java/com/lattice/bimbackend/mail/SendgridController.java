package com.lattice.bimbackend.mail;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping(value = "/mail")
public class SendgridController {

    private final Logger LOG = LoggerFactory.getLogger(SendgridController.class);

    /**
     *
     * @param url
     * @param cc
     * @return
     */
    @GetMapping(value = "magic")
    @CrossOrigin
    public ResponseEntity getMail(@RequestParam(value = "url") String url,@RequestParam(value = "cc", required = false) String cc) {
        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Email to = new Email("santhosh@lattice.site");
        Email from = new Email("santhosh@lattice.site");
        Mail mail = new Mail();
        mail.setFrom(new Email("santhosh@lattice.site"));
        mail.setTemplateId("d-bc95c34997ea4c75a29dc6bf3bad4eaf");
        Personalization personalization = new Personalization();
//        personalization.setFrom(new Email("santhosh@lattice.site"));
        personalization.addTo(new Email("sankha@lattice.site"));
        try{
            if(!Objects.isNull(cc))
                personalization.addCc(new Email(cc));
        } catch (Exception e) {
            LOG.error("Exception While Adding CC",e);
        }
        personalization.addDynamicTemplateData("urlLink",url);
        mail.addPersonalization(personalization);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (Exception ex) {
            LOG.error("Exception",ex);
        }
        return ResponseEntity.ok().build();
    }

}
