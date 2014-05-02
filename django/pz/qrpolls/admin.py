from django.contrib import admin
from qrpolls.models import QRPoll, Question, Choice, Vote


class QRPollAdmin(admin.ModelAdmin):
    fields = ['hash_id', 'start_date', 'room', 'subject']

# class QuestionAdmin(admin.ModelAdmin):
#     fields = ['question_text']

# class ChoiceAdmin(admin.ModelAdmin):
#     fields = ['choice_text', 'votes']






admin.site.register(QRPoll, QRPollAdmin)
admin.site.register(Question)
admin.site.register(Choice)
admin.site.register(Vote)
