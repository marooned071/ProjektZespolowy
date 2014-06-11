from django.conf.urls import patterns, include, url

from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'pz.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),
    #url(r'^qrpolls/', include('qrpolls.urls')),
    url(r'^qrpolls/', include('qrpolls.urls', namespace="qrpolls")),

    url(r'^admin/', include(admin.site.urls)),
)
