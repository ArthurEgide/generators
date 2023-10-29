<?php
require_once 'vendor/autoload.php';

class G_Card {
  private $faker;

  public function __construct() {
      $this->faker = Faker\Factory::create();
  }

  public function gerarCarta() {
      $name = $this->faker->company;
      $tipo = $this->faker->randomElement(['criatura', 'feitiço', 'artefato', 'encantamento']);
      $custo_mana = $this->faker->numberBetween(0, 7);
      $descricao = $this->faker->realText($maxNbChars = 80, $indexSize = 1);
      $poder = $this->faker->numberBetween(0, 12);
      $resistencia = $this->faker->numberBetween(0, 12);
      $raridade = $this->faker->randomElement(['comum', 'incomum', 'rara', 'mitica']);

      $carta = [
          'nome' => $name,
          'tipo' => $tipo,
          'custo_mana' => $custo_mana,
          'descricao' => $descricao,
          'poder' => $poder,
          'resistencia' => $resistencia,
          'raridade' => $raridade,
      ];

      return $carta;
  }
}

class G_Deck {
  private $faker;

  public function __construct() {
    $this->faker = Faker\Factory::create();
  }

  public function gerarDeck() {
    $nome = $this->faker->company;
    $descricao = $this->faker->realText($maxNbChars = 80, $indexSize = 1);
    $formato = $this->faker->randomElement(["commander", "padrão", "pioneiro", "moderno", "alchemy", "explorador", "histórico", "pauper", "brawl", "conspiracy", "gigante de duas cabeças", "legado", "vintage", "planechase", "oathbreaker"]);
    $numero_cartas = 60;

    $deck = [
      'nome' => $nome,
      'descricao' => $descricao,
      'formato' => $formato,
      'numero_cartas' => $numero_cartas,
    ];

    return $deck;
  }
}

function gerarDecks($n_decks) {
  $intervalo_reinicio = 1000;
  $g_deck = new G_Deck();
  
  $execId = uniqid();
  createStep($execId, 'inicio gerar', 'decks', $n_decks, 'fzaninotto_faker');
  for ($i = 0; $i < $n_decks; $i++) {

    if ($i % $intervalo_reinicio === 0) {
      unset($g_deck);
      $g_deck = new G_Deck();
    }
    $deckInfo = $g_deck->gerarDeck();
  }

  createStep($execId, 'fim gerar', 'decks', $n_decks, 'fzaninotto_faker');
}

function gerarCards($n_cards) {
  $intervalo_reinicio = 1000;
  $g_card = new G_Card();
  
  $execId = uniqid();
  createStep($execId, 'inicio gerar', 'cards', $n_cards, 'fzaninotto_faker');
  for ($i = 0; $i < $n_cards; $i++) {

    if ($i % $intervalo_reinicio === 0) {
      unset($g_card);
      $g_card = new G_Card();
    }
    $deckInfo = $g_card->gerarCarta();
  }

  createStep($execId, 'fim gerar', 'cards', $n_cards, 'fzaninotto_faker');
}

function createStep($execId, $acao, $tabela, $quantidade, $id) {
  $step = array(
      "execucao" => $execId,
      "horario" => DateTime::createFromFormat('U.u', microtime(true))->format("m-d-Y H:i:s.u"),
      "acao" => $acao,
      "tabela" => $tabela,
      "quantidade" => $quantidade,
      "id" => $id
  );
  
  postHTTP("http://tcc_loader:1997/register_step", json_encode($step));
}

function postHTTP($url, $data) {
  $options = array(
      'http' => array(
          'header' => "Content-type: application/x-www-form-urlencoded\r\n",
          'method' => 'POST',
          'content' => $data
      )
  );
  $context = stream_context_create($options);
  $result = file_get_contents($url, false, $context);

  echo $result->statusCode;
}

$n_cards = $argv[1];
$n_decks = $argv[2];

gerarCards($n_cards);
gerarDecks($n_decks);

?>