# Projcmov
Projecto de Computação Móvel e Ubíqua

App UbiBike
The goal of this project is to develop a distributed mobile application named UbiBike, which aims to provide a set
of functionalities to cyclists in urban centers.

Target: Android >= 4.0
Wireless comunication : Wifi Direct
(PS: Não existe realmente Bluetooth Comunication visto que na implementação eles são nós WifiDirect, para "simplicidade" do projecto)

Funcionalidades

Entre dispositivos móveis (utilizando Wifi Direct):

 - Enviar e receber pontos
 - Enviar e receber mensagens de texto
 
Entre dispositivos móveis e o servidor central:

 - Registar um utilizador
 - Enviar uma nova trajectória
 - Mostrar a trajectória mais recente e a ultima trajectoria no mapa.
 - Receber informação do utilizador (pontos e trajectorias)
 - Receber lista de "estações" (temos que arranjar um nome melhor para isto) com bicicletas para reservar.
 - Reservar determinada bicicleta de determinada "estação" (mostrando a sua localização no mapa)

Entre dispositivos moveis, BLE beacons e o servidor central (acho que é utilizando Wifi Direct):

 - Notificação que uma bicicleta foi "apanhada" (pick up?)
 - Notificação que uma bicicleta foi "deixada".
 
Entre utilizador e bicicleta a ser utilizada:

 - Notificar que bicicleta está a ser utilizada e por qual utilizador.
