package com.generator.jfairy;

import java.util.ArrayList;
import java.util.List;

import org.jfairy.Fairy;
import org.jfairy.producer.company.Company;
import org.jfairy.producer.person.Person;
import org.jfairy.producer.BaseProducer;
import org.jfairy.producer.util.AlphaNumberSystem;
// import org.jfairy.producer.text.TextProducer;
// import org.jfairy.producer.company.CompanyProvider;

class Card {
  String nome;
  String tipo;
  int custoMana;
  String descricao;
  int poder;
  int resistencia;
  String raridade;
  Fairy fairy;

  Card() {
    this.fairy = Fairy.create();
    System.out.println("Inicializado Cards");
    this.generateNome();
    this.generateTipo();
    this.generateCustomana();
    this.generateDescricao();
    this.generatePoder();
    this.generateResistencia();
    this.generateRaridade();
  }

  void generateNome() {
    // • Nome: O nome da carta, que deve ser uma sequência de caracteres. Até 42
    // caracteres
    Company company = this.fairy.company();
    Person person = this.fairy.person();

    this.nome = company.name().split(" ")[0] + " " + person.lastName();
  }

  void generateTipo() {
    // • Tipo: O tipo da carta, que pode ser uma criatura, feitiço, artefato,
    // encantamento
    BaseProducer bp = this.fairy.baseProducer();
    List<String> ix = new ArrayList<String>();
    ix.add("criatura");
    ix.add("feitiço");
    ix.add("artefato");
    ix.add("encantamento");
    this.tipo = bp.randomElement(ix);
  }

  void generateCustomana() {
    // • Custo de Mana: O custo de mana necessário para conjurar a carta,
    // representado por um valor numérico entre 0 e 7.
    this.custoMana = 1;
  }

  void generateDescricao() {
    // • Descrição: Uma descrição da carta que pode conter texto livre. Entre 40 e
    // 80 caracteres
    this.descricao = "generateDescricao";
  }

  void generatePoder() {
    // • Poder: O valor de poder da criatura (se aplicável), representado por um
    // valor numérico. Entre 0 e 12
    this.poder = 1;
  }

  void generateResistencia() {
    // • Resistência: A resistência da criatura (se aplicável), representada por um
    // valor numérico. Entre 0 e 12
    this.resistencia = 1;
  }

  void generateRaridade() {
    // • Raridade: A raridade da carta, que pode ser comum, incomum, rara ou mítica.
    this.raridade = "generateRaridade";
  }

  public String toString() {// overriding the toString() method
    return "Replaced toString: " + this.nome;
  }

}

public class App {
  public static void main(String[] args) {
    // Fairy fairy = Fairy.create();
    // Person person = fairy.person();
    // System.out.println(person.firstName());

    System.out.println("Iniciando aqui paizão");
    Card t1 = new Card();
    System.out.println(t1);
  }
}
