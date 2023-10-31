from faker import Faker
import uuid
import requests
import json
import sys

from datetime import datetime

def create_step(exec_id, acao, tabela, quantidade, id):
    current_time = datetime.now().strftime("%m-%d-%Y %H:%M:%S.%f")[:-3]
    step = {
        "execucao": exec_id,
        "horario": current_time,
        "acao": acao,
        "tabela": tabela,
        "quantidade": quantidade,
        "id": id
    }
    post_http("http://tcc_loader:1997/register_step", json.dumps(step))

def post_http(url, data):
    headers = {"Content-Type": "application/json"}
    response = requests.post(url, data=data, headers=headers)
    print("Response Code:", response.status_code)
    print("Response Content:", response.text)

fake = Faker()
class G_Card:
  def __init__(self):
    self.nome = fake.company()
    self.tipo = fake.random_choices(elements=('criatura', 'feitiço', 'artefato', 'encantamento'), length=1)[0]
    self.custo_mana = fake.random_int(0,7)
    self.descricao = fake.sentence(nb_words=30)
    self.poder = fake.random_int(0,12)
    self.resistencia = fake.random_int(0,12)
    self.raridade = fake.random_choices(elements=('comum', 'incomum', 'rara', 'mitica'), length=1)[0]

class G_Deck:
  def __init__(self):
    self.nome = fake.company()
    self.descricao = fake.sentence(nb_words=30)
    self.formato = fake.random_choices(elements=("commander", "padrão", "pioneiro", "moderno", "alchemy", "explorador", "histórico", "pauper", "brawl", "conspiracy", "gigante de duas cabeças", "legado", "vintage", "planechase", "oathbreaker"), length=1)[0]
    self.numero_de_cartas = 60

def generateCards(n_cards):
  execId = str(uuid.uuid4())
  create_step(execId, 'inicio gerar', 'cards', n_cards, 'joke2_faker')
  for i in range(n_cards):
    d = G_Card()
  create_step(execId, 'fim gerar', 'cards', n_cards, 'joke2_faker');

def generateDecks(n_decks):
  execId = str(uuid.uuid4())
  create_step(execId, 'inicio gerar', 'decks', n_decks, 'joke2_faker')
  for i in range(n_decks):
    d = G_Deck()
  create_step(execId, 'fim gerar', 'decks', n_decks, 'joke2_faker');
  
  

generateCards(int(sys.argv[1]))
generateDecks(int(sys.argv[2]))