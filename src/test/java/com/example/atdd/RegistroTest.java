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
// Historia de Usuario: Como usuario nuevo quiero registrar mis datos en la aplicación
//
// Caso de Prueba: Registro Exitoso de Usuario
// Descripción: Verifica que un nuevo usuario puede registrarse exitosamente en la aplicación
//
// Precondiciones:
// - La aplicación está en ejecución en http://localhost:8080
// - El navegador Chrome/Brave está instalado y configurado
//
// Pasos de la Prueba de Aceptación:
// 1. Ingresar a la página de registro (/register)
// 2. Ingresar nombre de usuario único: "usuarioTestNG"
// 3. Ingresar correo electrónico válido: "usuarioTestNG@atdd.com"
// 4. Ingresar contraseña: "clave123"
// 5. Confirmar contraseña: "clave123"
// 6. Hacer clic en el botón de registro
//
// Resultados Esperados:
// 1. El sistema redirige a la página de login
// 2. Se muestra un mensaje de éxito indicando que el registro fue exitoso
// 3. El botón de login está visible y disponible para su uso
//
// Datos de Prueba:
// - Usuario: usuarioTestNG
// - Email: usuarioTestNG@atdd.com
// - Contraseña: clave123
//
// Notas:
// - La prueba utiliza Selenium WebDriver para automatización
// - Se incluye una espera de 2 segundos para asegurar la carga de la página
// - Se verifica tanto el mensaje de éxito como la redirección
/****************************************/

public class RegistroTest {
    private WebDriver driver;

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
    public void registroExitoso() throws InterruptedException {
        // Navega a la página de registro
        driver.get("http://localhost:8080/register");

        // Ingresa nombre de usuario
        driver.findElement(By.id("username")).sendKeys("usuarioTestNG");
        // Ingresa correo electrónico
        driver.findElement(By.id("email")).sendKeys("usuarioTestNG@atdd.com");
        // Ingresa contraseña
        driver.findElement(By.id("password")).sendKeys("clave123");
        // Confirma contraseña
        driver.findElement(By.id("confirm")).sendKeys("clave123");
        // Hace clic en el botón de registro
        driver.findElement(By.id("registerButton")).click();

        TimeUnit.SECONDS.sleep(2);

        // Verifica que redirige a login con mensaje de éxito
        WebElement loginBtn = driver.findElement(By.id("loginButton"));
        assertTrue(loginBtn.isDisplayed(), "El botón de login debe estar presente tras registro.");
        assertTrue(driver.getPageSource().contains("Ahora puedes iniciar sesión") ||
                   driver.getPageSource().toLowerCase().contains("exitoso"),
                   "Debe mostrar mensaje de éxito tras el registro.");
    }
}