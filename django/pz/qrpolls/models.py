from django.db import models

class QRPoll(models.Model):
    hash_id = models.CharField(max_length=200)
    start_date = models.CharField(max_length=200)
    room = models.CharField(max_length=200)
    subject = models.CharField(max_length=200)

    def __str__(self):  # Python 3: def __str__(self):
    	return self.hash_id
