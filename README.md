Ulici Georgeta-Anca
# Documentație


Am scris teste automate în limbajul de programare Java pentru următoarele site-uri: https://fasttrackit-test.netlify.app/#/ și https://demo.wearehaive.com/ro/auth/sign-in.

Numele proiectelor sunt: [AutomationSolutionNetlify](https://github.com/GetaUlici/AutomationSolutionNetlify) și [HaiveAutomationNew](https://github.com/GetaUlici/HaiveAutomationNew)

Pentru a scrie testele automate, am folosit Selenium WebDriver, un framework de automatizare web care permite rularea testelor pe diverse browsere. Am executat testele pe browserul Chrome.

De asemenea, am utilizat Maven, un instrument de automatizare și management al proiectelor, care compilează codul și gestionează dependințele printr-un fișier central de configurare numit Project Object Model (POM), definit în pom.xml.

În pom.xml, am inclus dependința “webdrivermanager” pentru a facilita conexiunea între Selenium WebDriver și Chrome.

Am folosit și modelul Page Object Model (POM), un design pattern în Selenium care permite împărțirea claselor în categorii de tip „Page” și „Test”. Fiecare pagină din proiect are o clasă de testare corespunzătoare (de exemplu, CheckoutPage și CheckoutTest). Clasele de tip Test conțin toate cazurile de test, în timp ce clasele de tip Page stochează toate elementele UI (elementele Web) și metodele asociate.

Pentru inițializarea elementelor Web, am folosit PageFactory, o librarie Selenium care oferă adnotări precum @FindBy pentru a localiza elementele și a interacționa cu acestea la instanțiere.

Am utilizat și framework-ul de testare TestNG, care permite testare parametrizată și bazată pe date, testare în paralel și validări (assertions). De asemenea, mi-a permis să folosesc adnotări precum @Test, @BeforeMethod și @AfterMethod.

Pentru rapoartele de execuție ale testelor, am folosit ExtentReports, o librărie destinată generării de rapoarte detaliate pentru testele automate.
