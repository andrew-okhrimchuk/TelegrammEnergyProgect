package org.telegram.data;

public class BotMessageText {
    public final static String NEWLINE = "\n";
    public final static String FIRST_START_TEXT = "Привіт! \n Для початку вводу особового рахунку натисніть Старт або оберіть необхідний розділ";
    public final static String START_TEXT = "Для передачі показів електролічильника введіть, будь ласка, номер Вашого Особового рахунку. (Номер Особового рахунку складається з 9-ти цифр, перша з яких двійка: 2ХХХХХХХХ, або четвірка: 4ХХХХХХХХ)";
    public final static String START_TEXT_LEVEL_1_1 = "Ваш номер OP ";
    public final static String START_TEXT_LEVEL_1_2 = ". Вкажіть тип лічильника (Внизу екрана: /Звичайний, /Двозонний, /Тризонний).\n" + NEWLINE + "Якщо OP потребує змін - Натисніть Змінити ОР";

    public final static String START_TEXT_LEVEL_2_1 = "Ви обрали лічильник "; //Type
    public final static String START_TEXT_LEVEL_2_2  = NEWLINE + "Введіть показ\n" + "(Всі цифри до коми. Максимум сім цифр)";
    public final static String START_TEXT_LEVEL_2_2_1  = NEWLINE + "Введіть покази:\n НІЧ пробіл ДЕНЬ \n" + "(Всі цифри до коми. Максимум сім цифр)";
    public final static String START_TEXT_LEVEL_2_3  = NEWLINE + "Введіть покази:\n ПІК пробіл НАПІВПІК пробіл НІЧ \n" + "(Всі цифри до коми. Максимум сім цифр)";

    public final static String START_TEXT_LEVEL_3_1 = "Ваші покази на "; //data
    public final static String START_TEXT_LEVEL_3_3 = NEWLINE + "Номер рахунку: "; //OP
    public final static String START_TEXT_LEVEL_3_4 = NEWLINE + "Тип лічильника: "; //Type
    public final static String START_TEXT_LEVEL_3_5 = NEWLINE + "Покази лічильника: " ; //int
    public final static String START_TEXT_LEVEL_3_6 = NEWLINE + NEWLINE + "Якщо правильно, то натисніть кнопку \"Відправити\"";

    public final static String SENTED = "Ваші покази прийнято до опрацювання. Пам’ятайте: покази мають бути фактичними на дату передачі та будуть перевірятись на достовірність перед завантаженням до системи розрахунків Товариства." +
            NEWLINE + NEWLINE + "Бот надішле результат перевірки через деякий час." + NEWLINE + NEWLINE +
            "Дякуємо за співпрацю!" + NEWLINE + "Якщо ви бажаєте передати покази по іншому Особовому рахунку - натисніть кнопку /Старт";


    public final static String HELP_TEXT = "Чат-бот від Київобленерго працює для передачі показів електролічильників споживачами. Дана функція працює у будь-який момент користування чат-ботом.\n" +
            "Для повідомлення боту показів вашого лічильника, потрібно зробити три кроки\n" +
            "1. Натиснути кнопку \"Старт\" та ввести ваш номер особового рахунку (складається з 9-ти цифр, перша з яких двійка: 2ХХХХХХХХ, або четвірка: 4ХХХХХХХХ).\n" +
            "2. Обрати тип лічильника: Звичайний, Двозонний (день/ніч) або Тризонний ( пік, напівпік, ніч)\n" +
            "3. Повідомити покази лічильника (На дисплеї лічильника всі цифри до коми. Максимум сім цифр).\n" +
            NEWLINE +
            "Покази будуть прийняті на дату повідомлення. Проте покази 1-го числа будуть прийняті до розрахунку останньою датою попереднього розрахункового періоду.\n" +
            "Пам’ятайте: покази мають бути фактичними на дату передачі та будуть перевірятись на достовірність перед завантаженням до системи розрахунків Товариства.\n" +
            "Бот надішле результат перевірки через деякий час.\n" +
            NEWLINE +
            "Якщо у вас виникли інші запитання щодо роботи ПрАТ «Київобленерго», будь ласка, зверніться до нашого Колл-центру: 044 459 07 40, або на електронну пошту: info.koe@koe.vsei.ua\n" +
            NEWLINE +
            "Більше інформації щодо приєднання електроустановок до мереж, тарифів за електроенергію на нашому сайті: http://www.koe.vsei.ua/koe/index.php";

    public final static String ACC_INPUT_ERROR_MASSGE_1_1 = "Невірний формат.\n \n (Номер Особового рахунку складається з 9-ти цифр, перша з яких двійка: 2ХХХХХХХХ, або четвірка: 4ХХХХХХХХ).\n" +
            "\n" + "Якщо Ви хочете почати спочатку, то натисніть  /Старт або /Допомога для отримання допомоги.";
    public final static String TYPE_INPUT_ERROR_MASSGE_3_1 = "Невірний формат. Введіть покази відповідно лічильника, який Ви обрали: ";
    public final static String TYPE_INPUT_ERROR_MASSGE_3_2_1 = "Звичайний (Всі цифри. Максимум сім цифр) або змініть тип лічильника \n \n Якщо ви хочете почати спочатку то натисніть кнопку /Старт або натисніть кнопку /Допомога для отримання допомоги.";
    public final static String TYPE_INPUT_ERROR_MASSGE_3_2_2 = "Двозонний - НІЧ пробіл ДЕНЬ (Всі цифри. Максимум сім цифр) або змініть тип лічильника \n \n Якщо ви хочете почати спочатку то натисніть кнопку /Старт або натисніть кнопку /Допомога для отримання допомоги.";
    public final static String TYPE_INPUT_ERROR_MASSGE_3_2_3 = "Тризонний - ПІК пробіл НАПІВПІК пробіл НІЧ (Всі цифри. Максимум сім цифр) або змініть тип лічильника \n \n Якщо ви хочете почати спочатку то натисніть кнопку /Старт або натисніть кнопку /Допомога для отримання допомоги.";

    public static final String NAME_ZONE_1 = "Звичайний";
    public static final String NAME_ZONE_2 = "Двозонний";
    public static final String NAME_ZONE_3 = "Тризонний";
}
