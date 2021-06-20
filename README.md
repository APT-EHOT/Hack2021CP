# Тонометр.Онлайн
Продукт для дистанционного мониторинга уровня артериального давления и пульса у больных гипертензией.

Решение состоит из мобильного Android-приложения, web-приложения, computerVision-модуля и backend-сервиса

## Реализованный функционал
### Мобильное приложение
* Возможность ввода данных тонометра по фотографии и ручного ввода
* Добавление к измерениям активности, которая могла на них повлиять
* Возможность создавать и модифицировать шаблоны вышеупомянутых активностей
* Получение сообщений от врача, уведомления-напоминания
* Просмотр своего дневника измерений
* Регистрация смены модели тонометра у пациента

### Web-версия для врача
* Просмотр списка пациентов с краткой информацией по ним
* Полная информация по каждому пациенту с историей замеров
* Визуализация данных в виде графиков
* Возможность отправки сообщений клиенту

## Отличительные особенности
* Машинное зрение для распознавания значений тонометра по фотографии
* Удобный и наглядный web-интерфейс врача
* Расширенная коммуникация врача с пациентом через сообщения и уведомления
* Кастомизируемые шаблоны активностей перед измерением
* Интерфейс, ориентированный на возрастную (60+) аудиторию

## Основной стек
* Mobile - Android SDK, Java
* Web - HTML/CSS, JavaScript
* Backend - Spring, Java, JWT, JPA
* Computer Vision - OpenCV, NumPY, SciPY, Python

## Запуск
Для клонирования используйте команды  
`git clone https://github.com/Shureck/MedMonitor.git`  
И из корня папки с проектами
`git submodule update --init`

## Разработчики
Горшков Евгений

Левандровский Александр

Матчин Артемий

Цуканов Дмитрий
