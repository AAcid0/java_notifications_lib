# 🚀 NTF Library (Notification Engine)

Una librería Java robusta, agnóstica y extensible para el envío unificado de notificaciones (SMS, Email, Push). Diseñada bajo los principios de **Arquitectura Hexagonal (Ports & Adapters)** y **Domain-Driven Design (DDD)**.

## 📋 Tabla de Contenidos
- [Introducción](#-introducción)
- [Decisiones de Arquitectura](#-decisiones-de-arquitectura)
- [Proveedores Soportados & Referencias API](#-proveedores-soportados--referencias-api)
- [Instalación](#-instalación)
- [Configuración y Seguridad](#-configuración-y-seguridad)
- [Quick Start](#-quick-start)
- [API Reference](#-api-reference)

---

## 📖 Introducción

`ntf_library` resuelve el problema del acoplamiento fuerte entre la lógica de negocio y los proveedores de infraestructura. Permite a las aplicaciones enviar mensajes sin conocer los detalles de implementación de servicios externos como Twilio o AWS.

**Características Principales:**
* **Agnóstica del Proveedor:** Cambia de Twilio a Infobip sin tocar una sola línea de tu lógica de negocio.
* **Tipado Fuerte:** Modelos de dominio ricos (`SmsContent`, `EmailContent`) con validaciones integradas.
* **Simulación de Protocolo:** Los adaptadores actuales simulan el envío construyendo los *payloads* reales requeridos por las APIs oficiales, ideal para entornos de desarrollo y testing.

---

## 🏗 Decisiones de Arquitectura

El proyecto sigue estrictamente una **Arquitectura Hexagonal**:

1.  **Dominio (Núcleo):**
    * Contiene la lógica pura (`Notification`, `Content`, Excepciones).
    * **No tiene dependencias externas**. No sabe que existe HTTP, JSON o SQL.
    * Define "Puertos" (Interfaces como `NotificationProvider`) que el mundo exterior debe cumplir.

2.  **Infraestructura (Adaptadores):**
    * Implementa los puertos del dominio.
    * Aquí residen las implementaciones concretas (`TwilioSmsAdapter`, `SendGridEmailAdapter`).
    * Maneja la "suciedad" de la integración: Auth tokens, JSON mapping, HTTP calls.

3.  **Por qué este enfoque:**
    * **Testabilidad:** Podemos probar el núcleo de negocio usando Mocks sin necesidad de conexión a internet o cuentas reales.
    * **Mantenibilidad:** Si la API de Firebase cambia mañana, solo se modifica el `FcmAdapter`. El resto del sistema permanece intacto.
    * **Seguridad:** Las credenciales y configuraciones viven en la capa de infraestructura, lejos de la lógica de negocio.

---

## 🔌 Proveedores Soportados & Referencias API

Esta librería implementa adaptadores que cumplen con las especificaciones técnicas oficiales de los siguientes proveedores:

| Canal | Proveedor | Versión de API Implementada | Referencia Técnica |
| :--- | :--- | :--- | :--- |
| **SMS** | **Twilio** | `2010-04-01` | Se utiliza el formato `x-www-form-urlencoded` oficial para el recurso [Message Resource](https://www.twilio.com/docs/sms/api/message-resource). |
| **Email** | **SendGrid** | `v3` (Mail Send) | Implementación completa del payload JSON para el endpoint `/v3/mail/send` [SendGrid API Docs](https://docs.sendgrid.com/api-reference/mail-send/mail-send). |
| **Push** | **Firebase (FCM)** | `v1` (HTTP API) | Soporte para la estructura anidada `message` y autenticación OAuth 2.0 del endpoint `projects.messages.send` [FCM v1 Reference](https://firebase.google.com/docs/reference/fcm/rest/v1/projects.messages/send). |

---

## 📦 Instalación

Para instalar la librería en tu repositorio local:

```bash
# Clonar el repositorio
git clone https://github.com/AAcid0/java_notifications_lib.git

# Compilar e instalar (Maven)
mvn clean install
```

---

## 🔐 Configuración y Seguridad

Respetando los requerimientos de la prueba, esta librería **NO** utiliza archivos de configuración (`.yaml`, `.properties`) dentro del JAR para manejar secretos.

### Estrategia de "Configuration Objects"
La configuración se realiza 100% mediante código Java, inyectando objetos de configuración inmutables como (`TwilioConfig`, `SendGridConfig`) en los adaptadores.

### Mejores Prácticas (Seguridad)
Se recomienda encarecidamente obtener las credenciales desde **Variables de Entorno** del sistema operativo o contenedores (Docker/K8s).

```java
// Ejemplo seguro: Extracción de secretos del entorno
String accountSid = System.getenv("TWILIO_ACCOUNT_SID");
String authToken = System.getenv("TWILIO_AUTH_TOKEN");

// Inyección segura en el objeto de configuración
TwilioConfig config = TwilioConfig.builder()
    .accountSid(accountSid)
    .authToken(authToken)
    .build();
```

---

## 🚀 Quick Start

Ejemplo completo de cómo inicializar la librería y enviar un SMS.

```java
import com.aacid0.ntf_library.domain.NotificationLibrary;
import com.aacid0.ntf_library.domain.model.Notification;
import com.aacid0.ntf_library.domain.model.content.SmsContent;
import com.aacid0.ntf_library.infrastructure.adapters.sms.TwilioSmsAdapter;
import com.aacid0.ntf_library.infrastructure.configuration.sms.TwilioConfig;

public class App {
    public static void main(String[] args) {
        
        // 1. Configurar el Proveedor (Twilio)
        TwilioConfig twilioConfig = TwilioConfig.builder()
            .accountSid(System.getenv("TWILIO_SID"))
            .authToken(System.getenv("TWILIO_TOKEN"))
            .defaultFrom("+1555000000")
            .build();

        // 2. Inicializar la Librería y registrar el adaptador
        NotificationLibrary library = NotificationLibrary.builder()
            .registerProvider(new TwilioSmsAdapter(twilioConfig))
            .build();

        // 3. Crear el Contenido (SMS)
        SmsContent sms = SmsContent.builder()
            .recipient("+573001234567")
            .body("Hola mundo desde NTF Library!")
            .build();

        // 4. Empaquetar y Enviar
        Notification notification = Notification.builder()
            .content(sms)
            .build();

        library.send(notification);
    }
}
```

--- 

## 📚 API Reference & Architectural Decisions

Esta librería está diseñada modularmente para garantizar la **Inversión de Dependencias** (DIP) y la **Segregación de Interfaces** (ISP). A continuación se detallan los componentes clave y los patrones de diseño aplicados.

### 🧠 Core (Dominio & Aplicación)

#### `NotificationLibrary` (Application Service / Facade)
Es el punto de entrada único a la librería. Actúa como un **Orquestador**.
* **Patrón Strategy:** No conoce la implementación concreta de los proveedores. Utiliza una lista de interfaces `NotificationProvider` inyectadas al inicio.
* **Lógica de Enrutamiento:** Implementa un mecanismo de filtrado (`stream().filter().findFirst()`) para delegar el mensaje al adaptador correcto basándose en el tipo de contenido (`supports()`).
* **Responsabilidad:** Validar la entrada y garantizar que existe un proveedor capaz de procesar la solicitud.

#### `Notification` (Aggregate / Wrapper)
Representa el "Sobre" del mensaje. Diseñado como un objeto inmutable.
* **Identidad:** Genera automáticamente un UUID y Timestamp (`Instant.now()`) para garantizar trazabilidad y auditoría.
* **Composición:** Envuelve el objeto `Content`, separando los metadatos de entrega (ID, prioridad) de la carga útil del mensaje.

### 📦 Domain Models (Value Objects)

Todos los modelos de contenido (`SmsContent`, `EmailContent`, `PushNotificationContent`) son **Value Objects** inmutables implementados con Lombok `@Value` y `@Builder`.

* **Inmutabilidad:** Garantiza `Thread-Safety` y previene efectos secundarios indeseados durante el paso del objeto entre capas.
* **Validación en Construcción:** El patrón **Builder** incluye validaciones defensivas (Fail-fast). No es posible instanciar un objeto `EmailContent` sin un destinatario o asunto válido, garantizando la integridad de los datos desde el origen.
* **Polimorfismo:** Todos implementan la interfaz sellada `Content`, permitiendo que el sistema trate distintos tipos de mensajes de manera uniforme.

### 🔌 Ports & Adapters (Infraestructura)

#### `NotificationProvider` (Output Port)
Define el contrato que la capa de infraestructura debe cumplir.
* **Patrón Adapter:** Permite que clases externas (Twilio, SendGrid) se adapten a la interfaz que el dominio necesita.
* **Método `supports(Notification n)`:** Clave para el principio **Open/Closed**. Permite agregar nuevos proveedores (ej: WhatsApp) sin modificar la lógica central de la librería. Simplemente se agrega una nueva implementación que retorne `true` para su tipo de contenido.

#### `Exception Handling` (Domain Exceptions)
La librería utiliza su propia jerarquía de excepciones (`NotificationLibraryException`, `SmsException`, etc.).
* **Exception Translation:** Los adaptadores capturan errores de bajo nivel (HTTP 500, IOExceptions, JSON Parse Error) y los "traducen" a excepciones de dominio agnósticas. Esto evita que detalles de implementación (como una librería HTTP específica) se filtren a la capa de negocio.

---

*Desarrollado por [Luis Rosero](https://www.linkedin.com/in/luis-ro0/), prueba técnica para SEEK*