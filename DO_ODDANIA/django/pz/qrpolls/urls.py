'''
 Copyright 2014 Byliniak, Sliwka, Gambus, Celmer

 Licensed under the Surveys License, (the "License");
 you may not use this file except in compliance with the License.

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
'''

from django.conf.urls import patterns, url

from qrpolls import views

urlpatterns = patterns('',
    # ex: /polls/
    url(r'^$', views.index, name='index'),
    # ex: /polls/5/
    url(r'^create', views.create ,name='create'),
    url(r'^meeting/(?P<hash_id>\w+)/$', views.meeting, name='meeting'),
    url(r'^meetingCharts/(?P<hash_id>\w+)/$', views.meetingCharts, name='meetingCharts'),
    url(r'^meeting/(?P<hash_id>\w+)/newQuestion', views.newQuestion, name='newQuestion'),
    url(r'^meeting/(?P<hash_id>\w+)/api/(?P<question>\w+)/$$', views.api, name='api'),
    url(r'^meeting/(?P<hash_id>\w+)/api/vote/(?P<voter_id>\w+)/(?P<choice_ids>[A-Za-z0-9_,]+)/$$', views.api_vote_voter, name='api_vote_voter'),
)
