package com.rogerioreis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rogerioreis.dto.ServiceStatusDto;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@EnableScheduling
public class VerificarDisponibilidade {

    static Document document;
    static String regex = "\"([^\"]*)\"";
    static Pattern pattern = Pattern.compile(regex);

    private final long SEGUNDO = 5000;
    private final long MINUTO = SEGUNDO * 60;

    @Scheduled(fixedDelay = MINUTO)
    public void verificarAcada5Minutos() throws IOException {

        final String URL = "https://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx?versao=0.00&tipoConteudo=P2c98tUpxrI=";
        System.out.println("Conectando na url: " + URL);
        document = coletarDados(URL);
        tratandoDados();
        System.out.println("Verificando disponibilidade de serviços da NFE a cada 05 (cinco) minutos...");
    }

    static void tratandoDados() {
//                Filtrando conteúdo
        Element conteudo = document.getElementsByClass("tabelaListagemDados").first();

        Element tBody = conteudo.getElementsByTag("tbody").first();

//            Criando uma lista de objetos do tipo ServiceStatus

        List<ServiceStatusDto> objeto = new ArrayList<>();

        List<Element> td = tBody.getElementsByTag("tr");

        for (Element elementTD : td) {
            Matcher matcher = pattern.matcher(elementTD.getElementsByIndexEquals(5).toString());
            if (matcher.find()) {
                String status = matcher.group(1);

                ServiceStatusDto objStatus = new ServiceStatusDto(elementTD.getElementsByIndexEquals(0).text()
                        .trim(), status);
                objeto.add(objStatus);
            }
        }

        for (ServiceStatusDto service : objeto) {
            enviarStatusService(converterToJson(service));
        }
    }

    static Document coletarDados(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        return document;
    }

    static String converterToJson(ServiceStatusDto objeto) {
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

    static void enviarStatusService(String objetoJson) {
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
