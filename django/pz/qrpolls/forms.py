'''
 Copyright 2014 Byliniak, Sliwka, Gambus, Celmer

 Licensed under the Surveys License, (the "License");
 you may not use this file except in compliance with the License.

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
'''

from django import forms
from django.contrib.admin import widgets


class NewMeetingForm(forms.Form):
    time = forms.CharField(max_length=200,widget=forms.TextInput(attrs={'autocomplete':'off'}))
    room = forms.CharField(max_length=200)
    subject = forms.CharField(max_length=200)


