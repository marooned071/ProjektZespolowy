from django.db import models

class QRPoll(models.Model):
    hash_id = models.CharField(max_length=200)  # id spotkania
    time = models.CharField(max_length=200)     # czas (data rozpoczenia/zakonczenia) spotkania
    room = models.CharField(max_length=200)     # mijesce
    subject = models.CharField(max_length=200)  # temat
    version = models.IntegerField(default=1)    # wersja spotkania (po dodaniu nowego pytania wersia++)

    def __str__(self):  # Python 3: def __str__(self):
        return self.hash_id

class Question(models.Model):
    poll = models.ForeignKey(QRPoll)                    # referencja do spotknia do ktorego przypisane jest pytanie
    question_text = models.CharField(max_length=200)    # tekst pytania
    question_choices_max = models.IntegerField(default=1) # maksymalna ilosc odpowiedzi do zaznaczenia na dane pytanie - 1 - odpowiedz jednokrotnego wyboru, 2 - max - wielokrotnego wyboru, 0 - pytanie otwarte
    isRating = models.BooleanField(default=False)       # true - pytaneie przetrzymuje informacje o ocenie spotkania
    def __str__(self):  # Python 3: def __str__(self):
        return self.question_text


class Choice(models.Model):
    question = models.ForeignKey(Question)              # referencja do pytania do ktorego przypisana jest ta odpowiedz
    choice_text = models.CharField(max_length=200)      # tekst odpowiedzi
    def __str__(self):  # Python 3: def __str__(self):
        return self.choice_text

class Vote(models.Model):
    choice = models.ForeignKey(Choice)                  # referencja do odpowiedzi do ktorej przypisany jest ten glos
    voter_id = models.CharField(max_length=200);        # id glosujacego
    extra = models.CharField(max_length=200);           # ekstra informacje (nie uzywany w normlanych odpowiedziach)
    def __str__(self):  # Python 3: def __str__(self):
        return self.voter_id





