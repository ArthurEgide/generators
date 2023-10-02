CREATE TABLE cards (
    card_id serial PRIMARY KEY,
    nome VARCHAR(255),
    tipo VARCHAR(50),
    custo_mana VARCHAR(20),
    descricao TEXT,
    poder INT,
    resistencia INT,
    raridade VARCHAR(20)
);

CREATE TABLE decks (
    deck_id serial PRIMARY KEY,
    nome VARCHAR(255),
    descricao TEXT,
    formato VARCHAR(50),
    numero_cartas INT
);

CREATE TABLE deck_cards (
    deck_card_id serial PRIMARY KEY,
    deck_id INT,
    card_id INT,
    quantidade INT
);