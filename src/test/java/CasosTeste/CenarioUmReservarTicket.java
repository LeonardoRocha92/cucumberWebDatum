package CasosTeste;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class CenarioUmReservarTicket {

	private WebDriver driver;

	@Dado("^que estou acessando a aplicacao$")
	public void queEstouAcessandoAAplicacao() throws Throwable {
		driver = new ChromeDriver();
		driver.get("http://www.newtours.demoaut.com/");
		driver.manage().window().maximize();
	}

	@Quando("^informo o usuario \"([^\"]*)\"$")
	public void informoOUsuario(String arg1) throws Throwable {
		driver.findElement(By.name("userName")).sendKeys(arg1);
	}

	@Quando("^a senha \"([^\"]*)\"$")
	public void aSenha(String arg1) throws Throwable {
		driver.findElement(By.name("password")).sendKeys(arg1);
	}

	@Quando("^seleciono sign-in$")
	public void selecionoSignIn() throws Throwable {
		driver.findElement(By.name("login")).click();
	}

	@Entao("^visualizo a tela Flight Finder$")
	public void visualizoATelaFlightFinder() throws Throwable {
		Thread.sleep(2000);
		boolean inicio = driver.findElement(By.xpath("//img[@src='/images/masts/mast_flightfinder.gif']"))
				.isDisplayed();
		Assert.assertTrue("true", inicio);
	}

	@Quando("^selecionar cidade de origem e de destino$")
	public void selecionarCidadeDeOrigemEDeDestino() throws Throwable {
		Select origem = new Select(driver.findElement(By.name("fromPort")));
		origem.selectByIndex(2);

		Select retorno = new Select(driver.findElement(By.name("toPort")));
		retorno.selectByIndex(3);
	}

	@Quando("^selecionar data maior que a corrente$")
	public void selecionarDataMaiorQueACorrente() throws Throwable {
		Select dataRetorno = new Select(driver.findElement(By.name("toDay")));
		dataRetorno.selectByValue("15");
	}

	@Quando("^selecionar classe e o numero de passageiros$")
	public void selecionarClasseEONumeroDePassageiros() throws Throwable {
		driver.findElement(By.cssSelector("input[value='First']")).click();
		new Select(driver.findElement(By.name("passCount"))).selectByVisibleText("1");
	}

	@Quando("^selecionar continue$")
	public void selecionarContinue() throws Throwable {
		driver.findElement(By.xpath("//input[@src='/images/forms/continue.gif']")).click();
	}

	@Entao("^visualizo a tela Select Flight$")
	public void visualizoATelaSelectFlight() throws Throwable {
		Thread.sleep(2000);
		boolean selectFlight = driver.findElement(By.xpath("//img[@src='/images/masts/mast_selectflight.gif']"))
				.isDisplayed();
		Assert.assertTrue("true", selectFlight);
	}

	@Quando("^selecionar o voo de partida$")
	public void selecionarOVooDePartida() throws Throwable {
		driver.findElement(By.cssSelector("input[value='Blue Skies Airlines$361$271$7:10']")).click();

	}

	@Quando("^selecionar o voo de retorno$")
	public void selecionarOVooDeRetorno() throws Throwable {
		driver.findElement(By.cssSelector("input[value='Unified Airlines$633$303$18:44']")).click();

	}

	public void visualizoATelaBookAFlightS() throws Throwable {
		Thread.sleep(2000);
		boolean bookFlight = driver.findElement(By.xpath("//img[@src='/images/masts/mast_book.gif']")).isDisplayed();
		Assert.assertTrue("true", bookFlight);
	}

	@Quando("^preencher First Name e Last Name$")
	public void preencherFirstNameELastName() throws Throwable {
		driver.findElement(By.name("passFirst0")).sendKeys("Leonardo");
		driver.findElement(By.name("passLast0")).sendKeys("Rocha");
	}

	@Quando("^preencher numero do cartao$")
	public void preencherNumeroDoDartao() throws Throwable {
		driver.findElement(By.name("creditnumber")).sendKeys("99998888777");
	}

	@Quando("^selecionar Secure Puchase$")
	public void selecionarSecurePuchase() throws Throwable {
		driver.findElement(By.name("buyFlights")).click();
	}

	@Entao("^uma Ordem e gerada\\.$")
	public void umaOrdemGerada() throws Throwable {
		String ordem = driver
				.findElement(By
						.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/table/tbody/tr[5]/td/table/tbody/tr[1]/td/table/tbody/tr/td[1]/b/font/font/b/font[1]"))
				.getText().substring(20, 39);

		System.out.println("Sua ordem de reserva é a: " + ordem);
	}
	
	@After(order = 1)
	public void screenshot(Scenario cenario){
		
		try {
			File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File("target/screenshot/"+cenario.getId()+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@After(order = 0)
	public void fecharBrowser() {
		driver.quit();
	}

}
