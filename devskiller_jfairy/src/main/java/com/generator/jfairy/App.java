package com.generator.jfairy;

import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.jfairy.Fairy;
import org.jfairy.producer.company.Company;
import org.jfairy.producer.person.Person;
import org.jfairy.producer.text.TextProducer;
import org.jfairy.producer.BaseProducer;

class HttpClientEgide {
    final URI CARD_ENDPOINT = URI.create("http://tcc_loader:1997/create_cards");
    final URI DECK_ENDPOINT = URI.create("http://tcc_loader:1997/create_decks");
    final URI DECK_CARDS_ENDPOINT = URI.create("http://tcc_loader:1997/create_deck_cards");

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private void close() throws IOException {
        httpClient.close();
    }

    public void sendGet(URI uri) throws Exception {
        HttpGet request = new HttpGet(uri);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
            }
        }
    }

    // Cards post
    public void sendPost(Card card) throws Exception {
      
      List<Card> cardList = new ArrayList<>();
      cardList.add(card);

      Gson gson = new GsonBuilder().create();
      String json = gson.toJson(cardList);


      HttpEntity entity = new StringEntity(json);

      System.out.println("Eita nós moscada");

      sendPost(this.CARD_ENDPOINT, entity);
    }

    public void sendPost(URI uri, HttpEntity json) throws Exception {

      HttpPost post = new HttpPost(uri);
      post.setEntity(json);
  

      try (CloseableHttpClient httpClient = HttpClients.createDefault();
          CloseableHttpResponse response = httpClient.execute(post)) {

        System.out.println(EntityUtils.toString(response.getEntity()));
      }

    }

  }

class Card {
  private String nome;
  private String tipo;
  private int custoMana;
  private String descricao;
  private int poder;
  private int resistencia;
  private String raridade;
  
  static Fairy fairy;

  Card() {
    fairy = Fairy.create();
    this.generateNome();
    this.generateTipo();
    this.generateCustomana();
    this.generateDescricao();
    this.generatePoder();
    this.generateResistencia();
    this.generateRaridade();
  }

  public String getNome() {
    return nome;
  }

  public int getCustoMana() {
    return custoMana;
  }
  
  public String getDescricao() {
    return descricao;
  }

  public int getPoder() {
    return poder;
  }
  
  public String getRaridade() {
    return raridade;
  }

  public int getResistencia() {
    return resistencia;
  }

  public String getTipo() {
    return tipo;
  }


  // Done
  void generateNome() {
    // • Nome: O nome da carta, que deve ser uma sequência de caracteres. Até 42
    // caracteres
    Company company = fairy.company();
    Person person = fairy.person();

    this.nome = company.name().split(" ")[0] + " " + person.lastName();
  }
  
  // Done
  void generateTipo() {
    // • Tipo: O tipo da carta, que pode ser uma criatura, feitiço, artefato,
    // encantamento
    BaseProducer bp = fairy.baseProducer();
    List<String> al = new ArrayList<String>();
    al.add("criatura");
    al.add("feitiço");
    al.add("artefato");
    al.add("encantamento");
    this.tipo = bp.randomElement(al);
  }
  
  // Done
  void generateCustomana() {
    // • Custo de Mana: O custo de mana necessário para conjurar a carta,
    // representado por um valor numérico entre 0 e 7.
    BaseProducer bp = fairy.baseProducer();
    this.custoMana = bp.randomBetween(0, 7);
  }

  // Done
  void generateDescricao() {
    // • Descrição: Uma descrição da carta que pode conter texto livre. Entre 40 e
    // 80 caracteres
    TextProducer tp = fairy.textProducer();
    tp.limitedTo(80);
    this.descricao = tp.paragraph();
  }

  // Done
  void generatePoder() {
    // • Poder: O valor de poder da criatura (se aplicável), representado por um
    // valor numérico. Entre 0 e 12
    BaseProducer bp = fairy.baseProducer();
    this.poder = bp.randomBetween(0, 12);
  }

  // Done
  void generateResistencia() {
    // • Resistência: A resistência da criatura (se aplicável), representada por um
    // valor numérico. Entre 0 e 12
    BaseProducer bp = fairy.baseProducer();
    this.resistencia = bp.randomBetween(0, 12);
  }

  // Done
  void generateRaridade() {
    // • Raridade: A raridade da carta, que pode ser comum, incomum, rara ou mítica.
    BaseProducer bp = fairy.baseProducer();
    List<String> al = new ArrayList<String>();
    al.add("incomum");
    al.add("comum");
    al.add("rara");
    al.add("mitica");
    this.raridade = bp.randomElement(al);
  }

  public String toString() {// overriding the toString() method
    return "Replaced toString: " + this.nome;
  }

}

public class App {
  public static void main(String[] args) {
    Card card = new Card();

    System.out.println("Iniciando aqui paizão");
    // HttpClient httpclient = HttpClients.createDefault();
    // HttpPost httppost = new HttpPost("tcc_loader:1997");
    // URI address = new URI("https", null, "google.com", 80, "/", null, null);
    // HttpClient hc = new HttpClient();

    HttpClientEgide obj = new HttpClientEgide();
    URI x = URI.create("http://tcc_loader:1997");

    try {
      // obj.sendGet(x);
      obj.sendPost(card);
    } catch (Exception e) {
      System.out.println("Fodeu");
    }

    System.out.println("Finalizando aqui paizão");
    // System.out.println(t1);
  }
}
