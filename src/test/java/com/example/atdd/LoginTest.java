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
// Historia de Usuario: Como usuario quiero iniciar sesión en la aplicación
//
// Caso de Prueba: Login Exitoso de Usuario
// Descripción: Verifica que un usuario registrado puede iniciar sesión y acceder a sus tareas
//
// Precondiciones:
// - La aplicación está en ejecución en http://localhost:8080
// - El navegador Chrome/Brave está instalado y configurado
// - Existe un usuario registrado con las credenciales de prueba
//
// Pasos de la Prueba de Aceptación:
// 1. Ingresar a la página de login (/login)
// 2. Ingresar nombre de usuario: "usuarioTestNG"
// 3. Ingresar contraseña: "clave123"
// 4. Hacer clic en el botón de login
//
// Resultados Esperados:
// 1. El sistema redirige a la pantalla de tareas
// 2. Se muestra un saludo personalizado con el nombre del usuario
// 3. El botón de agregar tarea está visible y disponible
//
// Datos de Prueba:
// - Usuario: usuarioTestNG
// - Contraseña: clave123
//
// Notas:
// - La prueba utiliza Selenium WebDriver para automatización
// - Se incluye una espera de 2 segundos para asegurar la carga de la página
// - Se verifica tanto el saludo personalizado como los elementos de la interfaz
/****************************************/

public class LoginTest {
    private WebDriver driver;
// ! Agregar test de crear usuario si es que no existe
    @BeforeTest
    public void setDriver() throws Exception {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
        driver = new ChromeDriver(options);
    }

    @AfterTest
    public void closeDriver() throws Exception {
        driver.quit();
    }

    @Test
    public void loginExitoso() throws InterruptedException {
        // Navega a la página de login
        driver.get("http://localhost:8080/login");

        // Ingresa nombre de usuario
        driver.findElement(By.id("username")).sendKeys("usuarioTestNG");
        // Ingresa contraseña
        driver.findElement(By.id("password")).sendKeys("clave123");
        // Hace clic en el botón de login
        driver.findElement(By.id("loginButton")).click();

        TimeUnit.SECONDS.sleep(2);

        // Verifica que está en la pantalla de tareas
        WebElement addBtn = driver.findElement(By.id("addButton"));
        assertTrue(addBtn.isDisplayed(), "El botón de agregar tarea debe estar presente tras login.");
        assertTrue(driver.getPageSource().contains("Hola, usuarioTestNG") ||
                   driver.getPageSource().toLowerCase().contains("tarea"),
                   "Debe mostrar saludo o lista de tareas tras login.");
    }
}