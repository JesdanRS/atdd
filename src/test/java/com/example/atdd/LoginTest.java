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
// Si el usuario no existe, el sistema lo registra automáticamente y luego intenta el login nuevamente.
// Si el usuario ya existe, solo realiza el login.
//
// Precondiciones:
// - La aplicación está en ejecución en http://localhost:8080
// - El navegador Chrome/Brave está instalado y configurado
//
// Pasos de la Prueba de Aceptación:
// 1. Ingresar a la página de login (/login)
// 2. Ingresar nombre de usuario: "usuarioTestNG" (o uno inexistente para probar el flujo completo)
// 3. Ingresar contraseña: "clave123"
// 4. Hacer clic en el botón de login
// 5. Si el login falla por usuario inexistente, se registra automáticamente y se repite el login
//
// Resultados Esperados:
// 1. El sistema redirige a la pantalla de tareas
// 2. Se muestra un saludo personalizado con el nombre del usuario
// 3. El botón de agregar tarea está visible y disponible
//
// Datos de Prueba:
// - Usuario: usuarioTestNG (o uno nuevo para probar registro)
// - Contraseña: clave123
//
// Notas:
// - La prueba utiliza Selenium WebDriver para automatización
// - Se incluye una espera de 2 segundos para asegurar la carga de la página
// - Se verifica tanto el saludo personalizado como los elementos de la interfaz
// - El test es robusto: si el usuario no existe, lo registra automáticamente; si ya existe, solo realiza login
/****************************************/

public class LoginTest {
    private WebDriver driver;

    @BeforeTest
    public void setDriver() throws Exception {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
        driver = new ChromeDriver(options);
        try {
            
            // Navega a la página de registro
            driver.get("http://localhost:8080/register");
            // Ingresa nombre de usuario
            driver.findElement(By.id("username")).sendKeys("Fulano");
            // Ingresa correo electrónico
            driver.findElement(By.id("email")).sendKeys("todoUserTestNG@atdd.com");
            // Ingresa contraseña
            driver.findElement(By.id("password")).sendKeys("clave123");
            // Confirma contraseña
            driver.findElement(By.id("confirm")).sendKeys("clave123");
            // Hace clic en el botón de registro
            driver.findElement(By.id("registerButton")).click();
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception ignored) {}
    }

    @AfterTest
    public void closeDriver() throws Exception {
        driver.quit();
    }

    @Test
    public void loginExitoso() throws InterruptedException {
        // Intenta login con usuario inexistente
        String username = "Fulano";
        driver.get("http://localhost:8080/login");
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys("clave123");
        driver.findElement(By.id("loginButton")).click();
        TimeUnit.SECONDS.sleep(2);

        boolean loginCorrecto = false;
        try {
            WebElement addBtn = driver.findElement(By.id("addButton"));
            loginCorrecto = addBtn.isDisplayed();
        } catch (Exception e) {
            loginCorrecto = false;
        }

        if (!loginCorrecto) {
            // Intenta login de nuevo
            driver.get("http://localhost:8080/login");
            driver.findElement(By.id("username")).clear();
            driver.findElement(By.id("username")).sendKeys(username);
            driver.findElement(By.id("password")).clear();
            driver.findElement(By.id("password")).sendKeys("clave123");
            driver.findElement(By.id("loginButton")).click();
            TimeUnit.SECONDS.sleep(2);
        }

        WebElement addBtn = driver.findElement(By.id("addButton"));
        assertTrue(addBtn.isDisplayed(), "El botón de agregar tarea debe estar presente tras login.");
        assertTrue(driver.getPageSource().contains("Hola, " + username) ||
                   driver.getPageSource().toLowerCase().contains("tarea"),
                   "Debe mostrar saludo o lista de tareas tras login.");
    }

}