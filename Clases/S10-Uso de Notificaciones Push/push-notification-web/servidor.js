// servidor.js - Un pequeño servidor Node.js para manejar las solicitudes de FCM
const express = require('express');
const cors = require('cors');
const admin = require('firebase-admin');
const bodyParser = require('body-parser');

// Inicializa la app de Express
const app = express();
app.use(cors());
app.use(bodyParser.json());

// Inicializa Firebase Admin SDK
// Debes descargar tu archivo de credenciales desde la consola de Firebase
// Project Settings > Service accounts > Generate new private key
const serviceAccount = require('./notificacionesremotas-9340f-firebase-adminsdk-fbsvc-eb188ecab4.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

// Ruta para servir la interfaz web
app.use(express.static('public'));

// Endpoint para enviar notificaciones
app.post('/send-notification', async (req, res) => {
  try {
    const { subject, message } = req.body;
    
    // Validar que se proporcionaron el asunto y el mensaje
    if (!subject || !message) {
      return res.status(400).json({ error: 'El asunto y el mensaje son obligatorios' });
    }
    
    // Construir el mensaje para FCM
    const fcmMessage = {
      notification: {
        title: subject,
        body: message
      },
      topic: 'all' // Enviar a todos los dispositivos suscritos al tema 'all'
    };
    
    // Enviar el mensaje
    const response = await admin.messaging().send(fcmMessage);
    
    console.log('Notificación enviada con éxito:', response);
    res.status(200).json({ success: true, messageId: response });
  } catch (error) {
    console.error('Error al enviar la notificación:', error);
    res.status(500).json({ error: error.message });
  }
});

// Iniciar el servidor
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Servidor corriendo en el puerto ${PORT}`);
});
