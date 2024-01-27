import asyncio
import smtplib
from email.message import EmailMessage


async def enviar_correo(subject, content, to_address):
    try:
        # Configuración del servidor SMTP
        server = smtplib.SMTP('smtp-mail.outlook.com', 587)
        server.starttls()

        user = 'ejemploxd@hotmail.com' # Si quiere correrlo profe, nomas ponga su correo hotmail y su contraseña xd
        password = 'contrasena' # Y su contraseña jaja

        server.login(user, password)

        # Crear el mensaje de correo
        message = EmailMessage()
        message.set_content(content)
        message['Subject'] = subject
        message['From'] = user
        message['To'] = to_address

        # Enviar el correo
        server.send_message(message)

    except Exception as e:
        print(f"Error al enviar correo: {e}")

    finally:
        # Cerrar la conexión con el servidor SMTP
        server.quit()

async def main():
    # Lista de lass tareas
    tasks = [
        enviar_correo('Asunto 1', 'Contenido 1', 'kevinrodriguezteomitzi2@gmail.com'),
        enviar_correo('Asunto 2', 'Contenido 2', 'kevinrodriguezteomitzi2002@gmail.com'),
        enviar_correo('Asunto 3', 'Contenido 3', 'luckinfort@gmail.com'),
    ]

    # para ejecutarlas asincronicamente
    await asyncio.gather(*tasks)

if __name__ == "__main__":
    asyncio.run(main())
