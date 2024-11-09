# Wie heeft wat gemaakt

# modules

## Danilo
- module **group-generator** gemaakt.
- module **message-config** gemaakt

enige logica voor preference en DTO's zit nog bij registrations omdat de logica daar verantwoordelijk is.


## Thuja
### RPC
module **registrations** gemaakt (Hier zit course ook in).

### Messaging
In registrations, api mapje: rabbitMessage en CourseProducer
+ queue, exchange en binding aangemaakt in de rabbitConfig voor student, alleen in student niks gemaakt dus krijgt geen antwoord terug

## Fleur
module **submissions** en **teacher-review** gemaakt.

## Veron
module **assignments** en **peer-review**

# monoliet
## Danilo

Veel dingen zijn standaard, zoals het opzetten van de database met Spring-annotaties, maar deze zijn niet heel interessant omdat dit al vaak is gedaan. Hetzelfde geldt voor veel van de CRUD-functionaliteiten, zoals het toevoegen van een student. Ondanks dat zulke dingen nodig zijn voor de applicatie, sla ik ze over omdat één persoon dit snel kan maken.
hiernaast is zowat alles wat te maken heeft met studenten, classen teachers en preferences door mij gemaakt (Note: Thuutje2 heeft ook de studenten nodig gehad voor submissions)

### Use case: 
Als student wil ik automatisch in een klas ingedeelt worden met mijn voorkeur zodat ik weet bij welke mensen ik zit.

### Domain
**Klassen die zijn uitgewerkt**:
- (Een groot gedeelte van) Student
- Class
- Teacher
- (Een klein gedeelte van) Course

### Util
- ClassGenerator (hier ligt voornamelijk mijn use case)
  
### Application
**Service**
- (gedeeltelijk) StudentService
- (gedeeltelijk) CourseService

<br>

### Presentation
**Controller**:
- (gedeeltelijk) CourseController
- (gedeeltelijk) StudentController

<br>

**DTO's**:
- Teacher
- Student
- Class
- StudentPreference

#### Algemene keuzes
Er is voor gekozen om de `ClassGenerator` bij `Course` neer te zetten, omdat de `ClassGenerator` op `courseId` afgaat. Om deze reden heeft hij geen aparte service of controller gekregen dit zorgt er voor dat denlo1212 en Thuutje2 gezamelijk de Service en controller hebben gemaakt omdat deze beide met `Course` en `Student` werkte.
Bij ClassGenerator zull u meer vinden over hoe het algoritme werkt en waarmee er op vooruit gebouwen zou kunnen worden als hij is een keer verbeterd zou worden

<br>

Technisch gezien had ik een value object kunnen neerzetten bij student. Door een student een Adress te geven maar voor het doel van dit project en deze applicatie vind ik dit erg onnodig en zou je het alleen doen om punten te verzamelen wat ik een beetje raar vind aangezien de concepten van een value object gewoon 100% duidelijk zijn voor mij. Niet overal in de applicatie is een value object nodig

<br>

Alles wat ik heb gemaakt, is getest.

### Pluspunten
- Ondanks dat een aantal dingen van de controller en service CRUD zijn, is er veel uitgewerkt, waardoor de applicatie fatsoenlijk werkt.
- Er is een automatisch algoritme dat studenten en docenten in classes plaatst om manueel werk te voorkomen.

<br>

### Minpunten
- Er is niks gedaan met `Teacher`, dus deze is hardcoded op een bepaalde plek in de applicatie. Op deze manier kan je nog steeds simuleren hoe de klassen worden gemaakt.
- Bij course is er een getter gebruikt om de studenten active studenten te verkrijgen 

<br>

## Veron
## Use Case
>Als student wil ik gekoppeld worden aan een andere student,\
>zodat ik die andere student een peer review kan geven.

## Uitgewerkte klassen

### Domain
- Submission
- Review
- PeerReviewPair (Value Object)
- Assignment

<br>

### Application
**Services**
- ReviewService
- AssignmentService

**Exceptions**
- NotFountException
- PeerReviewNotAllowedException

<br>

### Presentation
**Controllers**
- AssignmentController
- ReviewController

## Waar heb ik aan gedacht?
Bij het maken van de assignments was geen logica, dat was puur CRUD.\
Maar bij het maken van mijn niet triviale use case heb ik natuurlijk goed gedacht aan **seperation of concerns** en **encapsulation**.\
In de ReviewController heb ik de volgende twee endpoints gemaakt en uitgewerkt.

```java
// Endpoint one
public List<SubmissionResponseDto> initPeerReview(@RequestBody InitPeerReviewRequestDto requestDto) {}
// Endpoint two
public SubmissionResponseDto submitPeerReview(@PathVariable long submissionId, @RequestBody ReviewRequestDto peerReview) {}
```

In de ReviewService heb ik de volgende methods gemaakt en uitgewerkt.
```java
public List<SubmissionResponseDto> initPeerReview(InitPeerReviewRequestDto requestDto) {}
public SubmissionResponseDto submitPeerReview(Long submissionId, ReviewRequestDto requestDto) {}
private List<SubmissionResponseDto> findStudentSubmissions(List<Submission> submissions, Long studentOneId, Long studentTwoId) {}
private List<SubmissionResponseDto> createReviewPair(Submission submissionStudentOne, Submission submissionStudentTwo) {}
private Review dtoToObject(ReviewRequestDto requestDto) {}
```

Zoals ik al zei heb ik bij mijn use case voornamelijk gedacht aan **seperation of concerns** en **encapsulation**,\
wat te zien is aan de private methods die de complexiteit van de service verbergen.

>- **initPeerReview** richt zich op de logica van het aanmaken van een peer review paar.


>- **submitPeerReview** richt zich op de logica van de koppeling tussen de inlevering en de review van een student. 

Mijn **Value Object** PeerReviewPair heeft als doel om het concept van een peer review te verduidelijken,\
door twee inleveringen (submissions) er in te stoppen
```java
public final class PeerReviewPair {
    private final Submission submissionOne;
    private final Submission submissionTwo;

    public PeerReviewPair(Submission submissionOne, Submission submissionTwo) {
        this.submissionOne = Objects.requireNonNull(submissionOne);
        this.submissionTwo = Objects.requireNonNull(submissionTwo);
    }
    
    // Equals & Hascode method below
}
```

## Waar heb ik niet aan gedacht?
Submission gebruikt een soort van een setter met een custom naam, dus niet (setSomething).\
Voorbeeld:
```java
// This method will set the submissionToPeerReview attribute
// Which is the id of another students submission
public void linkSubmissionToPeerReview(Long submissionId) {
    this.submissionToPeerReview = submissionId;
}
```

## Thuja

### Use case:
Als student wil ik me inschrijven voor een cursus zodat ik toegang krijg tot de gewenste cursus. 

Hiervoor moesten er bepaalde checks komen om te kijken of de student de cursus mag doen. 

### Domain
**Klassen die zijn uitgewerkt**:
- (gedeelte van) Course 
- Registration 
- (gedeelte van) Student 
- StudyDirection (Value Object)
  
### Application
**Service**
- (gedeeltelijk) StudentService
- (gedeeltelijk) CourseService
- RegistrationService
  
<br>

### Presentation
**Controller**:
- (gedeeltelijk) CourseController
- (gedeeltelijk) StudentController
- registrationController en behorende dto's

<br>

## Aangedacht:
Voor nu heb ik 3 verschillende checks die gedaan worden
- Completed Course, heeft de student de cursus al gedaan?
- Entered Course, is de student nog bezig met de cursus?
- StudyDirection, Heeft student de zelfde studieriching als de cursus? want nee dan kan hij/zij de cursus niet volgen

Registration begint bij PENDING en gaat daarna door de hand van de verschillende factorys kijken of deze true zijn, zo ja dan wordt de cursus ENTERED

Niet aangekomen:
- Voor student een get krijgen met registrations
- Een course zou een aantal assignments hebben en als die succesvol zijn afgerond dan zou course status naar COMPLETED kunnen.
- Mindere dto voor registrations

Meerdere checks die konden:
- Hoort de cursus bij het examenprogramma? 
- Of student de cursus wel kan doen in het leerjaar waar die in zit.


## Fleur

### Use case:
Als student wil ik individueel opdrachten kunnen inleveren zodat dit nagekeken en beoordeeld kan worden door een docent.
Als student wil ik ingeleverd werk vanuit mijn team kunnen bevestigen zodat dit beoordeeld kan worden door de docent'.
Als student wil ik werk vanuit mijn team kunnen inleveren zodat dit beoordeeld kan worden door de docent'.

### Domain
**Klassen die zijn uitgewerkt**:
- (gedeelte van) Submission
- Team
- Teacher
- (gedeelte van) Review

### Application
**Service**
- (gedeeltelijk) ReviewService
- (gedeeltelijk) SubmissionService
- TeacherService

<br>

### Presentation
**Controller**:
- (gedeeltelijk) ReviewController
- SubmissionController
- TeacherService

<br>

### Waar heb ik aan gedacht?
- Review was eerst een interface met daarbij 2 aparte klasses voor teacher en peer, maar na overleg hebben we
- besloten om het juist iets eenvouder te doen en te houden op een algemene klasse Review welke van beide zou kunnen zijn.

### Wat kan beter?
- In de Controller zou ik nog zelf nettere exceptions willen aanmaken
- In de Service klasses wil ik gebruiken maken van alleen andere Services (behalve als het om hetzelfde object gaat)
- In de klasse Submission zou ik nog een format willen toevoegen voor Score
- Ik zou als value object een deadline kunnen aanmaken voor in assignment zodat ik ook meer overlapping heb met de core van teamgenoten



# HULP Start Repository

Bij deze de start-repository voor de HULP opdracht.

In deze repository start een lege Spring-boot applicatie. Deze staat net 1 directory dieper dan je gewend 
bent. Dat kun je voor nu negeren, maar zorgt er voor dat de overstap in week 3 net iets prettiger loopt.


Na opstarten kun je alvast kijken op

* http://localhost:8080/actuator voor allerhande debug info (heel soms handig)
* http://localhost:8080/h2-console voor je database.
  
  Het is niet Postgres, maar ik denk dat je de weg wel vindt. Uiteraard mag je PostGres introduceren, 
  maar zet dan ook een bijpassende Dockerfile op.

  (als het goed is kloppen de defaults, anders moet je de url/username even uit de application.properties vissen)

