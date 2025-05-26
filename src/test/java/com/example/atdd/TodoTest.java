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

/****************************************/
// Historia de Usuario: Como usuario autenticado quiero borrar una tarea
//
// Caso de Prueba: Borrar Tarea Existente
// Descripción: Verifica que un usuario autenticado puede borrar una tarea de su lista
//
// Precondiciones:
// - La aplicación está en ejecución en http://localhost:8080
// - El navegador Chrome/Brave está instalado y configurado
// - Se requiere un usuario registrado y autenticado
// - Debe existir al menos una tarea en la lista
//
// Pasos de la Prueba de Aceptación:
// 1. Preparación del Usuario:
//    a. Registrar usuario si no existe (todoUserTestNG)
//    b. Iniciar sesión con el usuario
// 2. Agregar Tarea para asegurar que existe:
//    a. Navegar a la página de tareas (/todo)
//    b. Ingresar texto de la tarea: "Tarea para borrar"
//    c. Hacer clic en el botón de agregar
// 3. Borrar Tarea:
//    a. Hacer clic en el botón "Borrar" de la tarea
//    b. Confirmar la eliminación haciendo clic en "Aceptar" en el diálogo de confirmación
//
// Resultados Esperados:
// 1. La tarea "Tarea para borrar" desaparece de la lista de tareas
// 2. La tarea ya no es visible en la interfaz
//
// Datos de Prueba:
// - Usuario: todoUserTestNG
// - Email: todoUserTestNG@atdd.com
// - Contraseña: claveTodo
// - Texto de la tarea: "Tarea para borrar"
//
// Notas:
// - La prueba incluye registro automático del usuario si no existe
// - Se manejan las excepciones si el usuario ya está registrado
// - Se incluyen esperas estratégicas para asegurar la carga de elementos
// - Se utiliza XPath para verificar la ausencia de la tarea en la lista después de borrarla
/****************************************/

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
    public void testBorrarTarea() throws InterruptedException {
        // Navega a la página de tareas
        driver.get("http://localhost:8080/todo");

        // Ingresa el texto de la nueva tarea
        String tareaParaBorrar = "Tarea para borrar";
        driver.findElement(By.id("todoInput")).sendKeys(tareaParaBorrar);
        // Hace clic en el botón para agregar la tarea
        driver.findElement(By.id("addButton")).click();

        TimeUnit.SECONDS.sleep(2);

        // Verifica que la tarea aparece en la lista
        WebElement tarea = driver.findElement(By.xpath("//li[span[text()='" + tareaParaBorrar + "'] ]"));
        assertTrue(tarea.isDisplayed(), "La tarea debe aparecer en la lista tras agregarla.");

        // Encuentra el botón de borrar asociado a la tarea y hace clic en él
        WebElement botonBorrar = tarea.findElement(By.className("delete-btn"));
        botonBorrar.click();

        TimeUnit.SECONDS.sleep(1);

        // Verifica que el diálogo de confirmación está visible
        WebElement dialogoConfirmacion = driver.findElement(By.id("confirmDialog"));
        assertTrue(dialogoConfirmacion.isDisplayed(), "El diálogo de confirmación debe mostrarse.");

        // Hace clic en el botón "Aceptar" para confirmar el borrado
        driver.findElement(By.id("confirmYes")).click();

        TimeUnit.SECONDS.sleep(2);

        // Verifica que la tarea ya no aparece en la lista
        try {
            driver.findElement(By.xpath("//li[span[text()='" + tareaParaBorrar + "'] ]"));
            fail("La tarea no debería estar visible después de borrarla.");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            // Si se lanza esta excepción, es correcto porque la tarea ya no existe
            assertTrue(true, "La tarea se borró correctamente.");
        }
    }
}