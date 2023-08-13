
$JAVA_HOME = Read-Host("Enter the path to JDK_HOME")
Write-Output($JAVA_HOME)
$INSTALL_DIR = Read-Host("Enter installation path")
Write-Output($INSTALL_DIR)
$CHROMEDRIVER_BIN= Read-Host("Enter Path to the chromedriver")
Write-Output($CHROMEDRIVER_BIN)


Write-Output("Extracting files")
Expand-Archive -Path "omega-discovery-service.zip" -DestinationPath $INSTALL_DIR\omega-discovery-service -Force

$CONTENT = "SET JAVA_HOME=" + $JAVA_HOME +
            "`n" +
            "SET INSTALL_DIR=" + $INSTALL_DIR +
            "`n" +
            "SET PATH=%PATH%;%JAVA_HOME%\bin;"

# unzipn the contents in the INSTALL_DIR

$INSTALL_DIR = $INSTALL_DIR + "\omega-discovery-service"

# create a setenv.bat file and add it to the INSTALL_DIR path
Set-Content -Path $INSTALL_DIR\setenv.bat -Value $CONTENT


# update the application.properties
$applicationPropertiesContent = Get-Content -Path $INSTALL_DIR\config\application.properties
$updatedProperties = $applicationPropertiesContent -Replace '{value.application.basedir}',$INSTALL_DIR
$updatedProperties = $updatedProperties -Replace '{value.chromedriver.location}',$CHROMEDRIVER_BIN
$updatedProperties = $updatedProperties -Replace '\\', '/'

Set-Content -Path $INSTALL_DIR\config\application.properties -Value $updatedProperties