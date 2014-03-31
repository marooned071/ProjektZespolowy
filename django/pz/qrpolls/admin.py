from django.contrib import admin
from qrpolls.models import QRPoll


class QRPollAdmin(admin.ModelAdmin):
    fields = ['hash_id', 'start_date', 'room', 'subject']

admin.site.register(QRPoll, QRPollAdmin)