package CasosTeste;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class CenarioDoisCriacaoMassa {

	CenarioUmReservarTicket reserva = new CenarioUmReservarTicket();

	private WebDriver driver;

	@Dado("^planilha com informacoes de validacao do teste$")
	public void planilhaComInformaEsDeValidacaoDoTeste() throws Throwable {

		Reader reader = Files.newBufferedReader(Paths.get("src/main/java/massadados/SampleAppData.csv"));
		CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();

		reserva.queEstouAcessandoAAplicacao();
		reserva.informoOUsuario("mercury");
		reserva.aSenha("mercury");
		reserva.selecionoSignIn();
		reserva.visualizoATelaFlightFinder();

		List<String[]> SampleAppData = csvReader.readAll();
		for (String[] SampleData : SampleAppData) {

			selecionarCidadeDeOrigemEDeDestino(SampleData[4].trim(), SampleData[5].trim());
			selecionar_opcao_One_Way();
			selecionarDataMaiorQueACorrente(SampleData[3].replace("/", "").trim().substring(0, 2));
			selecionarClasseEONumeroDePassageiros(SampleData[2].trim(), SampleData[1].trim());
			reserva.selecionarContinue();
			reserva.visualizoATelaSelectFlight();
			selecionarOVooDePartida(SampleData[0].trim());
			reserva.selecionarContinue();
			reserva.visualizoATelaBookAFlightS();
			preencherFirstNameELastName(SampleData[1].trim());
			reserva.preencherNumeroDoDartao();
			reserva.selecionarSecurePuchase();
			reserva.umaOrdemGerada();

			System.out.println("FlightNumber : " + SampleData[0] + " - Passengers : " + SampleData[1] + " - Class : "
					+ SampleData[2] + " - On : " + SampleData[3] + " - DepartingFrom : " + SampleData[4]
					+ " - ArrivingIn : " + SampleData[5] + " - OrderNumber : " + SampleData[6]);
		}

	}

	private void selecionarCidadeDeOrigemEDeDestino(String origem, String destino) throws Throwable {
		Select cidadeOrigem = new Select(driver.findElement(By.name("fromPort")));
		cidadeOrigem.selectByVisibleText(origem);

		Select cidadeDestino = new Select(driver.findElement(By.name("toPort")));
		cidadeDestino.selectByVisibleText(destino);
	}

	private void selecionar_opcao_One_Way() throws Throwable {
		driver.findElement(By.cssSelector("input[name='tripType']")).click();
	}

	private void selecionarDataMaiorQueACorrente(String data) throws Throwable {
		Select dataRetorno = new Select(driver.findElement(By.name("fromDay")));
		dataRetorno.selectByValue(data);
	}

	private void selecionarClasseEONumeroDePassageiros(String classe, String passengers) throws Throwable {

		if (classe.contains("Businessclass")) {
			driver.findElement(By.cssSelector("input[value='Business']")).click();
		}
		if (classe.contains("Economyclass")) {
			driver.findElement(By.cssSelector("input[value='Coach']")).click();
		} else {
			driver.findElement(By.cssSelector("input[value='First']")).click();
		}

		new Select(driver.findElement(By.name("passCount"))).selectByVisibleText(passengers);
	}

	private void selecionarOVooDePartida(String voo) throws Throwable {

		if (voo.contains("PangaeaAirlines362")) {
			driver.findElement(By.cssSelector("input[value='Pangea Airlines$362$274$9:17']")).click();
		} else {
			driver.findElement(By.cssSelector("input[value='Blue Skies Airlines$361$271$7:10']")).click();
		}
	}

	private void preencherFirstNameELastName(String passagenrs) throws Throwable {

		if (passagenrs.contains("1")) {
			driver.findElement(By.name("passFirst0")).sendKeys("Leonardo");
			driver.findElement(By.name("passLast0")).sendKeys("Rocha");
		} else {
			driver.findElement(By.name("passFirst1")).sendKeys("Leonardo");
			driver.findElement(By.name("passLast1")).sendKeys("Rocha");
		}
	}

	@Quando("^exibir dados contidos na planilha$")

	public void exibirDadosPlanilha() throws IOException {

		Reader reader = Files.newBufferedReader(Paths.get("src/main/java/massadados/SampleAppData.csv"));
		CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();

		List<String[]> SampleAppData = csvReader.readAll();
		for (String[] SampleData : SampleAppData)

			System.out.println("FlightNumber : " + SampleData[0] + " - Passengers : " + SampleData[1] + " - Class : "
					+ SampleData[2] + " - On : " + (SampleData[3].replace("/", "").trim().substring(0, 2))
					+ " - DepartingFrom : " + SampleData[4] + " - ArrivingIn : " + SampleData[5] + " - OrderNumber : "
					+ SampleData[6]);
	}

	@Entao("^visualizo planiha$")

	public void visualizarPlanilha() throws IOException {
		File file = new File("src/main/java/massadados/SampleAppData.csv");
		Desktop.getDesktop().open(file);
	}

	@After(order = 1)
	public void screenshot(Scenario cenario) {

		try {
			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File("target/screenshot/" + cenario.getId() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@After(order = 0)
	public void fecharBrowser() {
		driver.quit();
	}

}