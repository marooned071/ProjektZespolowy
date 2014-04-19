from django.conf.urls import patterns, url

from qrpolls import views

urlpatterns = patterns('',
    # ex: /polls/
    url(r'^$', views.index, name='index'),
    # ex: /polls/5/
    url(r'^create', views.create ,name='create'),
    url(r'^meeting/(?P<hash_id>\w+)/$', views.meeting, name='meeting'),
    url(r'^meeting/(?P<hash_id>\w+)/newQuestion', views.newQuestion, name='newQuestion'),
    url(r'^meeting/(?P<hash_id>\w+)/api/(?P<question>\w+)/$$', views.api, name='api'),
    url(r'^meeting/(?P<hash_id>\w+)/api/vote/(?P<choice_id>\w+)/$$', views.api_vote, name='api_vote'),

)
