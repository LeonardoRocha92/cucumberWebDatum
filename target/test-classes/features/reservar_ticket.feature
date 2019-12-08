# language: pt
Funcionalidade: Realizar reservas de Tickets
  
  Como um usuario
  Gostaria de acessar a aplicacao
  Para que eu possa reservar tickets

  Cenario: Deve reservar um ticket com sucesso
    Dado que estou acessando a aplicacao
    Quando informo o usuario "mercury"
    E a senha "mercury"
    E seleciono sign-in
    Entao visualizo a tela Flight Finder
    Quando selecionar cidade de origem e de destino
    E selecionar data maior que a corrente
    E selecionar classe e o numero de passageiros
    E selecionar continue
    Entao visualizo a tela Select Flight
    Quando selecionar o voo de partida
    E selecionar o voo de retorno
    E selecionar continue
    Entao visualizo a tela Book A Flight s
    Quando preencher First Name e Last Name
    E preencher numero do cartao
    E selecionar Secure Puchase
    Entao uma Ordem e gerada.
