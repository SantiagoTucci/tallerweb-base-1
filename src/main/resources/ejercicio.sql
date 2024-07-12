INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 1, 'Sentadillas', 'Flexionar cadera y rodillas hasta un ángulo de 90 grados aproximadamente, manteniendo la espalda recta y el core activado. Evitar que las rodillas superen las puntas de los pies.', 'PIERNAS', 'GANANCIA_MUSCULAR', 'COMPUESTO', 3, 10
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 1);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 2, 'Press de Banca', 'Tumbado supino, descender la barra hasta el pecho con control, manteniendo la escápula retraída y el core activo. Extender los brazos completamente en el empuje vertical.', 'PECHO', 'GANANCIA_MUSCULAR', 'COMPUESTO', 3, 8
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 2);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 3, 'Remo con Barra', 'Desde posición sentada en la máquina, realizar un jalón hacia el pecho manteniendo la espalda recta y el core activado. Enfocarse en el trabajo de los músculos de la espalda.', 'ESPALDA', 'GANANCIA_MUSCULAR', 'COMPUESTO', 3, 12
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 3);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 4, 'Burpees', 'Iniciar de pie, flexionar cadera y rodillas hasta posición de sentadilla, apoyar manos en el suelo. Extender piernas hacia atrás y flexionar codos hasta tocar el pecho. Extender piernas y brazos para volver a posición inicial. Saltar lo más alto posible.', 'CUERPO_COMPLETO', 'PERDIDA_DE_PESO', 'CARDIO', 3, 10
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 4);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 5, 'Jumping Jacks', 'Separar y juntar piernas a la vez que se saltan, elevando y bajando los brazos de forma coordinada.', 'CUERPO_COMPLETO', 'PERDIDA_DE_PESO', 'CARDIO', 3, 30
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 5);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 6, 'Peso Muerto', 'Con barra en el suelo, flexionar las caderas y las rodillas para agarrar la barra. Mantener la espalda recta y el core activado mientras se levanta la barra hasta que el cuerpo esté completamente erguido.', 'ESPALDA', 'GANANCIA_MUSCULAR', 'COMPUESTO', 3, 8
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 6);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 7, 'Press Militar', 'Sentado o de pie, con una barra o mancuernas a la altura de los hombros, empujar hacia arriba hasta que los brazos estén completamente extendidos. Mantener el core activado y evitar arquear la espalda.', 'HOMBROS', 'GANANCIA_MUSCULAR', 'COMPUESTO', 3, 10
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 7);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 8, 'Curl de Bíceps', 'Con una mancuerna en cada mano y los brazos extendidos, flexionar los codos para llevar las mancuernas hacia los hombros. Mantener los codos cerca del cuerpo y controlar el movimiento.', 'BICEPS', 'GANANCIA_MUSCULAR', 'AISLADO', 3, 12
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 8);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 9, 'Extensiones de Tríceps', 'De pie o sentado, con una barra o mancuerna por encima de la cabeza, flexionar los codos para bajar el peso detrás de la cabeza y luego extender los codos para volver a la posición inicial.', 'TRICEPS', 'GANANCIA_MUSCULAR', 'AISLADO', 3, 12
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 9);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 10, 'Elevaciones Laterales', 'De pie, con una mancuerna en cada mano, levantar los brazos hacia los lados hasta que estén a la altura de los hombros. Mantener una ligera flexión en los codos y controlar el movimiento.', 'HOMBROS', 'GANANCIA_MUSCULAR', 'AISLADO', 3, 15
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 10);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 11, 'Plancha', 'Con el cuerpo en posición de plancha, apoyar los antebrazos y los pies en el suelo. Mantener el core activado y el cuerpo en línea recta desde la cabeza hasta los pies.', 'CORE', 'DEFINICION', 'ISOMETRICO', 3, 60
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 11);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 12, 'Mountain Climbers', 'Posición de plancha con manos debajo de hombros y pies juntos. Llevar una rodilla hacia el pecho manteniendo el core activado y el cuerpo en línea recta. Alternar piernas rápidamente.', 'PIERNAS', 'PERDIDA_DE_PESO', 'CARDIO', 3, 30
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 12);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 13, 'High Knees', 'Correr en el sitio elevando las rodillas lo más alto posible hacia el pecho, manteniendo un ritmo rápido y el core activado.', 'PIERNAS', 'PERDIDA_DE_PESO', 'CARDIO', 3, 30
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 13);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 14, 'Tijeras', 'De pie, alternar el cruce de las piernas adelante y atrás en un movimiento rápido y controlado, mientras los brazos se mueven en sentido opuesto.', 'PIERNAS', 'PERDIDA_DE_PESO', 'CARDIO', 3, 30
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 14);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 15, 'Saltar a la Cuerda', 'Con una cuerda de saltar, realizar saltos continuos y rápidos, manteniendo el core activado y un ritmo constante.', 'CUERPO_COMPLETO', 'PERDIDA_DE_PESO', 'CARDIO', 3, 60
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 15);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 16, 'Jump Squats', 'Realizar una sentadilla y, al subir, saltar lo más alto posible. Aterrizar suavemente y volver a la posición de sentadilla para repetir.', 'PIERNAS', 'PERDIDA_DE_PESO', 'PLIOMETRICO', 3, 12
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 16);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 17, 'Tabata Burpees', 'Iniciar de pie, realizar un burpee completo con salto y repetición rápida. Alternar 20 segundos de trabajo con 10 segundos de descanso durante 4 minutos.', 'CUERPO_COMPLETO', 'PERDIDA_DE_PESO', 'INTERVALO', 1, 8
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 17);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 18, 'Sprint en Cinta', 'Correr a máxima velocidad en una cinta de correr durante intervalos de 30 segundos, seguidos de 30 segundos de descanso activo (caminata ligera).', 'PIERNAS', 'PERDIDA_DE_PESO', 'INTERVALO', 3, 10
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 18);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 19, 'Crunch Abdominal', 'Tumbado boca arriba con las rodillas flexionadas y las manos detrás de la cabeza, elevar el torso hacia las rodillas, contrayendo los músculos abdominales.', 'ABDOMINALES', 'DEFINICION', 'AISLADO', 3, 20
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 19);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 20, 'Elevaciones de Piernas', 'Tumbado boca arriba con las piernas extendidas, elevar las piernas hasta que estén perpendiculares al suelo y luego bajarlas sin que toquen el suelo, manteniendo el core activado.', 'ABDOMINALES', 'DEFINICION', 'AISLADO', 3, 15
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 20);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 21, 'Pullover con Mancuerna', 'Tumbado en un banco con una mancuerna entre las manos, extender los brazos por encima del pecho y llevar la mancuerna hacia atrás por encima de la cabeza, manteniendo una ligera flexión en los codos.', 'ESPALDA', 'DEFINICION', 'COMPUESTO', 3, 12
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 21);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 22, 'Zancadas con Mancuernas', 'De pie, dar un paso hacia adelante con una pierna, flexionando ambas rodillas hasta que la rodilla trasera casi toque el suelo. Volver a la posición inicial y alternar las piernas.', 'PIERNAS', 'DEFINICION', 'COMPUESTO', 3, 15
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 22);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 23, 'Pájaro con Mancuernas', 'De pie, inclinarse hacia adelante con la espalda recta, levantar los brazos hacia los lados con una ligera flexión en los codos, enfocándose en trabajar los deltoides posteriores.', 'HOMBROS', 'DEFINICION', 'AISLADO', 3, 12
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 23);

INSERT INTO Ejercicio (idEjercicio, nombre, descripcion, grupoMuscularObjetivo, objetivo, tipoEjercicio, series, repeticiones)
SELECT 24, 'Patada de Tríceps', 'De pie, inclinarse hacia adelante con una mancuerna en cada mano y los codos flexionados. Extender los brazos hacia atrás hasta que estén completamente rectos y volver a la posición inicial.', 'TRICEPS', 'DEFINICION', 'AISLADO', 3, 15
    WHERE NOT EXISTS (SELECT 1 FROM Ejercicio WHERE idEjercicio = 24);
