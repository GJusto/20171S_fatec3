package br.sceweb.testeGUI;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import br.sceweb.teste.FormConvenio;
import br.sceweb.teste.FormEmpresa;

public class RoteiroDeNavegacao {
	private static WebDriver driver;
	private static String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	private static FormConvenio formConvenio;
	private static FormEmpresa formEmpresa;
	
	public RoteiroDeNavegacao(){
		inicializa();
	}
	public void inicializa(){
		try {
			ExcelUtils.setExcelFile(Constant.Path_TestData + Constant.File_TestData, "Planilha1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.setProperty("webdriver.chrome.driver", "C:/Users/esadm4/git/20171s_fatec3/sceweb/WebContent/WEB-INF/lib/chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	public void fluxo() throws Exception {
		String acao;
		
		for (int i = 1; i < 6; i++) {
			// linha x coluna
			 
			System.out.println("celula = " + ExcelUtils.getCellData(i, 2));
			Thread.sleep(5000);
			acao = ExcelUtils.getCellData(i, 2);
			if (acao.equals("abreFormConvenio")){
				formConvenio = new FormConvenio(driver);
				formConvenio.abre();
				Thread.sleep(5000);
			}
			if (acao.equals("abreFormEmpresa")){
				formEmpresa = new FormEmpresa(driver);
				formEmpresa.abre();
				Thread.sleep(5000);
			}
			if (acao.equals("cadastrarEmpresa")){
				formEmpresa = new FormEmpresa(driver);
				formEmpresa.cadastra(ExcelUtils.getCellData(i, 4), ExcelUtils.getCellData(i, 5),
						ExcelUtils.getCellData(i, 6), ExcelUtils.getCellData(i, 7),ExcelUtils.getCellData(i, 8),
						ExcelUtils.getCellData(i, 9), ExcelUtils.getCellData(i, 10), ExcelUtils.getCellData(i, 11),
						ExcelUtils.getCellData(i, 12), ExcelUtils.getCellData(i, 13));
				
				try {
					WebDriverWait wait = new WebDriverWait(driver, 10);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensagem")));
					//assertEquals(ExcelUtils.getCellData(1, 7), driver.findElement(By.id("mensagem")).getText());
					//driver.quit();
				} catch (Error e) {
					verificationErrors.append(e.toString());
				}
			}
			if (acao.equals("cadastrarConvenio")){
				formConvenio.cadastra(ExcelUtils.getCellData(i, 4), ExcelUtils.getCellData(i, 5),
						ExcelUtils.getCellData(i, 6));
				
				try {
					WebDriverWait wait = new WebDriverWait(driver, 10);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensagem")));
					//assertEquals(ExcelUtils.getCellData(1, 7), driver.findElement(By.id("mensagem")).getText());
					//driver.quit();
				} catch (Error e) {
					verificationErrors.append(e.toString());
				}
			}
			
			
		}
		driver.quit();
	}
	
}
