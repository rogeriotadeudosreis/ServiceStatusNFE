package com.rogerioreis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rogerioreis.dto.ServiceStatusDto;
import com.rogerioreis.model.ServiceStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ServiceStatusApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServiceStatusApplication.class, args);

        String url = "https://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx?versao=0.00&tipoConteudo=P2c98tUpxrI=";
        System.out.println("Conectando na url: " + url);

//        @scheduled -> start aplicação de acordo com o tempo determiando

        try {
//            Conectando na url
            Document document = Jsoup.connect(url).get();

//            Buscando conteúdo
            Element conteudo = document.getElementsByClass("tabelaListagemDados").first();

            Element tBody = conteudo.getElementsByTag("tbody").first();

//            Criando uma lista de objetos do tipo ServiceStatus

            List<ServiceStatusDto> objeto = new ArrayList<>();

            List<Element> td = tBody.getElementsByTag("tr");
            for (Element elementTD : td) {
                System.out.println("Tbody: " + elementTD);

                ServiceStatusDto objStatus = new ServiceStatusDto(elementTD.getElementsByIndexEquals(0).text().trim(),
                        elementTD.getElementsByIndexEquals(5).toString()
                );
                objeto.add(objStatus);
            }
            System.out.println("############################################################################################");

            for (ServiceStatusDto service : objeto) {
                enviarStatusService(converterToJson(service));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String converterToJson(ServiceStatusDto objeto) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(objeto);
            System.out.println("Objeto convertido para json: " + json);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void enviarStatusService(String objetoJson) {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/api/service-status");

        try {
            StringEntity entity = new StringEntity(objetoJson);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = client.execute(httpPost);
            client.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
