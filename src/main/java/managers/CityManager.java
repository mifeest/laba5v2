package managers;

import CityData.City;
import CityData.Coordinates;
import CityData.Human;
import CityData.StandardOfLiving;
import Validators.Validators;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import static CityData.utils.GenerateId.generateUniqueId;

/**
 * This class represents a CityManager that is responsible for loading and saving city data from/to an XML file.
 * It provides methods to load the city collection, save the city collection, get the city collection, and clear the city collection.
 * The city data is stored in a LinkedHashMap, where the key is a City object and the value is the city's ID.
 * The XML file format should follow a specific structure, with elements for each city's properties such as name, ID, coordinates, standard of living, population, area, meters above sea level, age, timezone, and creation date.
 * The CityManager class uses the Validators class to validate the values of the city properties before adding them to the city collection.
 */
public class FileManager {
    public static LinkedHashMap<City, Long> cityData = new LinkedHashMap<>();

    public static void loadCollection(String fileName) {
            try {

                File file = new File(fileName);
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(bis);
                document.getDocumentElement().normalize();
                NodeList nodeList = document.getElementsByTagName("city");


                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);

                    Element cityElement = (Element) node;
                    boolean flag = true;

                    String name = cityElement.getElementsByTagName("name").item(0).getTextContent();
                    if (name.equals("")) {
                        System.out.println("В названии города допущена ошибка. Посмотрите загрузочный файл.");
                        flag = false;
                    }
                    String id = cityElement.getElementsByTagName("id").item(0).getTextContent();
                    if (!Validators.isLong(id)) {
                        System.out.println("Неверный id. Значение должно быть больше 0. Посмотрите загрузочный файл.");
                        flag = false;
                    }
                    if (Integer.parseInt(id) <= 0 || id.equals("")) {
                        System.out.println("Неверный id. Значение должно быть больше 0. Посмотрите загрузочный файл. ");
                        flag = false;
                    }
                    String x = cityElement.getElementsByTagName("x").item(0).getTextContent();
                    if (!Validators.isLong(x)) {
                        System.out.println("Неверная координата по x. Посмотрите загрузочный файл. ");
                        flag = false;
                    }
                    String y = cityElement.getElementsByTagName("y").item(0).getTextContent();
                    if (!Validators.isFloat(y)) {
                        System.out.println("Неверная координата по y. Посмотрите загрузочный файл. ");
                        flag = false;
                    }
                    String area = cityElement.getElementsByTagName("area").item(0).getTextContent();
                    if (!Validators.isFloat(area)) {
                        System.out.println("Неверное значение площади");
                        flag = false;
                    }
                    String population = cityElement.getElementsByTagName("population").item(0).getTextContent();
                    if (!Validators.isInt(population)) {
                        System.out.println("Неверное значение населения");
                        flag = false;
                    }
                    String metersAboveSeaLevel = cityElement.getElementsByTagName("metersAboveSeaLevel").item(0).getTextContent();
                    if (!Validators.isInt(metersAboveSeaLevel)) {
                        System.out.println("Неверное значение metersAboveSeaLevel");
                        flag = false;
                    }
                    String timezone = cityElement.getElementsByTagName("timezone").item(0).getTextContent();
                    if (!Validators.isLong(timezone)) {
                        System.out.println("Неверное значение metersAboveSeaLevel");
                        flag = false;
                    }
                    if (Long.parseLong(timezone) < -13 || Long.parseLong(timezone) > 1) {
                        System.out.println("Неверное значение timezone");
                        flag = false;
                    }

                    String standardOfLiving = cityElement.getElementsByTagName("standardOfLiving").item(0).getTextContent();
                    if (!standardOfLiving.equals("ULTRA_HIGH") & !standardOfLiving.equals("HIGH") & !standardOfLiving.equals("MEDIUM") & !standardOfLiving.equals("ULTRA_LOW") & !standardOfLiving.equals("NIGHTMARE")) {
                        System.out.println("Неверный standardOfLiving. Значение должно соответствовать значениям класса enum. Посмотрите загрузочный файл.");
                        flag = false;
                    }
                    String age = cityElement.getElementsByTagName("age").item(0).getTextContent();
                    if (!Validators.isInt(age)) {
                        System.out.println("Неверный age. Значение должно быть больше 0 и не может быть пустым. Посмотрите загрузочный файл.  ");
                        flag = false;
                    } else {
                        if ((Integer.parseInt(age) <= 0) || (age.equals(""))) {
                            System.out.println("Неверный age. Значение должно быть больше 0 и не может быть пустым. Посмотрите загрузочный файл. ");
                            flag = false;
                        }
                    }
                    if (flag) {
                        String creationDateString = cityElement.getElementsByTagName("creationDate").item(0).getTextContent();
                        LocalDateTime creationDateTime = LocalDateTime.parse(creationDateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                        LocalDateTime creationDateStr = LocalDateTime.parse(creationDateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        Human governor = new Human();
                        governor.setAge(Integer.parseInt(age));
                        City city = new City();
                        city.setName(name);
                        city.setId(Long.parseLong(id));
                        Coordinates coordinates = new Coordinates();
                        coordinates.setX(Long.parseLong(x));
                        coordinates.setY(Double.parseDouble(y));
                        city.setCoordinates(coordinates);
                        city.setArea(Float.valueOf(area));
                        city.setPopulation(Integer.valueOf(population));
                        city.setMetersAboveSeaLevel(Integer.valueOf(metersAboveSeaLevel));
                        city.setTimezone(Long.valueOf(timezone));
                        city.setStandardOfLiving(StandardOfLiving.valueOf(standardOfLiving));
                        city.setCreationDate(creationDateStr);
                        city.setGovernor(governor);
                        cityData.put(city, city.getId());
                    }
                }
                bis.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    public void saveCollection() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement("cities");
            doc.appendChild(rootElement);

            for (Map.Entry<City, Long> entry : cityData.entrySet()) {
                City city = entry.getKey();
                Long id = entry.getValue();

                Element cityElement = doc.createElement("city");
                rootElement.appendChild(cityElement);

                Element nameElement = doc.createElement("name");
                nameElement.appendChild(doc.createTextNode(city.getName()));
                cityElement.appendChild(nameElement);

                Element idElement = doc.createElement("id");
                idElement.appendChild(doc.createTextNode(String.valueOf(id)));
                cityElement.appendChild(idElement);

                Element coordinatesElement = doc.createElement("coordinates");
                cityElement.appendChild(coordinatesElement);

                Element xElement = doc.createElement("x");
                xElement.appendChild(doc.createTextNode(String.valueOf(city.getCoordinates().getX())));
                coordinatesElement.appendChild(xElement);

                Element yElement = doc.createElement("y");
                yElement.appendChild(doc.createTextNode(String.valueOf(city.getCoordinates().getY())));
                coordinatesElement.appendChild(yElement);

                Element standardOfLivingElement = doc.createElement("standardOfLiving");
                standardOfLivingElement.appendChild(doc.createTextNode(city.getStandardOfLiving().toString()));
                cityElement.appendChild(standardOfLivingElement);

                Element populationElement = doc.createElement("population");
                populationElement.appendChild(doc.createTextNode(String.valueOf(city.getPopulation())));
                cityElement.appendChild(populationElement);

                Element areaElement = doc.createElement("area");
                areaElement.appendChild(doc.createTextNode(String.valueOf(city.getArea())));
                cityElement.appendChild(areaElement);

                Element metersAboveSeaLevelElement = doc.createElement("metersAboveSeaLevel");
                metersAboveSeaLevelElement.appendChild(doc.createTextNode(String.valueOf(city.getMetersAboveSeaLevel())));
                cityElement.appendChild(metersAboveSeaLevelElement);

                Element ageElement = doc.createElement("age");
                ageElement.appendChild(doc.createTextNode(String.valueOf(city.getGovernor().getAge())));
                cityElement.appendChild(ageElement);

                Element timezoneElement = doc.createElement("timezone");
                timezoneElement.appendChild(doc.createTextNode(String.valueOf(city.getTimezone())));
                cityElement.appendChild(timezoneElement);

                Element creationDateElement = doc.createElement("creationDate");
                creationDateElement.appendChild(doc.createTextNode(String.valueOf(city.getCreationDate())));
                cityElement.appendChild(creationDateElement);
            }

            // Сохраняем измененное DOM дерево обратно в XML файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);

            // Создаем поток вывода для записи в файл
            File file = new File("city_saved.xml");
            OutputStream outputStream = new FileOutputStream(file);
            StreamResult result = new StreamResult(outputStream);

            // Производим трансформацию и записываем результат в файл
            transformer.transform(source, result);

            // Закрываем поток вывода
            outputStream.close();

            System.out.println("XML файл успешно сохранен.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LinkedHashMap<City, Long> getCollection() {
        return cityData;
    }

    public static void clearCollection() {
        cityData.clear();
    }
    public static void addToCollection(City city) {
        cityData.put(city, generateUniqueId());
    }
    private static Scanner scannerForRead;
    public static void setScannerForRead(Scanner scanner) {
        scannerForRead = scanner;
    }
}


