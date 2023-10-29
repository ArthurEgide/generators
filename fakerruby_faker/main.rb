require 'faker'
require 'net/http'
require 'json'

def create_step(exec_id, acao, tabela, quantidade, id)
  step = {
    "execucao" => exec_id,
    "horario" => Time.now.strftime("%m-%d-%Y %H:%M:%S.%6N"),
    "acao" => acao,
    "tabela" => tabela,
    "quantidade" => quantidade,
    "id" => id
  }

  post_http("http://tcc_loader:1997/register_step", step.to_json)
end

def post_http(url, data)
  uri = URI(url)
  http = Net::HTTP.new(uri.host, uri.port)
  request = Net::HTTP::Post.new(uri)
  request['Content-Type'] = 'application/json'
  request.body = data

  response = http.request(request)
  puts "Response Code: #{response.code}"
  puts "Response Body: #{response.body}"
end

def generate_cards(n)
  exec_id = SecureRandom.uuid
  create_step(exec_id, 'inicio gerar', 'cards', n, 'fakerruby_faker')
  data = []

  n.times do
    nome = Faker::Company.name
    tipo = ['criatura', 'feitiço', 'artefato', 'encantamento'].sample
    custo_mana = rand(0..7)
    descricao = Faker::Lorem.words(number: rand(40..80)).join(' ')
    poder = rand(0..12)
    resistencia = rand(0..12)
    raridade = ['comum', 'incomum', 'rara', 'mitica'].sample

    card = {
      nome: nome,
      tipo: tipo,
      custo_mana: custo_mana,
      descricao: descricao,
      poder: poder,
      resistencia: resistencia,
      raridade: raridade
    }

    data << card
  end
  create_step(exec_id, 'fim gerar', 'cards', n, 'fakerruby_faker')
  data
end

def generate_decks(n)
  exec_id = SecureRandom.uuid
  create_step(exec_id, 'inicio gerar', 'decks', n, 'fakerruby_faker')  
  data = []

  n.times do
    nome = "#{Faker::Company.buzzword} #{Faker::Adjective.positive}"
    descricao = Faker::Lorem.words(number: rand(40..80)).join(' ')
    formato = ['commander', 'padrão', 'pioneiro', 'moderno', 'alchemy', 'explorador', 'histórico', 'pauper', 'brawl', 'conspiracy', 'gigante de duas cabeças', 'legado', 'vintage', 'planechase', 'oathbreaker'].sample
    numero_cartas = 60

    deck = {
      nome: nome,
      descricao: descricao,
      formato: formato,
      numero_cartas: numero_cartas
    }

    data << deck
  end
  create_step(exec_id, 'fim gerar', 'decks', n, 'fakerruby_faker')  
  data
end

n_cards = ARGV[0].to_i
n_decks = ARGV[1].to_i

cartas = generate_cards(n_cards)
decks = generate_decks(n_decks)

