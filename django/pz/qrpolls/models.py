from django.db import models

class QRPoll(models.Model):
    hash_id = models.CharField(max_length=200)
    start_date = models.CharField(max_length=200)
    room = models.CharField(max_length=200)
    subject = models.CharField(max_length=200)
    version = models.IntegerField(default=1)

    def __str__(self):  # Python 3: def __str__(self):
        return self.hash_id

class Question(models.Model):
    poll = models.ForeignKey(QRPoll)
    question_text = models.CharField(max_length=200)

    def __str__(self):  # Python 3: def __str__(self):
        return self.question_text


class Choice(models.Model):
    question = models.ForeignKey(Question)
    choice_text = models.CharField(max_length=200)
    votes = models.IntegerField(default=0)
    def __str__(self):  # Python 3: def __str__(self):
        return self.choice_text


