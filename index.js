const faker = require('@faker-js/faker');
const http = require('http');

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

async function createCards(cards) {
  const postData = JSON.stringify(cards);

  const options = {
    host: 'tcc_loader',
    port: 1997,
    path: '/create_cards',
    method: 'POST',
    timeout: 500000, 
    headers: {
      'Content-Type': 'application/json'
    },
  };

  const req = http.request(options, (res) => {
    console.log("Requisição recebida");
    res.setEncoding('utf8');
    
    res.on('data', (chunk) => {
      console.log(`Dados recebidos: ${chunk}`);
    });

    res.on('end', () => {
      console.log('No more data in response.');
    });
  });

  req.on('timeout', () => {
    console.log("Timeout ocorreu");
    req.destroy();
  });

  req.on('error', (e) => {
    console.error(`Problema com a requisição: ${e.message}`);
  });

  req.write(postData);
  req.end();
}


async function run() {
  const getWithForOf = async (num_cards) => {
    const data = [];
    for (const n of num_cards) {
      startTime = performance.now();
      console.log(`Iniciando ${n} cartas. ${startTime}`);
      cards = generateCards(n);
      await createCards(cards);
      endTime = performance.now();
      data.push({ "n": n, "t": endTime - startTime });
    }
    return data;
  };
  
  getWithForOf([2500, 10000, 50000]).then((times) => {
    times.forEach((element) => {
      console.log(`${element['n']} levou ${element['t']} ms`);
    });
  });
}

run()