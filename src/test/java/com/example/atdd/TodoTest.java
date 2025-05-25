package com.example.atdd;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.testng.Assert.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

/****************************************/
// Historia de Usuario: Como usuario autenticado quiero agregar una tarea
//
// Caso de Prueba: Agregar Nueva Tarea
// Descripción: Verifica que un usuario autenticado puede agregar una nueva tarea a su lista
//
// Precondiciones:
// - La aplicación está en ejecución en http://localhost:8080
// - El navegador Chrome/Brave está instalado y configurado
// - Se requiere un usuario registrado y autenticado
//
// Pasos de la Prueba de Aceptación:
// 1. Preparación del Usuario:
//    a. Registrar usuario si no existe (todoUserTestNG)
//    b. Iniciar sesión con el usuario
// 2. Agregar Tarea:
//    a. Navegar a la página de tareas (/todo)
//    b. Ingresar texto de la tarea: "Tarea ATDD TestNG"
//    c. Hacer clic en el botón de agregar
//
// Resultados Esperados:
// 1. La tarea "Tarea ATDD TestNG" aparece en la lista de tareas
// 2. La tarea es visible y legible en la interfaz
//
// Datos de Prueba:
// - Usuario: todoUserTestNG
// - Email: todoUserTestNG@atdd.com
// - Contraseña: claveTodo
// - Texto de la tarea: "Tarea ATDD TestNG"
//
// Notas:
// - La prueba incluye registro automático del usuario si no existe
// - Se manejan las excepciones si el usuario ya está registrado
// - Se incluyen esperas estratégicas para asegurar la carga de elementos
// - Se utiliza XPath para verificar la presencia de la tarea en la lista
/****************************************/

//! AGREGAR MAS PASOS DE PRUEBAS DE ACEPTACION PARA EL CASO DE PRUEBA DE AGREGAR TAREA
public class TodoTest {
    private WebDriver driver;

    @BeforeTest
    public void setDriver() throws Exception {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
        driver = new ChromeDriver(options);

        // Registro rápido del usuario para la prueba (si ya existe, ignora el error)
        try {
            // Navega a la página de registro
            driver.get("http://localhost:8080/register");
            // Ingresa nombre de usuario
            driver.findElement(By.id("username")).sendKeys("todoUserTestNG");
            // Ingresa correo electrónico
            driver.findElement(By.id("email")).sendKeys("todoUserTestNG@atdd.com");
            // Ingresa contraseña
            driver.findElement(By.id("password")).sendKeys("claveTodo");
            // Confirma contraseña
            driver.findElement(By.id("confirm")).sendKeys("claveTodo");
            // Hace clic en el botón de registro
            driver.findElement(By.id("registerButton")).click();
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception ignored) {}

        // Login del usuario para la prueba
        // Navega a la página de login
        driver.get("http://localhost:8080/login");
        // Ingresa nombre de usuario
        driver.findElement(By.id("username")).sendKeys("todoUserTestNG");
        // Ingresa contraseña
        driver.findElement(By.id("password")).sendKeys("claveTodo");
        // Hace clic en el botón de login
        driver.findElement(By.id("loginButton")).click();
        TimeUnit.SECONDS.sleep(1);
    }

    @AfterTest
    public void closeDriver() throws Exception {
        driver.quit();
    }

    @Test
    public void agregarTarea() throws InterruptedException {
        // Navega a la página de tareas
        driver.get("http://localhost:8080/todo");

        // Ingresa el texto de la nueva tarea
        driver.findElement(By.id("todoInput")).sendKeys("Tarea ATDD TestNG");
        // Hace clic en el botón para agregar la tarea
        driver.findElement(By.id("addButton")).click();

        TimeUnit.SECONDS.sleep(2);

        // Verifica que la tarea aparece en la lista
        WebElement tarea = driver.findElement(By.xpath("//li[contains(text(),'Tarea ATDD TestNG')]"));
        assertTrue(tarea.isDisplayed(), "La tarea debe aparecer en la lista tras agregarla.");
    }
}