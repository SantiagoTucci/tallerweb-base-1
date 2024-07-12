INSERT INTO reto (nombre, descripcion, imagen_url)
SELECT 'Cardio de Alta Intensidad', 'Realiza 5 minutos de jumping jacks, 5 minutos de burpees, 5 minutos de high knees, 5 minutos de escaladores, 5 minutos de saltos laterales y 5 minutos de descanso activo.', 'img/reto/burpees.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM reto WHERE nombre = 'Cardio de Alta Intensidad');

INSERT INTO reto (nombre, descripcion, imagen_url)
SELECT 'Cardio Intermedio', 'Realiza 5 minutos de trote en el lugar, 5 minutos de jumping jacks, 5 minutos de rodillas al pecho, 5 minutos de escaladores y 10 minutos de caminata rápida en el lugar.', 'img/reto/correrIntermedio.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM reto WHERE nombre = 'Cardio Intermedio');

INSERT INTO reto (nombre, descripcion, imagen_url)
SELECT 'Circuito Aeróbico', 'Alterna 1 minuto de burpees, 1 minuto de jumping jacks, 1 minuto de high knees, 1 minuto de escaladores y 1 minuto de descanso activo, repitiendo 6 veces.', 'img/reto/burpees.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM reto WHERE nombre = 'Circuito Aeróbico');

INSERT INTO reto (nombre, descripcion, imagen_url)
SELECT 'Cardio Matutino', 'Haz 5 minutos de jumping jacks, 5 minutos de high knees, 5 minutos de escaladores, 5 minutos de saltos de tijera y 10 minutos de trote en el lugar.', 'img/reto/burpees.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM reto WHERE nombre = 'Cardio Matutino');

INSERT INTO reto (nombre, descripcion, imagen_url)
SELECT 'Cardio de Trote y Saltos', 'Realiza 10 minutos de trote en el lugar, 5 minutos de jumping jacks, 5 minutos de high knees y 10 minutos de caminata rápida en el lugar.', 'img/reto/correrBajo.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM reto WHERE nombre = 'Cardio de Trote y Saltos');

INSERT INTO reto (nombre, descripcion, imagen_url)
SELECT 'Entrenamiento de Escaladores', 'Alterna 2 minutos de escaladores y 2 minutos de descanso activo, repitiendo 7 veces.', 'img/reto/correrRapido.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM reto WHERE nombre = 'Entrenamiento de Escaladores');

INSERT INTO reto (nombre, descripcion, imagen_url)
SELECT 'Cardio con Rodillas al Pecho', 'Haz 5 minutos de trote en el lugar, 5 minutos de rodillas al pecho, 5 minutos de jumping jacks, 5 minutos de high knees y 10 minutos de caminata rápida en el lugar.', 'img/reto/rodillasAlPecho.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM reto WHERE nombre = 'Cardio con Rodillas al Pecho');

INSERT INTO reto (nombre, descripcion, imagen_url)
SELECT 'Entrenamiento de Intervalos Aeróbicos', 'Alterna 1 minuto de burpees, 1 minuto de high knees, 1 minuto de escaladores y 1 minuto de descanso activo, repitiendo 7 veces.', 'img/reto/burpees.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM reto WHERE nombre = 'Entrenamiento de Intervalos Aeróbicos');

INSERT INTO reto (nombre, descripcion, imagen_url)
SELECT 'Cardio de Alta Energía', 'Realiza 5 minutos de trote en el lugar, 5 minutos de jumping jacks, 5 minutos de rodillas al pecho, 5 minutos de escaladores y 10 minutos de trote en el lugar.', 'img/reto/correrRapido.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM reto WHERE nombre = 'Cardio de Alta Energía');

INSERT INTO reto (nombre, descripcion, imagen_url)
SELECT 'Cardio de Intensidad Moderada', 'Haz 10 minutos de trote en el lugar, 5 minutos de jumping jacks, 5 minutos de high knees y 10 minutos de caminata rápida en el lugar.', 'img/reto/correrBajo.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM reto WHERE nombre = 'Cardio de Intensidad Moderada');

INSERT INTO reto (nombre, descripcion, imagen_url)
SELECT 'Entrenamiento de Burpees', 'Alterna 2 minutos de burpees y 2 minutos de descanso activo, repitiendo 7 veces.', 'img/reto/burpees.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM reto WHERE nombre = 'Entrenamiento de Burpees');

INSERT INTO reto (nombre, descripcion, imagen_url)
SELECT 'Cardio Suave', 'Realiza 30 minutos de caminata rápida en el lugar.', 'img/reto/correrBajo.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM reto WHERE nombre = 'Cardio Suave');

INSERT INTO reto (nombre, descripcion, imagen_url)
SELECT 'Cardio de Intensidad Variable', 'Alterna 5 minutos de trote en el lugar y 5 minutos de high knees, repitiendo 3 veces.', 'img/reto/correrBajo.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM reto WHERE nombre = 'Cardio de Intensidad Variable');

INSERT INTO reto (nombre, descripcion, imagen_url)
SELECT 'Cardio para Principiantes', 'Haz 10 minutos de trote en el lugar, 5 minutos de jumping jacks, 5 minutos de high knees y 10 minutos de trote en el lugar.', 'img/reto/correrBajo.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM reto WHERE nombre = 'Cardio para Principiantes');

INSERT INTO reto (nombre, descripcion, imagen_url)
SELECT 'Entrenamiento de Saltos', 'Alterna 2 minutos de jumping jacks y 2 minutos de descanso activo, repitiendo 7 veces.', 'img/reto/burpees.jpg'
    WHERE NOT EXISTS (SELECT 1 FROM reto WHERE nombre = 'Entrenamiento de Saltos');
