FindMovie

###### No desenvolvimento da aplicação foram usados: #########

*Retrofit 2: Para realizar as requisições HTTP; É a lib de HTTP Client que eu venho trabalhando já algum tempo, por sua praticidade e por deixar o código mais organizado também.

*GSON: Para parcear as respostas do servidor; Trabalha em conjunto com a Retrofit.

*Picasso: Como lib para tratar as imagens;

*Realm: Para a persistencia dos dados; Como é um insert simples e um select simples, é muito mais pratico utilizar do Realm nesse caso.


######### Layout e Funcionamento ###########

Busquei deixar o layout parecido com a Google Play, com uma lista de cards se movendo para o lado e um slider superior de imagens. São 3 listas, sendo a 3° apenas visivel quando favoritar algum item.
Ao clicar em "MAIS", é direcionado para uma tela com a lista selecionada, paginada, com todos os itens do request.
Ao clicar em um card, é direcionado para a tela de detalhes onde terá todos os dados importantes do filme, sua imagens e 2 botões: O primeiro, "Trailer", chamará uma intent para o Youtube. O segundo, "Favoritar", irá colocar o filme na sua lista de favoritos; Caso já esteja favoritado, o botão se chamará "Desfavoritar".