<sup>Lorenzo Mirto Bertoldo @climadd</sup>
# GATEWAY IOT

### Quickstart Guide
	
The 4 classes to run the gateway process are (ordered by launch timing):

	1. BackendMessageReceiver.java			 (in test.java.org.lore.mqtt.backend)
	2. GatewayApp.java 				 (in main.java.org.lore.app)
 	3. SimulatorGUIApp.java 			 (in test.java.org.lore.mqtt.backend.simulators)
	4. BackendMessageSender.java			 (in test.java.org.lore.mqtt.backend)

### Project Overview

<img src="/progetto_reti2/src/readme/hexagonal.png" style="display: inline-block; margin: 0 auto; max-width: 300px">

-The **Gateway**, as the bridge between the *Broker* and the *IOT Devices (Sensors and Actuators)*, communicates with the *Backend* through [MQTT protocol](https://mqtt.org/getting-started/), and makes use of dynamically-allocated TCP Sockets to establish the communication to the *IOT Devices*.

-The **Simulated Sensors** produce a fluctuation of values that are consistent to the measurement they are supposed to reproduce. As shown by the *GUI*, the Sensors will independently produce a stream of data whose instances will be sent through *TCP Socket* to the *Gateway* which, in turn, is going to encapsulate the recieved data in MQTT-formatted *Queries* for the *Backend* to recieve.<br />
This whole process of data withdrawal can either be part of a Backend script that periodically issues them, or issued each time a User manually requests the current measurements.

-The **Simulated Actuators**, in order to accurately depict IOT Actuators that can switch between different modes, hold several *boolean* or *enumeration* type variables in their memory that can be modified by Gateway-issued Queries on its own dedicated TCP Socket.
The GUI lets us check real-time the status of every IOT Actuators' variable.
	
-The **Simulated Backend** is split into 2 different Java Classes: *BackendMessageSender* e *BackendMessageReceiver*, they will print on terminal each sent or recieved Message.

-The **Messages** are MQTT formatted, every enumeration-type data relative to both Messages and Measurements is catalogued in "main.java.org.lore.models.mqtt" project's package.




### MQTT Message Topics and Format

**QUERIES**:
	
	- Ricezione richiesta temperatura da parte del backend
 	  - azienda{idAzienda}/serra{idSerra}/sensori/temperatura/rx 
            - *example* 3/5/sensori/temperatura/rx
	
	- TOPIC di ricezione richiesta di cambio stato o richiesta stato attuale
	  - azienda{idAzienda}/serra{idSerra}/attuatori/illuminazione/rx 
	    - 3/5/attuatori/illuminazione/rx

**ANSWERS**:
	
  	- azienda{idAzienda}/serra{idSerra}/sensori/temperatura/sx
	  - *example* 3/5/sensori/temperatura/sx
	
        - azienda{idAzienda}/serra{idSerra}/attuatori/illuminazione/sx
	  - *example* 3/5/attuatori/illuminazione/sx


**MESSAGE FORMAT:**
	 
	 - Messaggio su TOPIC Sensori di richiesta temperatura
               		{
                		type:"read",
                		device:"sensori"
               		}

	- Messaggio su TOPIC Attuatori di richiesta cambio stato
               		{
             		   type:"write",
		               device:"attuatori",
		               state:"on/off",
		               level:"low/medium/high"
 		               mode:"auto/manual"
     		            }
	- Messaggio su TOPIC Attuatori di richiesta stato e livello attuale
   		          {
   		            type:"read",
    		            device:"attuatori"
    		           }

**RESPONSE MESSAGES:**

	- Messaggio di risposta alla richiesta di valore attuale di sensore
    		          {
  		            type:"read",
     		     	      device:"sensori",
 		            value:float                      //Aggiunta campo di valore prelevato dal sensore
		          }

 	- Messaggio di risposta alla richiesta di cambio stato Attuatore
        		      {
         		      type:"write",
         		      device:"attuatori",
          		      state:"on/off",
           		      level:"low/medium/high",
          		      mode:"auto/manual",
          		      result:boolean                         //Aggiunta campo di successo(true = buon fine)
         		      }






### Structural Analysis
	
La GatewayApp si occuperà di ricevere messaggi MQTT su Topic e inoltarli tramite TCP socket ai Device. L'arrivo di un messaggio comporterà la costituzione di un oggetto "response" che verrà inviato al backend.

Sebbene in fase di specifica abbiamo adottato la convenzione che in un campo sia presente 3 sole coppie di device (sensore+attuatore), una per ogni deviceType (Temperatura/Umidità/Illuminazione), il Gateway si presta comunque a eventuale espansione progettuale facendo utilizzo delle hashmaps come struttura dinamica per poter inizializzare le socket TCP per il collegamento con i diversi devices.

Nella GatwayApp invocherò il metodo ".subscribe" a cui passerò come parametro l'oggettino "listener" creato sulla base della classe GWBEListener, in cui è anche presente anche la logica del processing dei messaggi MQTT: non appena un messaggio verrà ricevuto, il metodo ".messageArrived" di GWBEListener verrà invocato in modo da effettuare la split dei diversi campi del topic e l'unmarshalling del json per interpretarlo e permettere al Gateway di estrapolare quali saranno le informazioni da mandare e dove mandarle.


Il BackendMessageSender si occuperà di simulare le richieste che partono dal backend, stampando anche su terminale i messaggi MQTT inviati. Esso effettuerà la .disconnect() e la .close() una volta inviati tutti i messaggi previsti. 


il BackendMessageReceiver simula il reciever sul lato backend e stamperà su teminale i messaggi ricevuti. alla sua .subscribe() passerò sia il topic adeguato, sia il DummyListener.java: una interfaccia sulla base della classe IMqttMessageListener che effettua anche la print dei messaggi arrivati.


la SimulatorGUIApp, la cui logica è contenuta in SimulatorGUI, consiste nell'interfaccia grafica che ci permette di controllare le misure dei sensori rilevate periodicamente (Inizializzati a 0; ogni 30 secondi invoco il mio metodo .getRandomByRange() contenuto nella classe RandomUtils nel package "test.java.org.lore.utils"), e le variabili relative allo Stato, Livello e Modalità (descritte con varie enum nel package "main.java.org.lore.models.mqtt").
	 
La GUI effettua una refresh periodica per mostrare la variazione dei valori degli attuatori in seguito ai messaggi MQTT di tipo write. Il metodo .refreshGUI() viene invocato ogni 2 secondi.

La SimulatorGUI inoltre contiene diversi (nel nostro caso 6, uno per ogni tipo di device) Thread dedicati ai Server TCP per ogni rispettivo device. Stampa anche a terminale ogni volta che una connessione TCP viene stabilita con successo e su quale porta.

La logica dei device è contenuta nel package "test.java.org.lore.tcp" nelle 3 classi TempTCPServer,UmiTCPServer e IllTCPServer: in esse invoco la .getRandomByRange() secondo i valori di ognuna unità di misura. Ognuna di queste classi avrà il metodo .compute() che, in base al tipo (READ/WRITE) di messaggio MQTT inviato entrerà nella porzione di codice adeguata della switch.
