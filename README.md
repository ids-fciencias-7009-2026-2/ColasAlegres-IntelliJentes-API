# ColasAlegres — API Backend

API REST desarrollada con **Kotlin + Spring Boot** para la gestión de usuarios de la plataforma ColasAlegres.

---

## Tecnologías

* Kotlin
* Spring Boot
* Spring Data JPA
* MySQL
* Maven

---

## Estructura del repositorio

```
/src                    # Código fuente de la aplicación
/postman                # Colección de Postman para probar los endpoints
/database
    schema.sql          # Script principal y completo de la base de datos
README.md
```

---

## Configuración de la base de datos

### Requisitos previos

* MySQL instalado y corriendo
* JDK 17 o superior
* Maven

---

### Construir la base de datos desde cero

El archivo **`/database/schema.sql`** contiene la creación de la base de datos y todas las tablas necesarias del proyecto.

Ejecuta el siguiente comando en tu terminal desde la raíz del repositorio:

```bash
mysql -u tu_usuario -p < database/schema.sql
```

Por ejemplo:

```bash
mysql -u root -p < database/schema.sql
```

Después de ingresar tu contraseña, la base de datos `colas_alegres` se creará automáticamente junto con sus tablas.

---

## Variables de entorno

La aplicación lee las credenciales de la base de datos desde variables de entorno. Debes definirlas antes de correr el proyecto:

| Variable      | Descripción                               | Ejemplo                        |
| ------------- | ----------------------------------------- | ------------------------------ |
| `URL_DB`      | Host, puerto y nombre de la base de datos | `localhost:3306/colas_alegres` |
| `USER_DB`     | Usuario de MySQL                          | `root`                         |
| `PASSWORD_DB` | Contraseña de MySQL                       | `mi_password`                  |

Las credenciales de la base de datos se gestionan mediante un archivo `.env`.

Este archivo debe crearse manualmente en la raíz del proyecto y **no debe subirse al repositorio**, ya que contiene información sensible.

Ejemplo de archivo `.env`:

```
URL_DB=localhost:3306/colas_alegres
USER_DB=tu_usuario
PASSWORD_DB=tu_password
```

Asegúrate de que el archivo `.env` esté incluido en el `.gitignore`.


