from django.contrib import admin
from qrpolls.models import QRPoll, Question, Choice, Vote


class QRPollAdmin(admin.ModelAdmin):
    fields = ['hash_id', 'time', 'room', 'subject']


admin.site.register(QRPoll, QRPollAdmin)
admin.site.register(Question)
admin.site.register(Choice)
admin.site.register(Vote)
