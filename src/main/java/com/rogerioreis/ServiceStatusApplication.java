package com.rogerioreis;

import com.rogerioreis.model.ServiceStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ServiceStatusApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServiceStatusApplication.class, args);

        String url = "https://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx?versao=0.00&tipoConteudo=P2c98tUpxrI=";
        System.out.println("Conectando na url: " + url);

        try {
//            Conectando na url
            Document document = Jsoup.connect(url).get();

//            Buscando conteúdo
            Element conteudo = document.getElementsByClass("tabelaListagemDados").first();
            System.out.println("Título:" + conteudo.getElementsByTag("caption"));
            System.out.println("########################################################################################");

            Element tBody = conteudo.getElementsByTag("tbody").first();
//            System.out.println("Body da tabela: " + tBody);

//            Criando uma lista de objetos ServiceStatusModel

            List<ServiceStatus> objeto = new ArrayList<>();

            List<Element> td = tBody.getElementsByTag("td");
            for (Element elementTD : td) {
                System.out.println("Tbody: " + elementTD.getElementsByIndexEquals(5));

                ServiceStatus objStatus = new ServiceStatus(elementTD.getElementsByIndexEquals(0).text(),
                        elementTD.getElementsByIndexEquals(5).toString(),
                        LocalDateTime.now(), LocalDateTime.now());
                objeto.add(objStatus);
            }
                System.out.println("############################################################################################");

            for (ServiceStatus service : objeto) {
                System.out.println("Objeto Status: " + service.getAuthorizing() + " / " + service.getStatusServico() + " / " +
                        service.getConsultationDate() + " / " + service.getUpdateConsultationDate());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
