package com.college;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Головний клас програми для роботи з базою даних VideoHosting.
 *
 * <p>Цей клас використовує ORM фреймворк Hibernate для взаємодії з базою даних.
 * Він виконує дві основні операції:
 * <ul>
 *   <li>Перша транзакція: Додавання даних до таблиць Platform, ProducerName, Producer, ViewerName, Viewer, Video та ViewEvent.</li>
 *   <li>Друга транзакція: Виконання запиту для отримання інформації про перегляди відео користувачами.</li>
 * </ul>
 * </p>
 *
 * <p>Клас використовує наступні Entity-класи бази даних:
 * <ul>
 *   <li>{@link Platform}</li>
 *   <li>{@link ProducerName}</li>
 *   <li>{@link Producer}</li>
 *   <li>{@link ViewerName}</li>
 *   <li>{@link Viewer}</li>
 *   <li>{@link Video}</li>
 *   <li>{@link ViewEvent}</li>
 * </ul>
 * </p>
 *
 * <p>Запит у другій транзакції повертає об’єкти {@link ViewInfoDTO}, 
 * які містять інформацію про глядачів, відео, продюсерів, платформи, дати й час перегляду.</p>
 *
 * <p>Для запуску програми необхідно мати налаштований файл конфігурації Hibernate (hibernate.cfg.xml).</p>
 *
 * @see Platform
 * @see ProducerName
 * @see Producer
 * @see ViewerName
 * @see Viewer
 * @see Video
 * @see ViewEvent
 * @see ViewInfoDTO
 */
public class MainApp {
    public static void main(String[] args) {
        // Створення фабрики сесій Hibernate
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Platform.class)
                .addAnnotatedClass(ProducerName.class)
                .addAnnotatedClass(Producer.class)
                .addAnnotatedClass(ViewerName.class)
                .addAnnotatedClass(Viewer.class)
                .addAnnotatedClass(Video.class)
                .addAnnotatedClass(ViewEvent.class)
                .buildSessionFactory();

        // Перша транзакція: Додавання даних
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            // --- Додавання платформ ---
            Platform videoHub = new Platform();
            videoHub.setName("VideoHub");

            Platform filmStream = new Platform();
            filmStream.setName("FilmStream");

            Platform comedyVision = new Platform();
            comedyVision.setName("ComedyVision");

            session.save(videoHub);
            session.save(filmStream);
            session.save(comedyVision);

            // --- Додавання продюсерів ---
            ProducerName prodName1 = new ProducerName();
            prodName1.setFirstName("Ірина");
            prodName1.setLastName("Петренко");
            prodName1.setFatherly("Василівна");

            ProducerName prodName2 = new ProducerName();
            prodName2.setFirstName("Андрій");
            prodName2.setLastName("Іваненко");
            prodName2.setFatherly("Миколайович");

            ProducerName prodName3 = new ProducerName();
            prodName3.setFirstName("Оксана");
            prodName3.setLastName("Мельник");
            prodName3.setFatherly("Петрівна");

            session.save(prodName1);
            session.save(prodName2);
            session.save(prodName3);

            Producer producer1 = new Producer();
            producer1.setProducerName(prodName1);
            producer1.setCountry("Україна");

            Producer producer2 = new Producer();
            producer2.setProducerName(prodName2);
            producer2.setCountry("США");

            Producer producer3 = new Producer();
            producer3.setProducerName(prodName3);
            producer3.setCountry("Велика Британія");

            session.save(producer1);
            session.save(producer2);
            session.save(producer3);

            // --- Додавання відео ---
            Video video1 = new Video();
            video1.setTitle("Весняний ранок");
            video1.setDuration("00:15:45");
            video1.setGenre("Драма");
            video1.setRating(8.7);
            video1.setProducer(producer1);
            video1.setPlatform(videoHub);

            Video video2 = new Video();
            video2.setTitle("Нічне небо");
            video2.setDuration("00:22:30");
            video2.setGenre("Документальний");
            video2.setRating(9.2);
            video2.setProducer(producer2);
            video2.setPlatform(filmStream);

            Video video3 = new Video();
            video3.setTitle("Сміх до сліз");
            video3.setDuration("00:10:20");
            video3.setGenre("Комедія");
            video3.setRating(7.8);
            video3.setProducer(producer3);
            video3.setPlatform(comedyVision);

            Video video4 = new Video();
            video4.setTitle("Осінній дощ");
            video4.setDuration("00:18:15");
            video4.setGenre("Мелодрама");
            video4.setRating(8.3);
            video4.setProducer(producer1);
            video4.setPlatform(videoHub);

            Video video5 = new Video();
            video5.setTitle("Вечірні жарти");
            video5.setDuration("00:12:35");
            video5.setGenre("Комедія");
            video5.setRating(7.5);
            video5.setProducer(producer3);
            video5.setPlatform(comedyVision);

            Video video6 = new Video();
            video6.setTitle("Міські джунглі");
            video6.setDuration("00:25:40");
            video6.setGenre("Документальний");
            video6.setRating(8.9);
            video6.setProducer(producer2);
            video6.setPlatform(filmStream);

            session.save(video1);
            session.save(video2);
            session.save(video3);
            session.save(video4);
            session.save(video5);
            session.save(video6);

            // --- Додавання глядачів ---
            ViewerName viewerName1 = new ViewerName();
            viewerName1.setFirstName("Олег");
            viewerName1.setLastName("Коваленко");
            viewerName1.setFatherly("Іванович");

            ViewerName viewerName2 = new ViewerName();
            viewerName2.setFirstName("Марія");
            viewerName2.setLastName("Сидоренко");
            viewerName2.setFatherly("Олексіївна");

            ViewerName viewerName3 = new ViewerName();
            viewerName3.setFirstName("Дмитро");
            viewerName3.setLastName("Бондаренко");
            viewerName3.setFatherly("Сергійович");

            ViewerName viewerName4 = new ViewerName();
            viewerName4.setFirstName("Юлія");
            viewerName4.setLastName("Ткаченко");
            viewerName4.setFatherly("Ігорівна");

            ViewerName viewerName5 = new ViewerName();
            viewerName5.setFirstName("Сергій");
            viewerName5.setLastName("Кравченко");
            viewerName5.setFatherly("Володимирович");

            ViewerName viewerName6 = new ViewerName();
            viewerName6.setFirstName("Ірина");
            viewerName6.setLastName("Марченко");
            viewerName6.setFatherly("Олегівна");

            ViewerName viewerName7 = new ViewerName();
            viewerName7.setFirstName("Павло");
            viewerName7.setLastName("Шевчук");
            viewerName7.setFatherly("Михайлович");

            ViewerName viewerName8 = new ViewerName();
            viewerName8.setFirstName("Наталія");
            viewerName8.setLastName("Гончарук");
            viewerName8.setFatherly("Вікторівна");

            ViewerName viewerName9 = new ViewerName();
            viewerName9.setFirstName("Артем");
            viewerName9.setLastName("Лисенко");
            viewerName9.setFatherly("Олегович");

            ViewerName viewerName10 = new ViewerName();
            viewerName10.setFirstName("Михайло");
            viewerName10.setLastName("Ковальчук");
            viewerName10.setFatherly("Іванович");

            session.save(viewerName1);
            session.save(viewerName2);
            session.save(viewerName3);
            session.save(viewerName4);
            session.save(viewerName5);
            session.save(viewerName6);
            session.save(viewerName7);
            session.save(viewerName8);
            session.save(viewerName9);
            session.save(viewerName10);

            Viewer viewer1 = new Viewer();
            viewer1.setViewerName(viewerName1);

            Viewer viewer2 = new Viewer();
            viewer2.setViewerName(viewerName2);

            Viewer viewer3 = new Viewer();
            viewer3.setViewerName(viewerName3);

            Viewer viewer4 = new Viewer();
            viewer4.setViewerName(viewerName4);

            Viewer viewer5 = new Viewer();
            viewer5.setViewerName(viewerName5);

            Viewer viewer6 = new Viewer();
            viewer6.setViewerName(viewerName6);

            Viewer viewer7 = new Viewer();
            viewer7.setViewerName(viewerName7);

            Viewer viewer8 = new Viewer();
            viewer8.setViewerName(viewerName8);

            Viewer viewer9 = new Viewer();
            viewer9.setViewerName(viewerName9);

            Viewer viewer10 = new Viewer();
            viewer10.setViewerName(viewerName10);

            session.save(viewer1);
            session.save(viewer2);
            session.save(viewer3);
            session.save(viewer4);
            session.save(viewer5);
            session.save(viewer6);
            session.save(viewer7);
            session.save(viewer8);
            session.save(viewer9);
            session.save(viewer10);

            // --- Додавання подій перегляду ---
            ViewEvent view1 = new ViewEvent();
            view1.setViewer(viewer1);
            view1.setVideo(video1);
            view1.setViewDate(LocalDate.of(2024, 9, 14));
            view1.setViewTime(LocalTime.of(20, 30));

            ViewEvent view2 = new ViewEvent();
            view2.setViewer(viewer2);
            view2.setVideo(video1);
            view2.setViewDate(LocalDate.of(2024, 9, 14));
            view2.setViewTime(LocalTime.of(21, 45));

            ViewEvent view3 = new ViewEvent();
            view3.setViewer(viewer3);
            view3.setVideo(video2);
            view3.setViewDate(LocalDate.of(2024, 9, 14));
            view3.setViewTime(LocalTime.of(22, 20));

            ViewEvent view4 = new ViewEvent();
            view4.setViewer(viewer4);
            view4.setVideo(video2);
            view4.setViewDate(LocalDate.of(2024, 9, 14));
            view4.setViewTime(LocalTime.of(23, 30));

            ViewEvent view5 = new ViewEvent();
            view5.setViewer(viewer5);
            view5.setVideo(video3);
            view5.setViewDate(LocalDate.of(2024, 9, 15));
            view5.setViewTime(LocalTime.of(19, 15));

            ViewEvent view6 = new ViewEvent();
            view6.setViewer(viewer6);
            view6.setVideo(video3);
            view6.setViewDate(LocalDate.of(2024, 9, 15));
            view6.setViewTime(LocalTime.of(21, 00));

            ViewEvent view7 = new ViewEvent();
            view7.setViewer(viewer7);
            view7.setVideo(video4);
            view7.setViewDate(LocalDate.of(2024, 9, 15));
            view7.setViewTime(LocalTime.of(22, 45));

            ViewEvent view8 = new ViewEvent();
            view8.setViewer(viewer8);
            view8.setVideo(video6);
            view8.setViewDate(LocalDate.of(2024, 9, 16));
            view8.setViewTime(LocalTime.of(20, 30));

            ViewEvent view9 = new ViewEvent();
            view9.setViewer(viewer9);
            view9.setVideo(video5);
            view9.setViewDate(LocalDate.of(2024, 9, 16));
            view9.setViewTime(LocalTime.of(22, 15));

            ViewEvent view10 = new ViewEvent();
            view10.setViewer(viewer1);
            view10.setVideo(video4);
            view10.setViewDate(LocalDate.of(2024, 9, 16));
            view10.setViewTime(LocalTime.of(23, 00));

            ViewEvent view11 = new ViewEvent();
            view11.setViewer(viewer10);
            view11.setVideo(video2);
            view11.setViewDate(LocalDate.of(2024, 9, 17));
            view11.setViewTime(LocalTime.of(19, 45));

            ViewEvent view12 = new ViewEvent();
            view12.setViewer(viewer3);
            view12.setVideo(video3);
            view12.setViewDate(LocalDate.of(2024, 9, 17));
            view12.setViewTime(LocalTime.of(21, 30));

            session.save(view1);
            session.save(view2);
            session.save(view3);
            session.save(view4);
            session.save(view5);
            session.save(view6);
            session.save(view7);
            session.save(view8);
            session.save(view9);
            session.save(view10);
            session.save(view11);
            session.save(view12);

            // Підтвердження транзакції
            session.getTransaction().commit();
            System.out.println("✅ Дані успішно збережено у базу!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Друга транзакція: виконання запиту
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            String hql = "SELECT new com.college.ViewInfoDTO(" +
                    "vn.firstName, vn.lastName, " +
                    "pn.firstName, pn.lastName, " +
                    "v.title, v.genre, p.country, " +
                    "v.platform.name, v.rating, " +
                    "ve.viewDate, ve.viewTime) " +
                    "FROM ViewEvent ve " +
                    "JOIN ve.viewer vw " +
                    "JOIN vw.viewerName vn " +
                    "JOIN ve.video v " +
                    "JOIN v.producer p " +
                    "JOIN p.producerName pn";

            List<ViewInfoDTO> results = session.createQuery(hql, ViewInfoDTO.class).getResultList();

            System.out.println("\n📺 Інформація про перегляди відео:");
            System.out.println("--------------------------------------------------");

           // Обробка результатів
for (ViewInfoDTO info : results) {
    System.out.println("Глядач: " + info.getViewerFirstName() + " " + info.getViewerLastName());
    System.out.println("Продюсер: " + info.getProducerFirstName() + " " + info.getProducerLastName());
    System.out.println("Відео: " + info.getVideoTitle());
    System.out.println("Жанр: " + info.getVideoGenre());
    System.out.println("Країна продюсера: " + info.getProducerCountry());
    System.out.println("Платформа: " + info.getPlatformName());
    System.out.println("Рейтинг: " + info.getVideoRating());
    System.out.println("Дата перегляду: " + info.getViewDate());
    System.out.println("Час перегляду: " + info.getViewTime());
    System.out.println("--------------------------------------------------");
}


            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }
    }
}
