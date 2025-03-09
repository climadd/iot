<sup>Lorenzo Mirto Bertoldo @climadd</sup>

# IoT Gateway

## Quickstart Guide

The gateway process involves four main classes that must be executed in the following order:

1. **BackendMessageReceiver.java** – `test.java.org.lore.mqtt.backend`
2. **GatewayApp.java** – `main.java.org.lore.app`
3. **SimulatorGUIApp.java** – `test.java.org.lore.mqtt.backend.simulators`
4. **BackendMessageSender.java** – `test.java.org.lore.mqtt.backend`

---

## Project Overview

<img src="/progetto_reti2/src/readme/hexagonal.png" style="display: inline-block; margin: 0 auto; max-width: 300px">

- The **Gateway** acts as a bridge between the *Broker* and the *IoT Devices (Sensors and Actuators)*. It communicates with the *Backend* via the [MQTT protocol](https://mqtt.org/getting-started/) and uses dynamically allocated TCP sockets to establish communication with the *IoT Devices*.

- The **Simulated Sensors** generate realistic fluctuations in their measured values. As displayed in the *GUI*, sensors independently produce a stream of data, which is sent via *TCP Socket* to the *Gateway*. The gateway encapsulates this data into MQTT-formatted *Queries* for the *Backend*. This data retrieval process can either be scheduled by a backend script or triggered manually by user requests.

- The **Simulated Actuators** mimic real IoT actuators that toggle between different modes. They store multiple *boolean* or *enumeration* values, which can be modified by gateway-issued queries through their dedicated TCP socket. The GUI displays the real-time status of all actuator variables.

- The **Simulated Backend** consists of two classes:  
  - `BackendMessageSender` – Handles sending messages through the *MQTT Broker*.  
  - `BackendMessageReceiver` – Receives and prints incoming messages in the terminal.

- The **Messages** follow the MQTT format, and all enumeration-type data related to both messages and measurements is cataloged in the `main.java.org.lore.models.mqtt` package.

---

## MQTT Message Topics and Format

### **Query Topics**  
- **Request for sensor temperature data**  
  - Topic: `azienda{idAzienda}/serra{idSerra}/sensori/temperatura/rx`  
  - *Example:* `3/5/sensori/temperatura/rx`

- **Request for actuator state or status change**  
  - Topic: `azienda{idAzienda}/serra{idSerra}/attuatori/illuminazione/rx`  
  - *Example:* `3/5/attuatori/illuminazione/rx`

### **Response Topics**  
- **Sensor temperature response**  
  - Topic: `azienda{idAzienda}/serra{idSerra}/sensori/temperatura/sx`  
  - *Example:* `3/5/sensori/temperatura/sx`

- **Actuator state response**  
  - Topic: `azienda{idAzienda}/serra{idSerra}/attuatori/illuminazione/sx`  
  - *Example:* `3/5/attuatori/illuminazione/sx`

### **Message Format**
#### **Sensor Request Message**
```json
{
  "type": "read",
  "device": "sensori"
}
```

##### **Actuator State Change Request Message**
```json
{
  "type": "write",
  "device": "attuatori",
  "state": "on/off",
  "level": "low/medium/high",
  "mode": "auto/manual"
}
```
##### **Actuator Status Request Message**
```json
{
  "type": "read",
  "device": "attuatori"
}
```
### **RESPONSE MESSAGES:**
#### **Sensor Data Response**
```json
{
  "type": "read",
  "device": "sensori",
  "value": float		//Exclusive to sensor device
}
```
 #### **Actuator State Change Response**
```json
{
  "type": "write",
  "device": "attuatori",
  "state": "on/off",
  "level": "low/medium/high",
  "mode": "auto/manual",
  "result": boolean		//Exclusive to sensor (true = operation successful)
}
```

### Structural Analysis  

#### **GatewayApp**  
- **Function**: Receives MQTT messages and forwards them via TCP sockets to the appropriate IoT devices.  
- **Response Handling**: Creates a `response` object upon receiving a message and sends it to the backend.  

#### **Scalability**  
- **Initial Specification**: Assumes a fixed number of sensor-actuator pairs (Temperature, Humidity, Lighting).  
- **Future Expansion**: Uses hashmaps for dynamic TCP socket initialization, allowing support for additional devices.  

#### **MQTT Processing**  
- **Listener Class**: `GWBEListener`  
- **Subscription**: Registers a listener using the `.subscribe()` method.  
- **Message Handling**:  
  - **Trigger**: The `.messageArrived()` method is invoked upon message reception.  
  - **Processing**: Extracts topic fields and parses the JSON payload to determine the necessary actions.  

#### **BackendMessageSender**  
- **Purpose**: Simulates backend requests.  
- **Logging**: Prints all sent messages to the terminal.  
- **Connection Management**: Performs `.disconnect()` and `.close()` once all messages are sent.  

#### **BackendMessageReceiver**  
- **Purpose**: Simulates the backend receiving MQTT messages.  
- **Logging**: Prints all received messages to the terminal.  
- **Subscription**: Uses `.subscribe()` to listen to the appropriate topic, utilizing `DummyListener.java`, an interface extending `IMqttMessageListener`.  

#### **SimulatorGUIApp**  
- **Function**: Provides a graphical interface to monitor sensor measurements and actuator states.  
- **Sensor Values**:  
  - Initialized to `0`  
  - Updated every **30 seconds** using `.getRandomByRange()` from `RandomUtils`  
- **Actuator States**: Variables like State, Level, and Mode are defined as enums in `main.java.org.lore.models.mqtt`.  
- **GUI Refresh**:  
  - Refreshes every **2 seconds** via `.refreshGUI()`  
  - Displays actuator state updates in response to MQTT "write" messages.  

#### **TCP Servers for Simulated Devices**  
- **Thread Management**: GUI contains **6 threads** (one per device type), each running a dedicated TCP server.  
- **Logging**: Prints messages to the terminal when a TCP connection is successfully established.  
- **Device Logic**:  
  - Implemented in `test.java.org.lore.tcp` within the classes `TempTCPServer`, `UmiTCPServer`, and `IllTCPServer`.  
  - Each class invokes `.getRandomByRange()` to generate sensor values.  
  - The `.compute()` method processes incoming MQTT messages, executing the corresponding logic based on message type (`READ` or `WRITE`).  
