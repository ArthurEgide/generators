package com.generator.jfairy;

import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
    final URI STEP_ENDPOINT = URI.create("http://tcc_loader:1997/register_step");

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

    // TODO: Descomentar
    // Decks post
    // public void sendPost(List<Deck> deckList) throws Exception {
    //   Gson gson = new GsonBuilder().create();
    //   String json = gson.toJson(deckList);
    //   HttpEntity entity = new StringEntity(json);
    //   sendPost(this.DECK_ENDPOINT, entity);
    // }

    // Cards post
    public void sendPost(List<Card> cardList) throws Exception {
      Gson gson = new GsonBuilder().create();
      String json = gson.toJson(cardList);
      HttpEntity entity = new StringEntity(json);
      sendPost(this.CARD_ENDPOINT, entity);
    }

    // Step post
    public void sendPost(Step step) throws Exception {
      Gson gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd hh:mm:ss.S")
        .create();
      String json = gson.toJson(step);
      HttpEntity entity = new StringEntity(json);
      sendPost(this.STEP_ENDPOINT, entity);
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
  private int custo_mana;
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
    this.custo_mana = bp.randomBetween(0, 7);
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

}

class Step {
  String execucao;
  Timestamp horario;
  String acao;
  String tabela;
  int quantidade;
  String id = "jfairy";

  Step(String tbl, int qt){
    this.execucao = UUID.randomUUID().toString();
    this.tabela = tbl;
    this.quantidade = qt;
  }

  public String getAcao() {
    return this.acao;
  }

  public String getExecucao() {
    return this.execucao;
  }
  
  public Timestamp getHorario() {
    return this.horario;
  }

  public String getId() {
    return this.id;
  }

  public int getQuantidade() {
    return this.quantidade;
  }

  public String getTabela() {
    return this.tabela;
  }

  public void setAcao(String acao) {
    this.acao = acao;
    this.horario = new Timestamp(System.currentTimeMillis());
  }
  
}

public class App {
  static List<Card> generateCards(int n) {
    List<Card> cards = new ArrayList<Card>();
    while(n-->0){
      cards.add(new Card());
    }
    return cards;
  }

  // TODO: Descomentar
  // static List<Card> generateDecks(int n) {
  //   List<Deck> decks = new ArrayList<Deck>();
  //   while(n-->0){
  //     decks.add(new Deck());
  //   }
  //   return decks;
  // }

  public static void main(String[] args) {

    HttpClientEgide obj = new HttpClientEgide();
    
    try {
      // TODO: Corrigir os valores de geração
      int[] leve = {10, 2};
      int[] medio = {30, 3};
      int[] massivo = {50, 5};

      List<int[]> sizes = new ArrayList<int[]>();
      sizes.add(leve);
      sizes.add(medio);
      sizes.add(massivo);

      Step step;
      int qtCartas, qtDecks;
      for (int[] quantidades : sizes) {
        qtCartas = quantidades[0];
        qtDecks  = quantidades[1];

        step = new Step("cards", qtCartas);
        step.setAcao("inicio gerar");
        obj.sendPost(step);
        List<Card> cards = generateCards(qtCartas);
        step.setAcao("fim gerar");
        obj.sendPost(step);
        
        step = new Step("cards", qtCartas);
        step.setAcao("inicio persistir");
        obj.sendPost(step);
        obj.sendPost(cards);
        step.setAcao("fim persistir");
        obj.sendPost(step);

        // TODO: Descomentar
        // step = new Step("decks", qtDecks);
        // step.setAcao("inicio gerar");
        // obj.sendPost(step);
        // List<Deck> decks = generateDecks(qtDecks);
        // step.setAcao("fim gerar");
        // obj.sendPost(step);
        
        // step = new Step("decks", qtDecks);
        // step.setAcao("inicio persistir");
        // obj.sendPost(step);
        // obj.sendPost(decks);
        // step.setAcao("fim persistir");
        // obj.sendPost(step);


      }

    } catch (Exception e) {
      System.err.println(e);
    }
  }
}
