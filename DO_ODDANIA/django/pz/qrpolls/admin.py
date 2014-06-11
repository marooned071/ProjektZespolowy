'''
 Copyright 2014 Byliniak, Sliwka, Gambus, Celmer

 Licensed under the Surveys License, (the "License");
 you may not use this file except in compliance with the License.

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
'''

from django.contrib import admin
from qrpolls.models import QRPoll, Question, Choice, Vote


class QRPollAdmin(admin.ModelAdmin):
    fields = ['hash_id', 'time', 'room', 'subject']


admin.site.register(QRPoll, QRPollAdmin)
admin.site.register(Question)
admin.site.register(Choice)
admin.site.register(Vote)
