# Instructies voor het aanmaken van een Azure Virtual Machine 

## Stap 1: Een nieuwe resource aanmaken in Azure
1. Navigeer naar [Azure Portal](https://portal.azure.com).
2.  Klik op "Create a resource".
3. Zoek en selecteer **Virtual Machine**.

## Stap 2: Instellingen voor de Virtual Machine
1. Selecteer een actief abonnement.
2. **Configuratie van de VM**:
    - Kies een **minimale geheugen van 2 GB** om er voor te zorgen dat alles hier op kan draaien.
    - Je kan later upgraden als dit nodig is

## Stap 3: GitHub koppelen aan een Azure Web Service
Hoewel je geen GitHub-koppeling kunt instellen op een Virtual Machine, kun je dit wel doen met een **Azure Web Service**. Hierdoor kun je automatische deployments instellen vanuit een GitHub repository.

### Stappen voor GitHub Workflow configuratie:
1. **Maak een Azure Web Service aan**: Volg dezelfde stappen als hierboven, maar kies "Web App" in plaats van "Virtual Machine".
2. **Configureer GitHub Actions**:
    - Azure geeft een `.yml` bestand dat automatisch elke keer deployt als je naar een bepaalde branch pusht.
    - Open dit bestand in je GitHub repository en pas de configuratie aan zodat de workflow **op de `main` branch** automatisch deployt.

3. **Toevoegen van de Azure Key**:
    - Na het aanmaken van een Virtual machine verkrijg je een key **SLA DEZE OP!**
    - Zet de Azure key in je github secrets. Voeg de **Azure key** toe in de `.yml` via de secrets

4. ## stap 4
    
