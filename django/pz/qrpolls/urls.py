from django.conf.urls import patterns, url

from qrpolls import views

urlpatterns = patterns('',
    # ex: /polls/
    url(r'^$', views.index, name='index'),
    # ex: /polls/5/
    url(r'^create', views.create ,name='create'),
    url(r'^meeting/(?P<hash_id>\w+)/$', views.meeting, name='meeting'),
    url(r'^meeting/(?P<hash_id>\w+)/api/(?P<question>\w+)/$$', views.api, name='api'),

)
