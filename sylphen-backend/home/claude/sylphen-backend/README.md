# Sylphen Backend — Guía de instalación en Windows

## Requisitos previos

### 1. Verificar si Java está instalado
Abre PowerShell o CMD y escribe:
```
java -version
```
Si aparece `java version "21"` o superior, Java ya está instalado. Continúa al paso 3.

Si no, instala Java 21 (paso 2).

---

### 2. Instalar Java 21 (si no está instalado)

**Opción A — winget (más fácil):**
Abre PowerShell como administrador y escribe:
```powershell
winget install EclipseAdoptium.Temurin.21.JDK
```

**Opción B — descarga manual:**
1. Ve a https://adoptium.net/
2. Descarga **Temurin 21 (LTS)** para Windows x64
3. Ejecuta el instalador .msi
4. Marca la opción "Set JAVA_HOME variable" durante la instalación

Verifica con:
```
java -version
```

---

### 3. Opción A — Ejecutar con Maven Wrapper (SIN instalar Maven)

El proyecto incluye `mvnw.cmd` que descarga Maven automáticamente.

Abre una terminal en la carpeta del proyecto y ejecuta:
```cmd
mvnw.cmd spring-boot:run
```

Primera ejecución: descarga Maven (~10MB). Las siguientes son instantáneas.

---

### 4. Opción B — Instalar Maven globalmente

**Con winget:**
```powershell
winget install Apache.Maven
```
Cierra y vuelve a abrir la terminal. Luego:
```
mvn spring-boot:run
```

**Manual:**
1. Descarga de https://maven.apache.org/download.cgi
2. Extrae en `C:\maven`
3. Agrega `C:\maven\bin` a la variable de entorno PATH
4. Verifica con: `mvn -version`

---

### 5. Instalar extensiones en VS Code

Instala estos dos paquetes desde el Marketplace de VS Code:
- **Extension Pack for Java** (Microsoft)
- **Spring Boot Extension Pack** (VMware)

Con estas extensiones puedes ejecutar la app haciendo clic en
el botón ▶ **Run** que aparece encima de la clase `SylphenApplication`.

---

## Ejecutar el backend

```cmd
mvnw.cmd spring-boot:run
```
o si Maven está instalado globalmente:
```cmd
mvn spring-boot:run
```

El servidor arranca en **http://localhost:8080**

---

## Endpoints disponibles (datos desde SQL)

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | /api/estado | Estado del sistema (público, sin auth) |
| GET | /api/lectura/actual | Última lectura del INA226 |
| GET | /api/lecturas | Historial de lecturas |
| GET | /api/dispositivos | Estado de los 4 componentes |
| POST | /api/auth/login | Iniciar sesión |
| POST | /api/auth/registro | Registrar usuario |

Consola H2 (base de datos en memoria):
**http://localhost:8080/h2-console**
- JDBC URL: `jdbc:h2:mem:sylphendb`
- Usuario: `sa`
- Contraseña: (vacía)

---

## Conectar el frontend

En `app.js` del frontend, cambia esta línea:
```javascript
USE_API: false,  // ← cambiar a true
```
Y actualiza la URL:
```javascript
API_URL: 'http://localhost:8080/api/lectura/actual',
```

Si el frontend corre desde el celular en la misma red WiFi,
usa la IP de tu PC en lugar de localhost:
```javascript
API_URL: 'http://192.168.X.X:8080/api/lectura/actual',
```

---

## Estructura del proyecto

```
sylphen-backend/
├── pom.xml                          ← dependencias Maven
├── mvnw.cmd                         ← ejecutar en Windows sin Maven instalado
├── README.md                        ← esta guía
└── src/main/java/com/sylphen/
    ├── SylphenApplication.java      ← punto de entrada
    ├── config/
    │   └── CorsConfig.java          ← permite peticiones del frontend
    ├── controller/                  ← endpoints REST
    │   ├── AuthController.java
    │   ├── EstadoController.java
    │   ├── LecturaController.java
    │   └── DispositivoController.java
    ├── dto/                         ← objetos de respuesta JSON
    ├── entity/                      ← tablas de la base de datos
    ├── repository/                  ← acceso a datos (JPA)
    └── service/                     ← lógica de negocio
```
