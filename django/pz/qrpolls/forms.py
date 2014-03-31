from django import forms
from django.contrib.admin import widgets


class NewMeetingForm(forms.Form):
    start_date = forms.CharField(max_length=200)
    room = forms.CharField(max_length=200)
    subject = forms.CharField(max_length=200)


