const faker = require('@faker-js/faker');
const http = require('http');
const crypto = require('crypto')

var options = {
  host: 'tcc_loader',
  port: 1997,
  method: 'POST',
  timeout: 500000, 
  headers: {
    'Content-Type': 'application/json'
  },
};

function cardsRepetition(nRemaining, deck_id, card_idMin, card_idMax) {
  data = []

  while(nRemaining > 4) {
    quantidade = faker.fakerPT_BR.number.int({ min: 1, max: 4 })
    card_id = faker.fakerPT_BR.number.int({ min: card_idMin, max: card_idMax })
    data.push({
      "deck_id": deck_id,
      "card_id" : card_id,
      "quantidade"  : quantidade ,
    })
    nRemaining -= quantidade
  }

  quantidade = nRemaining
  card_id = faker.fakerPT_BR.number.int({ min: card_idMin, max: card_idMax })
  
  data.push({
    "deck_id": deck_id,
    "quantidade"  : quantidade ,
    "card_id" : card_id
  })

  return data;
}

function generateCards(n) {
  var data = []
  for (let index = 0; index < n; index++) {
    data.push({
      nome: faker.fakerPT_BR.string.faker.person.fullName(),
      tipo: faker.fakerPT_BR.helpers.arrayElement(['criatura', 'feitiço', 'artefato', 'encantamento']),
      custo_mana: faker.fakerPT_BR.number.int({ min: 0, max: 7 }),
      descricao: faker.fakerPT_BR.lorem.words({min:40, max:80}),
      poder: faker.fakerPT_BR.number.int({ min: 0, max: 12 }),
      resistencia: faker.fakerPT_BR.number.int({ min: 0, max: 12 }),
      raridade: faker.fakerPT_BR.helpers.arrayElement(['comum', 'incomum', 'rara', 'mitica']),
    })
  }
  return data;
}

function generateDecks(n) {
  var data = []
  for (let index = 0; index < n; index++) {
    data.push({
      nome: faker.fakerPT_BR.string.faker.company.buzzVerb() + " " + faker.fakerPT_BR.string.faker.company.buzzAdjective(),
      descricao: faker.fakerPT_BR.lorem.words({min:40, max:80}),
      formato: faker.fakerPT_BR.helpers.arrayElement(["commander","padrão","pioneiro","moderno","alchemy","explorador","histórico","pauper","brawl","conspiracy","gigante de duas cabeças","legado","vintage","planechase","oathbreaker"]),
      numero_cartas: 60
    })
  }
  return data;
}

function generateDecksCards(card_ids, deckIds) {
  let decks = []
  for( i = deckIds['min'] ; i < deckIds['max'] ; i++) {
    cards = cardsRepetition(60, i, card_ids['min'], card_ids['max'])
    decks.push(cards)
  }
  return decks;
}

async function doRequest(options, postData) {
  const req = new Promise ((resolve, reject) => {
    let response = '';
    let req = http.request(options, (resp) => {
      let data = '';

      // A chunk of data has been received.
      resp.on('data', (chunk) => {
        data += chunk;
      });

      // The whole response has been received. Print out the result.
      resp.on('end', () => {
        resolve(JSON.parse(data));
      });

    })

    req.on('error', err => {
      console.log("Error: " + err.message);
      reject(err);
    });

    if(postData != null){
      req.write(postData);
    }
    
    req.end();
  }); 

  return req;
}

async function persist(data, endpoint) {
  const postData = JSON.stringify(data);
  options['path'] = endpoint

  try {
    let res = await doRequest(options, postData);
    return res;
  } catch (err) {
    console.log('some error occurred...');
    console.error(err)
    return null;
  }
}

function createStep(execId, acao, tabela, quantidade, id){
  ts = new Date();
  step = {
    "execucao": execId,
    "horario": ts,
    "acao": acao,
    "tabela": tabela,
    "quantidade": quantidade,
    "id": id
  };
  console.log(step);
  return persist(step, '/register_step');
}

async function run() {
  const getWithForOf = async (num_cards) => {
    let s;
    const data = [];
    for (const n of num_cards) {
      exec_id = crypto.randomUUID()
      await createStep(exec_id, 'inicio gerar', 'cards', n['cards'], 'fakerjs')
      cards = generateCards(n['cards']);
      await createStep(exec_id, 'fim gerar', 'cards', n['cards'], 'fakerjs')
      
      // exec_id = crypto.randomUUID()
      // await createStep(exec_id, 'inicio persistir', 'cards', n['cards'], 'fakerjs')
      // card_id_range = await persist(cards, '/create_cards');
      // await createStep(exec_id, 'fim persistir', 'cards', n['cards'], 'fakerjs')
      
      exec_id = crypto.randomUUID()
      await createStep(exec_id, 'inicio gerar', 'decks', n['decks'], 'fakerjs')
      decks = generateDecks(n['decks']);
      await createStep(exec_id, 'fim gerar', 'decks', n['decks'], 'fakerjs')
      
      // exec_id = crypto.randomUUID()
      // await createStep(exec_id, 'inicio persistir', 'decks', n['decks'], 'fakerjs')
      // deck_id_range = await persist(decks, '/create_decks');
      // await createStep(exec_id, 'fim persistir', 'decks', n['decks'], 'fakerjs')
      
      // exec_id = crypto.randomUUID()
      // await createStep(exec_id, 'inicio', 'deck_cards gerar', 60 * n['decks'], 'fakerjs')
      // decks = generateDecksCards(card_id_range, deck_id_range);
      // await createStep(exec_id, 'fim gerar', 'deck_cards', 60 * n['decks'], 'fakerjs')
      
      // exec_id = crypto.randomUUID()
      // await createStep(exec_id, 'inicio', 'deck_cards persistir', 60 * n['decks'], 'fakerjs')
      // await persist(decks, '/create_deck_cards');
      // await createStep(exec_id, 'fim persistir', 'deck_cards', 60 * n['decks'], 'fakerjs')

    }
    return data;
  };
  
  getWithForOf([{
    cards: 10000,
    decks: 1000
  }, {
    cards: 100000,
    decks: 5000
  }, {
    cards: 300000,
    decks: 10000
  },]).then((times) => {
    times.forEach((element) => {
      console.log(`${element['n']} levou ${element['t']} ms`);
    });
  });
}

run()