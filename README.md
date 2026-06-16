# Sylphen — Sistema de Monitoreo Energético

> Plataforma web de monitoreo energético para kiosco sustentable.  
> Universidad Tecnológica de Ciudad Juárez · Grupo DSM31 · 2026

---

## ¿Qué es Sylphen?

Sylphen monitorea en tiempo real la energía generada por paneles solares y generadores eólicos, almacenada en una batería 18650. Muestra voltaje, corriente, potencia y nivel de batería a través de un dashboard web conectado a un backend Spring Boot y base de datos MySQL.

---

## Tecnologías

| Capa | Tecnología |
|------|-----------|
| Frontend | HTML5 + CSS3 + JavaScript Vanilla |
| Backend | Spring Boot 3.3.5 · Java 21 · Maven |
| Base de datos | MariaDB / MySQL via XAMPP |
| Hardware | Arduino Uno · ESP32 · Sensor INA226 |

---

## Requisitos previos

Instala estos programas antes de continuar:

### 1. Java 21
Descarga **Temurin 21 LTS** desde:  
https://adoptium.net

Durante la instalación marca ✅ **Set JAVA_HOME** y ✅ **Add to PATH**

Verifica:
```bash
java -version
# openjdk version "21.x.x"
```

### 2. Maven
Descarga el **Binary zip** desde:  
https://maven.apache.org/download.cgi

- Extrae en `C:\maven`
- Agrega `C:\maven\bin` a las variables de entorno (PATH)

Verifica:
```bash
mvn -version
# Apache Maven 3.9.x
```

### 3. XAMPP (MySQL)
Descarga desde:  
https://www.apachefriends.org

Instala y abre XAMPP Control Panel.

### 4. VS Code
Descarga desde:  
https://code.visualstudio.com

Instala la extensión **Live Server** (ritwickdey.LiveServer)

---

## Instalación

### Paso 1 — Clonar el repositorio
```bash
git clone https://github.com/TU_USUARIO/sylphen.git
cd sylphen
```

### Paso 2 — Crear la base de datos

1. Abre XAMPP → click **Start** en MySQL
2. Abre el navegador en: `http://localhost/phpmyadmin`
3. Click en **SQL** en el menú superior
4. Pega el contenido del archivo `sylphen_db.sql`
5. Click en **Continuar**

Esto crea la base de datos `sylphen_db` con las 4 tablas:
- `usuario` — usuarios del sistema
- `historial_login` — registro de sesiones
- `dispositivo` — componentes físicos (Arduino, ESP32, INA226, Batería)
- `lectura_energia` — mediciones del sensor INA226

### Paso 3 — Iniciar el backend

```bash
cd sylphen-backend
mvn spring-boot:run
```

Espera hasta ver:
```
Started SylphenApplication in X.XXX seconds
```

El servidor queda activo en `http://localhost:8080`

### Paso 4 — Abrir el frontend

1. Abre VS Code
2. Abre la carpeta `Sylphen`
3. Clic derecho en `index.html` → **Open with Live Server**
4. El navegador abre automáticamente en `http://localhost:5500`

---

## Verificar que funciona

Con el backend corriendo, abre estas URLs en el navegador:

| Endpoint | Descripción |
|----------|-------------|
| `http://localhost:8080/api/estado` | Estado del sistema |
| `http://localhost:8080/api/dispositivos` | Componentes físicos |
| `http://localhost:8080/api/lectura/actual` | Última lectura energética |
| `http://localhost:8080/api/lecturas` | Historial de lecturas |
| `http://localhost/phpmyadmin` | Consola de base de datos |

---

## Probar sin hardware (inserción manual)

La tabla `lectura_energia` estará vacía hasta que el ESP32 esté programado.  
Para ver datos en el dashboard, inserta un registro manual en phpMyAdmin:

**phpMyAdmin → sylphen_db → lectura_energia → Insertar:**

```
fecha_hora:         2026-06-15 10:00:00
voltaje:            3.89
corriente:          1.24
potencia:           4.83
bateria_porcentaje: 78
```

El dashboard mostrará los datos al instante.

---

## Estructura del proyecto

```
sylphen/
├── Sylphen/                    ← Frontend
│   ├── index.html
│   ├── styles.css
│   └── app.js
├── sylphen-backend/            ← Backend Spring Boot
│   ├── pom.xml
│   └── src/main/java/com/sylphen/
│       ├── controller/         ← Endpoints REST
│       ├── service/            ← Lógica de negocio
│       ├── repository/         ← Acceso a datos
│       ├── entity/             ← Tablas de BD
│       └── dto/                ← Objetos de respuesta
└── sylphen_db.sql              ← Script de base de datos
```

---

## Pantallas

| Pantalla | Descripción |
|----------|-------------|
| P1 Inicio | Estado público del sistema y nivel de batería |
| P2 Auth | Login y registro de usuarios |
| P3 Dashboard | Nivel de batería en tiempo real |
| P4 Generación | Potencia, voltaje y gráfica de producción |
| P5 Historial | Tabla de lecturas históricas |
| P6 Dispositivos | Estado de los componentes físicos |

---

## Hardware

| Componente | Función |
|-----------|---------|
| Arduino Uno | Lee el sensor INA226 vía I2C |
| ESP32 | Envía datos al backend vía WiFi (POST /api/lectura) |
| INA226 | Mide voltaje y corriente total del sistema |
| Batería 18650 | Almacenamiento de energía |
| Panel solar | Fuente de energía 1 |
| Generador eólico | Fuente de energía 2 |

> **Nota:** El sensor INA226 mide la corriente total combinada. No es posible medir por separado la aportación solar y eólica con el hardware actual.

---

## Equipo

| Nombre | Matrícula |
|--------|-----------|
| Bryan Lagos | 25311741 |
| Michelle Ramírez | 25311756 |
| David Ríos | 25311764 |
| Emmanuel Salcido | 25311752 |
| Kevin Vázquez | 25311734 |

**Docente:** Mariano Salomón Cristian Jahaziel  
**Universidad:** UTCJ · Grupo DSM31
