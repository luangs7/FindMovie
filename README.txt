FindMovie

###### No desenvolvimento da aplica��o foram usados: #########

*Retrofit 2: Para realizar as requisi��es HTTP; � a lib de HTTP Client que eu venho trabalhando j� algum tempo, por sua praticidade e por deixar o c�digo mais organizado tamb�m.

*GSON: Para parcear as respostas do servidor; Trabalha em conjunto com a Retrofit.

*Picasso: Como lib para tratar as imagens;

*Realm: Para a persistencia dos dados; Como � um insert simples e um select simples, � muito mais pratico utilizar do Realm nesse caso.


######### Layout e Funcionamento ###########

Busquei deixar o layout parecido com a Google Play, com uma lista de cards se movendo para o lado e um slider superior de imagens. S�o 3 listas, sendo a 3� apenas visivel quando favoritar algum item.
Ao clicar em "MAIS", � direcionado para uma tela com a lista selecionada, paginada, com todos os itens do request.
Ao clicar em um card, � direcionado para a tela de detalhes onde ter� todos os dados importantes do filme, sua imagens e 2 bot�es: O primeiro, "Trailer", chamar� uma intent para o Youtube. O segundo, "Favoritar", ir� colocar o filme na sua lista de favoritos; Caso j� esteja favoritado, o bot�o se chamar� "Desfavoritar".